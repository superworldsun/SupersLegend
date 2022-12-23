package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.*;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ArmorSnowquill extends NonEnchantArmor
{
	private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();

	public ArmorSnowquill(EquipmentSlotType slot)
	{
		// change armor material if needed
		super(ArmourInit.SNOWQUILL, slot, new Properties().tab(SupersLegendMain.APPAREL));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
	{
		if (MODELS_CACHE.isEmpty())
		{
			MODELS_CACHE.put(EquipmentSlotType.HEAD, new SnowquillHelmetModel());
			MODELS_CACHE.put(EquipmentSlotType.CHEST, new SnowquillTunicModel());
			MODELS_CACHE.put(EquipmentSlotType.LEGS, new SnowquillTrousersModel());
			MODELS_CACHE.put(EquipmentSlotType.FEET, new SnowquillBootsModel());
		}
		
		return (M) MODELS_CACHE.get(armorSlot);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return SupersLegendMain.MOD_ID + ":textures/models/armor/snowquill_armor.png";
	}
}