package superworldsun.superslegend.models.armor;
// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ModelAllnightmask extends BipedModel<PlayerEntity> {

    public ModelAllnightmask(float modelSize) {
        super(modelSize, 0.0F, 32, 32);

        bipedHeadwear = new ModelRenderer(this);
        bipedHeadwear.setRotationPoint(0.0F, 32.025F, -2.4412F);
        setRotationAngle(bipedHeadwear, 0.0F, 0.0F, -3.225F);
        bipedHeadwear.setTextureOffset(17, 12).addBox(-1.0F, -6.25F, -4.8088F, 2.0F, 3.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(21, 10).addBox(-1.0F, -8.25F, -4.8088F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(0, 9).addBox(-2.0F, -1.25F, -4.8088F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(15, 23).addBox(-3.0F, -2.25F, -4.8088F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(8, 21).addBox(2.0F, -2.25F, -4.8088F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(17, 17).addBox(-3.0F, -8.25F, -4.8088F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(13, 13).addBox(-4.0F, -8.25F, -4.8088F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(17, 0).addBox(-2.0F, -7.25F, -4.8088F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(13, 4).addBox(1.0F, -7.25F, -4.8088F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(4, 11).addBox(3.25F, -8.25F, -4.7838F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(21, 17).addBox(-4.25F, -8.25F, -3.7838F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(10, 21).addBox(3.25F, -8.25F, -3.7838F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(21, 14).addBox(-4.25F, -5.25F, -0.7838F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(20, 4).addBox(3.25F, -5.25F, -0.7838F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(0, 22).addBox(-4.25F, -6.25F, -1.7838F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(13, 10).addBox(3.25F, -6.25F, -1.7838F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(19, 21).addBox(3.25F, -5.25F, -3.7838F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(6, 18).addBox(-4.25F, -5.25F, -3.7838F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(21, 0).addBox(-4.25F, -4.25F, -1.7838F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(0, 19).addBox(3.25F, -4.25F, -1.7838F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(5, 5).addBox(-4.25F, -3.25F, -3.7838F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(0, 0).addBox(3.25F, -3.25F, -3.7838F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(21, 7).addBox(-4.25F, -6.25F, 0.2162F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(4, 21).addBox(3.25F, -6.25F, 0.2162F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(5, 1).addBox(-4.25F, -7.25F, -2.7838F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(0, 4).addBox(3.25F, -7.25F, -2.7838F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(9, 10).addBox(-4.25F, -8.25F, -4.7838F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(0, 11).addBox(3.0F, -8.25F, -4.8088F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        bipedHeadwear.setTextureOffset(17, 6).addBox(2.0F, -8.25F, -4.8088F, 1.0F, 5.0F, 1.0F, 0.0F, false);
    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bipedHeadwear.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}