package com.superworldsun.superslegend.items.weapons;

import java.util.List;

import com.superworldsun.superslegend.registries.ItemGroupInit;
import com.superworldsun.superslegend.util.ItemToolTiers;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MegatonHammer extends HammerItem
{
	public MegatonHammer()
	{
		super(ItemToolTiers.MEGATON_HAMMER, 2, new Properties().tab(ItemGroupInit.RESOURCES));
	}
	
	@Override
	protected int getLeftClickCooldown()
	{
		return 18;
	}

	//TODO When breaking plants there is no particle effect or sound played
	//TODO Add blocks to the Init, list isnt full
	//TODO Add a sound for when the hammer hits a block & entity
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getMainHandItem();
			{
				if(stack == equipped)
				{
					if(player.hasItemInSlot(EquipmentSlotType.OFFHAND))
					{
						player.spawnAtLocation(player.getOffhandItem());
						player.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
					}
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "A Large hammer from the Goron tribe"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Requires two hands to wield"));
	}
}