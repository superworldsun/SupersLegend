package com.superworldsun.superslegend.client.model.mobs;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.entities.TPBokoblinEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TPBokoblnModel <T extends TPBokoblinEntity> extends EntityModel<T> {
    private final ModelRenderer Head;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer Hair;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;
    private final ModelRenderer Teeth;
    private final ModelRenderer cube_r8;
    private final ModelRenderer Jaw;
    private final ModelRenderer cube_r9;
    private final ModelRenderer RightEar;
    private final ModelRenderer cube_r10;
    private final ModelRenderer LeftEar;
    private final ModelRenderer cube_r11;
    private final ModelRenderer Body;
    private final ModelRenderer cube_r12;
    private final ModelRenderer RightArm;
    private final ModelRenderer cube_r13;
    private final ModelRenderer Sword;
    private final ModelRenderer cube_r14;
    private final ModelRenderer LeftArm;
    private final ModelRenderer cube_r15;
    private final ModelRenderer LeftLeg;
    private final ModelRenderer cube_r16;
    private final ModelRenderer RightLeg;
    private final ModelRenderer cube_r17;

    public TPBokoblnModel() {
        texWidth = 128;
        texHeight = 128;

        Head = new ModelRenderer(this);
        Head.setPos(0.0F, -12.75F, -2.0F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-2.1837F, -5.1888F, 2.7521F);
        Head.addChild(cube_r1);
        setRotationAngle(cube_r1, 2.9234F, 0.0F, -3.1416F);
        cube_r1.texOffs(60, 29).addBox(-4.3163F, -2.991F, -2.6066F, 5.0F, 8.0F, 5.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-2.1837F, -5.1888F, 2.7521F);
        Head.addChild(cube_r2);
        setRotationAngle(cube_r2, -3.1416F, 0.0F, 3.1416F);
        cube_r2.texOffs(0, 35).addBox(-3.8163F, -4.8112F, 6.2479F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r2.texOffs(42, 18).addBox(-6.8163F, -4.8112F, -2.7521F, 10.0F, 2.0F, 4.0F, 0.0F, false);
        cube_r2.texOffs(0, 27).addBox(-6.8163F, -10.8112F, -2.7521F, 10.0F, 6.0F, 10.0F, 0.0F, false);

        Hair = new ModelRenderer(this);
        Hair.setPos(0.0F, 37.0F, 2.0F);
        Head.addChild(Hair);


        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-2.1837F, -42.1888F, 0.7521F);
        Hair.addChild(cube_r3);
        setRotationAngle(cube_r3, 2.9671F, 0.0F, -3.1416F);
        cube_r3.texOffs(66, 42).addBox(-4.8163F, -10.3292F, -6.8991F, 6.0F, 6.0F, 4.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-2.1837F, -42.1888F, 0.7521F);
        Hair.addChild(cube_r4);
        setRotationAngle(cube_r4, 2.4435F, 0.0F, -3.1416F);
        cube_r4.texOffs(26, 0).addBox(-3.8163F, -4.5796F, -13.4523F, 4.0F, 0.0F, 4.0F, 0.0F, false);
        cube_r4.texOffs(29, 34).addBox(-5.8163F, -4.5796F, -16.4523F, 8.0F, 0.0F, 1.0F, 0.0F, false);
        cube_r4.texOffs(28, 35).addBox(-4.8163F, -4.5796F, -15.4523F, 6.0F, 0.0F, 2.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(-2.1837F, -42.1888F, 0.7521F);
        Hair.addChild(cube_r5);
        setRotationAngle(cube_r5, -2.6616F, 0.0F, 3.1416F);
        cube_r5.texOffs(52, 24).addBox(-3.8163F, -8.1611F, -5.4163F, 4.0F, 0.0F, 4.0F, 0.0F, false);
        cube_r5.texOffs(15, 20).addBox(-5.8163F, -8.1611F, -8.4163F, 8.0F, 0.0F, 1.0F, 0.0F, false);
        cube_r5.texOffs(40, 24).addBox(-4.8163F, -8.1611F, -7.4163F, 6.0F, 0.0F, 2.0F, 0.0F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(-2.1837F, -42.1888F, 0.7521F);
        Hair.addChild(cube_r6);
        setRotationAngle(cube_r6, 2.9454F, 0.4721F, 3.0514F);
        cube_r6.texOffs(8, 26).addBox(-3.3385F, -11.3292F, -12.1484F, 0.0F, 8.0F, 1.0F, 0.0F, false);
        cube_r6.texOffs(30, 25).addBox(-3.3385F, -10.3292F, -11.1484F, 0.0F, 6.0F, 2.0F, 0.0F, false);
        cube_r6.texOffs(0, 27).addBox(-3.3385F, -9.3292F, -9.1484F, 0.0F, 4.0F, 4.0F, 0.0F, false);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(-2.1837F, -42.1888F, 0.7521F);
        Hair.addChild(cube_r7);
        setRotationAngle(cube_r7, 2.9454F, -0.4721F, -3.0514F);
        cube_r7.texOffs(68, 13).addBox(0.0683F, -11.3292F, -13.6727F, 0.0F, 8.0F, 1.0F, 0.0F, false);
        cube_r7.texOffs(18, 46).addBox(0.0683F, -10.3292F, -12.6727F, 0.0F, 6.0F, 2.0F, 0.0F, false);
        cube_r7.texOffs(0, 23).addBox(0.0683F, -9.3292F, -10.6727F, 0.0F, 4.0F, 4.0F, 0.0F, false);

        Teeth = new ModelRenderer(this);
        Teeth.setPos(0.0F, 37.0F, 2.25F);
        Head.addChild(Teeth);


        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(-2.1837F, -42.1888F, 0.5021F);
        Teeth.addChild(cube_r8);
        setRotationAngle(cube_r8, -3.1416F, 0.0F, 3.1416F);
        cube_r8.texOffs(0, 45).addBox(-6.3163F, -5.5612F, 2.4979F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r8.texOffs(13, 45).addBox(-5.8163F, -5.3112F, 5.9979F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r8.texOffs(29, 45).addBox(1.6837F, -5.5612F, 2.4979F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r8.texOffs(49, 34).addBox(-3.1163F, -4.3112F, 5.9979F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r8.texOffs(52, 35).addBox(-1.5163F, -4.3112F, 5.9979F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r8.texOffs(36, 54).addBox(1.1837F, -5.3112F, 5.9979F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        Jaw = new ModelRenderer(this);
        Jaw.setPos(0.0F, -9.0F, 2.0F);
        Head.addChild(Jaw);
        setRotationAngle(Jaw, 0.5672F, 0.0F, 0.0F);


        cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(1.8163F, 2.5438F, -3.1002F);
        Jaw.addChild(cube_r9);
        setRotationAngle(cube_r9, -2.6616F, 0.0F, 3.1416F);
        cube_r9.texOffs(42, 20).addBox(-2.2663F, -4.9811F, 1.4186F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r9.texOffs(0, 43).addBox(5.6337F, -4.9811F, 1.4186F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r9.texOffs(13, 43).addBox(2.9337F, -3.9811F, 3.4186F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r9.texOffs(29, 43).addBox(0.4337F, -3.9811F, 3.4186F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r9.texOffs(64, 18).addBox(-2.8163F, -4.5899F, -1.2881F, 3.0F, 1.0F, 6.0F, 0.0F, false);
        cube_r9.texOffs(67, 52).addBox(4.1837F, -4.5899F, -1.2881F, 3.0F, 1.0F, 6.0F, 0.0F, false);
        cube_r9.texOffs(13, 43).addBox(-0.8163F, -2.5899F, 0.7119F, 6.0F, 1.0F, 4.0F, 0.0F, false);
        cube_r9.texOffs(30, 27).addBox(-2.8163F, -3.5899F, -1.2881F, 10.0F, 1.0F, 6.0F, 0.0F, false);

        RightEar = new ModelRenderer(this);
        RightEar.setPos(4.6828F, -12.4F, 1.8298F);
        Head.addChild(RightEar);
        setRotationAngle(RightEar, 0.0F, 0.4363F, 0.0F);


        cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(-2.1425F, 7.2112F, -2.1882F);
        RightEar.addChild(cube_r10);
        setRotationAngle(cube_r10, -3.1416F, 0.0F, 3.1416F);
        cube_r10.texOffs(42, 18).addBox(13.5954F, -9.8112F, 1.6399F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r10.texOffs(42, 35).addBox(13.5954F, -8.8112F, 1.6399F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r10.texOffs(64, 0).addBox(11.5954F, -9.8112F, 1.6399F, 2.0F, 3.0F, 1.0F, 0.0F, false);
        cube_r10.texOffs(49, 54).addBox(9.5954F, -9.8112F, 1.6399F, 2.0F, 4.0F, 1.0F, 0.0F, false);
        cube_r10.texOffs(8, 71).addBox(6.5954F, -9.8112F, 1.6399F, 3.0F, 5.0F, 1.0F, 0.0F, false);

        LeftEar = new ModelRenderer(this);
        LeftEar.setPos(-4.6828F, -12.4F, 2.0798F);
        Head.addChild(LeftEar);
        setRotationAngle(LeftEar, 0.0F, -0.4363F, 0.0F);


        cube_r11 = new ModelRenderer(this);
        cube_r11.setPos(5.329F, 7.2112F, -3.9499F);
        LeftEar.addChild(cube_r11);
        setRotationAngle(cube_r11, -3.1416F, 0.0F, 3.1416F);
        cube_r11.texOffs(30, 4).addBox(-10.6371F, -9.8112F, -0.2058F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r11.texOffs(35, 19).addBox(-12.6371F, -8.8112F, -0.2058F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r11.texOffs(56, 28).addBox(-9.6371F, -9.8112F, -0.2058F, 2.0F, 3.0F, 1.0F, 0.0F, false);
        cube_r11.texOffs(0, 0).addBox(-7.6371F, -9.8112F, -0.2058F, 2.0F, 4.0F, 1.0F, 0.0F, false);
        cube_r11.texOffs(0, 71).addBox(-5.6371F, -9.8112F, -0.2058F, 3.0F, 5.0F, 1.0F, 0.0F, false);

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, -1.1667F, -0.3333F);


        cube_r12 = new ModelRenderer(this);
        cube_r12.setPos(-2.1837F, -16.7721F, 1.0854F);
        Body.addChild(cube_r12);
        setRotationAngle(cube_r12, -3.1416F, 0.0F, 3.1416F);
        cube_r12.texOffs(0, 0).addBox(-7.8163F, 13.9388F, -3.7521F, 12.0F, 14.0F, 6.0F, 0.0F, false);
        cube_r12.texOffs(36, 0).addBox(-7.8163F, 13.9388F, 2.2479F, 12.0F, 14.0F, 4.0F, 0.0F, false);
        cube_r12.texOffs(34, 37).addBox(-6.8163F, 2.9388F, -3.7521F, 10.0F, 11.0F, 6.0F, 0.0F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-7.0F, -13.0F, 1.25F);
        setRotationAngle(RightArm, 0.0F, 0.0F, -1.2217F);


        cube_r13 = new ModelRenderer(this);
        cube_r13.setPos(7.6563F, 6.5954F, -2.4979F);
        RightArm.addChild(cube_r13);
        setRotationAngle(cube_r13, -3.1416F, 0.0F, -0.6981F);
        cube_r13.texOffs(18, 49).addBox(-3.7817F, 7.7214F, -5.2521F, 4.0F, 23.0F, 5.0F, 0.0F, false);

        Sword = new ModelRenderer(this);
        Sword.setPos(3.5066F, 12.558F, -3.75F);
        RightArm.addChild(Sword);


        cube_r14 = new ModelRenderer(this);
        cube_r14.setPos(4.1497F, -5.9626F, 1.2521F);
        Sword.addChild(cube_r14);
        setRotationAngle(cube_r14, -3.1416F, 0.0F, -0.6981F);
        cube_r14.texOffs(0, 12).addBox(-2.0317F, 31.4714F, 12.9979F, 0.0F, 1.0F, 8.0F, 0.0F, false);
        cube_r14.texOffs(0, 0).addBox(-2.0317F, 24.9714F, 0.9979F, 0.0F, 6.0F, 21.0F, 0.0F, false);
        cube_r14.texOffs(66, 67).addBox(-2.5317F, 27.7214F, -6.0021F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        cube_r14.texOffs(72, 59).addBox(-2.5317F, 24.7214F, -0.0021F, 1.0F, 7.0F, 1.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(7.0F, -13.0F, 1.25F);
        setRotationAngle(LeftArm, 0.0F, 0.0F, 1.2654F);


        cube_r15 = new ModelRenderer(this);
        cube_r15.setPos(-6.269F, 3.4587F, -2.4979F);
        LeftArm.addChild(cube_r15);
        setRotationAngle(cube_r15, -3.1416F, 0.0F, 0.6109F);
        cube_r15.texOffs(0, 43).addBox(1.5164F, 3.4255F, -5.2521F, 4.0F, 23.0F, 5.0F, 0.0F, false);

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(3.5F, 10.75F, 1.75F);


        cube_r16 = new ModelRenderer(this);
        cube_r16.setPos(-5.6837F, -28.6888F, -0.9979F);
        LeftLeg.addChild(cube_r16);
        setRotationAngle(cube_r16, -3.1416F, 0.0F, 3.1416F);
        cube_r16.texOffs(68, 7).addBox(-7.8163F, 38.9388F, 1.2479F, 4.0F, 3.0F, 4.0F, 0.0F, false);
        cube_r16.texOffs(54, 54).addBox(-7.8163F, 27.9388F, -3.7521F, 4.0F, 14.0F, 5.0F, 0.0F, false);

        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-4.5F, 10.75F, 1.75F);


        cube_r17 = new ModelRenderer(this);
        cube_r17.setPos(2.3163F, -28.6888F, -0.9979F);
        RightLeg.addChild(cube_r17);
        setRotationAngle(cube_r17, -3.1416F, 0.0F, 3.1416F);
        cube_r17.texOffs(68, 0).addBox(0.1837F, 38.9388F, 1.2479F, 4.0F, 3.0F, 4.0F, 0.0F, false);
        cube_r17.texOffs(36, 54).addBox(0.1837F, 27.9388F, -3.7521F, 4.0F, 14.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

    }


    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Head.render(matrixStack, buffer, packedLight, packedOverlay);
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
        LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
        LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
