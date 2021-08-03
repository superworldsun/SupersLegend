package com.superworldsun.superslegend.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

/**
 * ModelPostmansHat - superworldsun
 * Created using Tabula 7.1.0
 */
public class ModelPostmansHat extends BipedModel<PlayerEntity> {


    public ModelPostmansHat(float modelSize) {
        super(modelSize, 0.0F, 64, 64);


        hat = new ModelRenderer(this);
        hat.setPos(0.0F, -10.0F, 0.0F);
        hat.texOffs(0, 0).addBox(-5.0F, -12.0F, -3.0F, 10.0F, 1.0F, 8.0F, 0.0F, false);
        hat.texOffs(0, 9).addBox(-4.5F, -8.475F, -5.05F, 5.0F, 1.0F, 10.0F, 0.0F, false);
        hat.texOffs(0, 20).addBox(0.5F, -8.475F, -5.075F, 4.0F, 1.0F, 10.0F, 0.0F, false);
        hat.texOffs(40, 18).addBox(4.0F, -11.0F, -3.5F, 1.0F, 3.0F, 8.0F, 0.0F, false);
        hat.texOffs(0, 4).addBox(4.0F, -9.5F, -4.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        hat.texOffs(0, 0).addBox(-5.0F, -9.5F, -4.5F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        hat.texOffs(4, 0).addBox(4.0F, -10.5F, -3.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        hat.texOffs(4, 4).addBox(-5.0F, -10.5F, -3.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        hat.texOffs(38, 1).addBox(4.5F, -8.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        hat.texOffs(0, 31).addBox(-5.5F, -8.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        hat.texOffs(14, 39).addBox(-5.0F, -11.0F, -3.5F, 1.0F, 3.0F, 8.0F, 0.0F, false);

        ModelRenderer cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(1.0F, -7.0F, 5.0F);
        hat.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -1.5708F, 0.0F);
        cube_r1.texOffs(18, 26).addBox(-1.0F, -4.0F, -4.0F, 1.0F, 3.0F, 10.0F, 0.0F, false);


        ModelRenderer cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, -6.0F, -5.35F);
        hat.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 1.5708F, 0.0F);
        cube_r2.texOffs(26, 0).addBox(-1.0F, -2.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);


        ModelRenderer cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, -6.0F, 4.5F);
        hat.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 1.5708F, 0.0F);
        cube_r3.texOffs(30, 30)
                .addBox(-1.0F, -2.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);


        ModelRenderer cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.0F, -6.3391F, -6.9656F);
        hat.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.5716F, 0.0F, 0.0F);
        cube_r4.texOffs(20, 13)
                .addBox(-3.0F, -0.4983F, -0.9982F, 6.0F, 1.0F, 2.0F, 0.0F, false);
        cube_r4.texOffs(0, 9)
                .addBox(-2.0F, -0.4958F, -1.9799F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r4.texOffs(20, 11)
                .addBox(-4.0F, -0.5059F, 0.9781F, 8.0F, 1.0F, 1.0F, 0.0F, false);


        ModelRenderer cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(0.0F, -9.1995F, -4.2144F);
        hat.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.9992F, 0.0F, 0.0F);
        cube_r5.texOffs(18, 20)
                .addBox(-5.0F, -0.5F, -2.0F, 10.0F, 1.0F, 5.0F, 0.0F, false);
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