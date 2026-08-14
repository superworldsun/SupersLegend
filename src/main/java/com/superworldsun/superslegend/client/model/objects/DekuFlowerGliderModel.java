package com.superworldsun.superslegend.client.model.objects;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.player.DekuFlowerGliderRenderLayer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DekuFlowerGliderModel extends GeoModel<DekuFlowerGliderRenderLayer.GliderAnimatable> {
    @Override
    public ResourceLocation getModelResource(DekuFlowerGliderRenderLayer.GliderAnimatable animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/objects/deku_flower_glider.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DekuFlowerGliderRenderLayer.GliderAnimatable animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/deku_flower_glider.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DekuFlowerGliderRenderLayer.GliderAnimatable animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/objects/deku_flower_glider_flight.animation.json");
    }
}
