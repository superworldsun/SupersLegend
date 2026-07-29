package com.superworldsun.superslegend.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.entities.MasterSwordBeamModel;
import com.superworldsun.superslegend.entities.projectiles.magic.MasterSwordBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
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

    @Override
    protected void applyRotations(MasterSwordBeamEntity entity, PoseStack poseStack,
                                  float ageInTicks, float rotationYaw, float partialTick) {
        Vec3 direction = entity.getDeltaMovement();
        if (direction.lengthSqr() < 1.0E-7D) {
            super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick);
            return;
        }

        direction = direction.normalize();
        double horizontalDistance = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        float yaw = (float) (Mth.atan2(-direction.x, direction.z) * Mth.RAD_TO_DEG);
        float pitch = (float) (Mth.atan2(-direction.y, horizontalDistance) * Mth.RAD_TO_DEG);

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
    }
}
