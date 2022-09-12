package com.superworldsun.superslegend.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MasterSword_Model extends EntityModel<Entity> {
    private final ModelRenderer bb_main;

	public MasterSword_Model() {
        texWidth = 32;
        texHeight = 32;

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(0, 3).addBox(-2.0F, 0.0F, 4.0F, 5.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 2).addBox(-3.0F, 0.0F, 3.0F, 7.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 1).addBox(-4.0F, 0.0F, 2.0F, 9.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 5).addBox(-5.0F, 0.0F, 0.0F, 4.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 4).addBox(2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(8, 4).addBox(3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(4, 6).addBox(4.0F, 0.0F, -3.0F, 2.0F, 0.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 6).addBox(-5.0F, 0.0F, -3.0F, 2.0F, 0.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 9).addBox(4.0F, 0.0F, -4.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(8, 8).addBox(3.0F, 0.0F, -5.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(6, 8).addBox(2.0F, 0.0F, -6.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 8).addBox(-5.0F, 0.0F, -1.0F, 3.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 0).addBox(-5.0F, 0.0F, 1.0F, 11.0F, 0.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(8, 5).addBox(-4.0F, 0.0F, -4.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}