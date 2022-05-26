package com.superworldsun.superslegend.events;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class VillagerTradesEvents
{
	@SubscribeEvent
	public static void addCustomTrades(VillagerTradesEvent event)
	{
		List<VillagerTrades.ITrade> level1Trades = event.getTrades().get(1);
		// List<VillagerTrades.ITrade> level2Trades = event.getTrades().get(2);
		// List<VillagerTrades.ITrade> level3Trades = event.getTrades().get(3);
		List<VillagerTrades.ITrade> level4Trades = event.getTrades().get(4);
		
		if (event.getType() == VillagerProfession.TOOLSMITH)
		{
			level1Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get()), new ItemStack(ItemInit.UNAPPRAISED_RING.get()), new ItemStack(ItemInit.APPRAISED_RING_BOX.get()), 99, 5, 0F));
			level1Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get()), new ItemStack(ItemInit.BOMB.get()), 20, 8, 0F));
		}
		
		if (event.getType() == VillagerProfession.ARMORER)
		{
			level4Trades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 2), new ItemStack(ItemInit.MAGIC_ARMOR_SET.get()), 1, 200, 0F));
		}
	}
	
	@SubscribeEvent
	public static void addCustomWanderingTrades(WandererTradesEvent event)
	{
		// List<VillagerTrades.ITrade> genericTrades = event.getGenericTrades();
		List<VillagerTrades.ITrade> rareTrades = event.getRareTrades();
		
		rareTrades.add(createTrade(new ItemStack(ItemInit.SILVER_RUPEE.get()), new ItemStack(ItemInit.HEART_PIECE.get()), 1, 200, 0F));
	}
	
	private static VillagerTrades.ITrade createTrade(ItemStack price, ItemStack result, int maxUses, int exp, float priceMultiplier)
	{
		return (trader, rand) -> new MerchantOffer(price, result, maxUses, exp, priceMultiplier);
	}
	
	private static VillagerTrades.ITrade createTrade(ItemStack price1, ItemStack price2, ItemStack result, int maxUses, int exp, float priceMultiplier)
	{
		return (trader, rand) -> new MerchantOffer(price1, price2, result, maxUses, exp, priceMultiplier);
	}
}
