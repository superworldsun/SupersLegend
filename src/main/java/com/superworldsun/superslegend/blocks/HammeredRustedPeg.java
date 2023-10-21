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
import org.jetbrains.annotations.NotNull;

public class HammeredRustedPeg extends Block

{

    protected static final VoxelShape SHAPE = Block.box(3.75D, 0.0D, 3.75D, 12.25D, 1.0D, 12.25D);

    public HammeredRustedPeg(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random)
    {
        super.randomTick(state, level, pos, random);
        {
            level.setBlockAndUpdate(pos, BlockInit.RUSTED_PEG_BLOCK.get().defaultBlockState());

            BlockPos currentPos = pos;
            //TODO test around to see if theres a better sound effect
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ANVIL_HIT, SoundSource.PLAYERS, 1f, 1f);
        }
        super.randomTick(state, level, pos, random);
    }

    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit)
    {
        BlockPos currentPos = player.blockPosition();
        level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ARMOR_EQUIP_IRON, SoundSource.PLAYERS, 1f, 1f);

        level.setBlock(pos, BlockInit.RUSTED_PEG_BLOCK.get().defaultBlockState(), 3);

        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }
}
