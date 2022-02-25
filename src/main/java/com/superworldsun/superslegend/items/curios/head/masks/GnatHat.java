package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.GnatHatModel;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.registries.ArmourInit;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GnatHat extends Item implements IEntityResizer, ICurioItem
{
	private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();
	
	public GnatHat(Properties properties) {
		super(properties);
	}

	/*
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
	{
		if (!MODELS_CACHE.containsKey(armorSlot))
		{
			MODELS_CACHE.put(armorSlot, new GnatHatModel());
		}

		return (M) MODELS_CACHE.get(armorSlot);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return SupersLegendMain.MOD_ID + ":textures/models/armor/gnat_hat.png";
	}
	*/

	@Override
	public float getScale(PlayerEntity player)
	{
		return 0.2F;
	}
}
