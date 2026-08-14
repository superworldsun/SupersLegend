package com.superworldsun.superslegend.trading.rupee;

import com.superworldsun.superslegend.SupersLegendMain;
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
    private static Map<VillagerProfession, List<RupeeTrade>> dataDrivenProfessionTrades = Map.of();
    private static Map<EntityType<?>, List<RupeeTrade>> dataDrivenEntityTypeTrades = Map.of();
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
            List<RupeeTrade> dailyDealCandidates = new ArrayList<>();
            List<RupeeTrade> dailyRequestCandidates = new ArrayList<>();
            for (RupeeTrade trade : matches.values()) {
                if (trade.isUnlockedAt(level)) {
                    if (trade.type() == RupeeTrade.Type.DAILY_DEAL) {
                        dailyDealCandidates.add(trade);
                    } else if (trade.type() == RupeeTrade.Type.DAILY_REQUEST) {
                        dailyRequestCandidates.add(trade);
                    } else {
                        unlocked.add(trade);
                    }
                }
            }
            unlocked.sort(DISPLAY_ORDER);
            if (trader instanceof WanderingTrader wanderingTrader) {
                unlocked = selectWanderingTraderOffers(wanderingTrader, unlocked);
            }
            selectDailyOffer(trader, dailyDealCandidates, 0L).ifPresent(unlocked::add);
            selectDailyOffer(trader, dailyRequestCandidates, 0x6A09E667F3BCC909L).ifPresent(unlocked::add);
            return List.copyOf(unlocked);
        }
    }

    /**
     * Returns every offer the trade screen may display. Villagers expose their
     * future Buy and Sell offers, plus future-level Daily pool entries, while
     * {@link #getTrades(AbstractVillager)} remains the authoritative unlocked
     * list used for transactions.
     */
    public static List<RupeeTrade> getVisibleTrades(AbstractVillager trader) {
        Objects.requireNonNull(trader, "trader");
        ensureBuiltIns();

        // Traders without villager levels have no future progression to preview.
        if (!(trader instanceof Villager villager)) {
            return getTrades(trader);
        }

        synchronized (LOCK) {
            Map<ResourceLocation, RupeeTrade> matches = new LinkedHashMap<>();
            addMatches(matches, ENTITY_TYPE_TRADES.get(trader.getType()));
            addMatches(matches, PROFESSION_TRADES.get(villager.getVillagerData().getProfession()));

            int level = traderLevel(trader);
            List<RupeeTrade> visible = new ArrayList<>();
            List<RupeeTrade> unlockedDailyDeals = new ArrayList<>();
            List<RupeeTrade> unlockedDailyRequests = new ArrayList<>();
            List<RupeeTrade> lockedDailyTrades = new ArrayList<>();

            for (RupeeTrade trade : matches.values()) {
                if (trade.type() == RupeeTrade.Type.DAILY_DEAL
                        || trade.type() == RupeeTrade.Type.DAILY_REQUEST) {
                    if (!trade.isUnlockedAt(level)) {
                        lockedDailyTrades.add(trade);
                    } else if (trade.type() == RupeeTrade.Type.DAILY_DEAL) {
                        unlockedDailyDeals.add(trade);
                    } else {
                        unlockedDailyRequests.add(trade);
                    }
                } else {
                    visible.add(trade);
                }
            }

            visible.sort(DISPLAY_ORDER);
            selectDailyOffer(trader, unlockedDailyDeals, 0L).ifPresent(visible::add);
            selectDailyOffer(trader, unlockedDailyRequests, 0x6A09E667F3BCC909L).ifPresent(visible::add);
            lockedDailyTrades.sort(DISPLAY_ORDER);
            visible.addAll(lockedDailyTrades);
            return List.copyOf(visible);
        }
    }

    /** Returns the one stable offer chosen for this trader during the current Minecraft day. */
    private static Optional<RupeeTrade> selectDailyOffer(AbstractVillager trader,
                                                          List<RupeeTrade> candidates,
                                                          long seedSalt) {
        if (candidates.isEmpty()) {
            return Optional.empty();
        }
        candidates.sort(DISPLAY_ORDER);
        long day = Math.floorDiv(trader.level().getDayTime(), 24000L);
        long seed = trader.getUUID().getMostSignificantBits()
                ^ Long.rotateLeft(trader.getUUID().getLeastSignificantBits(), 23)
                ^ day * 0x9E3779B97F4A7C15L
                ^ seedSalt;
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

    /** Atomically replaces trades loaded from each namespace's rupee_trades data folder. */
    public static void replaceDataDrivenTrades(
            Map<VillagerProfession, List<RupeeTrade>> professionTrades,
            Map<EntityType<?>, List<RupeeTrade>> entityTypeTrades) {
        Objects.requireNonNull(professionTrades, "professionTrades");
        Objects.requireNonNull(entityTypeTrades, "entityTypeTrades");
        synchronized (LOCK) {
            Map<VillagerProfession, List<RupeeTrade>> professionCopy = new HashMap<>();
            professionTrades.forEach((profession, trades) ->
                    professionCopy.put(profession, List.copyOf(trades)));
            Map<EntityType<?>, List<RupeeTrade>> entityCopy = new HashMap<>();
            entityTypeTrades.forEach((entityType, trades) ->
                    entityCopy.put(entityType, List.copyOf(trades)));
            dataDrivenProfessionTrades = Map.copyOf(professionCopy);
            dataDrivenEntityTypeTrades = Map.copyOf(entityCopy);
            builtInsLoaded = false;
            ensureBuiltIns();
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
        dataDrivenProfessionTrades.forEach((profession, trades) -> {
            for (RupeeTrade trade : trades) {
                registerInternal(PROFESSION_TRADES, profession, trade);
            }
        });
        dataDrivenEntityTypeTrades.forEach((entityType, trades) -> {
            for (RupeeTrade trade : trades) {
                registerInternal(ENTITY_TYPE_TRADES, entityType, trade);
            }
        });
    }
}

