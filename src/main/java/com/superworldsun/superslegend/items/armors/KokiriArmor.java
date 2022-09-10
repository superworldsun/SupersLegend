package com.superworldsun.superslegend.items.armors;

import java.util.HashMap;
import java.util.Map;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.KokiriArmorModel;
import com.superworldsun.superslegend.client.model.armor.KokiriBootsModel;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KokiriArmor extends NonEnchantArmor
{
	private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();
	
	public KokiriArmor(EquipmentSlotType slot, Properties properties)
	{
		super(ArmourInit.KOKIRI, slot, properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
	{
		if (!MODELS_CACHE.containsKey(armorSlot))
		{
			if (armorSlot != EquipmentSlotType.FEET)
				MODELS_CACHE.put(armorSlot, new KokiriArmorModel(armorSlot));
			else
				MODELS_CACHE.put(armorSlot, new KokiriBootsModel());
		}
		
		return (M) MODELS_CACHE.get(armorSlot);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type)
	{
		if (armorSlot != EquipmentSlotType.FEET)
			return SupersLegendMain.MOD_ID + ":textures/models/armor/kokiri_armor.png";
		else
			return SupersLegendMain.MOD_ID + ":textures/models/armor/kokiri_boots.png";
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "Traditional Heros Garb"));
	}
}