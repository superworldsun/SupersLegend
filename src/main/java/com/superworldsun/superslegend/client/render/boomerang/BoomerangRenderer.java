package com.superworldsun.superslegend.client.render.boomerang;

import com.mojang.blaze3d.vertex.PoseStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.boomerang.BoomerangEntity;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import software.bernie.shadowed.eliotlash.mclib.utils.MathHelper;

public class BoomerangRenderer extends EntityRenderer<BoomerangEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/item/boomerang.png");

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    public BoomerangRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    /*@Override
    public void render(BoomerangEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-entityYaw + 90.0f));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRotO) + 90.0F));
        poseStack.mulPose(Vector3f.YN.rotationDegrees(90.0f));
        poseStack.mulPose(Vector3f.ZN.rotationDegrees(entityIn.getBoomerangRotation()));
        this.itemRenderer.render(getItemStackForRender(entityIn), ItemCameraTransforms.TransformType.GROUND,true,poseStack,bufferIn,packedLightIn, OverlayTexture.NO_OVERLAY,Minecraft.getInstance().getItemRenderer().getModel(getItemStackForRender(entityIn),entityIn.level,null));
        poseStack.popPose();

        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }*/

    private ItemStack getItemStackForRender(BoomerangEntity entityIn) {
        return new ItemStack(ItemInit.BOOMERANG.get());
    }

    @Override
    public ResourceLocation getTextureLocation(BoomerangEntity entity) {
        return TEXTURE;
    }

}