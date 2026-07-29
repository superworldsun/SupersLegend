package com.superworldsun.superslegend.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import com.superworldsun.superslegend.interfaces.IHandRenderer;
import com.superworldsun.superslegend.util.PlayerAnimationUtil;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class MixinItemInHandLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {
    @Shadow
    @Final
    private ItemInHandRenderer itemInHandRenderer;

    // This constructor is fake and never used
    protected MixinItemInHandLayer() {
        super(null);
    }

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void superslegend$renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack,
            MultiBufferSource buffer, int light, CallbackInfo ci) {
        if (livingEntity instanceof Player player
                && DekuFlowerFlightEvents.isFlowerAbilityActive(player)) {
            ci.cancel();
            return;
        }

        if (getParentModel() instanceof IHandRenderer handRenderer) {
            if (!itemStack.isEmpty()) {
                poseStack.pushPose();
                getParentModel().translateToHand(arm, poseStack);
                handRenderer.renderThirdPersonItem(itemInHandRenderer, livingEntity, itemStack, displayContext, arm, poseStack, buffer, light);
                poseStack.popPose();
            }

            ci.cancel();
        }
    }

    @Inject(
            method = "renderArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/ArmedModel;translateToHand(Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;)V"
            )
    )
    private void superslegend$raisePegasusWeapon(LivingEntity livingEntity, ItemStack itemStack,
                                                  ItemDisplayContext displayContext, HumanoidArm arm,
                                                  PoseStack poseStack, MultiBufferSource buffer, int light,
                                                  CallbackInfo callbackInfo) {
        if (isPegasusChargeWeapon(livingEntity, itemStack)) {
            // Applied before the arm transform so these are position-only
            // adjustments and do not alter the weapon's current angle.
            // Entity models invert their Y axis during rendering, so positive
            // model-space Y moves the rendered weapon upward on the player.
            poseStack.translate(0.0D, -0.40D, -0.30D);
        }
    }

    @Inject(
            method = "renderArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/ArmedModel;translateToHand(Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void superslegend$pointPegasusWeaponForward(LivingEntity livingEntity, ItemStack itemStack,
                                                         ItemDisplayContext displayContext, HumanoidArm arm,
                                                         PoseStack poseStack, MultiBufferSource buffer, int light,
                                                         CallbackInfo callbackInfo) {
        if (isPegasusChargeWeapon(livingEntity, itemStack)) {
            // The vanilla hand transform makes an upright weapon. Rotating in
            // the positive direction aims it outward; 65 degrees keeps it
            // forward without pitching the model beneath the player.
            poseStack.mulPose(Axis.XP.rotationDegrees(65.0F));
        }
    }

    private static boolean isPegasusChargeWeapon(LivingEntity livingEntity, ItemStack itemStack) {
        return livingEntity instanceof Player player
                && PlayerAnimationUtil.hasPegasusWeaponForward(player)
                && (itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof TridentItem);
    }
}
