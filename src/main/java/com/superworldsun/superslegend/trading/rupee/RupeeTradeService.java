package com.superworldsun.superslegend.trading.rupee;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Server-authoritative validation, stock, and transaction logic for rupee trades. */
public final class RupeeTradeService {
    public static final int MAX_PURCHASE_QUANTITY = 64;
    private static final double MAX_TRADING_DISTANCE_SQUARED = 64.0D;
    private static final String STOCK_TAG = SupersLegendMain.MOD_ID + ":RupeeTradeStock";
    private static final String STOCK_DAY_TAG = "Day";
    private static final String STOCK_USES_TAG = "Uses";
    private static final String LEGACY_OFFERS_MIGRATION_TAG = SupersLegendMain.MOD_ID
            + ":RupeeTradeOffersMigration";
    private static final int LEGACY_OFFERS_MIGRATION_VERSION = 1;

    private RupeeTradeService() {
    }

    public enum PurchaseStatus {
        SUCCESS,
        INVALID_TRADER,
        INVALID_TRADE,
        INVALID_QUANTITY,
        OUT_OF_STOCK,
        INSUFFICIENT_RUPEES,
        MISSING_INGREDIENTS,
        ALREADY_COMMITTED;

        public boolean successful() {
            return this == SUCCESS;
        }
    }

    /**
     * Performs validation without mutating the wallet, inventory, stock, or XP.
     * Commit revalidates from authoritative server state to prevent stale-menu
     * and replay races.
     */
    public static PurchasePreparation preparePurchase(ServerPlayer player, AbstractVillager trader,
                                                      RupeeTrade requestedTrade, int quantity) {
        PlanResult result = createPlan(player, trader, requestedTrade, quantity);
        if (!result.status.successful()) {
            return PurchasePreparation.failed(result.status);
        }
        return PurchasePreparation.ready(new PreparedPurchase(player, trader, result.plan.trade, quantity));
    }

    /** One-call convenience used by packet handlers. */
    public static PurchaseStatus purchase(ServerPlayer player, AbstractVillager trader,
                                          RupeeTrade trade, int quantity) {
        PurchasePreparation preparation = preparePurchase(player, trader, trade, quantity);
        return preparation.preparedPurchase()
                .map(PreparedPurchase::commit)
                .orElse(preparation.status());
    }

    public static PurchaseStatus purchase(ServerPlayer player, AbstractVillager trader,
                                          ResourceLocation tradeId, int quantity) {
        Optional<RupeeTrade> trade = RupeeTradeRegistry.findTrade(trader, tradeId);
        return trade.map(value -> purchase(player, trader, value, quantity))
                .orElse(PurchaseStatus.INVALID_TRADE);
    }

    public static boolean tryPurchase(ServerPlayer player, AbstractVillager trader,
                                      RupeeTrade trade, int quantity) {
        return purchase(player, trader, trade, quantity).successful();
    }

    public static int getRemainingStock(AbstractVillager trader, RupeeTrade trade) {
        Objects.requireNonNull(trader, "trader");
        Objects.requireNonNull(trade, "trade");
        int used = getUsesToday(trader, trade.id());
        return Math.max(0, trade.maxUses() - used);
    }

    public static int getRemainingStock(AbstractVillager trader, ResourceLocation tradeId) {
        return RupeeTradeRegistry.findTrade(trader, tradeId)
                .map(trade -> getRemainingStock(trader, trade))
                .orElse(0);
    }

    public static int getUsesToday(AbstractVillager trader, ResourceLocation tradeId) {
        CompoundTag uses = getCurrentUsesTag(trader);
        return Math.max(0, uses.getInt(tradeId.toString()));
    }

    /** Explicit administrative/testing reset; normal stock resets on day change. */
    public static void resetDailyStock(AbstractVillager trader) {
        CompoundTag root = getCurrentStockTag(trader);
        root.put(STOCK_USES_TAG, new CompoundTag());
    }

    public static boolean hasIngredients(ServerPlayer player, RupeeTrade trade, int quantity) {
        if (quantity <= 0 || quantity > MAX_PURCHASE_QUANTITY) {
            return false;
        }
        return planIngredientRemoval(player.getInventory(), trade, quantity) != null;
    }

    /**
     * Removes offers created by the old VillagerTradeEvents hook from saved
     * entities. New entities no longer receive those offers, but MerchantOffers
     * are persisted in entity NBT and therefore need this one-time cleanup.
     *
     * @return number of legacy offers removed
     */
    public static int removeLegacyOffers(AbstractVillager trader) {
        CompoundTag persistentData = trader.getPersistentData();
        if (persistentData.getInt(LEGACY_OFFERS_MIGRATION_TAG) >= LEGACY_OFFERS_MIGRATION_VERSION) {
            return 0;
        }

        MerchantOffers offers = trader.getOffers();
        int oldSize = offers.size();
        offers.removeIf(RupeeTradeService::isLegacyOffer);
        persistentData.putInt(LEGACY_OFFERS_MIGRATION_TAG, LEGACY_OFFERS_MIGRATION_VERSION);
        return oldSize - offers.size();
    }

    /**
     * Completes each purchased unit through vanilla's trade reward path so
     * level-up scheduling, XP orbs, criteria, Forge trade events, and player
     * statistics remain compatible with ordinary trading. The synthetic offer
     * is used only as the vanilla callback payload; wallet payment has already
     * been validated and committed by this service.
     */
    public static void awardVillagerXp(ServerPlayer player, AbstractVillager trader, RupeeTrade trade,
                                       int quantity, ItemStack completedResult) {
        if (quantity <= 0 || quantity > MAX_PURCHASE_QUANTITY) {
            return;
        }
        Objects.requireNonNull(completedResult, "completedResult");

        List<ItemStack> completedResults = new ArrayList<>(quantity);
        for (int completed = 0; completed < quantity; completed++) {
            completedResults.add(completedResult);
        }
        awardVillagerXp(player, trader, trade, completedResults);
    }

    private static void awardVillagerXp(ServerPlayer player, AbstractVillager trader, RupeeTrade trade,
                                        List<ItemStack> completedResults) {
        int quantity = completedResults.size();
        if (quantity <= 0 || quantity > MAX_PURCHASE_QUANTITY) {
            return;
        }

        for (int completed = 0; completed < quantity; completed++) {
            int earnedXp = trade.villagerXp();
            if (trader instanceof Villager villager) {
                long currentXp = Math.max(0L, villager.getVillagerXp());
                long room = Math.max(0L, Integer.MAX_VALUE - currentXp);
                earnedXp = (int) Math.min(earnedXp, room);
            }

            MerchantOffer completedOffer = new MerchantOffer(new ItemStack(Items.EMERALD),
                    completedResults.get(completed).copy(), 1, earnedXp, 0.0F);
            trader.notifyTrade(completedOffer);
        }
        player.awardStat(Stats.TRADED_WITH_VILLAGER, quantity);
    }

    private static PurchaseStatus commit(PreparedPurchase prepared) {
        PlanResult result = createPlan(prepared.player, prepared.trader, prepared.trade, prepared.quantity);
        if (!result.status.successful()) {
            return result.status;
        }

        TransactionPlan plan = result.plan;
        if (!RupeeWalletUtil.trySpendExact(prepared.player, plan.totalRupeeCost)) {
            return PurchaseStatus.INSUFFICIENT_RUPEES;
        }

        applyIngredientRemoval(prepared.player.getInventory(), plan.inputPlan);
        addUses(prepared.trader, plan.trade.id(), prepared.quantity);

        for (ItemStack output : plan.outputs) {
            ItemStack remainder = output.copy();
            prepared.player.getInventory().add(remainder);
            if (!remainder.isEmpty()) {
                prepared.player.drop(remainder, false);
            }
        }
        prepared.player.getInventory().setChanged();
        awardVillagerXp(prepared.player, prepared.trader, plan.trade, plan.outputs);
        return PurchaseStatus.SUCCESS;
    }

    private static PlanResult createPlan(ServerPlayer player, AbstractVillager trader,
                                         RupeeTrade requestedTrade, int quantity) {
        if (!validTradingContext(player, trader)) {
            return PlanResult.failed(PurchaseStatus.INVALID_TRADER);
        }
        if (requestedTrade == null) {
            return PlanResult.failed(PurchaseStatus.INVALID_TRADE);
        }
        if (quantity <= 0 || quantity > MAX_PURCHASE_QUANTITY) {
            return PlanResult.failed(PurchaseStatus.INVALID_QUANTITY);
        }

        Optional<RupeeTrade> canonical = RupeeTradeRegistry.findTrade(trader, requestedTrade.id());
        if (canonical.isEmpty()) {
            return PlanResult.failed(PurchaseStatus.INVALID_TRADE);
        }
        RupeeTrade trade = canonical.get();
        if (getRemainingStock(trader, trade) < quantity) {
            return PlanResult.failed(PurchaseStatus.OUT_OF_STOCK);
        }

        int totalRupeeCost;
        try {
            totalRupeeCost = Math.multiplyExact(trade.rupeeCost(), quantity);
        } catch (ArithmeticException overflow) {
            return PlanResult.failed(PurchaseStatus.INVALID_QUANTITY);
        }
        if (!RupeeWalletUtil.canAfford(player, totalRupeeCost)) {
            return PlanResult.failed(PurchaseStatus.INSUFFICIENT_RUPEES);
        }

        InputPlan inputPlan = planIngredientRemoval(player.getInventory(), trade, quantity);
        if (inputPlan == null) {
            return PlanResult.failed(PurchaseStatus.MISSING_INGREDIENTS);
        }

        List<ItemStack> outputs = new ArrayList<>(quantity);
        for (int unit = 0; unit < quantity; unit++) {
            outputs.add(trade.createResult(inputPlan.copiedInputs.get(unit)));
        }
        return PlanResult.ready(new TransactionPlan(trade, totalRupeeCost, inputPlan, outputs));
    }

    private static boolean validTradingContext(ServerPlayer player, AbstractVillager trader) {
        if (player == null || trader == null) {
            return false;
        }
        MinecraftServer server = player.getServer();
        if (server == null || !server.isSameThread() || !player.isAlive() || player.isSpectator()
                || !trader.isAlive() || trader.isBaby() || player.level() != trader.level()
                || player.distanceToSqr(trader) > MAX_TRADING_DISTANCE_SQUARED) {
            return false;
        }
        // The menu owns this reservation. Requiring the exact trading player
        // prevents forged packets from purchasing from any merely-nearby NPC.
        return trader.getTradingPlayer() == player;
    }

    private static InputPlan planIngredientRemoval(Inventory inventory, RupeeTrade trade, int quantity) {
        int slotCount = inventory.items.size();
        int[] available = new int[slotCount];
        int[] removals = new int[slotCount];
        for (int slot = 0; slot < slotCount; slot++) {
            available[slot] = inventory.items.get(slot).getCount();
        }

        List<ItemStack> ingredients = trade.ingredients();
        List<ItemStack> copiedInputs = new ArrayList<>(quantity);
        for (int unit = 0; unit < quantity; unit++) {
            ItemStack copiedInput = ItemStack.EMPTY;
            for (int ingredientIndex = 0; ingredientIndex < ingredients.size(); ingredientIndex++) {
                ItemStack ingredient = ingredients.get(ingredientIndex);
                int needed = ingredient.getCount();

                for (int slot = 0; slot < slotCount && needed > 0; slot++) {
                    ItemStack candidate = inventory.items.get(slot);
                    if (available[slot] <= 0 || !matchesIngredient(candidate, ingredient)) {
                        continue;
                    }
                    int taken = Math.min(needed, available[slot]);
                    if (ingredientIndex == trade.copyNbtFromIngredient() && copiedInput.isEmpty()) {
                        copiedInput = candidate.copyWithCount(1);
                    }
                    available[slot] -= taken;
                    removals[slot] += taken;
                    needed -= taken;
                }

                if (needed > 0) {
                    return null;
                }
            }
            copiedInputs.add(copiedInput);
        }
        return new InputPlan(removals, copiedInputs);
    }

    private static boolean matchesIngredient(ItemStack candidate, ItemStack required) {
        if (candidate.isEmpty() || !ItemStack.isSameItem(candidate, required)) {
            return false;
        }
        return !required.hasTag() || candidate.hasTag()
                && NbtUtils.compareNbt(required.getTag(), candidate.getTag(), false);
    }

    private static void applyIngredientRemoval(Inventory inventory, InputPlan plan) {
        for (int slot = 0; slot < plan.removals.length; slot++) {
            int count = plan.removals[slot];
            if (count <= 0) {
                continue;
            }
            ItemStack stack = inventory.items.get(slot);
            stack.shrink(count);
            if (stack.isEmpty()) {
                inventory.items.set(slot, ItemStack.EMPTY);
            }
        }
        inventory.setChanged();
    }

    private static CompoundTag getCurrentStockTag(AbstractVillager trader) {
        CompoundTag persistentData = trader.getPersistentData();
        CompoundTag stock;
        if (persistentData.contains(STOCK_TAG, Tag.TAG_COMPOUND)) {
            stock = persistentData.getCompound(STOCK_TAG);
        } else {
            stock = new CompoundTag();
            persistentData.put(STOCK_TAG, stock);
        }

        long currentDay = Math.floorDiv(trader.level().getDayTime(), 24000L);
        if (!stock.contains(STOCK_DAY_TAG, Tag.TAG_LONG) || stock.getLong(STOCK_DAY_TAG) != currentDay) {
            stock.putLong(STOCK_DAY_TAG, currentDay);
            stock.put(STOCK_USES_TAG, new CompoundTag());
        } else if (!stock.contains(STOCK_USES_TAG, Tag.TAG_COMPOUND)) {
            stock.put(STOCK_USES_TAG, new CompoundTag());
        }
        return stock;
    }

    private static CompoundTag getCurrentUsesTag(AbstractVillager trader) {
        return getCurrentStockTag(trader).getCompound(STOCK_USES_TAG);
    }

    private static void addUses(AbstractVillager trader, ResourceLocation tradeId, int amount) {
        CompoundTag uses = getCurrentUsesTag(trader);
        String key = tradeId.toString();
        long updatedUses = (long) Math.max(0, uses.getInt(key)) + Math.max(0, amount);
        uses.putInt(key, (int) Math.min(Integer.MAX_VALUE, updatedUses));
    }

    private static boolean isLegacyOffer(MerchantOffer offer) {
        // Match the old event registrations exactly. A broad "rupee input + mod
        // output" check would silently delete legitimate offers added later by
        // this mod or another mod whenever a saved trader is opened.
        return legacyOffer(offer, ItemInit.RED_RUPEE.get(), 1, ItemInit.UNAPPRAISED_RING.get(), 1,
                ItemInit.APPRAISED_RING_BOX.get(), 99, 5, 0.0F)
                || legacyOffer(offer, ItemInit.BLUE_RUPEE.get(), 3, ItemInit.BOMB.get(), 20, 8, 1.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 1, ItemInit.WATER_BOMB.get(), 10, 10, 1.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 2, ItemInit.DEKU_SHIELD.get(), 1, 20, 0.0F)
                || legacyOffer(offer, ItemInit.SILVER_RUPEE.get(), 1, ItemInit.RING_BOX_L1.get(), 5, 10, 1.0F)
                || legacyOffer(offer, ItemInit.BLUE_RUPEE.get(), 30, ItemInit.RING_BOX_L1.get(), 1,
                ItemInit.RING_BOX_L2.get(), 5, 30, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 1, ItemInit.RING_BOX_L2.get(), 1,
                ItemInit.RING_BOX_L3.get(), 5, 70, 0.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 3, ItemInit.ARMOR_RING_L3.get(), 1,
                ItemInit.BLUE_RING.get(), 5, 70, 0.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 3, ItemInit.POWER_RING_L3.get(), 1,
                ItemInit.RED_RING.get(), 5, 70, 0.0F)

                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 2, ItemInit.DEKU_SHIELD.get(), 1, 9, 1.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 1, ItemInit.SILVER_RUPEE.get(), 2,
                ItemInit.SACRED_SHIELD.get(), 1, 30, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 2, ItemInit.MAGIC_ARMOR_SET.get(), 1, 200, 0.0F)

                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 5, ItemInit.HEART_PIECE.get(), 1, 50, 0.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 3, ItemInit.FISHING_ROD.get(), 1, 50, 0.0F)
                || legacyOffer(offer, ItemInit.SILVER_RUPEE.get(), 1, ItemInit.FAIRY_BOW.get(), 1, 50, 0.0F)

                || legacyOffer(offer, ItemInit.MASTER_SWORD.get(), 1, ItemInit.MASTER_ORE.get(), 2,
                ItemInit.MASTER_SWORD_V2.get(), 1, 70, 0.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 2, ItemInit.KOKIRI_SWORD.get(), 1, 10, 0.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 6, ItemInit.BOOMERANG.get(), 1,
                ItemInit.MAGIC_BOOMERANG.get(), 1, 20, 0.0F)
                || legacyOffer(offer, ItemInit.SILVER_RUPEE.get(), 2, ItemInit.KOKIRI_SWORD.get(), 1,
                ItemInit.RAZOR_SWORD.get(), 6, 30, 0.0F)
                || legacyOffer(offer, ItemInit.RAZOR_SWORD.get(), 1, Items.GOLD_BLOCK, 2,
                ItemInit.GILDED_SWORD.get(), 1, 70, 0.0F)
                || legacyOffer(offer, ItemInit.MASTER_SWORD_V2.get(), 1, ItemInit.MASTER_ORE.get(), 6,
                ItemInit.TRUE_MASTER_SWORD.get(), 1, 100, 0.0F)

                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 1, ItemInit.BAIT_BAG.get(), 1, 8, 1.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 1, ItemInit.SPOILS_BAG.get(), 1, 8, 1.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 1, ItemInit.DELIVERY_BAG.get(), 1, 8, 1.0F)
                || legacyOffer(offer, ItemInit.SILVER_RUPEE.get(), 1, ItemInit.QUIVER.get(), 1, 8, 1.0F)
                || legacyOffer(offer, ItemInit.BLUE_RUPEE.get(), 30, ItemInit.QUIVER.get(), 1,
                ItemInit.BIG_QUIVER.get(), 5, 30, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 1, ItemInit.BIG_QUIVER.get(), 1,
                ItemInit.BIGGEST_QUIVER.get(), 5, 30, 0.0F)
                || legacyOffer(offer, ItemInit.SILVER_RUPEE.get(), 1, ItemInit.BULLET_BAG.get(), 1, 8, 1.0F)
                || legacyOffer(offer, ItemInit.BLUE_RUPEE.get(), 30, ItemInit.BULLET_BAG.get(), 1,
                ItemInit.BIG_BULLET_BAG.get(), 5, 30, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 1, ItemInit.BIG_BULLET_BAG.get(), 1,
                ItemInit.BIGGEST_BULLET_BAG.get(), 5, 30, 0.0F)
                || legacyOffer(offer, ItemInit.SILVER_RUPEE.get(), 1, ItemInit.BOMB_BAG.get(), 1, 8, 1.0F)
                || legacyOffer(offer, ItemInit.BLUE_RUPEE.get(), 30, ItemInit.BOMB_BAG.get(), 1,
                ItemInit.BIG_BOMB_BAG.get(), 5, 30, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 1, ItemInit.BIG_BOMB_BAG.get(), 1,
                ItemInit.BIGGEST_BOMB_BAG.get(), 5, 30, 0.0F)

                || legacyOffer(offer, ItemInit.RUPEE.get(), 1, ItemInit.DEKU_SEEDS.get(), 64, 5, 0.0F)
                || legacyOffer(offer, ItemInit.RED_RUPEE.get(), 3, ItemInit.APPRAISED_RING_BOX.get(), 4, 30, 0.0F)
                || legacyOffer(offer, ItemInit.SILVER_RUPEE.get(), 1, ItemInit.HEART_PIECE.get(), 1, 200, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 2, ItemInit.SILVER_RUPEE.get(), 1,
                ItemInit.GORONS_BRACELET.get(), 2, 200, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 5, ItemInit.SILVER_GAUNTLETS.get(), 1, 200, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 15, ItemInit.GOLDEN_GAUNTLETS.get(), 1, 600, 0.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 4, ItemInit.MAGIC_FIRE_ARROW.get(), 1, 400, 5.0F)
                || legacyOffer(offer, ItemInit.GOLD_RUPEE.get(), 4, ItemInit.MAGIC_ICE_ARROW.get(), 1, 400, 5.0F);
    }

    private static boolean legacyOffer(MerchantOffer offer, Item costAItem, int costACount,
                                       Item resultItem, int maxUses, int xp, float priceMultiplier) {
        return legacyOffer(offer, costAItem, costACount, null, 0,
                resultItem, maxUses, xp, priceMultiplier);
    }

    private static boolean legacyOffer(MerchantOffer offer, Item costAItem, int costACount,
                                       Item costBItem, int costBCount, Item resultItem,
                                       int maxUses, int xp, float priceMultiplier) {
        return legacyStackMatches(offer.getBaseCostA(), costAItem, costACount)
                && (costBItem == null
                ? offer.getCostB().isEmpty()
                : legacyStackMatches(offer.getCostB(), costBItem, costBCount))
                && legacyStackMatches(offer.getResult(), resultItem, 1)
                && offer.getMaxUses() == maxUses
                && offer.getXp() == xp
                && Float.compare(offer.getPriceMultiplier(), priceMultiplier) == 0;
    }

    private static boolean legacyStackMatches(ItemStack stack, Item item, int count) {
        // Damageable stacks commonly carry a generated Damage:0 tag, so NBT
        // cannot be part of the migration signature. The complete item/count,
        // max-uses, XP, and multiplier tuple above remains exact.
        return stack.is(item) && stack.getCount() == count;
    }

    public static final class PurchasePreparation {
        private final PurchaseStatus status;
        private final PreparedPurchase preparedPurchase;

        private PurchasePreparation(PurchaseStatus status, PreparedPurchase preparedPurchase) {
            this.status = status;
            this.preparedPurchase = preparedPurchase;
        }

        private static PurchasePreparation failed(PurchaseStatus status) {
            return new PurchasePreparation(status, null);
        }

        private static PurchasePreparation ready(PreparedPurchase purchase) {
            return new PurchasePreparation(PurchaseStatus.SUCCESS, purchase);
        }

        public PurchaseStatus status() {
            return status;
        }

        public boolean ready() {
            return preparedPurchase != null;
        }

        public Optional<PreparedPurchase> preparedPurchase() {
            return Optional.ofNullable(preparedPurchase);
        }
    }

    public static final class PreparedPurchase {
        private final ServerPlayer player;
        private final AbstractVillager trader;
        private final RupeeTrade trade;
        private final int quantity;
        private boolean committed;

        private PreparedPurchase(ServerPlayer player, AbstractVillager trader, RupeeTrade trade, int quantity) {
            this.player = player;
            this.trader = trader;
            this.trade = trade;
            this.quantity = quantity;
        }

        public ServerPlayer player() {
            return player;
        }

        public AbstractVillager trader() {
            return trader;
        }

        public RupeeTrade trade() {
            return trade;
        }

        public int quantity() {
            return quantity;
        }

        public synchronized PurchaseStatus commit() {
            if (committed) {
                return PurchaseStatus.ALREADY_COMMITTED;
            }
            committed = true;
            return RupeeTradeService.commit(this);
        }
    }

    private static final class InputPlan {
        private final int[] removals;
        private final List<ItemStack> copiedInputs;

        private InputPlan(int[] removals, List<ItemStack> copiedInputs) {
            this.removals = Arrays.copyOf(removals, removals.length);
            this.copiedInputs = List.copyOf(copiedInputs);
        }
    }

    private static final class TransactionPlan {
        private final RupeeTrade trade;
        private final int totalRupeeCost;
        private final InputPlan inputPlan;
        private final List<ItemStack> outputs;

        private TransactionPlan(RupeeTrade trade, int totalRupeeCost, InputPlan inputPlan, List<ItemStack> outputs) {
            this.trade = trade;
            this.totalRupeeCost = totalRupeeCost;
            this.inputPlan = inputPlan;
            this.outputs = outputs;
        }
    }

    private static final class PlanResult {
        private final PurchaseStatus status;
        private final TransactionPlan plan;

        private PlanResult(PurchaseStatus status, TransactionPlan plan) {
            this.status = status;
            this.plan = plan;
        }

        private static PlanResult failed(PurchaseStatus status) {
            return new PlanResult(status, null);
        }

        private static PlanResult ready(TransactionPlan plan) {
            return new PlanResult(PurchaseStatus.SUCCESS, plan);
        }
    }
}
