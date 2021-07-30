package com.superworldsun.superslegend.blocks;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

import com.superworldsun.superslegend.blocks.tile.FanTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class FanBlock extends DirectionalBlock implements IWaterLoggable
{
	private static final VoxelShape SHAPE = VoxelShapes.or(VoxelShapes.box(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.8125D),
			VoxelShapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), VoxelShapes.box(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D),
			VoxelShapes.box(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D), VoxelShapes.box(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	
	public FanBlock()
	{
		super(Properties.of(Material.STONE).strength(10.0F, 1000.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE));
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false));
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new FanTileEntity();
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING).add(WATERLOGGED);
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
	{
		return rotateShape(Direction.SOUTH, state.getValue(FACING), SHAPE);
	}
	
	@Override
	public FluidState getFluidState(BlockState state)
	{
		return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : Fluids.EMPTY.defaultFluidState();
	}
	
	@Override
	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		worldIn.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld level, BlockPos currentPos, BlockPos facingPos)
	{
		if (state.getValue(WATERLOGGED))
		{
			level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		
		return state;
	}
	
	// This should be in some helper class, it is super useful
	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape)
	{
		VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };
		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		
		for (int i = 0; i < times; i++)
		{
			buffer[0].forAllBoxes(
					(minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1], VoxelShapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}
		
		return buffer[0];
	}
}
