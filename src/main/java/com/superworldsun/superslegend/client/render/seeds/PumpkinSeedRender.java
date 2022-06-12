package com.superworldsun.superslegend.client.render.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.PumpkinSeedEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class PumpkinSeedRender extends SeedRenderer<PumpkinSeedEntity>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/seeds/pumpkin_seed.png");
	
	public PumpkinSeedRender(EntityRendererManager renderManager)
	{
		super(renderManager, TEXTURE);
	}
}