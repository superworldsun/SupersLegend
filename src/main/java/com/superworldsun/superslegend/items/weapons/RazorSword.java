package com.superworldsun.superslegend.items.weapons;

import com.superworldsun.superslegend.items.custom.ItemCustomSword;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.ItemToolTiers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RazorSword extends ItemCustomSword
{
	public RazorSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
		if(this.getTier() == ItemToolTiers.KOKIRI_SWORD) {
			return false;
		}
		
		if(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1) {
			super.hurtEnemy(itemStack, attacked, attacker);
		}

		else {
			ItemStack	newItemStack = new ItemStack(ItemInit.KOKIRI_SWORD.get());
			newItemStack.setDamageValue(newItemStack.getMaxDamage());
			attacker.setItemSlot(EquipmentSlotType.MAINHAND, newItemStack);
			attacker.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		}
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack itemStack, World world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
		if(this.getTier() == ItemToolTiers.KOKIRI_SWORD) {
			return false;
		}
		
		if(itemStack.getDamageValue() < itemStack.getMaxDamage() - 2) {
			super.mineBlock(itemStack, world, blockstate, pos, entity);
		}
		else {
			ItemStack	newItemStack = new ItemStack(ItemInit.KOKIRI_SWORD.get());
			newItemStack.setDamageValue(newItemStack.getMaxDamage());
			entity.setItemSlot(EquipmentSlotType.MAINHAND, newItemStack);
			entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		}
		return true;
	}
}
