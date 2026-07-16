package com.superworldsun.superslegend.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    @Unique
    private PlayerModel<AbstractClientPlayer> superslegend$baseModel;
    @Unique
    private RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> superslegend$armorLayer;
    @Unique
    private RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> superslegend$capeLayer;

    // This constructor is fake and never used
    protected MixinPlayerRenderer() {
        super(null, null, 0);
    }

    @Inject(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    private void superslegend$chooseCurrentModel(AbstractClientPlayer player, float rotationYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
        if (superslegend$baseModel == null) {
            superslegend$baseModel = model;

            for (RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> layer : layers) {
                if (layer instanceof HumanoidArmorLayer) {
                    superslegend$armorLayer = layer;
                } else if (layer instanceof CapeLayer) {
                    superslegend$capeLayer = layer;
                }
            }
        }

        IPlayerModelChanger changer = IPlayerModelChanger.get(player);
        model = changer != null ? changer.getPlayerModel(player) : superslegend$baseModel;

        // Armor and cape do not fit the transformed models
        if (model != superslegend$baseModel) {
            layers.remove(superslegend$armorLayer);
            layers.remove(superslegend$capeLayer);
        } else if (!layers.contains(superslegend$armorLayer)) {
            layers.add(superslegend$armorLayer);
            layers.add(superslegend$capeLayer);
        }
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    private void superslegend$getTextureLocation(AbstractClientPlayer player, CallbackInfoReturnable<ResourceLocation> ci) {
        IPlayerModelChanger changer = IPlayerModelChanger.get(player);

        if (changer != null) {
            ci.setReturnValue(changer.getPlayerTexture(player));
        }
    }
}
