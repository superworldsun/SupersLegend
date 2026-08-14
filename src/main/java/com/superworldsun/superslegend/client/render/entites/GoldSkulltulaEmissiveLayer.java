package com.superworldsun.superslegend.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.mobs.GoldSkulltulaEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GoldSkulltulaEmissiveLayer extends GeoRenderLayer<GoldSkulltulaEntity> {
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(
            SupersLegendMain.MOD_ID, "textures/entity/mobs/golden_skulltula_e.png");

    public GoldSkulltulaEmissiveLayer(GeoRenderer<GoldSkulltulaEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, GoldSkulltulaEntity entity,
                       BakedGeoModel bakedModel, RenderType renderType,
                       MultiBufferSource bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay) {
        RenderType emissive = RenderType.entityTranslucentEmissive(EMISSIVE_TEXTURE);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, entity,
                emissive, bufferSource.getBuffer(emissive), partialTick,
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
    }
}
