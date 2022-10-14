package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.PropertiesInit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PostboxTopBlock extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	protected static final VoxelShape SHAPE = Block.box(0.0D, -16.0D, 2.0D, 16.0D, 5.0D, 14.0D);

	public PostboxTopBlock()
	{
		super(PropertiesInit.WRECKAGE);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}
	
	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
	
	@Override
	public void destroy(IWorld world, BlockPos pos, BlockState state)
	{
		world.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		pos = pos.below();
		return world.getBlockState(pos).getBlock().getPickBlock(state, target, world, pos, player);
	}
	
	@SuppressWarnings("deprecation")
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		pos = pos.below();
		return world.getBlockState(pos).getBlock().use(state, world, pos, player, hand, rayTraceResult);
	}
}
