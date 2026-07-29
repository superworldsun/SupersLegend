package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Marks mobs created by monster spawners and prevents those mobs from being
 * used to farm SupersLegend rewards. The marker lives in Forge persistent
 * entity data, so it survives chunk unloading and world restarts.
 */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class SpawnerMobDropProtection {
    private static final String SPAWNER_MOB_MARKER = SupersLegendMain.MOD_ID + ":spawned_from_spawner";

    private SpawnerMobDropProtection() {
    }

    @SubscribeEvent
    public static void markSpawnerMob(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getSpawnType() == MobSpawnType.SPAWNER) {
            event.getEntity().getPersistentData().putBoolean(SPAWNER_MOB_MARKER, true);
        }
    }

    /**
     * Runs after normal loot and other drop handlers have populated the event.
     * Only this mod's items are removed; vanilla and other mod namespaces stay.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void removeSupersLegendDrops(LivingDropsEvent event) {
        if (!isSpawnerMob(event.getEntity())) {
            return;
        }

        event.getDrops().removeIf(SpawnerMobDropProtection::isSupersLegendItem);
    }

    public static boolean isSpawnerMob(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(SPAWNER_MOB_MARKER);
    }

    private static boolean isSupersLegendItem(ItemEntity itemEntity) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(itemEntity.getItem().getItem());
        return itemId != null && SupersLegendMain.MOD_ID.equals(itemId.getNamespace());
    }
}
