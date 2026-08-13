package com.superworldsun.superslegend.client.model.entities;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.statue.ElegyStatueEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ElegyStatueModel extends GeoModel<ElegyStatueEntity> {
    private static final ResourceLocation STATIC_ANIMATION = new ResourceLocation(
            SupersLegendMain.MOD_ID, "animations/objects/elegy_of_emptyness_statue.animation.json");

    @Override
    public ResourceLocation getModelResource(ElegyStatueEntity entity) {
        return entity.getStatueVariant().model();
    }

    @Override
    public ResourceLocation getTextureResource(ElegyStatueEntity entity) {
        return entity.getStatueVariant().texture();
    }

    @Override
    public ResourceLocation getAnimationResource(ElegyStatueEntity entity) {
        return STATIC_ANIMATION;
    }
}
