package com.superworldsun.superslegend.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LongshotModel<T extends Entity> extends SegmentedModel<T> {
    private final ModelRenderer main;

    public LongshotModel() {
        texWidth = 16;
        texHeight = 16;

        main = new ModelRenderer(this);
        main.setPos(-1.0F, 3.0F, -7.0F);
        setRotationAngle(main, 3.1416F, -0.7854F, 3.1416F);
        main.texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        main.texOffs(0, 4).addBox(0.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        main.texOffs(5, 0).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

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
