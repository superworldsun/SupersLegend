package com.superworldsun.superslegend.client.init;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.mana.ManaHelper;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ItemModelPropertiesInit {
	private static final IItemPropertyGetter IN_USE = ItemModelPropertiesInit::isInUse;
	private static final IItemPropertyGetter USE_PROGRESS = ItemModelPropertiesInit::getUseProgress;
	private static final IItemPropertyGetter NO_MAGIC = ItemModelPropertiesInit::isNoMagic;
	private static final IItemPropertyGetter IS_FISHING = ItemModelPropertiesInit::isFishing;

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
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
		// ItemModelsProperties.register(ItemInit.MIRROR_SHIELD.get(), new ResourceLocation("blocking"), IN_USE);
		ItemModelsProperties.register(ItemInit.DEKU_LEAF.get(), new ResourceLocation("no_magic"), NO_MAGIC);
		ItemModelsProperties.register(ItemInit.FISHING_ROD.get(), new ResourceLocation("cast"), IS_FISHING);
		///TODO the models for these still dont switch to their fired model
		ItemModelsProperties.register(ItemInit.HOOKSHOT.get(), new ResourceLocation("pulling"), IN_USE);
		ItemModelsProperties.register(ItemInit.LONGSHOT.get(), new ResourceLocation("pulling"), IN_USE);
		// registerFishingRodModelProperties(ItemInit.CLAWSHOT.get());
	}

	private static float getUseProgress(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity) {
		return livingEntity != null && livingEntity.getUseItem() == itemStack ? (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F : 0.0F;
	}

	private static float isInUse(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity) {
		return livingEntity != null && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
	}

	private static float isNoMagic(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity) {
		if (!(livingEntity instanceof PlayerEntity)) {
			return 0.0F;
		}

		PlayerEntity player = (PlayerEntity) livingEntity;
		return !ManaHelper.hasMana(player, 0.1F) ? 1.0F : 0.0F;
	}

	private static float isFishing(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity) {
		if (!(livingEntity instanceof PlayerEntity)) {
			return 0.0F;
		}

		PlayerEntity player = (PlayerEntity) livingEntity;
		boolean isInMainHand = player.getMainHandItem() == itemStack;
		boolean isInOffhand = player.getOffhandItem() == itemStack;

		if (player.getMainHandItem().getItem() instanceof FishingRodItem) {
			isInOffhand = false;
		}

		return (isInMainHand || isInOffhand) && player.fishing != null ? 1.0F : 0.0F;
	}
}
