package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.RupeeEntity;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Ensures every physical rupee uses {@link RupeeEntity}, regardless of whether
 * it came from mob loot, a player, a dropper, a broken container, or another
 * source that normally creates a vanilla {@link ItemEntity}.
 */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public final class RupeeGroundEntityEvents {
    private RupeeGroundEntityEvents() {
    }

    @SubscribeEvent
    public static void replaceVanillaRupeeItemEntity(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()
                || !(event.getEntity() instanceof ItemEntity itemEntity)
                || itemEntity instanceof RupeeEntity
                || RupeeValue.from(itemEntity.getItem()) == null) {
            return;
        }

        // Copy all vanilla ItemEntity state, including its stack, position,
        // velocity, age, pickup delay, owner/thrower, and remaining lifespan.
        CompoundTag itemData = new CompoundTag();
        itemEntity.saveWithoutId(itemData);

        Level level = itemEntity.level();
        RupeeEntity rupeeEntity = new RupeeEntity(EntityTypeInit.RUPEE_ENTITY.get(), level);
        rupeeEntity.load(itemData);

        event.setCanceled(true);
        level.addFreshEntity(rupeeEntity);
    }
}
