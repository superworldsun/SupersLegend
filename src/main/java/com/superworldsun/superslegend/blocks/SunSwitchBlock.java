package com.superworldsun.superslegend.blocks;

import static net.minecraft.state.properties.BlockStateProperties.FACING;
import static net.minecraft.state.properties.BlockStateProperties.POWER;
import static net.minecraft.state.properties.BlockStateProperties.POWERED;

import com.superworldsun.superslegend.blocks.tile.SunSwitchTileEntity;
import com.superworldsun.superslegend.util.BlockShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class SunSwitchBlock extends Block
{
	private static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 16D, 1D);
	// only for model rotation
	public static final DirectionProperty ROTATION = DirectionProperty.create("rotation", Direction.Plane.HORIZONTAL);
	
	public SunSwitchBlock()
	{
		super(Properties.of(Material.STONE).noOcclusion().isViewBlocking((s, r, p) -> false));
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.SOUTH).setValue(ROTATION, Direction.SOUTH).setValue(POWERED, false).setValue(POWER, 0));
	}
	
	@Override
	public int getSignal(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, Direction direction)
	{
		return blockState.getValue(POWER);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(ROTATION, context.getHorizontalDirection());
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING).add(ROTATION).add(POWERED).add(POWER);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
	{
		return BlockShapeHelper.rotateShape(Direction.SOUTH, state.getValue(FACING), SHAPE);
	}
	
	@Override
	public boolean isSignalSource(BlockState blockState)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState blockState, IBlockReader world)
	{
		return new SunSwitchTileEntity();
	}
	
	@Override
	public boolean hasTileEntity(BlockState blockState)
	{
		return true;
	}
}
