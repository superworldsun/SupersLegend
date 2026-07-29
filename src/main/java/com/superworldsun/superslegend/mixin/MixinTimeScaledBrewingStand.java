package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandBlockEntity.class)
public abstract class MixinTimeScaledBrewingStand {
    @Unique
    private static final ThreadLocal<Boolean> superslegend$extraTimeSongTick =
            ThreadLocal.withInitial(() -> false);

    @Inject(method = "serverTick", at = @At("HEAD"), cancellable = true)
    private static void superslegend$slowBrewing(Level level, BlockPos pos, BlockState state,
                                                 BrewingStandBlockEntity brewingStand,
                                                 CallbackInfo callbackInfo) {
        if (Config.timeSongsAffectBrewingStands()
                && !superslegend$extraTimeSongTick.get()
                && TimeSongSavedData.getMode(level) == TimeSongSavedData.Mode.INVERTED
                && !TimeSongSavedData.shouldRunInvertedTick(level)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "serverTick", at = @At("TAIL"))
    private static void superslegend$doubleBrewing(Level level, BlockPos pos, BlockState state,
                                                   BrewingStandBlockEntity brewingStand,
                                                   CallbackInfo callbackInfo) {
        if (!Config.timeSongsAffectBrewingStands()
                || superslegend$extraTimeSongTick.get()
                || TimeSongSavedData.getMode(level) != TimeSongSavedData.Mode.DOUBLE) {
            return;
        }

        superslegend$extraTimeSongTick.set(true);
        try {
            BrewingStandBlockEntity.serverTick(level, pos, state, brewingStand);
        } finally {
            superslegend$extraTimeSongTick.set(false);
        }
    }
}
