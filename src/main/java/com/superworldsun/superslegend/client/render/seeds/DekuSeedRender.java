package com.superworldsun.superslegend.client.render.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.DekuSeedEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DekuSeedRender extends SeedRenderer<DekuSeedEntity>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/arrows/pellet.png");
	
	public DekuSeedRender(EntityRendererManager renderManager)
	{
		super(renderManager, TEXTURE);
	}
}