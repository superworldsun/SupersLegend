package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.items.items.MedallionItem;
import com.superworldsun.superslegend.registries.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class WarpPadBlock extends HorizontalBlock {
	public static final IntegerProperty BLOCK_PART_X = IntegerProperty.create("model_part_x", 0, 2);
	public static final IntegerProperty BLOCK_PART_Z = IntegerProperty.create("model_part_z", 0, 2);

	public WarpPadBlock() {
		super(Properties.of(Material.STONE).noOcclusion());
		registerDefaultState(getStateForPartCoords(0, 0).setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, ISelectionContext selectionContext) {
		return getShapeForState(blockState);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState blockState) {
		return isCenterBlock(blockState) ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(BLOCK_PART_X).add(BLOCK_PART_Z).add(FACING);
	}

	@Override
	public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (x != 0 || z != 0) {
					BlockState blockPartState = getStateForPartCoords(x, z);
					BlockPos blockPartPos = blockPos.offset(x, 0, z);
					world.setBlockAndUpdate(blockPartPos, blockPartState);
				}
			}
		}
	}

	@Override
	public void destroy(IWorld world, BlockPos blockPos, BlockState blockState) {
		blockPos = blockPos.offset(-getBlockPartX(blockState), 0, -getBlockPartZ(blockState));

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				BlockPos blockPartPos = blockPos.offset(x, 0, z);
				world.removeBlock(blockPartPos, false);
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
		if (blockState.getBlock() == BlockInit.WARP_PAD.get()) {
			Item itemInHand = playerEntity.getItemInHand(hand).getItem();

			if (itemInHand instanceof MedallionItem) {
				MedallionItem medallionItem = (MedallionItem) itemInHand;
				world.setBlockAndUpdate(blockPos, medallionItem.transformWarpPadState(blockState));
			}
		}

		return ActionResultType.FAIL;
	}

	private BlockState getStateForPartCoords(int x, int z) {
		return defaultBlockState().setValue(BLOCK_PART_X, x + 1).setValue(BLOCK_PART_Z, z + 1);
	}

	private VoxelShape getShapeForState(BlockState blockState) {
		VoxelShape blockShape = Block.box(-16, 0, -16, 32, 2, 32);
		double blockShapeShiftX = -getBlockPartX(blockState);
		double blockShapeShiftZ = -getBlockPartZ(blockState);
		return blockShape.move(blockShapeShiftX, 0, blockShapeShiftZ);
	}

	protected boolean isCenterBlock(BlockState blockState) {
		return getBlockPartX(blockState) == 0 && getBlockPartZ(blockState) == 0;
	}

	private static double getBlockPartX(BlockState blockState) {
		return blockState.getValue(BLOCK_PART_X).intValue() - 1;
	}

	private static double getBlockPartZ(BlockState blockState) {
		return blockState.getValue(BLOCK_PART_Z).intValue() - 1;
	}
}
