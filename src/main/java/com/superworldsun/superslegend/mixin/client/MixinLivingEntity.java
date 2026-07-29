package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.client.PegasusBootsInputEvents;
import com.superworldsun.superslegend.interfaces.IHookshotSwimAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity implements IHookshotSwimAnimation {
    @Shadow
    @Final
    public WalkAnimationState walkAnimation;

    @Shadow
    private float swimAmount;

    @Shadow
    private float swimAmountO;

    @Override
    public void superslegend$resetHookshotSwimAnimation() {
        swimAmount = 0.0F;
        swimAmountO = 0.0F;
    }

    @ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
    private Vec3 holdPegasusBootsPlayerInPlace(Vec3 travelVector) {
        if ((Object) this == Minecraft.getInstance().player && PegasusBootsInputEvents.isWarmingUp()) {
            LivingEntity player = (LivingEntity) (Object) this;
            Vec3 movement = player.getDeltaMovement();
            player.setDeltaMovement(0.0D, movement.y, 0.0D);
            return new Vec3(0.0D, travelVector.y, 0.0D);
        }
        return travelVector;
    }

    @Inject(method = "updateWalkAnimation", at = @At("HEAD"), cancellable = true)
    private void animatePegasusBootsWarmUp(float movementAmount, CallbackInfo callbackInfo) {
        if ((Object) this == Minecraft.getInstance().player && PegasusBootsInputEvents.isWarmingUp()) {
            walkAnimation.update(1.0F, 0.4F);
            callbackInfo.cancel();
        }
    }
}
