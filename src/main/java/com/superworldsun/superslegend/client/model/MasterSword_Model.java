package com.superworldsun.superslegend.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MasterSword_Model extends EntityModel<Entity> {
    private final ModelRenderer swordbeam;

	public MasterSword_Model() {
        texWidth = 32;
        texHeight = 32;

        swordbeam = new ModelRenderer(this);
        swordbeam.setPos(0.0F, 24.0F, 0.0F);
        setRotationAngle(swordbeam, 0.0F, -0.7854F, 0.0F);
        swordbeam.texOffs(10, 16).addBox(-8.0F, -1.0F, -8.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(10, 11).addBox(5.0F, -1.0F, 6.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
        swordbeam.texOffs(0, 16).addBox(4.0F, -1.0F, 5.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(17, 17).addBox(3.0F, -1.0F, 4.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(11, 9).addBox(0.0F, -1.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(10, 14).addBox(-2.0F, -1.0F, 4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(0, 14).addBox(-3.0F, -1.0F, 5.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(13, 3).addBox(0.0F, -1.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(0, 2).addBox(-1.0F, -1.0F, 1.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(0, 0).addBox(-2.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(16, 0).addBox(4.0F, -1.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        swordbeam.texOffs(0, 18).addBox(5.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(11, 7).addBox(-3.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(11, 5).addBox(-4.0F, -1.0F, -2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(0, 10).addBox(-5.0F, -1.0F, -3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(0, 8).addBox(-6.0F, -1.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(0, 6).addBox(-7.0F, -1.0F, -5.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(0, 4).addBox(-8.0F, -1.0F, -6.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        swordbeam.texOffs(0, 12).addBox(-8.0F, -1.0F, -7.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        swordbeam.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}