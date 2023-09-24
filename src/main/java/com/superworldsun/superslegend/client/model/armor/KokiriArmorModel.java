package com.superworldsun.superslegend.client.model.armor;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.armors.DesertVoeArmorItem;
import com.superworldsun.superslegend.items.armors.KokiriArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KokiriArmorModel extends GeoModel<KokiriArmor> {
    @Override
    public ResourceLocation getModelResource(KokiriArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/kokiri_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KokiriArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/armor/kokiri_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KokiriArmor animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/kokiri_armor.animation.json");
    }
}