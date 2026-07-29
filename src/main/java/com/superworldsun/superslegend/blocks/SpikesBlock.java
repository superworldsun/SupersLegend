package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.items.armors.HoverBootsArmor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SpikesBlock extends Block

{
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public SpikesBlock(Properties properties) {
        super(properties);
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void entityInside(@NotNull BlockState pState, Level level, @NotNull BlockPos pPos, Entity entity) {
        // Spike collision is reported even when the player is exactly level with the top of the
        // surrounding floor. Let active Hover Boots consume that contact before applying damage.
        // If their hazard hover has expired, this returns false and spikes hurt normally.
        if (entity instanceof Player player && HoverBootsArmor.handleSpikeContact(player, pPos)) {
            return;
        }
        entity.hurt(level.damageSources().cactus(), 6.0F);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
        return false;
    }

    @Override
    public boolean isPossibleToRespawnInThis(@NotNull BlockState pState) {
        return false;
    }
}
