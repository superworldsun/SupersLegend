package com.superworldsun.superslegend.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.mobs.TPBokoblnModel;
import com.superworldsun.superslegend.entities.TPBokoblinEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class TPBokoblinRender extends MobRenderer<TPBokoblinEntity, TPBokoblnModel<TPBokoblinEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/tp_bokoblin.png");

    public TPBokoblinRender(EntityRendererManager manager){
        super (manager, new TPBokoblnModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(TPBokoblinEntity entity) {
        return TEXTURE;
    }

    public void render(TPBokoblinEntity entity, float floatvalue, float floatvalue2, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int intvalue) {
        this.shadowRadius = 0.5F;
        super.render(entity, floatvalue, floatvalue2, matrixStack, renderTypeBuffer, intvalue);
    }

    protected void scale(TPBokoblinEntity entity, MatrixStack matrixStack, float getfloat) {
        matrixStack.scale(0.65F, 0.65F, 0.65F);
        matrixStack.translate(0.0D, (double)0.0F, 0.0D);
    }

}
