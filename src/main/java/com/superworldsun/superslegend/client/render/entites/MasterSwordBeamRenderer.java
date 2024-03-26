package com.superworldsun.superslegend.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.entities.MasterSwordBeamModel;
import com.superworldsun.superslegend.entities.projectiles.magic.MasterSwordBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MasterSwordBeamRenderer extends GeoEntityRenderer<MasterSwordBeamEntity> {
    public MasterSwordBeamRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MasterSwordBeamModel());
    }

    @Override
    public ResourceLocation getTextureLocation(MasterSwordBeamEntity animatable) {
        return new ResourceLocation(SupersLegendMain.MOD_ID,"textures/entity/master_sword_beam.png");
    }

    @Override
    public void render(MasterSwordBeamEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(2.5F, 1.0F, 2.5F);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
