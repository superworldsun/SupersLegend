package com.superworldsun.superslegend.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ClawshotModel<T extends Entity> extends SegmentedModel<T> {
    private final ModelRenderer bone;

    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;
    private final ModelRenderer cube_r9;
    private final ModelRenderer cube_r10;

    public ClawshotModel() {
        texWidth = 16;
        texHeight = 16;

        //Old model
        /*main = new ModelRenderer(this);
        main.texOffs(-1.0F, 6.0F, -9.0F);
        setRotationAngle(main, -1.5708F, 0.0F, 1.5708F);
        main.texOffs(3, 7).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        main.texOffs(0, 3).addBox(-0.5F, -3.25F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        main.texOffs(0, 6).addBox(0.0F, -2.5F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        main.texOffs(5, 2).addBox(-0.5F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        main.texOffs(5, 5).addBox(0.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        main.texOffs(0, 0).addBox(-0.5F, -3.25F, 1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        main.texOffs(4, 0).addBox(0.0F, -2.5F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);*/

        bone = new ModelRenderer(this);
        bone.setPos(-0.0233F, 16.9132F, -0.8936F);
        setRotationAngle(bone, 1.5708F, 0.0F, 3.1416F);
        bone.texOffs(0, 0).addBox(2.0233F, -15.9132F, -10.1064F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        bone.texOffs(12, 2).addBox(2.5233F, -20.0355F, -13.8886F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bone.texOffs(6, 11).addBox(6.8224F, -20.134F, -9.2064F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bone.texOffs(8, 9).addBox(-1.621F, -20.0337F, -9.2064F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(1.9913F, -15.5731F, -8.8314F);
        bone.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.3927F);
        cube_r1.texOffs(5, 5).addBox(-2.0388F, -4.4807F, -0.375F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(1.9913F, -15.5731F, -9.0814F);
        bone.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.3927F);
        cube_r2.texOffs(9, 5).addBox(-2.1816F, -0.6054F, -0.125F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r2.texOffs(0, 11).addBox(-3.5565F, -2.0453F, -0.125F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(1.0233F, -15.9132F, -9.0814F);
        bone.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, 0.3927F);
        cube_r3.texOffs(4, 3).addBox(0.25F, -0.75F, -0.125F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(4.4581F, -15.5673F, -8.8314F);
        bone.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, -0.3927F);
        cube_r4.texOffs(3, 10).addBox(2.5438F, -2.0307F, -0.375F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r4.texOffs(9, 7).addBox(-0.2142F, -0.6102F, -0.375F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(4.4581F, -15.5673F, -8.8314F);
        bone.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, 0.3927F);
        cube_r5.texOffs(0, 6).addBox(0.7884F, -4.4872F, -0.375F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(5.0233F, -15.9132F, -9.0814F);
        bone.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, -0.3927F);
        cube_r6.texOffs(10, 11).addBox(-1.25F, -0.75F, -0.125F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(3.1483F, -15.5885F, -10.5345F);
        bone.addChild(cube_r7);
        setRotationAngle(cube_r7, -0.3927F, 0.0F, 0.0F);
        cube_r7.texOffs(12, 0).addBox(-0.625F, -2.0736F, -3.5947F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        cube_r7.texOffs(0, 3).addBox(-0.625F, -0.596F, -1.7689F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(3.1483F, -15.5885F, -10.5345F);
        bone.addChild(cube_r8);
        setRotationAngle(cube_r8, 0.3927F, 0.0F, 0.0F);
        cube_r8.texOffs(8, 0).addBox(-0.625F, -4.4651F, -1.7865F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(3.1483F, -15.9132F, -11.1064F);
        bone.addChild(cube_r9);
        setRotationAngle(cube_r9, -0.3927F, 0.0F, 0.0F);
        cube_r9.texOffs(3, 12).addBox(-0.625F, -0.6F, 0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(3.0233F, -15.4132F, -9.1064F);
        bone.addChild(cube_r10);
        setRotationAngle(cube_r10, 0.0F, 0.0F, 0.7854F);
        cube_r10.texOffs(12, 9).addBox(-0.9F, -0.85F, -0.4F, 1.0F, 1.0F, 1.0F, 0.0F, false);

    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.bone);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){}

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
