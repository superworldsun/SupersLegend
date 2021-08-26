package com.superworldsun.superslegend.items.weapons;

import com.superworldsun.superslegend.items.custom.ItemCustomSword;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class GiantsKnife extends ItemCustomSword
{
	public GiantsKnife(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GRAY + "A Large Sword that requires two hands to wield"));
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
						if(player.hasItemInSlot(EquipmentSlotType.OFFHAND))
						{
							player.spawnAtLocation(player.getOffhandItem());
							player.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
						}
					}
			}
		}
	}
}
