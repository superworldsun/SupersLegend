package superworldsun.superslegend.entities.projectiles.boomerang;

import com.mojang.blaze3d.matrix.MatrixStack;
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
import net.minecraftforge.fml.client.registry.IRenderFactory;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ItemList;

public class BoomerangRender extends EntityRenderer<BoomerangEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/item/regular_boomerang.png");

    private final ItemRenderer itemRenderer;

    public BoomerangRender(EntityRendererManager renderManager, ItemRenderer item) {
        super(renderManager);
        this.itemRenderer = item;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.80F;
    }

    @Override
    public void render(BoomerangEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-entityYaw + 90.0f));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + 90.0F));
        matrixStackIn.rotate(Vector3f.YN.rotationDegrees(90.0f));
        matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(entityIn.getBoomerangRotation()));
        this.itemRenderer.renderItem(getItemStackForRender(entityIn), ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
        matrixStackIn.pop();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private ItemStack getItemStackForRender(BoomerangEntity entityIn) {
        return new ItemStack(ItemList.regular_boomerang);
    }

    @Override
    public ResourceLocation getEntityTexture(BoomerangEntity entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<BoomerangEntity> {
        @Override
        public EntityRenderer<? super BoomerangEntity> createRenderFor(EntityRendererManager manager) {
            return new BoomerangRender(manager, Minecraft.getInstance().getItemRenderer());
        }

    }
}
