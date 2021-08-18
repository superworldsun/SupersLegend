package com.superworldsun.superslegend.items.food;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class HylianLoach extends Item
{	
	public HylianLoach()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES).food(new Food.Builder().saturationMod(0.4f).nutrition(2).meat().build()));
	}
	
	@Override
	public UseAction getUseAnimation(ItemStack stack)
	{
		return UseAction.EAT;
	}
	
	@Override
	public int getUseDuration(ItemStack p_77626_1_)
	{
		return 32;
	}
	
	@SubscribeEvent
	public static void onItemFished(ItemFishedEvent event)
	{
		event.getDrops().add(new ItemStack(ItemInit.HYLIAN_LOACH.get()));
	}
}
