package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinTimeScaledFurnace {
    @Unique
    private static final ThreadLocal<Boolean> superslegend$extraTimeSongTick =
            ThreadLocal.withInitial(() -> false);

    @Inject(method = "serverTick", at = @At("HEAD"), cancellable = true)
    private static void superslegend$slowFurnace(Level level, BlockPos pos, BlockState state,
                                                 AbstractFurnaceBlockEntity furnace,
                                                 CallbackInfo callbackInfo) {
        if (Config.timeSongsAffectFurnaces()
                && !superslegend$extraTimeSongTick.get()
                && TimeSongSavedData.getMode(level) == TimeSongSavedData.Mode.INVERTED
                && !TimeSongSavedData.shouldRunInvertedTick(level)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "serverTick", at = @At("TAIL"))
    private static void superslegend$doubleFurnace(Level level, BlockPos pos, BlockState state,
                                                   AbstractFurnaceBlockEntity furnace,
                                                   CallbackInfo callbackInfo) {
        if (!Config.timeSongsAffectFurnaces()
                || superslegend$extraTimeSongTick.get()
                || TimeSongSavedData.getMode(level) != TimeSongSavedData.Mode.DOUBLE) {
            return;
        }

        superslegend$extraTimeSongTick.set(true);
        try {
            AbstractFurnaceBlockEntity.serverTick(level, pos, state, furnace);
        } finally {
            superslegend$extraTimeSongTick.set(false);
        }
    }
}
