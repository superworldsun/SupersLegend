package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CrackedFloor extends Block {
    public CrackedFloor(Properties properties) {
        super(properties);
    }

    //TODO, Make it so the player cant "B Hop" their way across
    @Override
    public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float p_180658_4_) {
        super.fallOn(worldIn, pos, entityIn, p_180658_4_);

        if (entityIn.isAlive())
        {
            worldIn.setBlock(pos.below().above(), Blocks.AIR.defaultBlockState(), 1);
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.CHAIN_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    //TODO, Make it so the block breaks regardless of how its been stepped on
    @Override
    public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
        super.stepOn(worldIn, pos, entityIn);

        if(entityIn.isAlive() && entityIn.tickCount % 5 == 0)
        {
            worldIn.setBlock(pos.below().above(), Blocks.AIR.defaultBlockState(), 1);

            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }


}
