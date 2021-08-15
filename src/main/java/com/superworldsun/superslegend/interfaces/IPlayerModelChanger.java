package com.superworldsun.superslegend.interfaces;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPlayerModelChanger
{
	@OnlyIn(value = Dist.CLIENT)
	PlayerModel<AbstractClientPlayerEntity> getPlayerModel(AbstractClientPlayerEntity player);
	
	@OnlyIn(value = Dist.CLIENT)
	ResourceLocation getPlayerTexture(AbstractClientPlayerEntity player);
}
