package com.superworldsun.superslegend.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.superworldsun.superslegend.entities.statue.ElegyStatueEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelBakery;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ElegyStatueCrackLayer extends GeoRenderLayer<ElegyStatueEntity> {
    public ElegyStatueCrackLayer(GeoRenderer<ElegyStatueEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, ElegyStatueEntity statue, BakedGeoModel bakedModel,
                       RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay) {
        int stage = statue.getBreakStage();
        if (stage < 0 || stage >= ModelBakery.DESTROY_TYPES.size()) {
            return;
        }

        PoseStack.Pose pose = poseStack.last();
        VertexConsumer crackBuffer = new SheetedDecalTextureGenerator(
                bufferSource.getBuffer(ModelBakery.DESTROY_TYPES.get(stage)),
                pose.pose(), pose.normal(), 1.0F);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, statue, renderType,
                crackBuffer, partialTick, packedLight, packedOverlay,
                1.0F, 1.0F, 1.0F, 1.0F);
    }
}
