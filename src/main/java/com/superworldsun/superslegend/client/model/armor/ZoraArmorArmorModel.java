package com.superworldsun.superslegend.client.model.armor;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.ZoraArmorArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ZoraArmorArmorModel extends GeoModel<ZoraArmorArmor> {
    @Override
    public ResourceLocation getModelResource(ZoraArmorArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/zora_armor_armor.geo.json");
    }

    //TODO, make it so when the players eyes in water, the zora_armor_armor_covered texture is in use.

    @Override
    public ResourceLocation getTextureResource(ZoraArmorArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/armor/zora_armor_armor.png");
        //return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/armor/zora_armor_armor_covered.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ZoraArmorArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/zora_armor_armor.animation.json");
    }
}