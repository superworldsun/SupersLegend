package com.superworldsun.superslegend.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.util.PlayerAnimationUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class MixinItemInHandRenderer {
    @Inject(
            method = "renderArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
                    ordinal = 1
            )
    )
    private void superslegend$pointPegasusWeaponForward(AbstractClientPlayer player, float partialTick,
                                                         float pitch, InteractionHand hand, float swingProgress,
                                                         ItemStack itemStack, float equippedProgress,
                                                         PoseStack poseStack, MultiBufferSource buffer, int light,
                                                         CallbackInfo callbackInfo) {
        if (PlayerAnimationUtil.hasPegasusWeaponForward(player)
                && (itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof TridentItem)) {
            // Match the forward pitch used by the third-person charge pose.
            // Applying it immediately before item rendering preserves the
            // normal first-person hand placement and handedness.
            poseStack.mulPose(Axis.XP.rotationDegrees(-80.0F));
        }
    }
}
