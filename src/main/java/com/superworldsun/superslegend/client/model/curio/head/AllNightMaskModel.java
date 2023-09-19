package com.superworldsun.superslegend.client.model.curio.head;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.ZoraArmorArmor;
import com.superworldsun.superslegend.items.curios.head.masks.AllNightMask;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AllNightMaskModel extends GeoModel<AllNightMask> {
    @Override
    public ResourceLocation getModelResource(AllNightMask animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/all_night_mask.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AllNightMask animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/curio/all_night_mask.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AllNightMask animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/all_night_mask.animation.json");
    }
}