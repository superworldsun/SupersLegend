package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLevel.class)
public abstract class MixinTimeScaledRandomTick {
    @Redirect(
            method = "tickChunk",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;randomTick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V")
    )
    private void superslegend$scaleConfiguredRandomTicks(BlockState state, ServerLevel level,
                                                          BlockPos pos, RandomSource random) {
        Block block = state.getBlock();
        boolean affected = block instanceof FireBlock
                ? Config.timeSongsAffectFireSpread()
                : Config.timeSongsAffectCropGrowth() && superslegend$isGrowingPlant(block);
        if (!affected) {
            state.randomTick(level, pos, random);
            return;
        }

        TimeSongSavedData.Mode mode = TimeSongSavedData.getMode(level);
        if (mode != TimeSongSavedData.Mode.INVERTED
                || TimeSongSavedData.shouldRunInvertedTick(level)) {
            state.randomTick(level, pos, random);
        }
        if (mode == TimeSongSavedData.Mode.DOUBLE) {
            state.randomTick(level, pos, random);
        }
    }

    @Unique
    private static boolean superslegend$isGrowingPlant(Block block) {
        return block instanceof BonemealableBlock
                || block instanceof SugarCaneBlock
                || block instanceof CactusBlock
                || block instanceof NetherWartBlock
                || block instanceof GrowingPlantHeadBlock
                || block instanceof ChorusFlowerBlock
                || block instanceof BambooStalkBlock;
    }
}
