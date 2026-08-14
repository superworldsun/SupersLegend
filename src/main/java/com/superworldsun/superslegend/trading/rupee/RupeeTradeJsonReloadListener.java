package com.superworldsun.superslegend.trading.rupee;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Loads readable, mergeable rupee-trade files from data packs. */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public final class RupeeTradeJsonReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final RupeeTradeJsonReloadListener INSTANCE = new RupeeTradeJsonReloadListener();

    private RupeeTradeJsonReloadListener() {
        super(GSON, "rupee_trades");
    }

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(INSTANCE);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> files, ResourceManager resourceManager,
                         ProfilerFiller profiler) {
        Map<VillagerProfession, List<RupeeTrade>> loaded = new HashMap<>();
        Map<EntityType<?>, List<RupeeTrade>> loadedEntityTypes = new HashMap<>();
        int tradeCount = 0;

        for (Map.Entry<ResourceLocation, JsonElement> entry : files.entrySet()) {
            try {
                JsonObject root = GsonHelper.convertToJsonObject(entry.getValue(), "rupee trade file");
                boolean targetsProfession = root.has("profession");
                boolean targetsEntityType = root.has("entity_type");
                if (targetsProfession == targetsEntityType) {
                    throw new IllegalArgumentException(
                            "Specify exactly one of 'profession' or 'entity_type'");
                }
                JsonArray trades = GsonHelper.getAsJsonArray(root, "trades");
                List<RupeeTrade> parsedFile = new ArrayList<>(trades.size());
                for (JsonElement tradeElement : trades) {
                    parsedFile.add(readTrade(GsonHelper.convertToJsonObject(
                            tradeElement, "rupee trade")));
                }
                if (targetsProfession) {
                    ResourceLocation professionId = requiredId(root, "profession");
                    VillagerProfession profession = BuiltInRegistries.VILLAGER_PROFESSION
                            .getOptional(professionId)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Unknown villager profession: " + professionId));
                    loaded.computeIfAbsent(profession, ignored -> new ArrayList<>()).addAll(parsedFile);
                } else {
                    ResourceLocation entityTypeId = requiredId(root, "entity_type");
                    EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE
                            .getOptional(entityTypeId)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Unknown entity type: " + entityTypeId));
                    loadedEntityTypes.computeIfAbsent(entityType, ignored -> new ArrayList<>())
                            .addAll(parsedFile);
                }
                tradeCount += parsedFile.size();
            } catch (RuntimeException exception) {
                LOGGER.error("Could not load rupee trades from {}", entry.getKey(), exception);
            }
        }

        RupeeTradeRegistry.replaceDataDrivenTrades(loaded, loadedEntityTypes);
        LOGGER.info("Loaded {} data-driven rupee trades from {} files", tradeCount, files.size());
    }

    private static RupeeTrade readTrade(JsonObject json) {
        ResourceLocation tradeId = requiredId(json, "id");
        RupeeTrade.Type type = RupeeTrade.Type.valueOf(
                GsonHelper.getAsString(json, "type", "buy").toUpperCase(Locale.ROOT));
        ItemStack tradedItem = readStack(GsonHelper.getAsJsonObject(json, "item"));

        RupeeTrade.Builder builder = RupeeTrade.builder(tradeId, tradedItem)
                .type(type)
                .displayOrder(GsonHelper.getAsInt(json, "order", 0))
                .requiredVillagerLevel(GsonHelper.getAsInt(json, "level", 1))
                .rupeeCost(GsonHelper.getAsInt(json, "rupees", 0))
                .stock(GsonHelper.getAsInt(json, "stock", 1))
                .xpReward(GsonHelper.getAsInt(json, "villager_xp", 0));

        if (json.has("requirements")) {
            JsonArray requirements = GsonHelper.getAsJsonArray(json, "requirements");
            if (requirements.size() > RupeeTrade.MAX_REQUIREMENTS) {
                throw new IllegalArgumentException("Rupee trade " + tradeId + " has "
                        + requirements.size() + " item requirements; the maximum is "
                        + RupeeTrade.MAX_REQUIREMENTS);
            }
            for (JsonElement requirement : requirements) {
                builder.ingredient(readStack(GsonHelper.convertToJsonObject(
                        requirement, "trade requirement")));
            }
        }
        if (json.has("copy_data_from_requirement")) {
            builder.copyNbtFromIngredient(GsonHelper.getAsInt(json,
                    "copy_data_from_requirement"));
        }
        return builder.build();
    }

    private static ItemStack readStack(JsonObject json) {
        ResourceLocation itemId = requiredId(json, "id");
        Item item = BuiltInRegistries.ITEM.getOptional(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown item: " + itemId));
        int count = GsonHelper.getAsInt(json, "count", 1);
        if (count < 1 || count > item.getMaxStackSize()) {
            throw new IllegalArgumentException("Invalid count " + count + " for item " + itemId);
        }
        return new ItemStack(item, count);
    }

    private static ResourceLocation requiredId(JsonObject json, String field) {
        String value = GsonHelper.getAsString(json, field);
        ResourceLocation id = ResourceLocation.tryParse(value);
        if (id == null) {
            throw new IllegalArgumentException("Invalid resource location in '" + field + "': " + value);
        }
        return id;
    }
}
