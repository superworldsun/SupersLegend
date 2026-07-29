package com.superworldsun.superslegend.entities;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RupeeEntity extends ItemEntity {
    public RupeeEntity(EntityType<? extends RupeeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RupeeEntity(Level level, double x, double y, double z, ItemStack stack) {
        this(EntityTypeInit.RUPEE_ENTITY.get(), level);
        setPos(x, y, z);
        setDeltaMovement(level.random.nextDouble() * 0.2D - 0.1D, 0.2D,
                level.random.nextDouble() * 0.2D - 0.1D);
        setItem(stack);
        lifespan = stack.getEntityLifespan(level);
    }

    @Override
    public void playerTouch(Player player) {
        ItemStack beforeStack = getItem().copy();
        int beforeCount = beforeStack.getCount();
        super.playerTouch(player);

        int afterCount = isRemoved() ? 0 : getItem().getCount();
        if (!level().isClientSide && beforeCount > afterCount) {
            RupeeValue rupee = RupeeValue.from(beforeStack);
            SoundEvent sound = rupee == null ? null : rupee.pickupSound();
            if (sound != null) {
                level().playSound(null, player.getX(), player.getY(), player.getZ(), sound,
                        SoundSource.PLAYERS, 0.8F, 1.0F);
            }
        }
    }

}
