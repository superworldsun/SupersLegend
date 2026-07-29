package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.client.PegasusBootsInputEvents;
import net.minecraft.client.MouseHandler;
import net.minecraft.util.SmoothDouble;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MixinMouseHandler {
    @Shadow
    private double accumulatedDX;

    @Shadow
    @Final
    private SmoothDouble smoothTurnX;

    @Inject(method = "turnPlayer", at = @At("HEAD"))
    private void lockPegasusBootsHorizontalLook(CallbackInfo callbackInfo) {
        if (PegasusBootsInputEvents.shouldLockHorizontalLook()) {
            accumulatedDX = 0.0D;
            smoothTurnX.reset();
        }
    }
}
