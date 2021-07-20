package com.superworldsun.superslegend.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.util.IResizableEntity;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends EntityRenderer<PlayerEntity>
{
	// This constructor is fake and never used
	protected MixinPlayerRenderer(EntityRendererManager manager)
	{
		super(manager);
	}
	
	@Overwrite
	protected void scale(AbstractClientPlayerEntity player, MatrixStack matrix, float partialTicks)
	{
		float playerScale = ((IResizableEntity) player).getScaleForRender(partialTicks);
		float scale = 0.9375F * playerScale;
		matrix.scale(scale, scale, scale);
		shadowRadius = 0.5F * playerScale;
	}
}
