package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.entity.GossipStoneTileEntity;
import com.superworldsun.superslegend.client.screen.GossipStoneScreen;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.UUID;

public class LockedDoor extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public LockedDoor(Properties properties)
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
		return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.above()).is(BlockInit.LOCKED_DOOR_TOP.get()) || world.isEmptyBlock(pos.above()));
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
		world.setBlockAndUpdate(pos.above(), BlockInit.LOCKED_DOOR_TOP.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
	}

	@Override
	public void destroy(IWorld world, BlockPos pos, BlockState state)
	{
		world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
	}

	public ActionResultType use(BlockState blockstate, World worldIn, BlockPos pos, PlayerEntity playerentity, Hand hand, BlockRayTraceResult blocktrace) {
		ItemStack itemstack = playerentity.getItemInHand(hand);
		Item item = itemstack.getItem();
		if (item != ItemInit.RUPEE.get())
		{
			return super.use(blockstate, worldIn, pos, playerentity, hand, blocktrace);
		}
		else
		worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 1);
		{
			if (!playerentity.isCreative()) {
				if (item == ItemInit.RUPEE.get())
				{
					itemstack.shrink(1);
				}
			}

			return ActionResultType.sidedSuccess(worldIn.isClientSide);
		}
	}


}