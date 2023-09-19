package com.superworldsun.superslegend.client.model.armor;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.BarbarianArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BarbarianArmorModel extends GeoModel<BarbarianArmor> {
    @Override
    public ResourceLocation getModelResource(BarbarianArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/barbarian_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BarbarianArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/armor/barbarian_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BarbarianArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/barbarian_armor.animation.json");
    }
}