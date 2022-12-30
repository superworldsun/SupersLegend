package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

public abstract class RingItem extends Item implements ICurioItem
{
	public RingItem(Properties properties)
	{
		super(properties.stacksTo(1).tab(ItemGroupInit.APPAREL));
	}
	
	@Override
	public DropRule getDropRule(LivingEntity livingEntity, ItemStack stack)
	{
		return ICurio.DropRule.ALWAYS_KEEP;
	}
	
	@Override
	public SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack)
	{
		return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
	}

	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
}
