package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.util.PlayerAnimationUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Applies custom player arm rotations after vanilla finishes animating the model. */
@Mixin(HumanoidModel.class)
public abstract class MixinHumanoidModel<T extends LivingEntity> {
    @Shadow public ModelPart leftArm;
    @Shadow public ModelPart rightArm;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void superslegend$applyCustomPlayerPose(T entity, float limbSwing, float limbSwingAmount,
                                                     float ageInTicks, float netHeadYaw, float headPitch,
                                                     CallbackInfo callbackInfo) {
        if (!(entity instanceof Player player)) {
            return;
        }

        if (PlayerAnimationUtil.hasArmsRaised(player)) {
            // Humanoid arms point down at zero rotation. A half turn points them
            // vertically upward while retaining their normal shoulder pivots.
            leftArm.xRot = (float) -Math.PI;
            leftArm.yRot = 0.0F;
            leftArm.zRot = 0.0F;
            rightArm.xRot = (float) -Math.PI;
            rightArm.yRot = 0.0F;
            rightArm.zRot = 0.0F;
            return;
        }

        if (PlayerAnimationUtil.hasPegasusWeaponForward(player)) {
            HumanoidArm weaponArm = findWeaponArm(player);
            ModelPart arm = weaponArm == HumanoidArm.LEFT ? leftArm : rightArm;
            arm.xRot = (float) -Math.PI / 2.0F;
            arm.yRot = 0.0F;
            arm.zRot = 0.0F;
        }
    }

    private static HumanoidArm findWeaponArm(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        if (isChargeWeapon(mainHand)) {
            return player.getMainArm();
        }
        return player.getMainArm() == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }

    private static boolean isChargeWeapon(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof TridentItem;
    }
}
