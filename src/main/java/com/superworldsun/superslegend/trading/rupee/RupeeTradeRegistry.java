package com.superworldsun.superslegend.trading.rupee;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Central registry for wallet-backed trades.
 *
 * <p>Trades can target either a villager profession or an entity type. Entity
 * type registration is useful for wandering traders and future custom trader
 * mobs. Registration order does not affect display order; each trade carries
 * an explicit stable order value.</p>
 */
public final class RupeeTradeRegistry {
    /** A wandering trader permanently chooses this many offers from its pool. */
    private static final int WANDERING_TRADER_OFFER_COUNT = 3;
    private static final String WANDERING_TRADES_TAG =
            SupersLegendMain.MOD_ID + ":SelectedRupeeTrades";
    private static final Object LOCK = new Object();
    private static final Comparator<RupeeTrade> DISPLAY_ORDER = Comparator
            .comparingInt(RupeeTrade::order)
            .thenComparing(trade -> trade.id().toString());

    private static final Map<ResourceLocation, RupeeTrade> TRADES_BY_ID = new LinkedHashMap<>();
    private static final Map<VillagerProfession, List<RupeeTrade>> PROFESSION_TRADES = new HashMap<>();
    private static final Map<EntityType<?>, List<RupeeTrade>> ENTITY_TYPE_TRADES = new HashMap<>();
    private static boolean builtInsLoaded;

    private RupeeTradeRegistry() {
    }

    /** Registers a trade for all villagers with the supplied profession. */
    public static RupeeTrade register(VillagerProfession profession, RupeeTrade trade) {
        Objects.requireNonNull(profession, "profession");
        Objects.requireNonNull(trade, "trade");
        ensureBuiltIns();
        synchronized (LOCK) {
            registerInternal(PROFESSION_TRADES, profession, trade);
            return trade;
        }
    }

    /** Registers a trade for every supported trader of the supplied entity type. */
    public static RupeeTrade register(EntityType<?> entityType, RupeeTrade trade) {
        Objects.requireNonNull(entityType, "entityType");
        Objects.requireNonNull(trade, "trade");
        ensureBuiltIns();
        synchronized (LOCK) {
            registerInternal(ENTITY_TYPE_TRADES, entityType, trade);
            return trade;
        }
    }

    public static RupeeTrade registerProfessionTrade(VillagerProfession profession, RupeeTrade trade) {
        return register(profession, trade);
    }

    public static RupeeTrade registerEntityTypeTrade(EntityType<?> entityType, RupeeTrade trade) {
        return register(entityType, trade);
    }

    /** Returns all currently unlocked trades for this trader in stable display order. */
    public static List<RupeeTrade> getTrades(AbstractVillager trader) {
        Objects.requireNonNull(trader, "trader");
        ensureBuiltIns();

        synchronized (LOCK) {
            Map<ResourceLocation, RupeeTrade> matches = new LinkedHashMap<>();
            addMatches(matches, ENTITY_TYPE_TRADES.get(trader.getType()));
            if (trader instanceof Villager villager) {
                addMatches(matches, PROFESSION_TRADES.get(villager.getVillagerData().getProfession()));
            }

            int level = traderLevel(trader);
            List<RupeeTrade> unlocked = new ArrayList<>();
            List<RupeeTrade> dailyCandidates = new ArrayList<>();
            for (RupeeTrade trade : matches.values()) {
                if (trade.isUnlockedAt(level)) {
                    if (trade.type() == RupeeTrade.Type.DAILY_DEAL) {
                        dailyCandidates.add(trade);
                    } else {
                        unlocked.add(trade);
                    }
                }
            }
            unlocked.sort(DISPLAY_ORDER);
            if (trader instanceof WanderingTrader wanderingTrader) {
                unlocked = selectWanderingTraderOffers(wanderingTrader, unlocked);
            }
            selectDailyDeal(trader, dailyCandidates).ifPresent(unlocked::add);
            return List.copyOf(unlocked);
        }
    }

    /** Returns the one stable offer chosen for this trader during the current Minecraft day. */
    private static Optional<RupeeTrade> selectDailyDeal(AbstractVillager trader,
                                                         List<RupeeTrade> candidates) {
        if (candidates.isEmpty()) {
            return Optional.empty();
        }
        candidates.sort(DISPLAY_ORDER);
        long day = Math.floorDiv(trader.level().getDayTime(), 24000L);
        long seed = trader.getUUID().getMostSignificantBits()
                ^ Long.rotateLeft(trader.getUUID().getLeastSignificantBits(), 23)
                ^ day * 0x9E3779B97F4A7C15L;
        return Optional.of(candidates.get(RandomSource.create(seed).nextInt(candidates.size())));
    }

    /** Resolves the exact server-selected trade IDs sent when a menu opens. */
    public static List<RupeeTrade> getTradesById(List<ResourceLocation> tradeIds) {
        Objects.requireNonNull(tradeIds, "tradeIds");
        ensureBuiltIns();
        synchronized (LOCK) {
            List<RupeeTrade> resolved = new ArrayList<>(tradeIds.size());
            Set<ResourceLocation> seen = new HashSet<>();
            for (ResourceLocation id : tradeIds) {
                RupeeTrade trade = TRADES_BY_ID.get(id);
                if (trade != null && seen.add(id)) {
                    resolved.add(trade);
                }
            }
            return List.copyOf(resolved);
        }
    }

    /** Resolves an ID only when the trade is registered and unlocked for this trader. */
    public static Optional<RupeeTrade> findTrade(AbstractVillager trader, ResourceLocation id) {
        Objects.requireNonNull(id, "id");
        for (RupeeTrade trade : getTrades(trader)) {
            if (trade.id().equals(id)) {
                return Optional.of(trade);
            }
        }
        return Optional.empty();
    }

    public static Optional<RupeeTrade> getTrade(AbstractVillager trader, ResourceLocation id) {
        return findTrade(trader, id);
    }

    public static Optional<RupeeTrade> getTrade(ResourceLocation id) {
        Objects.requireNonNull(id, "id");
        ensureBuiltIns();
        synchronized (LOCK) {
            return Optional.ofNullable(TRADES_BY_ID.get(id));
        }
    }

    public static List<RupeeTrade> getRegisteredTrades() {
        ensureBuiltIns();
        synchronized (LOCK) {
            List<RupeeTrade> trades = new ArrayList<>(TRADES_BY_ID.values());
            trades.sort(DISPLAY_ORDER);
            return List.copyOf(trades);
        }
    }

    public static int traderLevel(AbstractVillager trader) {
        return trader instanceof Villager villager ? villager.getVillagerData().getLevel() : 1;
    }

    private static void addMatches(Map<ResourceLocation, RupeeTrade> destination, List<RupeeTrade> source) {
        if (source == null) {
            return;
        }
        for (RupeeTrade trade : source) {
            destination.putIfAbsent(trade.id(), trade);
        }
    }

    /**
     * Chooses a stable random subset once per wandering trader. The IDs live in
     * Forge's persistent entity data, so saving/reloading cannot reroll stock.
     */
    private static List<RupeeTrade> selectWanderingTraderOffers(WanderingTrader trader,
                                                                 List<RupeeTrade> candidates) {
        int desiredCount = Math.min(WANDERING_TRADER_OFFER_COUNT, candidates.size());
        if (desiredCount == 0) {
            return List.of();
        }

        Map<ResourceLocation, RupeeTrade> candidatesById = new LinkedHashMap<>();
        for (RupeeTrade candidate : candidates) {
            candidatesById.put(candidate.id(), candidate);
        }

        CompoundTag persistentData = trader.getPersistentData();
        ListTag savedIds = persistentData.getList(WANDERING_TRADES_TAG, Tag.TAG_STRING);
        Set<ResourceLocation> selectedIds = new HashSet<>();
        for (Tag savedTag : savedIds) {
            ResourceLocation id = ResourceLocation.tryParse(savedTag.getAsString());
            if (id != null && candidatesById.containsKey(id) && selectedIds.size() < desiredCount) {
                selectedIds.add(id);
            }
        }

        boolean selectionChanged = !persistentData.contains(WANDERING_TRADES_TAG, Tag.TAG_LIST)
                || selectedIds.size() != savedIds.size();
        if (selectedIds.size() < desiredCount) {
            List<RupeeTrade> remaining = new ArrayList<>();
            for (RupeeTrade candidate : candidates) {
                if (!selectedIds.contains(candidate.id())) {
                    remaining.add(candidate);
                }
            }
            for (int index = remaining.size() - 1; index > 0; index--) {
                Collections.swap(remaining, index, trader.getRandom().nextInt(index + 1));
            }
            for (RupeeTrade candidate : remaining) {
                if (selectedIds.size() >= desiredCount) {
                    break;
                }
                selectedIds.add(candidate.id());
                selectionChanged = true;
            }
        }

        if (selectionChanged) {
            ListTag storedSelection = new ListTag();
            for (ResourceLocation selectedId : selectedIds) {
                storedSelection.add(StringTag.valueOf(selectedId.toString()));
            }
            persistentData.put(WANDERING_TRADES_TAG, storedSelection);
        }

        List<RupeeTrade> selectedTrades = new ArrayList<>(desiredCount);
        for (RupeeTrade candidate : candidates) {
            if (selectedIds.contains(candidate.id())) {
                selectedTrades.add(candidate);
            }
        }
        selectedTrades.sort(DISPLAY_ORDER);
        return selectedTrades;
    }

    private static void ensureBuiltIns() {
        synchronized (LOCK) {
            if (builtInsLoaded) {
                return;
            }
            // Build from a clean registry. If one malformed entry fails, do not
            // leave a partially populated registry that produces misleading
            // duplicate-ID errors on every later interaction.
            TRADES_BY_ID.clear();
            PROFESSION_TRADES.clear();
            ENTITY_TYPE_TRADES.clear();
            try {
                registerBuiltIns();
                builtInsLoaded = true;
            } catch (RuntimeException exception) {
                TRADES_BY_ID.clear();
                PROFESSION_TRADES.clear();
                ENTITY_TYPE_TRADES.clear();
                throw exception;
            }
        }
    }

    private static <K> void registerInternal(Map<K, List<RupeeTrade>> targetMap, K target, RupeeTrade trade) {
        RupeeTrade existing = TRADES_BY_ID.get(trade.id());
        if (existing != null && existing != trade) {
            throw new IllegalArgumentException("Duplicate rupee trade ID: " + trade.id());
        }
        TRADES_BY_ID.putIfAbsent(trade.id(), trade);

        List<RupeeTrade> targetTrades = targetMap.computeIfAbsent(target, ignored -> new ArrayList<>());
        if (targetTrades.stream().noneMatch(candidate -> candidate.id().equals(trade.id()))) {
            targetTrades.add(trade);
            targetTrades.sort(DISPLAY_ORDER);
        }
    }

    private static void registerBuiltIns() {
        registerFarmerTrades();
        registerToolsmithTrades();
        registerArmorerTrades();
        registerClericTrades();
        registerFishermanTrades();
        registerFletcherTrades();
        registerWeaponsmithTrades();
        registerLeatherworkerTrades();
        registerWanderingTraderTrades();
        registerSellTrades();
        registerDailyDealTrades();
    }

    /*
     * HOW TO ADD A RUPEE TRADE
     * ------------------------
     * Copy one of these entries and change its values:
     *
     * villagerTrade(VillagerProfession.FARMER, trade("farmer/example", Items.APPLE)
     *         .displayOrder(10)          // Lower numbers appear first in the menu.
     *         .requiredVillagerLevel(1)  // 1=Novice, 2=Apprentice, 3=Journeyman, 4=Expert, 5=Master.
     *         .priceInRupees(25)         // Rupees removed from the equipped wallet per purchase.
     *         .stock(4)                  // Number of purchases this individual trader allows.
     *         .xpReward(10));            // Villager profession XP earned by each purchase.
     *
     * Optional item payments can be appended with .requires(Items.DIAMOND, 1).
     * Upgrade trades should also use .preserveFirstIngredientData() so the
     * first input item's damage, enchantments, and custom data carry forward.
     *
     * Use entityTrade(EntityType.WANDERING_TRADER, ...) for the wandering
     * trader pool. Each wandering trader randomly keeps only three pool entries.
     *
     * CATEGORY EXAMPLES
     * -----------------
     * Normal Buy entry: use trade(...) exactly as above; Buy is the default.
     * Sell entry: use sellTrade("farmer/wheat", Items.WHEAT)
     *         .payoutInRupees(2).stock(64).xpReward(1)
     * Daily Deal pool entry: use dailyDeal("farmer/apple_deal", Items.APPLE)
     *         .priceInRupees(3).stock(16).xpReward(2)
     * Only one unlocked Daily Deal pool entry is shown per trader per Minecraft day.
     */

    private static void registerFarmerTrades() {
        villagerTrade(VillagerProfession.FARMER, trade("farmer/melon_slice", Items.MELON_SLICE)
                .displayOrder(1).requiredVillagerLevel(1).priceInRupees(4).stock(15).xpReward(4));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/beetroot", Items.BEETROOT)
                .displayOrder(2).requiredVillagerLevel(1).priceInRupees(5).stock(6).xpReward(6));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/potato", Items.POTATO)
                .displayOrder(3).requiredVillagerLevel(1).priceInRupees(10).stock(10).xpReward(5));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/carrot", Items.CARROT)
                .displayOrder(4).requiredVillagerLevel(2).priceInRupees(15).stock(5).xpReward(8));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/apple", Items.APPLE)
                .displayOrder(5).requiredVillagerLevel(2).priceInRupees(20).stock(6).xpReward(12));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/sweet_berries", Items.SWEET_BERRIES)
                .displayOrder(6).requiredVillagerLevel(2).priceInRupees(5).stock(20).xpReward(4));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/wheat", Items.WHEAT)
                .displayOrder(7).requiredVillagerLevel(3).priceInRupees(2).stock(30).xpReward(4));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/bone_meal", Items.BONE_MEAL)
                .displayOrder(8).requiredVillagerLevel(3).priceInRupees(5).stock(10).xpReward(8));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/glow_berries", Items.GLOW_BERRIES)
                .displayOrder(9).requiredVillagerLevel(4).priceInRupees(10).stock(5).xpReward(15));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/cake", Items.CAKE)
                .displayOrder(10).requiredVillagerLevel(4).priceInRupees(25).stock(3).xpReward(20));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/pumpkin_pie", Items.PUMPKIN_PIE)
                .displayOrder(11).requiredVillagerLevel(4).priceInRupees(20).stock(10).xpReward(20));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/honey_bottle", Items.HONEY_BOTTLE)
                .displayOrder(12).requiredVillagerLevel(4).priceInRupees(20).stock(10).xpReward(20));
        villagerTrade(VillagerProfession.FARMER, trade("farmer/diamond_hoe", Items.DIAMOND_HOE)
                .displayOrder(13).requiredVillagerLevel(5).priceInRupees(120).stock(1).xpReward(40));
    }

    private static void registerToolsmithTrades() {
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/appraise_ring", ItemInit.APPRAISED_RING_BOX.get())
                .displayOrder(10).requiredVillagerLevel(1).priceInRupees(20).stock(99).xpReward(5)
                .requires(ItemInit.UNAPPRAISED_RING.get(), 1));
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/bomb", ItemInit.BOMB.get())
                .displayOrder(20).requiredVillagerLevel(1).priceInRupees(15).stock(20).xpReward(8));
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/water_bomb", ItemInit.WATER_BOMB.get())
                .displayOrder(30).requiredVillagerLevel(1).priceInRupees(20).stock(10).xpReward(10));
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/deku_shield", ItemInit.DEKU_SHIELD.get())
                .displayOrder(40).requiredVillagerLevel(1).priceInRupees(40).stock(1).xpReward(20));
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/ring_box_l1", ItemInit.RING_BOX_L1.get())
                .displayOrder(50).requiredVillagerLevel(2).priceInRupees(100).stock(5).xpReward(10));
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/ring_box_l2_upgrade", ItemInit.RING_BOX_L2.get())
                .displayOrder(60).requiredVillagerLevel(3).priceInRupees(150).stock(5).xpReward(30)
                .requires(ItemInit.RING_BOX_L1.get(), 1).preserveFirstIngredientData());
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/ring_box_l3_upgrade", ItemInit.RING_BOX_L3.get())
                .displayOrder(70).requiredVillagerLevel(4).priceInRupees(300).stock(5).xpReward(70)
                .requires(ItemInit.RING_BOX_L2.get(), 1).preserveFirstIngredientData());
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/blue_ring", ItemInit.BLUE_RING.get())
                .displayOrder(80).requiredVillagerLevel(5).priceInRupees(60).stock(5).xpReward(70)
                .requires(ItemInit.ARMOR_RING_L3.get(), 1).preserveFirstIngredientData());
        villagerTrade(VillagerProfession.TOOLSMITH, trade("toolsmith/red_ring", ItemInit.RED_RING.get())
                .displayOrder(90).requiredVillagerLevel(5).priceInRupees(60).stock(5).xpReward(70)
                .requires(ItemInit.POWER_RING_L3.get(), 1).preserveFirstIngredientData());
    }

    private static void registerArmorerTrades() {
        villagerTrade(VillagerProfession.ARMORER, trade("armorer/deku_shield", ItemInit.DEKU_SHIELD.get())
                .displayOrder(10).requiredVillagerLevel(1).priceInRupees(40).stock(1).xpReward(9));
        villagerTrade(VillagerProfession.ARMORER, trade("armorer/sacred_shield", ItemInit.SACRED_SHIELD.get())
                .displayOrder(20).requiredVillagerLevel(3).priceInRupees(500).stock(1).xpReward(30));
        villagerTrade(VillagerProfession.ARMORER, trade("armorer/magic_armor", ItemInit.MAGIC_ARMOR_SET.get())
                .displayOrder(30).requiredVillagerLevel(4).priceInRupees(600).stock(1).xpReward(200));
    }

    private static void registerClericTrades() {
        villagerTrade(VillagerProfession.CLERIC, trade("cleric/heart_piece", ItemInit.HEART_PIECE.get())
                .displayOrder(10).requiredVillagerLevel(5).priceInRupees(1500).stock(1).xpReward(50));
    }

    private static void registerFishermanTrades() {

        villagerTrade(VillagerProfession.FISHERMAN, trade("fisherman/hyrule_bass", ItemInit.HYRULE_BASS.get())
                .displayOrder(1).requiredVillagerLevel(1).priceInRupees(20).stock(30).xpReward(5));
        villagerTrade(VillagerProfession.FISHERMAN, trade("fisherman/hylian_bass", ItemInit.HYLIAN_LOACH.get())
                .displayOrder(2).requiredVillagerLevel(1).priceInRupees(35).stock(20).xpReward(8));

        villagerTrade(VillagerProfession.FISHERMAN, trade("fisherman/fishing_rod", ItemInit.FISHING_ROD.get())
                .displayOrder(10).requiredVillagerLevel(3).priceInRupees(60).stock(1).xpReward(50));
    }

    private static void registerFletcherTrades() {
        villagerTrade(VillagerProfession.FLETCHER, trade("fletcher/fairy_bow", ItemInit.FAIRY_BOW.get())
                .displayOrder(10).requiredVillagerLevel(2).priceInRupees(100).stock(1).xpReward(50));
    }

    private static void registerWeaponsmithTrades() {
        villagerTrade(VillagerProfession.WEAPONSMITH,
                trade("weaponsmith/master_sword_v2_upgrade", ItemInit.MASTER_SWORD_V2.get())
                        .displayOrder(10).requiredVillagerLevel(1).priceInRupees(0).stock(1).xpReward(70)
                        .requires(ItemInit.MASTER_SWORD.get(), 1)
                        .requires(ItemInit.MASTER_ORE.get(), 2)
                        .preserveFirstIngredientData());
        villagerTrade(VillagerProfession.WEAPONSMITH, trade("weaponsmith/kokiri_sword", ItemInit.KOKIRI_SWORD.get())
                .displayOrder(20).requiredVillagerLevel(1).priceInRupees(40).stock(1).xpReward(10));
        villagerTrade(VillagerProfession.WEAPONSMITH,
                trade("weaponsmith/magic_boomerang_upgrade", ItemInit.MAGIC_BOOMERANG.get())
                        .displayOrder(30).requiredVillagerLevel(2).priceInRupees(120).stock(1).xpReward(20)
                        .requires(ItemInit.BOOMERANG.get(), 1).preserveFirstIngredientData());
        villagerTrade(VillagerProfession.WEAPONSMITH,
                trade("weaponsmith/razor_sword_upgrade", ItemInit.RAZOR_SWORD.get())
                        .displayOrder(40).requiredVillagerLevel(3).priceInRupees(200).stock(6).xpReward(30)
                        .requires(ItemInit.KOKIRI_SWORD.get(), 1).preserveFirstIngredientData());
        villagerTrade(VillagerProfession.WEAPONSMITH,
                trade("weaponsmith/gilded_sword_upgrade", ItemInit.GILDED_SWORD.get())
                        .displayOrder(50).requiredVillagerLevel(4).priceInRupees(0).stock(1).xpReward(70)
                        .requires(ItemInit.RAZOR_SWORD.get(), 1)
                        .requires(Items.GOLD_BLOCK, 2)
                        .preserveFirstIngredientData());
        villagerTrade(VillagerProfession.WEAPONSMITH,
                trade("weaponsmith/true_master_sword_upgrade", ItemInit.TRUE_MASTER_SWORD.get())
                        .displayOrder(60).requiredVillagerLevel(5).priceInRupees(0).stock(1).xpReward(100)
                        .requires(ItemInit.MASTER_SWORD_V2.get(), 1)
                        .requires(ItemInit.MASTER_ORE.get(), 6)
                        .preserveFirstIngredientData());
    }

    private static void registerLeatherworkerTrades() {
        villagerTrade(VillagerProfession.LEATHERWORKER, trade("leatherworker/bait_bag", ItemInit.BAIT_BAG.get())
                .displayOrder(10).requiredVillagerLevel(1).priceInRupees(20).stock(1).xpReward(8));
        villagerTrade(VillagerProfession.LEATHERWORKER, trade("leatherworker/spoils_bag", ItemInit.SPOILS_BAG.get())
                .displayOrder(20).requiredVillagerLevel(2).priceInRupees(20).stock(1).xpReward(8));
        villagerTrade(VillagerProfession.LEATHERWORKER, trade("leatherworker/delivery_bag", ItemInit.DELIVERY_BAG.get())
                .displayOrder(30).requiredVillagerLevel(2).priceInRupees(20).stock(1).xpReward(8));

        villagerTrade(VillagerProfession.LEATHERWORKER, trade("leatherworker/quiver", ItemInit.QUIVER.get())
                .displayOrder(40).requiredVillagerLevel(3).priceInRupees(100).stock(1).xpReward(8));
        villagerTrade(VillagerProfession.LEATHERWORKER,
                trade("leatherworker/big_quiver_upgrade", ItemInit.BIG_QUIVER.get())
                        .displayOrder(50).requiredVillagerLevel(4).priceInRupees(150).stock(5).xpReward(30)
                        .requires(ItemInit.QUIVER.get(), 1).preserveFirstIngredientData());
        villagerTrade(VillagerProfession.LEATHERWORKER,
                trade("leatherworker/biggest_quiver_upgrade", ItemInit.BIGGEST_QUIVER.get())
                        .displayOrder(60).requiredVillagerLevel(5).priceInRupees(300).stock(5).xpReward(30)
                        .requires(ItemInit.BIG_QUIVER.get(), 1).preserveFirstIngredientData());

        villagerTrade(VillagerProfession.LEATHERWORKER, trade("leatherworker/bullet_bag", ItemInit.BULLET_BAG.get())
                .displayOrder(70).requiredVillagerLevel(3).priceInRupees(100).stock(1).xpReward(8));
        villagerTrade(VillagerProfession.LEATHERWORKER,
                trade("leatherworker/big_bullet_bag_upgrade", ItemInit.BIG_BULLET_BAG.get())
                        .displayOrder(80).requiredVillagerLevel(4).priceInRupees(150).stock(5).xpReward(30)
                        .requires(ItemInit.BULLET_BAG.get(), 1).preserveFirstIngredientData());
        villagerTrade(VillagerProfession.LEATHERWORKER,
                trade("leatherworker/biggest_bullet_bag_upgrade", ItemInit.BIGGEST_BULLET_BAG.get())
                        .displayOrder(90).requiredVillagerLevel(5).priceInRupees(300).stock(5).xpReward(30)
                        .requires(ItemInit.BIG_BULLET_BAG.get(), 1).preserveFirstIngredientData());

        villagerTrade(VillagerProfession.LEATHERWORKER, trade("leatherworker/bomb_bag", ItemInit.BOMB_BAG.get())
                .displayOrder(100).requiredVillagerLevel(3).priceInRupees(100).stock(1).xpReward(8));
        villagerTrade(VillagerProfession.LEATHERWORKER,
                trade("leatherworker/big_bomb_bag_upgrade", ItemInit.BIG_BOMB_BAG.get())
                        .displayOrder(110).requiredVillagerLevel(4).priceInRupees(150).stock(5).xpReward(30)
                        .requires(ItemInit.BOMB_BAG.get(), 1).preserveFirstIngredientData());
        villagerTrade(VillagerProfession.LEATHERWORKER,
                trade("leatherworker/biggest_bomb_bag_upgrade", ItemInit.BIGGEST_BOMB_BAG.get())
                        .displayOrder(120).requiredVillagerLevel(5).priceInRupees(300).stock(5).xpReward(30)
                        .requires(ItemInit.BIG_BOMB_BAG.get(), 1).preserveFirstIngredientData());
    }

    private static void registerWanderingTraderTrades() {
        entityTrade(EntityType.WANDERING_TRADER, trade("wandering/deku_seeds", ItemInit.DEKU_SEEDS.get())
                .displayOrder(10).requiredVillagerLevel(1).priceInRupees(1).stock(64).xpReward(5));
        entityTrade(EntityType.WANDERING_TRADER,
                trade("wandering/appraised_ring_box", ItemInit.APPRAISED_RING_BOX.get())
                        .displayOrder(20).requiredVillagerLevel(1).priceInRupees(60).stock(4).xpReward(30));
        entityTrade(EntityType.WANDERING_TRADER, trade("wandering/heart_piece", ItemInit.HEART_PIECE.get())
                .displayOrder(30).requiredVillagerLevel(1).priceInRupees(100).stock(1).xpReward(200));
        entityTrade(EntityType.WANDERING_TRADER, trade("wandering/gorons_bracelet", ItemInit.GORONS_BRACELET.get())
                .displayOrder(40).requiredVillagerLevel(1).priceInRupees(700).stock(2).xpReward(200));
        entityTrade(EntityType.WANDERING_TRADER, trade("wandering/silver_gauntlets", ItemInit.SILVER_GAUNTLETS.get())
                .displayOrder(50).requiredVillagerLevel(1).priceInRupees(1500).stock(1).xpReward(200));
        entityTrade(EntityType.WANDERING_TRADER, trade("wandering/golden_gauntlets", ItemInit.GOLDEN_GAUNTLETS.get())
                .displayOrder(60).requiredVillagerLevel(1).priceInRupees(4500).stock(1).xpReward(600));
        entityTrade(EntityType.WANDERING_TRADER, trade("wandering/magic_fire_arrow", ItemInit.MAGIC_FIRE_ARROW.get())
                .displayOrder(70).requiredVillagerLevel(1).priceInRupees(1200).stock(1).xpReward(400));
        entityTrade(EntityType.WANDERING_TRADER, trade("wandering/magic_ice_arrow", ItemInit.MAGIC_ICE_ARROW.get())
                .displayOrder(80).requiredVillagerLevel(1).priceInRupees(1200).stock(1).xpReward(400));
    }

    /**
     * SELL LIST
     * Add offers here when a trader should buy an item from the player. The
     * item/count in the ItemStack is removed once per selected quantity and
     * the payout is deposited directly into the equipped wallet.
     *
     * Example:
     * villagerTrade(VillagerProfession.FARMER,
     *         sellTrade("farmer/sell_wheat", new ItemStack(Items.WHEAT, 16))
     *                 .displayOrder(10).requiredVillagerLevel(1)
     *                 .payoutInRupees(5).stock(64).xpReward(1));
     */
    private static void registerSellTrades() {



        villagerTrade(VillagerProfession.FISHERMAN,
                sellTrade("fisherman/raw_cod", new ItemStack(Items.COD, 1))
                        .displayOrder(1)
                        .requiredVillagerLevel(1)
                        .payoutInRupees(4)
                        .stock(16)
                        .xpReward(2));

        // Example Toolsmith sell offer: the player sells four iron ingots for 12 rupees.
        villagerTrade(VillagerProfession.TOOLSMITH,
                sellTrade("toolsmith/sell_iron_ingots", new ItemStack(Items.IRON_INGOT, 4))
                        .displayOrder(10)
                        .requiredVillagerLevel(1)
                        .payoutInRupees(12)
                        .stock(16)
                        .xpReward(2));
    }

    /**
     * DAILY DEAL POOLS
     * Add as many candidates as desired for each profession/entity. Exactly
     * one unlocked candidate is chosen for each individual trader each
     * Minecraft day, and its stock resets when the day changes.
     *
     * Example:
     * villagerTrade(VillagerProfession.FARMER,
     *         dailyDeal("farmer/daily_apple", Items.APPLE)
     *                 .displayOrder(10).requiredVillagerLevel(1)
     *                 .priceInRupees(3).stock(16).xpReward(2));
     */
    private static void registerDailyDealTrades() {


        villagerTrade(VillagerProfession.FISHERMAN,
                dailyDeal("fisherman/daily_swimmers_ring", ItemInit.SWIMMERS_RING.get())
                        .displayOrder(1)
                        .requiredVillagerLevel(1)
                        .priceInRupees(100)
                        .stock(1)
                        .xpReward(5));

        // Example Toolsmith daily deal: one iron pickaxe for 25 rupees.
        villagerTrade(VillagerProfession.TOOLSMITH,
                dailyDeal("toolsmith/daily_iron_pickaxe", Items.IRON_PICKAXE)
                        .displayOrder(10)
                        .requiredVillagerLevel(1)
                        .priceInRupees(25)
                        .stock(1)
                        .xpReward(5));
    }

    private static RupeeTrade.Builder trade(String path, ItemLike result) {
        return RupeeTrade.builder(id(path), result);
    }

    private static RupeeTrade.Builder sellTrade(String path, ItemLike itemSoldByPlayer) {
        return RupeeTrade.builder(id(path), itemSoldByPlayer).sell();
    }

    private static RupeeTrade.Builder sellTrade(String path, ItemStack itemSoldByPlayer) {
        return RupeeTrade.builder(id(path), itemSoldByPlayer).sell();
    }

    private static RupeeTrade.Builder dailyDeal(String path, ItemLike result) {
        return RupeeTrade.builder(id(path), result).dailyDeal();
    }

    private static void villagerTrade(VillagerProfession profession, RupeeTrade.Builder builder) {
        registerInternal(PROFESSION_TRADES, profession, builder.build());
    }

    private static void entityTrade(EntityType<?> entityType, RupeeTrade.Builder builder) {
        registerInternal(ENTITY_TYPE_TRADES, entityType, builder.build());
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, path);
    }
}
