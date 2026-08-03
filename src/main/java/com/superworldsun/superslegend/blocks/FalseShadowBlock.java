package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.entity.FalseShadowBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FalseShadowBlock extends ShadowBlock {

    public FalseShadowBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FalseShadowBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    /**
     * Collision and light occlusion are deliberately separate for this block. The player can
     * pass through it, but lighting should treat it like the block it is impersonating.
     */
    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof FalseShadowBlockEntity falseShadowBlockEntity) {
            BlockState disguise = falseShadowBlockEntity.getDisguise();
            if (disguise != null && !(disguise.getBlock() instanceof ShadowBlock)) {
                return disguise.getLightBlock(level, pos);
            }
        }
        return super.getLightBlock(state, level, pos);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (level.getBlockEntity(pos) instanceof FalseShadowBlockEntity falseShadowBlockEntity) {
            BlockState disguise = falseShadowBlockEntity.getDisguise();
            if (disguise != null && !(disguise.getBlock() instanceof ShadowBlock)) {
                return disguise.getShape(level, pos, context);
            }
        }
        return super.getShape(state, level, pos, context);
    }
}
