package com.superworldsun.superslegend.interfaces;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
public interface IHandRenderer {
    void renderFirstPersonHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, ModelPart hand, ModelPart handOverlay,
            ResourceLocation texture);
}
