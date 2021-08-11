package com.superworldsun.superslegend.items.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GoldenScale extends Item
{

	public GoldenScale(Properties properties)
	{
		super(properties);
	}
	
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity && !world.isClientSide)
		{
			PlayerEntity player = (PlayerEntity)entity;

			if(!world.isClientSide)
			{
				//TODO PUT THIS IN CHANGELOG
				if(!player.isEyeInFluid(FluidTags.WATER) && !player.isEyeInFluid(FluidTags.LAVA) && player.isInWater())
	            	{
	            		player.addEffect(new EffectInstance(Effects.WATER_BREATHING, 415, 0, false, false, false));
	            	}
			}
		}
	}
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GOLD + "Allows you to dive even longer"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Have this anywhere in your inventory"));
	}   
}
