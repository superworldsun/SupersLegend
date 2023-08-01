package com.superworldsun.superslegend.items.customclass;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class RingItem extends Item implements ICurioItem {

    public RingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ICurio.DropRule getDropRule(LivingEntity livingEntity, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_KEEP;
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}