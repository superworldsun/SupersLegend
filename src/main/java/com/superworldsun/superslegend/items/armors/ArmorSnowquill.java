package com.superworldsun.superslegend.items.armors;

import java.util.HashMap;
import java.util.Map;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.SnowquillBootsModel;
import com.superworldsun.superslegend.client.model.armor.SnowquillHelmetModel;
import com.superworldsun.superslegend.client.model.armor.SnowquillTrousersModel;
import com.superworldsun.superslegend.client.model.armor.SnowquillTunicModel;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.EffectInit;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class ArmorSnowquill extends NonEnchantArmor {
	private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();

	public ArmorSnowquill(EquipmentSlotType slot) {
		super(ArmourInit.SNOWQUILL, slot, new Properties().tab(SupersLegendMain.APPAREL));
	}

	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default) {
		if (MODELS_CACHE.isEmpty()) {
			MODELS_CACHE.put(EquipmentSlotType.HEAD, new SnowquillHelmetModel());
			MODELS_CACHE.put(EquipmentSlotType.CHEST, new SnowquillTunicModel());
			MODELS_CACHE.put(EquipmentSlotType.LEGS, new SnowquillTrousersModel());
			MODELS_CACHE.put(EquipmentSlotType.FEET, new SnowquillBootsModel());
		}

		return (M) MODELS_CACHE.get(armorSlot);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();

		if (entity.hasEffect(EffectInit.FREEZE.get())) {
			if ((entity.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof ArmorSnowquill)
					&& (entity.getItemBySlot(EquipmentSlotType.FEET).getItem() instanceof ArmorSnowquill)
					&& (entity.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof ArmorSnowquill)
					&& (entity.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof ArmorSnowquill)) {
				entity.removeEffect(EffectInit.FREEZE.get());
			}
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return SupersLegendMain.MOD_ID + ":textures/models/armor/snowquill_armor.png";
	}
}