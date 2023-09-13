package com.superworldsun.superslegend.items.item.ammo;

import com.superworldsun.superslegend.entities.projectiles.arrows.MagicFireArrowEntity;
import com.superworldsun.superslegend.items.item.MagicArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicArrowFire extends MagicArrow
{
    @Override
    public AbstractArrow createMagicArrow(Level world, ItemStack stack, LivingEntity shooter)
    {
        return new MagicFireArrowEntity(world, shooter);
    }
}