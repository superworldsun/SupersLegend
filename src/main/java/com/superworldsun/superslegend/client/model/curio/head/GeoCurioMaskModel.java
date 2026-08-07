package com.superworldsun.superslegend.client.model.curio.head;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.curios.head.masks.GeoCurioMaskItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GeoCurioMaskModel extends GeoModel<GeoCurioMaskItem> {
    @Override
    public ResourceLocation getModelResource(GeoCurioMaskItem mask) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "geo/masks/" + mask.getModelName() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoCurioMaskItem mask) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "textures/curio/" + mask.getModelName() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoCurioMaskItem mask) {
        return new ResourceLocation(SupersLegendMain.MOD_ID, "animations/masks/static_mask.animation.json");
    }
}
