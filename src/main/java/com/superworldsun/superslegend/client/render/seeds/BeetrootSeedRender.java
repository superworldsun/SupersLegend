package com.superworldsun.superslegend.client.render.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.BeetrootSeedEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BeetrootSeedRender extends SeedRenderer<BeetrootSeedEntity>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/seeds/beetroot_seed.png");
	
	public BeetrootSeedRender(EntityRendererManager renderManager)
	{
		super(renderManager, TEXTURE);
	}
}