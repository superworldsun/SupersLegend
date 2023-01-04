package com.superworldsun.superslegend.blocks;

import java.util.Optional;

import com.superworldsun.superslegend.blocks.tile.PostboxTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
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

public class PostboxTopBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public PostboxTopBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return PostboxBlock.SHAPE.move(0, -1, 0);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public void destroy(IWorld world, BlockPos pos, BlockState state) {
		world.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return world.getBlockState(pos.below()).getBlock().getPickBlock(state, target, world, pos.below(), player);
	}

	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if (!world.isClientSide) {
			getTileEntity(world, blockPos).ifPresent(postbox -> postbox.openGui(blockPos, player));
		}

		return ActionResultType.SUCCESS;
	}

	private Optional<PostboxTileEntity> getTileEntity(World world, BlockPos blockPos) {
		return Optional.ofNullable(world.getBlockEntity(blockPos.below())).map(t -> (PostboxTileEntity) t);
	}
}
