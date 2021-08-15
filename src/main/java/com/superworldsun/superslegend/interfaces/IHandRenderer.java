package com.superworldsun.superslegend.interfaces;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
public interface IHandRenderer
{
	void renderHand(MatrixStack matrix, IRenderTypeBuffer buffer, int color, AbstractClientPlayerEntity player, ModelRenderer hand, ModelRenderer handOverlay, ResourceLocation texture);
}
