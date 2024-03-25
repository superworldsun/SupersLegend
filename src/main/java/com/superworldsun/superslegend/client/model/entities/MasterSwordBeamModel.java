package com.superworldsun.superslegend.client.model.entities;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.magic.MasterSwordBeamEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MasterSwordBeamModel extends GeoModel<MasterSwordBeamEntity> {
    @Override
    public ResourceLocation getModelResource(MasterSwordBeamEntity animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,"geo/master_sword_beam.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MasterSwordBeamEntity animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,"textures/entity/master_sword_beam.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MasterSwordBeamEntity animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,"animations/master_sword_beam.animation.json");
    }
}
