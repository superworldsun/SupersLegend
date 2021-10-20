package com.superworldsun.superslegend.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.RailState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RailStateGetter extends RailState
{
    public RailStateGetter(World p_i47755_1_, BlockPos p_i47755_2_, BlockState p_i47755_3_)
    {
        super(p_i47755_1_, p_i47755_2_, p_i47755_3_);
    }

    @Override
    public int countPotentialConnections()
    {
        return super.countPotentialConnections();
    }
}
