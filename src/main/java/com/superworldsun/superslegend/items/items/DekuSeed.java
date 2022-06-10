package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.entities.projectiles.seeds.DekuSeedEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DekuSeed extends ArrowItem
{
	public DekuSeed(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter)
	{
		DekuSeedEntity pellet = new DekuSeedEntity(worldIn, shooter);
		return pellet;
	}
}