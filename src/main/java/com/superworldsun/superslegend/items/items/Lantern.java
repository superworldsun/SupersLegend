package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.List;
import java.util.function.Consumer;

public class Lantern extends Item
{

	public Lantern(Properties properties)
	{
		super(properties);
	}



	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GREEN + "Provides light on held"));
		list.add(new StringTextComponent(TextFormatting.RED + "Runs out of fuel, use carefully"));
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity user, int tick, boolean p_77663_5_) {

		for (ItemStack item : user.getHandSlots()) {
			if (item == stack) {
				if (stack.getDamageValue() < stack.getMaxDamage()) {
					stack.setDamageValue(stack.getDamageValue() + 1);
				}
				if (stack.getDamageValue() == stack.getMaxDamage()) {

				}
			}
		}
		if (user instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) user;
			if (player.getMainHandItem() == stack) {
				if (stack.getDamageValue() == stack.getMaxDamage()) {
					player.setItemInHand(Hand.MAIN_HAND,new ItemStack(ItemInit.EXTINGUISHEDLANTERN.get()));
				}
			}
			if (player.getOffhandItem() == stack) {
				if (stack.getDamageValue() == stack.getMaxDamage()) {
					player.setItemInHand(Hand.OFF_HAND,new ItemStack(ItemInit.EXTINGUISHEDLANTERN.get()));
				}
			}
		}
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		return super.onEntityItemUpdate(stack, entity);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}



}