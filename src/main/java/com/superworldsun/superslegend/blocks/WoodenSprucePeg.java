package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodenSprucePeg extends Block {

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);
    protected static final VoxelShape HITBOX_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 24.0D, 12.0D);

    public WoodenSprucePeg(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return HITBOX_SHAPE;
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player)
    {
        if (player.isHolding(ItemInit.MAGIC_HAMMER.get()) || player.isHolding(ItemInit.MEGATON_HAMMER.get())
                || player.isHolding(ItemInit.SKULL_HAMMER.get()))
        {
            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1f, 1f);

            world.setBlock(pos, BlockInit.HAMMERED_WOODEN_PEG_SPRUCE.get().defaultBlockState(), 3);
        }
        super.attack(state, world, pos, player);
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
