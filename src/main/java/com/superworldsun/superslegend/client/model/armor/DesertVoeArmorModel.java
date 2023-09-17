package com.superworldsun.superslegend.client.model.armor;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.DesertVoeArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DesertVoeArmorModel extends GeoModel<DesertVoeArmorItem> {
    @Override
    public ResourceLocation getModelResource(DesertVoeArmorItem animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/desert_voe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DesertVoeArmorItem animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/armor/desert_voe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DesertVoeArmorItem animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/desert_voe.animation.json");
    }
}