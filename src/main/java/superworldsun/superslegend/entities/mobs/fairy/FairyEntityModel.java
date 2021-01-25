package superworldsun.superslegend.entities.mobs.fairy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.AnimalEntity;

public class FairyEntityModel extends EntityModel<FairyEntity> {
    private final ModelRenderer fairy;
    private final ModelRenderer TopLeftWing_r1;
    private final ModelRenderer BottomRightWing_r1;


    public FairyEntityModel() {
        textureWidth = 16;
        textureHeight = 16;


        fairy = new ModelRenderer(this, 0, 0);
        fairy.setRotationPoint(0.0F, 24.0F, 0.0F);
        fairy.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -1.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        TopLeftWing_r1 = new ModelRenderer(this);
        TopLeftWing_r1.setRotationPoint(1.6F, -6.0F, 1.75F);
        fairy.addChild(TopLeftWing_r1);
        setRotationAngle(TopLeftWing_r1, -0.7854F, 0.0F, 0.0F);
        TopLeftWing_r1.setTextureOffset(0, 4).addBox(0.0F, -2.5F, -0.5F, 0.0F, 4.0F, 2.0F, 0.0F, false);
        TopLeftWing_r1.setTextureOffset(4, 4).addBox(-2.1F, -2.5F, -0.5F, 0.0F, 4.0F, 2.0F, 0.0F, false);

        BottomRightWing_r1 = new ModelRenderer(this);
        BottomRightWing_r1.setRotationPoint(-0.2F, -2.75F, 2.5F);
        fairy.addChild(BottomRightWing_r1);
        setRotationAngle(BottomRightWing_r1, 0.6981F, 0.0F, 0.0F);
        BottomRightWing_r1.setTextureOffset(0, 8).addBox(0.0F, -1.5F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, false);
        BottomRightWing_r1.setTextureOffset(4, 8).addBox(1.45F, -1.5F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(FairyEntity weirdMobEntity, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder IVertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        fairy.render(matrixStack, IVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
