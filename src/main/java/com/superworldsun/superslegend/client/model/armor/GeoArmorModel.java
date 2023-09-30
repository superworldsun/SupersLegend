package com.superworldsun.superslegend.client.model.armor;

import com.superworldsun.superslegend.client.render.armor.GeoArmorRendererExtension;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.model.GeoModel;

public class GeoArmorModel<T extends Item & GeoItem> extends GeoModel<T> {
    private final GeoArmorRendererExtension<T> extension;

    public GeoArmorModel(GeoArmorRendererExtension<T> extension) {
        this.extension = extension;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return extension.getModelResource();
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return extension.getTextureResource();
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return extension.getAnimationResource();
    }
}