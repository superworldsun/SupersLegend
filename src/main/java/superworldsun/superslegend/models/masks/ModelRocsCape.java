package superworldsun.superslegend.models.masks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

/**
 * ModelBunnyHood - superworldsun
 * Created using Tabula 7.1.0
 */
public class ModelRocsCape extends BipedModel<PlayerEntity> {


    public ModelRocsCape(float modelSize) {
        super(modelSize, 0.0F, 64, 64);

        ModelRenderer Cape = new ModelRenderer(this);
        Cape.setRotationPoint(0.5F, 9.8F, 1.2F);
        bipedBody.addChild(Cape);
        Cape.setTextureOffset(50, 60).addBox(-4.75F, -9.55F, -4.25F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        Cape.setTextureOffset(40, 46).addBox(-3.5F, -9.55F, -4.25F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        Cape.setTextureOffset(56, 58).addBox(1.75F, -9.55F, -4.25F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        Cape.setTextureOffset(40, 41).addBox(-6.5F, -10.05F, -3.25F, 2.0F, 1.0F, 4.0F, 0.0F, false);
        Cape.setTextureOffset(52, 62).addBox(-5.25F, -9.8F, -4.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Cape.setTextureOffset(48, 62).addBox(3.5F, -9.8F, -4.25F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Cape.setTextureOffset(40, 41).addBox(3.75F, -10.05F, -3.25F, 2.0F, 1.0F, 4.0F, 0.0F, false);

        ModelRenderer capeOG_r1 = new ModelRenderer(this);
        capeOG_r1.setRotationPoint(-8.4F, 9.4F, 0.9F);
        bipedBody.addChild(capeOG_r1);
        setRotationAngle(capeOG_r1, 0.2618F, 0.0F, 0.0F);
        capeOG_r1.setTextureOffset(54, 46).addBox(4.0F, 4.0F, 2.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(46, 58).addBox(8.0F, 5.0F, 2.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(50, 58).addBox(7.5F, 4.0F, 2.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(48, 52).addBox(5.0F, 4.0F, 2.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(44, 52).addBox(11.0F, 4.0F, 2.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(44, 62).addBox(3.0F, 8.0F, 2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(40, 62).addBox(13.0F, 8.0F, 2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(54, 46).addBox(12.0F, 4.0F, 2.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(41, 48).addBox(12.0F, 2.0F, 2.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(40, 48).addBox(7.0F, 2.0F, 2.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(42, 48).addBox(3.0F, 2.0F, 2.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        capeOG_r1.setTextureOffset(40, 29).addBox(3.0F, -9.0F, 2.5F, 11.0F, 11.0F, 1.0F, 0.0F, false);
    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bipedBody.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}