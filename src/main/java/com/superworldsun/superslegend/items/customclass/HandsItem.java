package com.superworldsun.superslegend.items.customclass;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

import org.jetbrains.annotations.NotNull;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.*;

public class HandsItem extends Item implements ICurioItem {

    public HandsItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ICurio.@NotNull DropRule getDropRule(LivingEntity livingEntity, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_KEEP;
    }

    @Override
    public ICurio.@NotNull SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}