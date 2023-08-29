package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HammeredWoodenOakPeg extends Block

{

    protected static final VoxelShape SHAPE = Block.box(3.75D, 0.0D, 3.75D, 12.25D, 1.0D, 12.25D);

    public HammeredWoodenOakPeg(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        super.randomTick(state, level, pos, random);
        {
            level.setBlockAndUpdate(pos, BlockInit.OAK_PEG_BLOCK.get().defaultBlockState());

            BlockPos currentPos = pos;
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WOOD_HIT, SoundSource.PLAYERS, 1f, 1f);
        }
        super.randomTick(state, level, pos, random);
    }

    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        BlockPos currentPos = player.blockPosition();
        level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WOOD_HIT, SoundSource.PLAYERS, 1f, 1f);

        level.setBlock(pos, BlockInit.OAK_PEG_BLOCK.get().defaultBlockState(), 3);

        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
