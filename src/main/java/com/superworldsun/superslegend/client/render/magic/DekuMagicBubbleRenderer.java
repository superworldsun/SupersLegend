package com.superworldsun.superslegend.client.render.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.ModelLayers;
import com.superworldsun.superslegend.client.model.entities.DekuLinkBubbleModel;
import com.superworldsun.superslegend.entities.projectiles.magic.DekuMagicBubbleEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class DekuMagicBubbleRenderer extends EntityRenderer<DekuMagicBubbleEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            SupersLegendMain.MOD_ID,
            "textures/entity/deku_magic_bubble.png"
    );
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE);

    // Corrects the raw Blockbench model scale. This affects both first- and third-person rendering.
    private static final float MODEL_SIZE_NORMALIZATION = 3.2F;

    // Extra multiplier used only while viewing the charging bubble in first person.
    private static final float FIRST_PERSON_SCALE = 0.70F;

    // Distance in blocks that the first-person bubble is rendered in front of the camera.
    private static final double FIRST_PERSON_DISTANCE = 0.30D;

    // Distance the first-person bubble is moved down along the camera's local up/down axis.
    private static final double FIRST_PERSON_DOWN_OFFSET = 0.17D;
    private final DekuLinkBubbleModel<DekuMagicBubbleEntity> model;

    public DekuMagicBubbleRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new DekuLinkBubbleModel<>(context.bakeLayer(ModelLayers.DEKU_MAGIC_BUBBLE));
    }

    @Override
    public void render(
            @NotNull DekuMagicBubbleEntity entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight
    ) {
        poseStack.pushPose();
        applyFirstPersonChargingOffset(entity, partialTick, poseStack);
        poseStack.translate(0.0D, entity.getModelCenterYOffset(partialTick), 0.0D);
        rotateTowardTravelDirection(entity, partialTick, poseStack);
        float scale = entity.getVisualScale(partialTick) * MODEL_SIZE_NORMALIZATION;
        if (isFirstPersonChargingBubble(entity)) {
            scale *= FIRST_PERSON_SCALE;
        }
        poseStack.scale(scale, scale, scale);

        VertexConsumer vertices = bufferSource.getBuffer(RENDER_TYPE);
        model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTick, 0.0F, 0.0F);
        model.renderToBuffer(
                poseStack,
                vertices,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );
        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static void applyFirstPersonChargingOffset(
            DekuMagicBubbleEntity bubble,
            float partialTick,
            PoseStack poseStack
    ) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!isFirstPersonChargingBubble(bubble)) {
            return;
        }

        Camera camera = minecraft.gameRenderer.getMainCamera();
        Vector3f cameraLook = camera.getLookVector();
        Vector3f cameraUp = camera.getUpVector();
        Vec3 desiredPosition = camera.getPosition().add(
                cameraLook.x() * FIRST_PERSON_DISTANCE - cameraUp.x() * FIRST_PERSON_DOWN_OFFSET,
                cameraLook.y() * FIRST_PERSON_DISTANCE - cameraUp.y() * FIRST_PERSON_DOWN_OFFSET
                        - bubble.getModelCenterYOffset(partialTick),
                cameraLook.z() * FIRST_PERSON_DISTANCE - cameraUp.z() * FIRST_PERSON_DOWN_OFFSET
        );
        Vec3 renderedBubblePosition = new Vec3(
                Mth.lerp(partialTick, bubble.xo, bubble.getX()),
                Mth.lerp(partialTick, bubble.yo, bubble.getY()),
                Mth.lerp(partialTick, bubble.zo, bubble.getZ())
        );
        Vec3 offset = desiredPosition.subtract(renderedBubblePosition);
        poseStack.translate(offset.x, offset.y, offset.z);
    }

    private static boolean isFirstPersonChargingBubble(DekuMagicBubbleEntity bubble) {
        Minecraft minecraft = Minecraft.getInstance();
        return bubble.isCharging()
                && bubble.getOwner() == minecraft.player
                && minecraft.options.getCameraType().isFirstPerson();
    }

    private static void rotateTowardTravelDirection(
            DekuMagicBubbleEntity bubble,
            float partialTick,
            PoseStack poseStack
    ) {
        Entity owner = bubble.getOwner();
        float yaw;
        float pitch;
        Minecraft minecraft = Minecraft.getInstance();
        if (bubble.isCharging()
                && owner == minecraft.player
                && minecraft.options.getCameraType().isFirstPerson()) {
            Camera camera = minecraft.gameRenderer.getMainCamera();
            yaw = camera.getYRot();
            pitch = camera.getXRot();
        } else if (bubble.isCharging() && owner != null) {
            yaw = Mth.rotLerp(partialTick, owner.yRotO, owner.getYRot());
            pitch = Mth.lerp(partialTick, owner.xRotO, owner.getXRot());
        } else {
            Vec3 direction = bubble.getFlightDirection();
            if (direction.lengthSqr() < 1.0E-6D) {
                return;
            }

            direction = direction.normalize();
            double horizontalLength = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
            yaw = (float) (Mth.atan2(-direction.x, direction.z) * Mth.RAD_TO_DEG);
            pitch = (float) (Mth.atan2(-direction.y, horizontalLength) * Mth.RAD_TO_DEG);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DekuMagicBubbleEntity entity) {
        return TEXTURE;
    }
}
