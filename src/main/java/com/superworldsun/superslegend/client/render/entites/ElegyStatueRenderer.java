package com.superworldsun.superslegend.client.render.entites;

import com.superworldsun.superslegend.client.model.entities.ElegyStatueModel;
import com.superworldsun.superslegend.entities.statue.ElegyStatueEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ElegyStatueRenderer extends GeoEntityRenderer<ElegyStatueEntity> {
    public ElegyStatueRenderer(EntityRendererProvider.Context context) {
        super(context, new ElegyStatueModel());
        addRenderLayer(new ElegyStatueCrackLayer(this));
        shadowRadius = 0.45F;
    }
}
