package com.superworldsun.superslegend.client.model.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.bombs.BombEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BombModel extends GeoModel<BombEntity> {
    @Override
    public ResourceLocation getModelResource(BombEntity animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,"geo/bomb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BombEntity animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,"textures/entity/bomb.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BombEntity animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,"animations/bomb.animation.json");
    }
}
