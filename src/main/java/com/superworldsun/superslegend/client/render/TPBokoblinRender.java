package com.superworldsun.superslegend.client.render;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.mobs.TPBokoblnModel;
import com.superworldsun.superslegend.entities.TPBokoblinEntity;
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

}
