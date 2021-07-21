package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.arrows.IceArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ArrowIce extends ArrowItem
{

    public ArrowIce(Properties builder)
    {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        IceArrowEntity entityArrowIce = new IceArrowEntity(worldIn, shooter);
        return entityArrowIce;
    }
}
