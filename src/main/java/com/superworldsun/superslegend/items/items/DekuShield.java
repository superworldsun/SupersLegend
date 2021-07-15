package com.superworldsun.superslegend.items.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class DekuShield extends ShieldItem
{
	public DekuShield(Properties builder) {
	      super(builder);
	}
	      
	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return true;
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getMainHandItem();
			{
				if(stack == equipped)
		        {
					if(player.isOnFire())
					{
						stack.shrink(1);
						
						BlockPos currentPos = player.blockPosition();
						 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.SHIELD_BREAK, SoundCategory.PLAYERS, 1f, 1f);
						 
						player.displayClientMessage(new TranslationTextComponent(TextFormatting.RED + "Your shield is gone!"), true);
					}
		        }
			}
		}
		if(entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getOffhandItem();
			{
				if(stack == equipped)
		        {
					if(player.isOnFire())
					{
						stack.shrink(1);
						
						BlockPos currentPos = player.blockPosition();
						 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.SHIELD_BREAK, SoundCategory.PLAYERS, 1f, 1f);
						 
						player.displayClientMessage(new TranslationTextComponent(TextFormatting.RED + "Your shield is gone!"), true);
					}
		        }
			}
		}
	}
	      @Override
	      public boolean isBookEnchantable(ItemStack stack, ItemStack book)
		  {
	    	  return false;
		  }
}
