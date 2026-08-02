package com.superworldsun.superslegend.client.model.entities;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.NayrusLoveCrystalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NayrusLoveCrystalModel extends GeoModel<NayrusLoveCrystalEntity> {
    @Override
    public ResourceLocation getModelResource(NayrusLoveCrystalEntity entity) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/objects/nayrus_love_crystal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NayrusLoveCrystalEntity entity) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/nayrus_love_crystal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NayrusLoveCrystalEntity entity) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,
                "animations/objects/nayrus_love_crystal.animation.json");
    }
}
