package com.superworldsun.superslegend.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.entities.GoldSkulltulaModel;
import com.superworldsun.superslegend.entities.mobs.GoldSkulltulaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GoldSkulltulaRenderer extends GeoEntityRenderer<GoldSkulltulaEntity> {
    private static final float MODEL_SCALE = 0.36F;
    private static final double CEILING_CENTER_Z_OFFSET = -0.55D;
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            SupersLegendMain.MOD_ID, "textures/entity/mobs/gold_skulltula.png");

    public GoldSkulltulaRenderer(EntityRendererProvider.Context context) {
        super(context, new GoldSkulltulaModel());
        addRenderLayer(new GoldSkulltulaEmissiveLayer(this));
        shadowRadius = 0.22F;
    }

    @Override
    public ResourceLocation getTextureLocation(GoldSkulltulaEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void applyRotations(GoldSkulltulaEntity entity, PoseStack poseStack,
                                  float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick);
        if (entity.isAnchored() && entity.getAttachmentDirection() == Direction.UP) {
            // Rotate the upright Blockbench model onto the ceiling with its
            // back toward the support and its belly facing down into the room.
            // Rotating around this raised pivot also introduces a half-block
            // horizontal displacement. Cancel it so ceiling models remain
            // centered on the entity/support position without changing their
            // carefully tuned ceiling height.
            // The authored model's visual center is slightly forward of its root
            // pivot. The extra 0.05 block keeps it centered on the ceiling cell.
            poseStack.translate(0.0D, 0.5D, CEILING_CENTER_Z_OFFSET);
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.translate(0.0D, -0.5D, 0.0D);
        } else if (entity.isDetached()) {
            // DETACHED is synchronized and stable. Do not also check onGround:
            // that client-side flag can briefly alternate while the stationary
            // entity is corrected by the server, making the model flash between
            // its upright and flat poses.
            poseStack.translate(0.0D, 0.16D, 0.0D);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.translate(0.0D, -0.16D, 0.0D);
        }

        // Match the second 20% model reduction applied to the entity's collision dimensions.
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}
