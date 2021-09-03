package com.superworldsun.superslegend.client.model;
// Made with Blockbench 3.7.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class ModelKokiriBoots extends BipedModel<PlayerEntity> {
    private final ModelRenderer leftLeg;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer rightLeg;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;

    public ModelKokiriBoots(float modelSize) {
        super(modelSize, 0.0F, 32, 32);


        leftLeg = new ModelRenderer(this);
        leftLeg.setPos(0.0F, 32.025F, -2.4412F);
        setRotationAngle(leftLeg, 2.0F, 12.0F, 0.0F);

        leftLeg.texOffs(0, 9).addBox(-2.0F, 6.9F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);
        leftLeg.texOffs(0, 1).addBox(-1.0F, 7.9F, 2.0F, 2.0F, 1.0F, 0.0F, 0.2F, false);
        leftLeg.texOffs(6, 19).addBox(-1.5F, 6.8F, 2.0F, 3.0F, 1.0F, 0.0F, 0.2F, false);
        leftLeg.texOffs(12, 2).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        leftLeg.texOffs(21, 1).addBox(-1.5F, 11.0F, -4.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        leftLeg.texOffs(21, 3).addBox(-1.5F, 10.0F, -3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, 32.025F, -2.4412F);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -3.225F);
        leftLeg.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, 0.1309F);
        cube_r1.texOffs(16, 20).addBox(-4.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, 32.025F, -2.4412F);
        setRotationAngle(cube_r2, 0.0F, 0.0F, -3.225F);
        leftLeg.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, -0.1309F);
        cube_r2.texOffs(22, 22).addBox(3.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setPos(0.0F, 32.025F, -2.4412F);
        setRotationAngle(rightLeg, 0.0F, 0.0F, -3.225F);
        rightLeg.texOffs(0, 0).addBox(-6.0F, 6.9F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);
        rightLeg.texOffs(12, 11).addBox(-5.5F, 10.0F, -3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        rightLeg.texOffs(12, 0).addBox(-6.0F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        rightLeg.texOffs(12, 20).addBox(-5.5F, 11.0F, -4.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        rightLeg.texOffs(0, 0).addBox(-5.0F, 7.9F, 2.0F, 2.0F, 1.0F, 0.0F, 0.2F, false);
        rightLeg.texOffs(6, 18).addBox(-5.5F, 6.8F, 2.0F, 3.0F, 1.0F, 0.0F, 0.2F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, 32.025F, -2.4412F);
        setRotationAngle(cube_r3, 0.0F, 0.0F, -3.225F);
        rightLeg.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, -0.1309F);
        cube_r3.texOffs(6, 20).addBox(3.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.0F, 32.025F, -2.4412F);
        setRotationAngle(cube_r4, 0.0F, 0.0F, -3.225F);
        rightLeg.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, 0.1309F);
        cube_r4.texOffs(0, 18).addBox(-4.0F, -2.2F, -2.0F, 1.0F, 2.0F, 4.0F, 0.2F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}