package com.superworldsun.superslegend.util;

import net.minecraft.util.math.BlockPos;

public class DimBlockPos {
  private BlockPos pos;
  private int dim;

  public DimBlockPos(BlockPos pos, int dim) {
    this.pos = pos;
    this.dim = dim;
  }

  public BlockPos getPos() {
    return pos;
  }

  public int getDim() {
    return dim;
  }
}
