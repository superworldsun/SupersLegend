package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.tile.GossipStoneTileEntity;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.util.UUID;

public class PostboxBlock extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 15.0D, 23.0D, 15.0D);

	public PostboxBlock(Properties properties)
	{
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return facing == Direction.DOWN && !canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos)
	{
		return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.above()).is(BlockInit.POSTBOX_TOP.get()) || world.isEmptyBlock(pos.above()));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.MODEL;
	}
	
	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
	
	@Override
	public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean flag)
	{
		world.setBlockAndUpdate(pos.above(), BlockInit.POSTBOX_TOP.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
	}
	
	@Override
	public void destroy(IWorld world, BlockPos pos, BlockState state)
	{
		world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

}