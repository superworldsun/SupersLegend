package com.superworldsun.superslegend.client.render.hookshot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.ModelLayers;
import com.superworldsun.superslegend.client.model.hooks.HookshotModel;
import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HookshotRender extends EntityRenderer<HookshotEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/hookshot.png");
    private static final ResourceLocation CHAIN_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/chain.png");
    private static final RenderType CHAIN_LAYER = RenderType.entityCutoutNoCull(CHAIN_TEXTURE);
    private final HookshotModel model;

    public HookshotRender(EntityRendererProvider.Context context) {
        super(context);
        model = new HookshotModel(context.bakeLayer(ModelLayers.HOOKSHOT));
    }

    @Override
    public boolean shouldRender(HookshotEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
        return entity.isAlive();
    }

    @Override
    public void render(HookshotEntity hookshotEntity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Player player = hookshotEntity.getHookOwner();
        if (player == null) {
            player = Minecraft.getInstance().player;
        }
        if (player != null) {
            poseStack.pushPose();
            Vec3 difference = HookshotRenderHelper.getChainVector(hookshotEntity, player,
                    hookshotEntity.getFiredHand(), partialTicks, entityRenderDispatcher);
            Minecraft minecraft = Minecraft.getInstance();
            float arrivalProgress = player == minecraft.player
                    && minecraft.options.getCameraType().isFirstPerson()
                    ? hookshotEntity.getReturnArrivalProgress(partialTicks)
                    : 0.0F;
            if (arrivalProgress > 0.0F) {
                poseStack.translate(difference.x * arrivalProgress, difference.y * arrivalProgress, difference.z * arrivalProgress);
                difference = difference.scale(1.0D - arrivalProgress);
            }
            float distanceX = (float) difference.x;
            float distanceY = (float) difference.y;
            float distanceZ = (float) difference.z;

            float distanceSquared = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
            if (distanceSquared >= 0.01F) {
                renderChain(distanceX, distanceY, distanceZ, partialTicks, hookshotEntity.tickCount, hookshotEntity.isRetrieving(), poseStack, buffer, packedLight);
                renderSecondChain(distanceX, distanceY, distanceZ, partialTicks, hookshotEntity.tickCount, hookshotEntity.isRetrieving(), poseStack, buffer, packedLight);
            }

            if (arrivalProgress < 0.999F) {
                Vec3 visualDirection = hookshotEntity.getVisualDirection();
                renderHook((float) visualDirection.x, (float) visualDirection.y, (float) visualDirection.z,
                        partialTicks, hookshotEntity.tickCount, poseStack, buffer, packedLight);
            }

            poseStack.popPose();
        }

        super.render(hookshotEntity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderHook(float x, float y, float z, float partialTicks, int age, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float lengthXY = Mth.sqrt(x * x + z * z);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(z, x)) - Mth.HALF_PI));
        poseStack.mulPose(Axis.XP.rotation((float) (-Math.atan2(lengthXY, y)) - Mth.HALF_PI));
        poseStack.scale(1.0F, 1.0F, 1.0F);

        VertexConsumer vertexConsumer = buffer.getBuffer(model.renderType(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }

    private void renderChain(float x, float y, float z, float partialTicks, int age, boolean reversing, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        renderChainSegment(x, y, z, partialTicks, age, reversing, poseStack, buffer, packedLight, 0.75F);
    }

    private void renderSecondChain(float x, float y, float z, float partialTicks, int age, boolean reversing, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        renderChainSegment(x, y, z, partialTicks, age, reversing, poseStack, buffer, packedLight, -0.10F);
    }

    private void renderChainSegment(float x, float y, float z, float partialTicks, int age, boolean reversing, PoseStack poseStack, MultiBufferSource buffer, int packedLight, float yOffset) {
        float lengthXY = Mth.sqrt(x * x + z * z);
        float squaredLength = x * x + y * y + z * z;
        float length = Mth.sqrt(squaredLength);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(z, x)) - Mth.HALF_PI));
        poseStack.mulPose(Axis.XP.rotation((float) (-Math.atan2(lengthXY, y)) - Mth.HALF_PI));

        VertexConsumer vertexConsumer = buffer.getBuffer(CHAIN_LAYER);
        float textureScroll = ((float) age + partialTicks) * (reversing ? 0.01F : -0.01F);
        float h = textureScroll;
        float i = Mth.sqrt(squaredLength) / 32.0F + textureScroll;
        float k = 0.0F;
        float l = yOffset;
        float m = 0.0F;
        PoseStack.Pose pose = poseStack.last();

        for(int n = 1; n <= 8; ++n) {
            float o = Mth.sin((float) n * Mth.TWO_PI / 8.0F) * 0.125F;
            float p = Mth.cos((float) n * Mth.TWO_PI / 8.0F) * 0.125F;
            float q = (float) n / 8.0F;

            vertexConsumer.vertex(pose.pose(), k * 0.2F, l * 0.2F, 0.0F).color(255, 255, 255, 255).uv(m, h).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(pose.normal(), 0.0F, -1.0F, 0.0F).endVertex();
            vertexConsumer.vertex(pose.pose(), k, l, length).color(255, 255, 255, 255).uv(m, i).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(pose.normal(), 0.0F, -1.0F, 0.0F).endVertex();
            vertexConsumer.vertex(pose.pose(), o, p, length).color(255, 255, 255, 255).uv(q, i).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(pose.normal(), 0.0F, -1.0F, 0.0F).endVertex();
            vertexConsumer.vertex(pose.pose(), o * 0.2F, p * 0.2F, 0.0F).color(255, 255, 255, 255).uv(q, h).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(pose.normal(), 0.0F, -1.0F, 0.0F).endVertex();

            k = o;
            l = p;
            m = q;
        }

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(HookshotEntity entity) {
        return TEXTURE;
    }
}
