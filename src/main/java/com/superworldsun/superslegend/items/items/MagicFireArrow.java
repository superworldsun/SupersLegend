package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.arrows.FireArrowEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MagicFireArrow extends MagicArrow
{
	public MagicFireArrow(Properties builder)
	{
		super(builder);
	}
	
	@Override
	public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter)
	{
		FireArrowEntity entityFireArrow = new FireArrowEntity(worldIn, shooter);
		return entityFireArrow;
	}	
}
