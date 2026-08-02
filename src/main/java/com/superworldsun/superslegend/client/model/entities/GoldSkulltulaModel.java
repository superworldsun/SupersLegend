package com.superworldsun.superslegend.client.model.entities;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.mobs.GoldSkulltulaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GoldSkulltulaModel extends GeoModel<GoldSkulltulaEntity> {
    @Override
    public ResourceLocation getModelResource(GoldSkulltulaEntity entity) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/mobs/gold_skulltula.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GoldSkulltulaEntity entity) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/mobs/gold_skulltula.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GoldSkulltulaEntity entity) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/mobs/gold_skulltula.animation.json");
    }
}
