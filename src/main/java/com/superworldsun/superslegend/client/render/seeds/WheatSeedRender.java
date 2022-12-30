package com.superworldsun.superslegend.client.render.seeds;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.seeds.WheatSeedEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class WheatSeedRender extends SeedRenderer<WheatSeedEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/entity/seeds/wheat_seed.png");

	public WheatSeedRender(EntityRendererManager renderManager) {
		super(renderManager, TEXTURE);
	}
}