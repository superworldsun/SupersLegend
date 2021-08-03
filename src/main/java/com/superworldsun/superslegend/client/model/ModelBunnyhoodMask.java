package com.superworldsun.superslegend.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

/**
 * ModelBunnyHood - superworldsun
 * Created using Tabula 7.1.0
 */
public class ModelBunnyhoodMask extends BipedModel<PlayerEntity> {


    public ModelBunnyhoodMask(float modelSize) {
        super(modelSize, 0.0F, 32, 32);


        hat = new ModelRenderer(this);
        hat.setPos(0.0F, -10.0F, 0.0F);
        hat.texOffs(0, 5).addBox(-4.7F, -8.3F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, false);
        hat.texOffs(0, 0).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 1.0F, 4.0F, 0.0F, false);
        hat.texOffs(10, 10).addBox(3.705F, -8.295F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, false);

        ModelRenderer cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(4.0F, -7.575F, -2.0F);
        hat.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, 0.7854F);
        cube_r1.texOffs(10, 5).addBox(-1.01F, -1.007F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);


        ModelRenderer cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-4.0F, -7.575F, -2.0F);
        hat.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.7854F);
        cube_r2.texOffs(0, 15).addBox(-1.01F, -1.005F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);


        ModelRenderer rightear = new ModelRenderer(this);
        rightear.setPos(0.0F, 0.0F, 0.0F);
        hat.addChild(rightear);
        rightear.texOffs(18, 8).addBox(1.5F, -13.0F, -0.25F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        ModelRenderer cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(3.5F, -12.175F, -0.05F);
        rightear.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.4363F, 0.0F, 0.0F);
        cube_r3.texOffs(8, 20).addBox(-2.0F, -2.985F, -0.02F, 2.0F, 3.0F, 2.0F, 0.0F, false);
        cube_r3.texOffs(16, 5).addBox(-1.5F, -3.685F, -0.02F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        ModelRenderer leftear = new ModelRenderer(this);
        leftear.setPos(0.0F, 0.0F, 0.0F);
        hat.addChild(leftear);
        leftear.texOffs(18, 18).addBox(-3.5F, -13.0F, -0.25F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        ModelRenderer cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-1.5F, -12.175F, -0.05F);
        leftear.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.4363F, 0.0F, 0.0F);
        cube_r4.texOffs(0, 20).addBox(-2.0F, -2.985F, -0.02F, 2.0F, 3.0F, 2.0F, 0.0F, false);
        cube_r4.texOffs(6, 6).addBox(-1.5F, -3.685F, -0.02F, 1.0F, 1.0F, 2.0F, 0.0F, false);
    }


    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        hat.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}