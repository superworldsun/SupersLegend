package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.items.curios.head.masks.GiantsMask;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinGiantFootsteps {
    @Inject(method = "playStepSound", at = @At("TAIL"))
    private void superslegend$playGiantFootstep(BlockPos position,
                                                BlockState state,
                                                CallbackInfo callbackInfo) {
        GiantsMask.playHeavyFootstep((Player) (Object) this);
    }
}
