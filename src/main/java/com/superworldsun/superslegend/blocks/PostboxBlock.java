package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.entity.PostboxBlockEntity;
import com.superworldsun.superslegend.registries.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PostboxBlock extends Block implements EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final VoxelShape SHAPE = createPostboxShape();

	public PostboxBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.DOWN && !canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.above()).is(BlockInit.POSTBOX_TOP.get()) || world.isEmptyBlock(pos.above()));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean flag) {
		world.setBlockAndUpdate(pos.above(), BlockInit.POSTBOX_TOP.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			getBlockEntity(world, pos).ifPresent(PostboxBlockEntity::dropInventoryContents);
			world.removeBlockEntity(pos);
			world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}

	private Optional<PostboxBlockEntity> getBlockEntity(Level world, BlockPos pos) {
		return Optional.ofNullable(world.getBlockEntity(pos)).map(te -> (PostboxBlockEntity) te);
	}


	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {

		BlockEntity be = level.getBlockEntity(pos);
		if (!(be instanceof PostboxBlockEntity blockEntity))
			return InteractionResult.PASS;

		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}

		if (player instanceof ServerPlayer sPlayer) {
			getBlockEntity(level, pos).ifPresent(postbox -> postbox.interact(sPlayer, hand, state, level, pos));
		}

		return InteractionResult.CONSUME;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PostboxBlockEntity(pos, state);
	}

	private static VoxelShape createPostboxShape() {
		return Shapes.or(Block.box(6, 0, 6, 10, 6, 10), Block.box(1, 6, 1, 15, 22, 15), Block.box(-1, 22, -1, 17, 23, 17));
	}
}