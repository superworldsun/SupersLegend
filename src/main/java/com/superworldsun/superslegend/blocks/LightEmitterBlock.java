package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.tile.LightEmitterTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;

public class LightEmitterBlock extends Block
{
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	
	public LightEmitterBlock()
	{
		super(Properties.of(Material.STONE).lightLevel(s -> 15));
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state)
	{
		state.add(FACING);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx)
	{
		return defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection().getOpposite());
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
