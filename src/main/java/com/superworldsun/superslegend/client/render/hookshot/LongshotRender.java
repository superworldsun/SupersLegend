package com.superworldsun.superslegend.client.render.hookshot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.ModelLayers;
import com.superworldsun.superslegend.client.model.hooks.LongshotModel;
import com.superworldsun.superslegend.entities.projectiles.hooks.LongshotEntity;
import net.minecraft.client.Minecraft;
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
public class LongshotRender extends EntityRenderer<com.superworldsun.superslegend.entities.projectiles.hooks.LongshotEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/hookshot.png");
    private static final ResourceLocation CHAIN_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/chain.png");
    private static final RenderType CHAIN_LAYER = RenderType.entityCutoutNoCull(CHAIN_TEXTURE);
    private final LongshotModel<LongshotEntity> model;

    public LongshotRender(EntityRendererProvider.Context context) {
        super(context);
        this.model = new LongshotModel<>(context.bakeLayer(ModelLayers.LONGSHOT));
    }

    @Override
    public boolean shouldRender(LongshotEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
        return entity.isAlive();
    }

    @Override
    public void render(LongshotEntity longshotEntity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Player player = longshotEntity.getHookOwner();
        if (player == null) {
            player = Minecraft.getInstance().player;
        }
        if (player != null) {
            poseStack.pushPose();
            Vec3 difference = HookshotRenderHelper.getChainVector(longshotEntity, player,
                    longshotEntity.getFiredHand(), partialTicks, entityRenderDispatcher);
            Minecraft minecraft = Minecraft.getInstance();
            float arrivalProgress = player == minecraft.player
                    && minecraft.options.getCameraType().isFirstPerson()
                    ? longshotEntity.getReturnArrivalProgress(partialTicks)
                    : 0.0F;
            if (arrivalProgress > 0.0F) {
                poseStack.translate(difference.x * arrivalProgress, difference.y * arrivalProgress, difference.z * arrivalProgress);
                difference = difference.scale(1.0D - arrivalProgress);
            }

            if (difference.lengthSqr() >= 0.01D) {
                renderChain(difference, partialTicks, longshotEntity.tickCount, longshotEntity.isRetrieving(), poseStack, buffer, packedLight);
                renderSecondChain(difference, partialTicks, longshotEntity.tickCount, longshotEntity.isRetrieving(), poseStack, buffer, packedLight);
            }
            if (arrivalProgress < 0.999F) {
                renderHook(longshotEntity.getVisualDirection(), partialTicks, longshotEntity.tickCount, poseStack, buffer, packedLight);
            }

            poseStack.popPose();
        }

        super.render(longshotEntity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderHook(Vec3 difference, float partialTicks, int age, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float lengthXZ = Mth.sqrt((float) (difference.x * difference.x + difference.z * difference.z));

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(difference.z, difference.x)) - Mth.HALF_PI));
        poseStack.mulPose(Axis.XP.rotation((float) (-Math.atan2(lengthXZ, difference.y)) - Mth.HALF_PI));
        poseStack.scale(1.0F, 1.0F, 1.0F);

        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }

    private void renderChain(Vec3 difference, float partialTicks, int age, boolean reversing, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        renderChainSegment(difference, partialTicks, age, reversing, poseStack, buffer, packedLight, 0.0F, 0.75F, 0.0F);
    }

    private void renderSecondChain(Vec3 difference, float partialTicks, int age, boolean reversing, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        renderChainSegment(difference, partialTicks, age, reversing, poseStack, buffer, packedLight, -0.10F, 0.525F, 0.525F);
    }

    private void renderChainSegment(Vec3 difference, float partialTicks, int age, boolean reversing, PoseStack poseStack, MultiBufferSource buffer, int packedLight, float yOffset, float zOffset, float uvOffset) {
        float lengthXZ = Mth.sqrt((float) (difference.x * difference.x + difference.z * difference.z));
        float length = (float) difference.length();

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(difference.z, difference.x)) - Mth.HALF_PI));
        poseStack.mulPose(Axis.XP.rotation((float) (-Math.atan2(lengthXZ, difference.y)) - Mth.HALF_PI));

        VertexConsumer vertexConsumer = buffer.getBuffer(CHAIN_LAYER);
        float textureScroll = ((float) age + partialTicks) * (reversing ? 0.01F : -0.01F);
        float h = textureScroll;
        float i = length / 32.0F + textureScroll;
        float k = 0.0F;
        float l = yOffset;
        float m = uvOffset;

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
    public ResourceLocation getTextureLocation(LongshotEntity entity) {
        return TEXTURE;
    }
}
