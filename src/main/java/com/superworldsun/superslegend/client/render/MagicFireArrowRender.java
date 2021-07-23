package com.superworldsun.superslegend.client.render;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicFireArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MagicFireArrowRender extends ArrowRenderer<MagicFireArrowEntity>
{
	protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/arrows/magic_fire_arrow.png");
	
	public MagicFireArrowRender(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getTextureLocation(MagicFireArrowEntity entity)
	{
		return TEXTURE;
	}
}