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
		List<VillagerTrades.ITrade> level2Trades = event.getTrades().get(2);
		List<VillagerTrades.ITrade> level3Trades = event.getTrades().get(3);
		List<VillagerTrades.ITrade> level4Trades = event.getTrades().get(4);
		List<VillagerTrades.ITrade> level5Trades = event.getTrades().get(5);
		
		if (event.getType() == VillagerProfession.TOOLSMITH)
		{
			level1Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get()), new ItemStack(ItemInit.UNAPPRAISED_RING.get()), new ItemStack(ItemInit.APPRAISED_RING_BOX.get()), 99, 5, 0F));
			level1Trades.add(createTrade(new ItemStack(ItemInit.BLUE_RUPEE.get(), 3), new ItemStack(ItemInit.BOMB.get()), 20, 8, 1F));
			level1Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 2), new ItemStack(ItemInit.DEKU_SHIELD.get()), 1, 20, 0F));
			level2Trades.add(createTrade(new ItemStack(ItemInit.SILVER_RUPEE.get(), 1), new ItemStack(ItemInit.RING_BOX_L1.get()), 5, 10, 1F));
			level3Trades.add(createTrade(new ItemStack(ItemInit.BLUE_RUPEE.get(), 30),(new ItemStack(ItemInit.RING_BOX_L1.get(), 1)) , new ItemStack(ItemInit.RING_BOX_L2.get()), 5, 30, 0F));
			level4Trades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 1),(new ItemStack(ItemInit.RING_BOX_L2.get(), 1)) , new ItemStack(ItemInit.RING_BOX_L3.get()), 5, 70, 0F));
			level5Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 3),(new ItemStack(ItemInit.ARMOR_RING_L3.get(), 1)) , new ItemStack(ItemInit.BLUE_RING.get()), 5, 70, 0F));
			level5Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 3),(new ItemStack(ItemInit.POWER_RING_L3.get(), 1)) , new ItemStack(ItemInit.RED_RING.get()), 5, 70, 0F));
		}
		if (event.getType() == VillagerProfession.ARMORER)
		{
			level1Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 2), new ItemStack(ItemInit.DEKU_SHIELD.get()), 1, 9, 1F));
			level3Trades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 1),(new ItemStack(ItemInit.SILVER_RUPEE.get(), 2)) , new ItemStack(ItemInit.SACRED_SHIELD.get()), 1, 30, 0F));
			level4Trades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 2), new ItemStack(ItemInit.MAGIC_ARMOR_SET.get()), 1, 200, 0F));
		}
		if (event.getType() == VillagerProfession.CLERIC)
		{
			level5Trades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 5), new ItemStack(ItemInit.HEART_PIECE.get()), 1, 50, 0F));
		}
		if (event.getType() == VillagerProfession.FISHERMAN)
		{
			level3Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 3), new ItemStack(ItemInit.FISHING_ROD.get()), 1, 50, 0F));
		}
		if (event.getType() == VillagerProfession.FLETCHER)
		{
			level2Trades.add(createTrade(new ItemStack(ItemInit.SILVER_RUPEE.get(), 1), new ItemStack(ItemInit.FAIRY_BOW.get()), 1, 50, 0F));
		}
		if (event.getType() == VillagerProfession.WEAPONSMITH)
		{
			level1Trades.add(createTrade(new ItemStack(ItemInit.MASTER_SWORD.get(), 1),(new ItemStack(ItemInit.MASTER_ORE.get(), 2)) , new ItemStack(ItemInit.MASTER_SWORD_V2.get()), 1, 70, 0F));
			level1Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 2), new ItemStack(ItemInit.KOKIRI_SWORD.get()), 1, 10, 0F));
			level2Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 6),(new ItemStack(ItemInit.BOOMERANG.get(), 1)) , new ItemStack(ItemInit.MAGIC_BOOMERANG.get()), 1, 20, 0F));
			level3Trades.add(createTrade(new ItemStack(ItemInit.SILVER_RUPEE.get(), 2), new ItemStack(ItemInit.RAZOR_SWORD.get()), 1, 10, 0F));
			level4Trades.add(createTrade(new ItemStack(ItemInit.RAZOR_SWORD.get(), 1),(new ItemStack(ItemInit.GOLD_RUPEE_BLOCK.get(), 2)) , new ItemStack(ItemInit.RAZOR_SWORD.get()), 1, 70, 0F));
			level5Trades.add(createTrade(new ItemStack(ItemInit.MASTER_SWORD_V2.get(), 1),(new ItemStack(ItemInit.MASTER_ORE.get(), 6)) , new ItemStack(ItemInit.TRUE_MASTER_SWORD.get()), 1, 100, 0F));
		}
		if (event.getType() == VillagerProfession.LEATHERWORKER)
		{
			level1Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 1), new ItemStack(ItemInit.BAIT_BAG.get()), 1, 8, 1F));
			level2Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 1), new ItemStack(ItemInit.SPOILS_BAG.get()), 1, 8, 1F));
			level2Trades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 1), new ItemStack(ItemInit.DELIVERY_BAG.get()), 1, 8, 1F));
			level3Trades.add(createTrade(new ItemStack(ItemInit.SILVER_RUPEE.get(), 1), new ItemStack(ItemInit.QUIVER.get()), 1, 8, 1F));
			level4Trades.add(createTrade(new ItemStack(ItemInit.BLUE_RUPEE.get(), 30),(new ItemStack(ItemInit.QUIVER.get(), 1)) , new ItemStack(ItemInit.BIG_QUIVER.get()), 5, 30, 0F));
			level5Trades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 1),(new ItemStack(ItemInit.BIG_QUIVER.get(), 1)) , new ItemStack(ItemInit.BIGGEST_QUIVER.get()), 5, 30, 0F));
			level3Trades.add(createTrade(new ItemStack(ItemInit.SILVER_RUPEE.get(), 1), new ItemStack(ItemInit.BULLET_BAG.get()), 1, 8, 1F));
			level4Trades.add(createTrade(new ItemStack(ItemInit.BLUE_RUPEE.get(), 30),(new ItemStack(ItemInit.BULLET_BAG.get(), 1)) , new ItemStack(ItemInit.BIG_BULLET_BAG.get()), 5, 30, 0F));
			level5Trades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 1),(new ItemStack(ItemInit.BIG_BULLET_BAG.get(), 1)) , new ItemStack(ItemInit.BIGGEST_BULLET_BAG.get()), 5, 30, 0F));
			level3Trades.add(createTrade(new ItemStack(ItemInit.SILVER_RUPEE.get(), 1), new ItemStack(ItemInit.BOMB_BAG.get()), 1, 8, 1F));
			level4Trades.add(createTrade(new ItemStack(ItemInit.BLUE_RUPEE.get(), 30),(new ItemStack(ItemInit.BOMB_BAG.get(), 1)) , new ItemStack(ItemInit.BIG_BOMB_BAG.get()), 5, 30, 0F));
			level5Trades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 1),(new ItemStack(ItemInit.BIG_BOMB_BAG.get(), 1)) , new ItemStack(ItemInit.BIGGEST_BOMB_BAG.get()), 5, 30, 0F));

		}
	}
	
	@SubscribeEvent
	public static void addCustomWanderingTrades(WandererTradesEvent event)
	{
		List<VillagerTrades.ITrade> genericTrades = event.getGenericTrades();
		List<VillagerTrades.ITrade> rareTrades = event.getRareTrades();

		genericTrades.add(createTrade(new ItemStack(ItemInit.RUPEE.get()), new ItemStack(ItemInit.DEKU_SEEDS.get()), 64, 5, 0F));
		genericTrades.add(createTrade(new ItemStack(ItemInit.RED_RUPEE.get(), 3), new ItemStack(ItemInit.APPRAISED_RING_BOX.get()), 4, 30, 0F));
		rareTrades.add(createTrade(new ItemStack(ItemInit.SILVER_RUPEE.get()), new ItemStack(ItemInit.HEART_PIECE.get()), 1, 200, 0F));
		rareTrades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 4), new ItemStack(ItemInit.MAGIC_FIRE_ARROW.get()), 1, 400, 5F));
		rareTrades.add(createTrade(new ItemStack(ItemInit.GOLD_RUPEE.get(), 4), new ItemStack(ItemInit.MAGIC_ICE_ARROW.get()), 1, 400, 5F));
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
