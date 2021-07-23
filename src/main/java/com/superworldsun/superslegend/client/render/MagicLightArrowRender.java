package com.superworldsun.superslegend.client.render;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicLightArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MagicLightArrowRender extends ArrowRenderer<MagicLightArrowEntity>
{
	protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/arrows/magic_light_arrow.png");
	
	public MagicLightArrowRender(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getTextureLocation(MagicLightArrowEntity entity)
	{
		return TEXTURE;
	}
}