package com.superworldsun.superslegend.client.render.player;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.client.model.objects.DekuFlowerGliderModel;
import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import org.joml.Matrix4f;

/** Renders the exported flower once in each of Deku Link's raised hands. */
public class DekuFlowerGliderRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    // Deku Link's custom arm reaches 8.5 model pixels below its shoulder pivot.
    // HumanoidModel#translateToHand only applies the arm pivot/rotation, so a
    // custom GEO attachment must travel the remaining distance to the palm.
    private static final double DEKU_HAND_DISTANCE = 8.5D / 16.0D;
    // The glide pose rolls each arm 67.5 degrees from vertical. The flower is
    // kept at the transformed hand position, then this roll is undone so its
    // stem remains upright.
    private static final float GLIDE_ARM_ROLL_DEGREES = 67.5F;
    // Fan the stems away from Deku Link's head to match the wide glider pose.
    // These are mirrored so both flowers keep the same pose in opposite hands.
    private static final float FLOWER_OUTWARD_ROLL_DEGREES = 22.5F;
    private static final float FLOWER_OUTWARD_YAW_DEGREES = 18.0F;
    private static final GliderAnimatable FLOWER = new GliderAnimatable();
    private final GeoObjectRenderer<GliderAnimatable> flowerRenderer =
            new HandFlowerRenderer().withScale(1.6F);

    public DekuFlowerGliderRenderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight,
                       AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!DekuFlowerFlightEvents.isGliding(player) || player.isInvisible()) {
            return;
        }

        renderFlower(HumanoidArm.RIGHT, poseStack, buffers, packedLight);
        renderFlower(HumanoidArm.LEFT, poseStack, buffers, packedLight);
    }

    private void renderFlower(HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffers, int packedLight) {
        poseStack.pushPose();
        getParentModel().translateToHand(arm, poseStack);
        poseStack.translate(0.0D, DEKU_HAND_DISTANCE, 0.0D);

        // The GEO origin is the bottom of the stem. Place that origin inside the
        // palm, then aim the stem away from the hand along the raised arm.
        boolean left = arm == HumanoidArm.LEFT;
        poseStack.translate(left ? 0.035D : -0.035D, -0.03D, 0.0D);

        // translateToHand also carries the arm's T-pose roll into the flower.
        // Keep the flower anchored at the palm, but cancel that roll so the
        // stem and bloom remain upright instead of turning sideways with the arm.
        poseStack.mulPose(Axis.ZP.rotationDegrees(left
                ? GLIDE_ARM_ROLL_DEGREES
                : -GLIDE_ARM_ROLL_DEGREES));

        // Move each complete flower closer to Deku Link without changing its rotation.
        poseStack.translate(left ? -0.2D : 0.2D, 0.0D, 0.0D);

        // Turn the exported flower upright without changing its hand anchor,
        // turn its front around the vertical axis without flipping it, and fan
        // the two stems outward into the broad pose used by the reference.
        // Seat the stem slightly deeper in Deku Link's fist.
        poseStack.translate(0.0D, 0.1D, 0.0D);

        // Seat the stem slightly deeper in Deku Link's fist.
        poseStack.translate(0.0D, 0.1D, 0.0D);

        // Seat the stem slightly deeper in Deku Link's fist.
        poseStack.translate(0.0D, 0.1D, 0.0D);

        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(left
                ? FLOWER_OUTWARD_ROLL_DEGREES
                : -FLOWER_OUTWARD_ROLL_DEGREES));
        // Tip both flowers outward and downward, mirrored between the hands.
        poseStack.mulPose(Axis.ZP.rotationDegrees(left ? 8.0F : -8.0F));
        // Tip both flowers outward and downward, mirrored between the hands.
        poseStack.mulPose(Axis.ZP.rotationDegrees(left ? 8.0F : -8.0F));
        // Tip both flowers outward and downward, mirrored between the hands.
        poseStack.mulPose(Axis.ZP.rotationDegrees(left ? 8.0F : -8.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(left
                ? -FLOWER_OUTWARD_YAW_DEGREES
                : FLOWER_OUTWARD_YAW_DEGREES));

        // The flowers face away from one another in the reference instead of
        // both copies presenting the same side of the exported model.
        if (left) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        }

        ResourceLocation texture = flowerRenderer.getTextureLocation(FLOWER);
        RenderType renderType = flowerRenderer.getRenderType(FLOWER, texture, buffers, 0.0F);
        VertexConsumer vertexConsumer = buffers.getBuffer(renderType);
        flowerRenderer.render(poseStack, FLOWER, buffers, renderType, vertexConsumer, packedLight);
        poseStack.popPose();
    }

    /**
     * GeoObjectRenderer normally centers its output inside a block by adding a
     * half-block translation. A wearable attachment must retain the exact
     * origin supplied by translateToHand instead.
     */
    private static final class HandFlowerRenderer extends GeoObjectRenderer<GliderAnimatable> {
        private HandFlowerRenderer() {
            super(new DekuFlowerGliderModel());
        }

        @Override
        public void preRender(PoseStack poseStack, GliderAnimatable animatable, BakedGeoModel model,
                              MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                              float partialTick, int packedLight, int packedOverlay,
                              float red, float green, float blue, float alpha) {
            this.objectRenderTranslations = new Matrix4f(poseStack.last().pose());
            scaleModelForRender(this.scaleWidth, this.scaleHeight, poseStack, animatable, model,
                    isReRender, partialTick, packedLight, packedOverlay);
        }
    }

    public static final class GliderAnimatable implements GeoAnimatable {
        private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

        @Override
        public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
            // The supplied animation currently has no keyed bones; hand movement
            // comes from the player model so the flower remains locked to the palm.
        }

        @Override
        public AnimatableInstanceCache getAnimatableInstanceCache() {
            return cache;
        }

        @Override
        public double getTick(Object context) {
            return 0.0D;
        }
    }
}
