package com.superworldsun.superslegend.client.model.armor;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.SnowquillArmor;
import com.superworldsun.superslegend.items.armors.SnowquillArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SnowQuillArmorModel extends GeoModel<SnowquillArmor> {
    @Override
    public ResourceLocation getModelResource(SnowquillArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/snow_quill.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SnowquillArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/armor/snow_quill.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SnowquillArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/snow_quill.animation.json");
    }
}