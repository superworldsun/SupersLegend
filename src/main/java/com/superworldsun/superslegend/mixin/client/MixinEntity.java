package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.items.curios.head.masks.GiantsMask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Inject(method = "spawnSprintParticle", at = @At("HEAD"), cancellable = true)
    private void superslegend$replaceGiantSprintParticle(CallbackInfo callbackInfo) {
        if ((Object) this instanceof Player player
                && GiantsMask.isGiantTransformationActive(player)) {
            callbackInfo.cancel();
        }
    }

}
