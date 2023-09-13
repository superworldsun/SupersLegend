package com.superworldsun.superslegend.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.joml.Vector3f;

import java.util.Random;

/**
 * Provides some useful rendering functions.
 */
public final class RenderUtil {
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    /*private RenderUtil() {
    }

    /**
     * Renders a projectile entity, using its item image. It applies color
     * modulation using the given color values.
     *
     * @param entityIn The entity
     * @param poseStack The matrix stack
     * @param bufferIn The render type buffer
     * @param packedLightIn The packed light
     * @param renderManager The render manager
     * @param itemRenderer The item renderer
     * @param red The value to multiply the red channel of the image by. Values should be from 0 to 1.
     * @param green The value to multiply the green channel of the image by. Values should be from 0 to 1.
     * @param blue The value to multiply the blue channel of the image by. Values should be from 0 to 1.
     */
    /*public static void renderProjectileEntityWithModulation(ProjectileWeaponItem entityIn,
                                                            PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn,
                                                            EntityRenderers renderManager, ItemRenderer itemRenderer, float red, float green, float blue)
    {
        if (entityIn.tickCount < 2 && renderManager.camera.getEntity().distanceToSqr(entityIn) < 12.25D)
            return;

        poseStack.pushPose();
        poseStack.mulPose(renderManager.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));

        int combinedOverlayIn = OverlayTexture.NO_OVERLAY;

        ItemStack itemStack = entityIn.getItem();
        IBakedModel bakedModel = itemRenderer.getModel(itemStack, null, null);
        bakedModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(poseStack, bakedModel,
                ItemCameraTransforms.TransformType.GROUND, false);
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        RenderType renderType = RenderTypeLookup.getRenderType(itemStack, false);
        IVertexBuilder vertexBuilder = ItemRenderer.getFoilBufferDirect(bufferIn, renderType, true,
                itemStack.hasFoil());

        renderQuadsWithModulation(poseStack, vertexBuilder, bakedModel, packedLightIn, combinedOverlayIn, red, green, blue);

        poseStack.popPose();
    }

    private static void renderQuadsWithModulation(PoseStack poseStack, IVertexBuilder vertexBuilder,
                                                  IBakedModel bakedModel, int combinedLights, int combinedOverlay, float red,
                                                  float blue, float green)
    {
        PoseStack.Pose matrixStackEntry = poseStack.last();

        // This is what the Minecraft code does, for some reason, so I'll do the same
        Random random = new Random();
        random.setSeed(42L);
        for (BakedQuad bakedQuad : bakedModel.getQuads(null, null, random, null))
        {
            vertexBuilder.addVertexData(matrixStackEntry, bakedQuad, red, green, blue, combinedLights, combinedOverlay, true);
        }
    }*/
}

