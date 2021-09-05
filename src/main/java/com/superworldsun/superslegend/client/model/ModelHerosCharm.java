package com.superworldsun.superslegend.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelHerosCharm extends BipedModel<PlayerEntity>
{
    private final ModelRenderer head;

    public ModelHerosCharm(EquipmentSlotType armorSlot) {

        super(0F);

        texWidth = 32;
        texHeight = 32;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 23.0F, 0.0F);
        head.texOffs(14, 16).addBox(-5.5F, -7.0F, -5.6F, 6.0F, 5.0F, 1.0F, -0.5F, false);
        head.texOffs(0, 27).addBox(1.2F, -8.2F, -6.125F, 3.0F, 3.0F, 2.0F, -1.0F, false);
        head.texOffs(10, 22).addBox(-4.4F, -8.2F, -6.125F, 3.0F, 3.0F, 2.0F, -1.0F, false);
        head.texOffs(0, 16).addBox(-0.5F, -7.0F, -5.6F, 6.0F, 5.0F, 1.0F, -0.5F, false);
        head.texOffs(2, 4).addBox(-3.0F, -3.0F, -5.0F, 1.0F, 3.0F, 0.0F, 0.1F, false);
        head.texOffs(0, 4).addBox(2.0F, -3.0F, -5.0F, 1.0F, 3.0F, 0.0F, 0.1F, false);

        ModelRenderer cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, 0.2F, 0.0F);
        head.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.3054F);
        cube_r1.texOffs(0, 0).addBox(-0.225F, -1.3F, -5.0F, 3.0F, 2.0F, 0.0F, 0.0F, false);

        ModelRenderer cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, 0.2F, 0.0F);
        head.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.3054F);
        cube_r2.texOffs(0, 2).addBox(-2.8F, -1.3F, -5.0F, 3.0F, 2.0F, 0.0F, 0.0F, false);

        ModelRenderer cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-4.5F, -6.1F, -5.125F);
        head.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, -0.6545F);
        cube_r3.texOffs(20, 22).addBox(-1.3F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, -1.0F, false);

        ModelRenderer cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-1.2F, -6.7F, -5.125F);
        head.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, 0.3054F);
        cube_r4.texOffs(0, 22).addBox(-1.5F, -1.3F, -1.0F, 3.0F, 3.0F, 2.0F, -1.0F, false);

        ModelRenderer cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(1.2F, -7.2F, -5.125F);
        head.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, -0.3054F);
        cube_r5.texOffs(24, 0).addBox(-1.7F, -0.8F, -1.0F, 3.0F, 3.0F, 2.0F, -1.0F, false);

        ModelRenderer cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(4.2F, -6.6F, -5.125F);
        head.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.6545F);
        cube_r6.texOffs(10, 27).addBox(-1.2F, -1.3F, -1.0F, 3.0F, 3.0F, 2.0F, -1.0F, false);
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