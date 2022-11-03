package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.arrows.MagicFireArrowEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MagicFireArrow extends MagicArrow
{
	@Override
	public AbstractArrowEntity createMagicArrow(World world, ItemStack stack, LivingEntity shooter)
	{
		return new MagicFireArrowEntity(world, shooter);
	}
}
