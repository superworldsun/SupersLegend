package superworldsun.superslegend.entities.projectiles.items.bomb;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sun.org.apache.xerces.internal.impl.validation.EntityState;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import superworldsun.superslegend.SupersLegend;

public class BombRenderer extends EntityRenderer<BombEntity> {
    public static final ResourceLocation BOMB = new ResourceLocation(SupersLegend.modid,"textures/entity/projectiles/bomb.png");
    public static final ResourceLocation bg = new ResourceLocation(SupersLegend.modid,"textures/item/bomb.gif");
    private final BombModel bombModel = new BombModel();

    public BombRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowRadius = 0.3F;
    }

    public void render(BombEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0, 1.5f, 0);
        int i = entityIn.getFuse();
        if (i > -1 && (float)i - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)i - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            matrixStackIn.scale(f1, f1, f1);
        }


        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(entityYaw));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0f));
        matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(0.0f));
        matrixStackIn.mulPose(Vector3f.ZN.rotationDegrees(0.0f));
        IVertexBuilder ivertexbuild = ItemRenderer.getFoilBufferDirect(bufferIn, this.bombModel.renderType(BOMB), false, false);
        this.bombModel.renderToBuffer(matrixStackIn, ivertexbuild, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }



    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(BombEntity entity) {
        return BOMB;
    }

}
