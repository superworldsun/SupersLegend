package com.superworldsun.superslegend.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.client.events.HookshotPlayerPoseEvents;
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
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
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

    @Inject(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("TAIL"))
    private void superslegend$restoreBaseModel(AbstractClientPlayer player, float rotationYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
        if (superslegend$baseModel == null) {
            return;
        }

        // PlayerRenderer is shared by every player using this skin type and is
        // also reused for the local first-person arm. Never leave another
        // player's transformation model installed after their render finishes.
        model = superslegend$baseModel;

        if (superslegend$armorLayer != null && !layers.contains(superslegend$armorLayer)) {
            layers.add(superslegend$armorLayer);
        }
        if (superslegend$capeLayer != null && !layers.contains(superslegend$capeLayer)) {
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

    @Inject(method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At("TAIL"))
    private void superslegend$orientHookshotPull(AbstractClientPlayer player, PoseStack poseStack, float ageInTicks,
                                                  float rotationYaw, float partialTicks, CallbackInfo ci) {
        Vec3 pullDirection = HookshotPlayerPoseEvents.getPullDirection(player, partialTicks);
        if (pullDirection == null || pullDirection.lengthSqr() < 1.0E-7D) {
            return;
        }

        // Vanilla shifts visually-swimming models down by a full block. The hookshot uses the
        // swimming hitbox on land, so undo that render-only shift before aiming the model.
        if (player.isVisuallySwimming()) {
            poseStack.translate(0.0D, 1.0D, -0.3D);
        }

        float horizontalDistance = Mth.sqrt((float) (pullDirection.x * pullDirection.x + pullDirection.z * pullDirection.z));
        float elevation = (float) (Mth.atan2(pullDirection.y, horizontalDistance) * Mth.RAD_TO_DEG);
        float swimAmount = player.getSwimAmount(partialTicks);
        float vanillaSwimRotation = Mth.lerp(swimAmount, 0.0F,
                player.isInWater() ? -90.0F - player.getXRot() : -90.0F);
        float desiredPullRotation = -90.0F + elevation;
        poseStack.mulPose(Axis.XP.rotationDegrees(desiredPullRotation - vanillaSwimRotation));
    }

    @Inject(method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At("HEAD"))
    private void superslegend$liftHookshotPullModel(AbstractClientPlayer player, PoseStack poseStack, float ageInTicks,
                                                     float rotationYaw, float partialTicks, CallbackInfo ci) {
        Vec3 pullDirection = HookshotPlayerPoseEvents.getPullDirection(player, partialTicks);
        if (pullDirection != null && pullDirection.lengthSqr() >= 1.0E-7D) {
            poseStack.translate(0.0D, 0.7D, 0.0D);
        }
    }
}
