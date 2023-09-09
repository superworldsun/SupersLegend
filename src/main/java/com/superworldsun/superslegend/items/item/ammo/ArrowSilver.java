package com.superworldsun.superslegend.items.item.ammo;

import com.superworldsun.superslegend.entities.projectiles.arrows.AncientArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.SilverArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ArrowSilver extends ArrowItem {
    public ArrowSilver(Properties properties)
    {
        super(properties.stacksTo(64));
    }

    @Override @Nonnull
    @ParametersAreNonnullByDefault
    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
        return new SilverArrowEntity(worldIn, shooter);
    }
}

