package com.superworldsun.superslegend.client.render.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.CocoaBeanEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CocoaBeanRender extends SeedRenderer<CocoaBeanEntity>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/seeds/cocoa_bean.png");
	
	public CocoaBeanRender(EntityRendererManager renderManager)
	{
		super(renderManager, TEXTURE);
	}
}