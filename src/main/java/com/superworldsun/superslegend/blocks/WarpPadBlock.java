package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.capability.warppads.WarpPadsHelper;
import com.superworldsun.superslegend.items.items.MedallionItem;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.warppads.WarpPadsServerData;

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
import net.minecraftforge.common.ToolType;

public class WarpPadBlock extends HorizontalBlock {
	public static final IntegerProperty BLOCK_PART_X = IntegerProperty.create("model_part_x", 0, 2);
	public static final IntegerProperty BLOCK_PART_Z = IntegerProperty.create("model_part_z", 0, 2);

	public WarpPadBlock() {
		super(Properties.of(Material.STONE).strength(4f,4f).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1).noOcclusion());
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
		if (!isCenterBlock(blockState)) {
			return;
		}
		WarpPadsServerData.instance().placeWarpPad(blockPos, this);
		Iterable<BlockPos> occupiedPositions = getOccupiedPositions(blockPos, blockState);
		occupiedPositions.forEach(pos -> {
			int blockPartX = pos.getX() - blockPos.getX();
			int blockPartZ = pos.getZ() - blockPos.getZ();
			BlockState blockPartState = getStateForPartCoords(blockPartX, blockPartZ);
			if (blockPartX != 0 || blockPartZ != 0) {
				world.setBlockAndUpdate(pos, blockPartState);
			}
		});
	}

	@Override
	public void destroy(IWorld world, BlockPos blockPos, BlockState blockState) {
		BlockPos centerPos = getCenterBlockPos(blockState, blockPos);
		WarpPadsServerData.instance().removeWarpPad(centerPos);
		Iterable<BlockPos> occupiedPositions = getOccupiedPositions(blockPos, blockState);
		occupiedPositions.forEach(pos -> world.removeBlock(pos, false));
	}

	protected Iterable<BlockPos> getOccupiedPositions(BlockPos blockPos, BlockState blockState) {
		BlockPos centerPos = getCenterBlockPos(blockState, blockPos);
		Iterable<BlockPos> occupiedPositions = BlockPos.betweenClosed(centerPos.offset(-1, 0, -1), centerPos.offset(1, 0, 1));
		return occupiedPositions;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		boolean isBaseWarpPad = blockState.getBlock() == BlockInit.WARP_PAD.get();
		ItemStack itemStackInHand = player.getItemInHand(hand);
		Item itemInHand = itemStackInHand.getItem();
		boolean usingMedallion = itemInHand instanceof MedallionItem;
		boolean usingEmptyHand = itemStackInHand.isEmpty();
		if (isBaseWarpPad && usingMedallion) {
			transformWarpPad(blockState, world, blockPos, itemInHand);
			return ActionResultType.SUCCESS;
		} else if (!isBaseWarpPad && usingEmptyHand) {
			BlockPos centerPos = getCenterBlockPos(blockState, blockPos);
			WarpPadsHelper.saveWarpPosition(player, this, centerPos);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}

	protected void transformWarpPad(BlockState blockState, World world, BlockPos blockPos, Item itemInHand) {
		MedallionItem medallion = (MedallionItem) itemInHand;
		Iterable<BlockPos> occupiedPositions = getOccupiedPositions(blockPos, blockState);
		occupiedPositions.forEach(pos -> {
			BlockState blockPartState = world.getBlockState(pos);
			BlockState transformedBlockState = medallion.transformWarpPadState(blockPartState);
			world.setBlockAndUpdate(pos, transformedBlockState);
		});
		WarpPadBlock transformedWarpPad = (WarpPadBlock) medallion.transformWarpPadState(blockState).getBlock();
		BlockPos centerPos = getCenterBlockPos(blockState, blockPos);
		WarpPadsServerData.instance().placeWarpPad(centerPos, transformedWarpPad);
	}

	protected BlockPos getCenterBlockPos(BlockState blockState, BlockPos blockPos) {
		int centerBlockOffsetX = -getBlockPartX(blockState);
		int centerBlockOffsetZ = -getBlockPartZ(blockState);
		BlockPos centerBlockPos = blockPos.offset(centerBlockOffsetX, 0, centerBlockOffsetZ);
		return centerBlockPos;
	}

	private BlockState getStateForPartCoords(int x, int z) {
		return defaultBlockState().setValue(BLOCK_PART_X, x + 1).setValue(BLOCK_PART_Z, z + 1);
	}

	private VoxelShape getShapeForState(BlockState blockState) {
		VoxelShape blockShape = Block.box(-16, 0, -16, 32, 2, 32);
		int blockShapeShiftX = -getBlockPartX(blockState);
		int blockShapeShiftZ = -getBlockPartZ(blockState);
		return blockShape.move(blockShapeShiftX, 0, blockShapeShiftZ);
	}

	protected boolean isCenterBlock(BlockState blockState) {
		return getBlockPartX(blockState) == 0 && getBlockPartZ(blockState) == 0;
	}

	private static int getBlockPartX(BlockState blockState) {
		return blockState.getValue(BLOCK_PART_X).intValue() - 1;
	}

	private static int getBlockPartZ(BlockState blockState) {
		return blockState.getValue(BLOCK_PART_Z).intValue() - 1;
	}
}
