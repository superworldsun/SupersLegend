package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.tile.LightEmitterTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class LightEmitterBlock extends Block
{
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	
	public LightEmitterBlock()
	{
		super(Properties.of(Material.STONE).lightLevel(s -> s.getValue(LIT) ? 15 : 0));
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}
	
	@Override
	public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos neighborPos, boolean flag)
	{
		if (!world.isClientSide)
		{
			if (blockState.getValue(LIT) != world.hasNeighborSignal(blockPos))
			{
				world.setBlock(blockPos, blockState.cycle(LIT), 2);
			}
		}
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state)
	{
		state.add(FACING).add(LIT);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx)
	{
		Direction facing = ctx.getNearestLookingDirection().getOpposite();
		boolean lit = ctx.getLevel().hasNeighborSignal(ctx.getClickedPos());
		return defaultBlockState().setValue(FACING, facing).setValue(LIT, lit);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new LightEmitterTileEntity(state.getValue(FACING));
	}
}
