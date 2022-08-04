package com.superworldsun.superslegend.entities.projectiles.boomerang;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GaleBoomerangRender extends EntityRenderer<GaleBoomerangEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/item/gale_boomerang.png");

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    public GaleBoomerangRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    /*@Override
    public void render(GaleBoomerangEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-entityYaw + 90.0f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot) + 90.0F));
        matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(90.0f));
        matrixStackIn.mulPose(Vector3f.ZN.rotationDegrees(entityIn.getBoomerangRotation()));
        this.itemRenderer.render(getItemStackForRender(entityIn), ItemCameraTransforms.TransformType.GROUND,true,matrixStackIn,bufferIn,packedLightIn,OverlayTexture.NO_OVERLAY,Minecraft.getInstance().getItemRenderer().getModel(getItemStackForRender(entityIn),entityIn.level,null));
        matrixStackIn.popPose();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }*/

    /*private ItemStack getItemStackForRender(GaleBoomerangEntity entityIn) {
        return new ItemStack(ItemInit.GALE_BOOMERANG.get());
    }*/

    @Override
    public ResourceLocation getTextureLocation(GaleBoomerangEntity entity) {
        return TEXTURE;
    }

}