package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickPriority;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LevelAccessor.class)
public interface MixinTimeScaledLevelAccessor {
    /**
     * Replaces the interface default so scheduled block ticks (including redstone)
     * use the active song's time scale. Fluid ticks intentionally remain unchanged.
     *
     * @author SupersLegend
     * @reason Scale scheduled block ticks while a time song is active.
     */
    @Overwrite
    default void scheduleTick(BlockPos pos, Block block, int delay, TickPriority priority) {
        LevelAccessor level = (LevelAccessor) this;
        int scaledDelay = TimeSongSavedData.scaleScheduledBlockDelay(level, block, delay);
        level.getBlockTicks().schedule(new ScheduledTick<>(block, pos,
                level.getLevelData().getGameTime() + scaledDelay,
                priority, level.nextSubTickCount()));
    }

    /**
     * @author SupersLegend
     * @reason Scale scheduled block ticks while a time song is active.
     */
    @Overwrite
    default void scheduleTick(BlockPos pos, Block block, int delay) {
        LevelAccessor level = (LevelAccessor) this;
        int scaledDelay = TimeSongSavedData.scaleScheduledBlockDelay(level, block, delay);
        level.getBlockTicks().schedule(new ScheduledTick<>(block, pos,
                level.getLevelData().getGameTime() + scaledDelay,
                level.nextSubTickCount()));
    }

}
