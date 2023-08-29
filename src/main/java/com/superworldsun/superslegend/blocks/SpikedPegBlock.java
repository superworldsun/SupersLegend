package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpikedPegBlock extends Block {

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape HITBOX_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 24.0D, 14.0D);

    public SpikedPegBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return HITBOX_SHAPE;
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player)
    {
        if (player.isHolding(ItemInit.SKULL_HAMMER.get()))
        {
            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundSource.PLAYERS, 1f, 1f);

            world.setBlock(pos, BlockInit.HAMMERED_SPIKED_PEG_BLOCK.get().defaultBlockState(), 3);
        }
        super.attack(state, world, pos, player);
    }

    @Override
    public void entityInside(BlockState pState, Level level, BlockPos pPos, Entity entity) {
        entity.hurt(level.damageSources().cactus(), 2.0F);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

}
