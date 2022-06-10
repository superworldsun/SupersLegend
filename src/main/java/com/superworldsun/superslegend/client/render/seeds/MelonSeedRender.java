package com.superworldsun.superslegend.client.render.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.MelonSeedEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MelonSeedRender extends SeedRenderer<MelonSeedEntity>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/seeds/melon_seed.png");
	
	public MelonSeedRender(EntityRendererManager renderManager)
	{
		super(renderManager, TEXTURE);
	}
}