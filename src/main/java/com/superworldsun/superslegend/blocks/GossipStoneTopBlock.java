package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.PropertiesInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.DebugPacketSender;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class GossipStoneTopBlock extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	protected static final VoxelShape SHAPE = Block.box(0.0D, -16.0D, 2.0D, 16.0D, 5.0D, 14.0D);
	
	public GossipStoneTopBlock()
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
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos)
	{
		return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.below()).is(BlockInit.GOSSIP_STONE_BLOCK.get()));
	}
	
	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean flag)
	{
		DebugPacketSender.sendNeighborsUpdatePacket(world, pos);
		
		if (!canSurvive(state, world, pos))
		{
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}
	
	@SuppressWarnings("deprecation")
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		pos = pos.below();
		return world.getBlockState(pos).getBlock().use(state, world, pos, player, hand, rayTraceResult);
	}
}
