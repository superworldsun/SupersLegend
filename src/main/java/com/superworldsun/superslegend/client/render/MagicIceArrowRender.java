package com.superworldsun.superslegend.client.render;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicIceArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MagicIceArrowRender extends ArrowRenderer<MagicIceArrowEntity>
{
	protected static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/arrows/magic_ice_arrow.png");
	
	public MagicIceArrowRender(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getTextureLocation(MagicIceArrowEntity entity)
	{
		return TEXTURE;
	}
}