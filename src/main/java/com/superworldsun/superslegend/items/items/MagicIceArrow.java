package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.arrows.MagicIceArrowEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MagicIceArrow extends MagicArrow
{
	@Override
	public AbstractArrowEntity createArrow(World world, ItemStack stack, LivingEntity shooter)
	{
		return new MagicIceArrowEntity(world, shooter);
	}
}
