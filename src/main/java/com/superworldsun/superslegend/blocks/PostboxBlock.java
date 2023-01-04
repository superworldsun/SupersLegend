package com.superworldsun.superslegend.blocks;

import java.util.Optional;

import com.superworldsun.superslegend.blocks.entity.PostboxTileEntity;
import com.superworldsun.superslegend.registries.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
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
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PostboxBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final VoxelShape SHAPE = createPostboxShape();

	public PostboxBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.DOWN && !canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.above()).is(BlockInit.POSTBOX_TOP.get()) || world.isEmptyBlock(pos.above()));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean flag) {
		world.setBlockAndUpdate(pos.above(), BlockInit.POSTBOX_TOP.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
	}

	@Override
	public void destroy(IWorld world, BlockPos pos, BlockState state) {
		world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
	}

	@Override
	public void onRemove(BlockState oldBlockState, World world, BlockPos blockPos, BlockState blockState, boolean flag) {
		getTileEntity(world, blockPos).ifPresent(postbox -> InventoryHelper.dropContents(world, blockPos, postbox.inventory));
		world.removeBlockEntity(blockPos);
		world.setBlock(blockPos.above(), Blocks.AIR.defaultBlockState(), 3);
	}

	private Optional<PostboxTileEntity> getTileEntity(World world, BlockPos blockPos) {
		return Optional.ofNullable(world.getBlockEntity(blockPos)).map(t -> (PostboxTileEntity) t);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if (!world.isClientSide) {
			getTileEntity(world, blockPos).ifPresent(postbox -> postbox.openGui(blockPos, player));
		}

		return ActionResultType.SUCCESS;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new PostboxTileEntity();
	}

	private static VoxelShape createPostboxShape() {
		return VoxelShapes.or(Block.box(6, 0, 6, 10, 6, 10), Block.box(1, 6, 1, 15, 22, 15), Block.box(-1, 22, -1, 17, 23, 17));
	}
}