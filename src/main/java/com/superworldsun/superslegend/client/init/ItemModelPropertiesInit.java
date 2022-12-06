package com.superworldsun.superslegend.client.init;

import static com.superworldsun.superslegend.items.ClawshotItem.spriteC;
import static com.superworldsun.superslegend.items.HookshotItem.sprite;
import static com.superworldsun.superslegend.items.LongshotItem.spriteL;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.ClawshotItem;
import com.superworldsun.superslegend.items.HookshotItem;
import com.superworldsun.superslegend.items.LongshotItem;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemModelPropertiesInit
{
	private static final IItemPropertyGetter IN_USE = ItemModelPropertiesInit::isInUse;
	private static final IItemPropertyGetter USE_PROGRESS = ItemModelPropertiesInit::getUseProgress;
	private static final IItemPropertyGetter NO_MAGIC = ItemModelPropertiesInit::isNoMagic;
	
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		ItemModelsProperties.register(ItemInit.LENS_OF_TRUTH.get(), new ResourceLocation("using"), IN_USE);
		// ItemModelsProperties.register(ItemInit.CLAWSHOT.get(), new ResourceLocation("pulling"), IN_USE);
		ItemModelsProperties.register(ItemInit.HEROS_BOW.get(), new ResourceLocation("pull"), USE_PROGRESS);
		ItemModelsProperties.register(ItemInit.HEROS_BOW.get(), new ResourceLocation("pulling"), IN_USE);
		ItemModelsProperties.register(ItemInit.FAIRY_BOW.get(), new ResourceLocation("pull"), USE_PROGRESS);
		ItemModelsProperties.register(ItemInit.FAIRY_BOW.get(), new ResourceLocation("pulling"), IN_USE);
		ItemModelsProperties.register(ItemInit.LYNEL_BOW_X3.get(), new ResourceLocation("pull"), USE_PROGRESS);
		ItemModelsProperties.register(ItemInit.LYNEL_BOW_X3.get(), new ResourceLocation("pulling"), IN_USE);
		ItemModelsProperties.register(ItemInit.LYNEL_BOW_X5.get(), new ResourceLocation("pull"), USE_PROGRESS);
		ItemModelsProperties.register(ItemInit.LYNEL_BOW_X5.get(), new ResourceLocation("pulling"), IN_USE);
		ItemModelsProperties.register(ItemInit.DEKU_SHIELD.get(), new ResourceLocation("blocking"), IN_USE);
		ItemModelsProperties.register(ItemInit.HYLIAN_SHIELD.get(), new ResourceLocation("blocking"), IN_USE);
		ItemModelsProperties.register(ItemInit.SACRED_SHIELD.get(), new ResourceLocation("blocking"), IN_USE);
		ItemModelsProperties.register(ItemInit.MIRROR_SHIELD.get(), new ResourceLocation("blocking"), IN_USE);
		ItemModelsProperties.register(ItemInit.DEKU_LEAF.get(), new ResourceLocation("no_magic"), NO_MAGIC);
		
		ItemModelsProperties.register(ItemInit.FISHING_ROD.get(), new ResourceLocation("cast"), (p_239422_0_, p_239422_1_, p_239422_2_) ->
		{
			if (p_239422_2_ == null)
			{
				return 0.0F;
			}
			else
			{
				boolean flag = p_239422_2_.getMainHandItem() == p_239422_0_;
				boolean flag1 = p_239422_2_.getOffhandItem() == p_239422_0_;
				if (p_239422_2_.getMainHandItem().getItem() instanceof FishingRodItem)
				{
					flag1 = false;
				}
				
				return (flag || flag1) && p_239422_2_ instanceof PlayerEntity && ((PlayerEntity) p_239422_2_).fishing != null ? 1.0F : 0.0F;
			}
		});
		
		registerFishingRodModelProperties(ItemInit.HOOKSHOT.get());
		registerFishingRodModelProperties(ItemInit.LONGSHOT.get());
		// registerFishingRodModelProperties(ItemInit.CLAWSHOT.get());
	}
	
	private static float getUseProgress(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity)
	{
		return livingEntity != null && livingEntity.getUseItem() == itemStack ? (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F : 0.0F;
	}
	
	private static float isInUse(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity)
	{
		return livingEntity != null && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
	}
	
	private static float isNoMagic(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity)
	{
		if (!(livingEntity instanceof PlayerEntity))
			return 0F;
		
		PlayerEntity player = (PlayerEntity) livingEntity;
		return !player.abilities.instabuild && ManaProvider.get(player).getMana() < 0.1F ? 1.0F : 0.0F;
	}
	
	public static void registerFishingRodModelProperties(Item hookshot)
	{
		ItemModelsProperties.register(hookshot, new ResourceLocation("cast"), (heldStack, world, livingEntity) ->
		{
			if (livingEntity == null)
			{
				return 0.0F;
			}
			else
			{
				boolean isMainhand = livingEntity.getMainHandItem() == heldStack;
				boolean isOffHand = livingEntity.getOffhandItem() == heldStack;
				if (livingEntity.getMainHandItem().getItem() instanceof HookshotItem || livingEntity.getMainHandItem().getItem() instanceof LongshotItem
						|| livingEntity.getMainHandItem().getItem() instanceof ClawshotItem)
				{
					isOffHand = false;
				}
				return (isMainhand || isOffHand) && livingEntity instanceof PlayerEntity && (sprite || spriteL || spriteC) ? 1.0F : 0.0F;
			}
		});
	}
}
