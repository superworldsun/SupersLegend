package com.superworldsun.superslegend.client.model.armor;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.ZoraArmorArmor;
import com.superworldsun.superslegend.items.armors.ZoraFlippersArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ZoraFlippersArmorModel extends GeoModel<ZoraFlippersArmor> {
    @Override
    public ResourceLocation getModelResource(ZoraFlippersArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/zora_flippers.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ZoraFlippersArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/armor/zora_flippers.png");
        //return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/armor/zora_armor_armor_covered.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ZoraFlippersArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/zora_flippers.animation.json");
    }
}