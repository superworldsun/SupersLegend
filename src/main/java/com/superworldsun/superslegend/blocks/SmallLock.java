package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoorHingeSide;
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

public class SmallLock extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	protected static final VoxelShape FACE_EAST_AABB = Block.box(-3.0D, 0.0D, 0.0D, 0.0D, 32.0D, 16.0D);
	protected static final VoxelShape FACE_WEST_AABB = Block.box(10.0D, 0.0D, 0.0D, 19.0D, 32.0D, 16.0D);
	protected static final VoxelShape FACE_SOUTH_AABB = Block.box(0.0D, 0.0D, -3.0D, 16.0D, 32.0D, 6.0D);
	protected static final VoxelShape FACE_NORTH_AABB = Block.box(0.0D, 0.0D, 19.0D, 16.0D, 32.0D, 16.0D);
	/*protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 3.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 32.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 32.0D, 16.0D);*/

	public SmallLock(Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch((Direction)state.getValue(FACING)) {
			case EAST:
			default:
				return FACE_EAST_AABB;
			case WEST:
				return FACE_WEST_AABB;
			case SOUTH:
				return FACE_SOUTH_AABB;
			case NORTH:
				return FACE_NORTH_AABB;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return facing == Direction.DOWN && !canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
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
	public void destroy(IWorld world, BlockPos pos, BlockState state)
	{
		world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
	}

	public ActionResultType use(BlockState blockstate, World worldIn, BlockPos pos, PlayerEntity playerentity, Hand hand, BlockRayTraceResult blocktrace) {
		ItemStack itemstack = playerentity.getItemInHand(hand);
		Item item = itemstack.getItem();
		if (item != ItemInit.SMALL_KEY.get())
		{
			return super.use(blockstate, worldIn, pos, playerentity, hand, blocktrace);
		}
		else
		worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 1);
		{
			if (!playerentity.isCreative()) {
				if (item == ItemInit.SMALL_KEY.get())
				{
					itemstack.shrink(1);
				}
			}

			return ActionResultType.sidedSuccess(worldIn.isClientSide);
		}
	}


}