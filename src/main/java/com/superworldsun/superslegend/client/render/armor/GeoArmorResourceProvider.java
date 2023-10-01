package com.superworldsun.superslegend.client.render.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface GeoArmorResourceProvider {
    ResourceLocation getTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot);
}