package com.superworldsun.superslegend.items.weapons;

import com.superworldsun.superslegend.items.custom.ItemCustomSword;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.ItemToolTiers;

import java.util.List;

public class GiantsKnife extends ItemCustomSword
{
	public GiantsKnife(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@OnlyIn(Dist.CLIENT)
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

	public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
		if(this.getTier() == ItemToolTiers.BROKEN_GIANTS_KNIFE) {
			return false;
		}
		
		if(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1) {
			super.hurtEnemy(itemStack, attacked, attacker);
		}

		else {
			ItemStack	newItemStack = new ItemStack(ItemInit.BROKEN_GIANTS_KNIFE.get());
			newItemStack.setDamageValue(newItemStack.getMaxDamage());
			attacker.setItemSlot(EquipmentSlotType.MAINHAND, newItemStack);
			attacker.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		}
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack itemStack, World world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
		if(this.getTier() == ItemToolTiers.BROKEN_GIANTS_KNIFE) {
			return false;
		}
		
		if(itemStack.getDamageValue() < itemStack.getMaxDamage() - 2) {
			super.mineBlock(itemStack, world, blockstate, pos, entity);
		}
		else {
			ItemStack	newItemStack = new ItemStack(ItemInit.BROKEN_GIANTS_KNIFE.get());
			newItemStack.setDamageValue(newItemStack.getMaxDamage());
			entity.setItemSlot(EquipmentSlotType.MAINHAND, newItemStack);
			entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		}
		return true;
	}
}
