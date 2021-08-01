package com.superworldsun.superslegend.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ClawshotModel<T extends Entity> extends SegmentedModel<T> {
    private final ModelRenderer main;

    public ClawshotModel() {
        texWidth = 16;
        texHeight = 16;

        main = new ModelRenderer(this);
        main.setPos(-1.0F, 6.0F, -9.0F);
        setRotationAngle(main, -1.5708F, 0.0F, 1.5708F);
        main.texOffs(3, 7).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        main.texOffs(0, 3).addBox(-0.5F, -3.25F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        main.texOffs(0, 6).addBox(0.0F, -2.5F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        main.texOffs(5, 2).addBox(-0.5F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        main.texOffs(5, 5).addBox(0.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        main.texOffs(0, 0).addBox(-0.5F, -3.25F, 1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        main.texOffs(4, 0).addBox(0.0F, -2.5F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.main);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){}

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
