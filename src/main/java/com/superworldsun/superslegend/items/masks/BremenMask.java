package com.superworldsun.superslegend.items.masks;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.sound.BremenMaskSound;
import com.superworldsun.superslegend.entities.ai.FollowBremenMaskGoal;
import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class BremenMask extends NonEnchantArmor implements IMaskAbility
{
	public BremenMask(Properties properties)
	{
		super(ArmourInit.bremenmask, EquipmentSlotType.HEAD, properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.WHITE + "A mask animals would love!"));
	}
	
	@Override
	public void startUsingAbility(PlayerEntity player)
	{
		if (player.level.isClientSide)
		{
			playMaskSound(player);
		}
		
		IMaskAbility.super.startUsingAbility(player);
	}
	
	@OnlyIn(value = Dist.CLIENT)
	private void playMaskSound(PlayerEntity player)
	{
		Minecraft client = Minecraft.getInstance();
		client.getSoundManager().play(new BremenMaskSound(player));
	}
	
	/**
	 * Adds goal for following players with Bremen mask into every animal
	 */
	@SubscribeEvent
	public static void onEntityConstructing(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof AnimalEntity)
		{
			AnimalEntity animal = (AnimalEntity) event.getEntity();
			animal.goalSelector.addGoal(3, new FollowBremenMaskGoal(animal, 1.2D, false));
		}
	}
}