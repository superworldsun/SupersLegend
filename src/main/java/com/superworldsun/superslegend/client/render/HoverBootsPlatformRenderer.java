package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IHoveringEntity;
import com.superworldsun.superslegend.items.armors.HoverBootsArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class HoverBootsPlatformRenderer {
    private static final ResourceLocation PLATFORM_TEXTURE = new ResourceLocation(
            SupersLegendMain.MOD_ID, "textures/armor/hoverboots_platform.png");
    private static final ResourceLocation SHADOW_TEXTURE = new ResourceLocation(
            "minecraft", "textures/misc/shadow.png");
    private static final float PLATFORM_RADIUS = 0.72F;
    private static final float PLATFORM_Y_OFFSET = 0.025F;
    private static final float PLATFORM_SHADOW_RADIUS = 0.50F;
    private static final float PLATFORM_SHADOW_Y_OFFSET = 0.026F;
    // Keep the shadow visible without substantially dimming the full-bright platform texture.
    private static final float PLATFORM_SHADOW_ALPHA = 0.18F;

    private HoverBootsPlatformRenderer() {
    }

    @SubscribeEvent
    public static void renderPlatforms(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }

        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPosition = event.getCamera().getPosition();
        MultiBufferSource.BufferSource buffers = minecraft.renderBuffers().bufferSource();
        RenderType platformRenderType = RenderType.entityTranslucentEmissive(PLATFORM_TEXTURE);
        VertexConsumer platformVertices = buffers.getBuffer(platformRenderType);

        for (Player player : minecraft.level.players()) {
            IHoveringEntity hover = (IHoveringEntity) player;
            if (!hover.isHovering()) {
                continue;
            }

            float remaining = 1.0F - (hover.getHoverTime() + event.getPartialTick())
                    / (float) HoverBootsArmor.MAX_HOVER_TICKS;
            float alpha = Mth.clamp(remaining, 0.0F, 1.0F);
            if (alpha <= 0.0F) {
                continue;
            }

            double x = Mth.lerp(event.getPartialTick(), player.xOld, player.getX()) - cameraPosition.x;
            double y = Mth.lerp(event.getPartialTick(), player.yOld, player.getY()) - cameraPosition.y
                    + PLATFORM_Y_OFFSET;
            double z = Mth.lerp(event.getPartialTick(), player.zOld, player.getZ()) - cameraPosition.z;

            poseStack.pushPose();
            poseStack.translate(x, y, z);
            drawQuad(platformVertices, poseStack, PLATFORM_RADIUS, alpha);
            poseStack.popPose();
        }

        // Flush the bright platform first. It replaces the vanilla ground-projected shadow only
        // inside its disk; a softer, centered shadow is then restored directly on the platform.
        buffers.endBatch(platformRenderType);

        RenderType shadowRenderType = RenderType.entityShadow(SHADOW_TEXTURE);
        VertexConsumer shadowVertices = buffers.getBuffer(shadowRenderType);
        for (Player player : minecraft.level.players()) {
            IHoveringEntity hover = (IHoveringEntity) player;
            if (!hover.isHovering()) {
                continue;
            }

            float remaining = 1.0F - (hover.getHoverTime() + event.getPartialTick())
                    / (float) HoverBootsArmor.MAX_HOVER_TICKS;
            float alpha = Mth.clamp(remaining, 0.0F, 1.0F) * PLATFORM_SHADOW_ALPHA;
            if (alpha <= 0.0F) {
                continue;
            }

            double x = Mth.lerp(event.getPartialTick(), player.xOld, player.getX()) - cameraPosition.x;
            double y = Mth.lerp(event.getPartialTick(), player.yOld, player.getY()) - cameraPosition.y
                    + PLATFORM_SHADOW_Y_OFFSET;
            double z = Mth.lerp(event.getPartialTick(), player.zOld, player.getZ()) - cameraPosition.z;

            poseStack.pushPose();
            poseStack.translate(x, y, z);
            drawQuad(shadowVertices, poseStack, PLATFORM_SHADOW_RADIUS, alpha);
            poseStack.popPose();
        }
        buffers.endBatch(shadowRenderType);
    }

    private static void drawQuad(VertexConsumer vertices, PoseStack poseStack, float radius, float alpha) {
        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        vertex(vertices, pose, normal, -radius, -radius, 0.0F, 0.0F, alpha);
        vertex(vertices, pose, normal, -radius, radius, 0.0F, 1.0F, alpha);
        vertex(vertices, pose, normal, radius, radius, 1.0F, 1.0F, alpha);
        vertex(vertices, pose, normal, radius, -radius, 1.0F, 0.0F, alpha);
    }

    private static void vertex(VertexConsumer vertices, Matrix4f pose, Matrix3f normal,
                               float x, float z, float u, float v, float alpha) {
        vertices.vertex(pose, x, 0.0F, z)
                .color(1.0F, 1.0F, 1.0F, alpha)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }
}
