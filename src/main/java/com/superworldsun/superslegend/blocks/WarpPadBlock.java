package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.items.item.MedallionItem;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.warppads.WarpPadsServerData;
import com.superworldsun.superslegend.warppads.WarpPadsStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class WarpPadBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty BLOCK_PART_X = IntegerProperty.create("model_part_x", 0, 2);
    public static final IntegerProperty BLOCK_PART_Z = IntegerProperty.create("model_part_z", 0, 2);

    public WarpPadBlock() {
        super(Properties.of()
                .strength(4f, 4f)
                .requiresCorrectToolForDrops()
                .noOcclusion().mapColor(MapColor.STONE));
        registerDefaultState(getStateForPartCoords(0, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return getShapeForState(blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return isCenterBlock(blockState) ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_PART_X).add(BLOCK_PART_Z).add(FACING);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        if (level.isClientSide) return;
        if (!isCenterBlock(blockState)) return;
        MinecraftServer server = level.getServer();
        WarpPadsServerData.instance(server).placeWarpPad(blockPos, this);
        Iterable<BlockPos> occupiedPositions = getOccupiedPositions(blockPos, blockState);
        occupiedPositions.forEach(pos -> {
            int blockPartX = pos.getX() - blockPos.getX();
            int blockPartZ = pos.getZ() - blockPos.getZ();
            BlockState blockPartState = getStateForPartCoords(blockPartX, blockPartZ);
            if (blockPartX != 0 || blockPartZ != 0) {
                level.setBlockAndUpdate(pos, blockPartState);
            }
        });
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        if (levelAccessor.isClientSide()) return;
        if (!(levelAccessor instanceof ServerLevel)) return;
        ServerLevel serverLevel = (ServerLevel) levelAccessor;
        BlockPos centerPos = getCenterBlockPos(blockState, blockPos);
        WarpPadsServerData.instance(serverLevel.getServer()).removeWarpPad(centerPos);
        Iterable<BlockPos> occupiedPositions = getOccupiedPositions(blockPos, blockState);
        occupiedPositions.forEach(pos -> levelAccessor.removeBlock(pos, false));
    }

    protected Iterable<BlockPos> getOccupiedPositions(BlockPos blockPos, BlockState blockState) {
        BlockPos centerPos = getCenterBlockPos(blockState, blockPos);
        return BlockPos.betweenClosed(centerPos.offset(-1, 0, -1), centerPos.offset(1, 0, 1));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        boolean isBaseWarpPad = blockState.getBlock() == BlockInit.WARP_PAD.get();
        ItemStack itemStackInHand = player.getItemInHand(hand);
        Item itemInHand = itemStackInHand.getItem();

        boolean usingMedallion = itemInHand instanceof MedallionItem;
        boolean usingEmptyHand = itemStackInHand.isEmpty();

        if (isBaseWarpPad && usingMedallion) {
            if (level.isClientSide) return InteractionResult.SUCCESS;
            transformWarpPad(blockState, level, blockPos, itemInHand);
            return InteractionResult.SUCCESS;
        } else if (!isBaseWarpPad && usingEmptyHand) {
            if (level.isClientSide) return InteractionResult.SUCCESS;
            if (player instanceof ServerPlayer serverPlayer) {
                BlockPos centerPos = getCenterBlockPos(blockState, blockPos);
                boolean isNewPosition = WarpPadsStorage.saveWarpPosition(serverPlayer, this, centerPos);
                if (isNewPosition) {
                    player.sendSystemMessage(Component.translatable("superslegend.message.warp_saved")
                            .withStyle(ChatFormatting.GREEN));
                } else {
                    player.sendSystemMessage(Component.translatable("superslegend.message.warp_already_saved")
                            .withStyle(ChatFormatting.GREEN));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    protected void transformWarpPad(BlockState blockState, Level level, BlockPos blockPos, Item itemInHand) {
        if (level.isClientSide) return;
        MedallionItem medallion = (MedallionItem) itemInHand;
        Iterable<BlockPos> occupiedPositions = getOccupiedPositions(blockPos, blockState);
        occupiedPositions.forEach(pos -> {
            BlockState blockPartState = level.getBlockState(pos);
            BlockState transformedBlockState = medallion.transformWarpPadState(blockPartState);
            level.setBlockAndUpdate(pos, transformedBlockState);
        });
        WarpPadBlock transformedWarpPad = (WarpPadBlock) medallion.transformWarpPadState(blockState).getBlock();
        BlockPos centerPos = getCenterBlockPos(blockState, blockPos);
        WarpPadsServerData.instance(level.getServer()).placeWarpPad(centerPos, transformedWarpPad);
    }

    protected BlockPos getCenterBlockPos(BlockState blockState, BlockPos blockPos) {
        int centerBlockOffsetX = -getBlockPartX(blockState);
        int centerBlockOffsetZ = -getBlockPartZ(blockState);
        return blockPos.offset(centerBlockOffsetX, 0, centerBlockOffsetZ);
    }

    private BlockState getStateForPartCoords(int x, int z) {
        return defaultBlockState().setValue(BLOCK_PART_X, x + 1).setValue(BLOCK_PART_Z, z + 1);
    }

    private VoxelShape getShapeForState(BlockState blockState) {
        VoxelShape baseShape = Block.box(-16, 0, -16, 32, 3, 32);
        int blockShapeShiftX = -getBlockPartX(blockState);
        int blockShapeShiftZ = -getBlockPartZ(blockState);
        return baseShape.move(blockShapeShiftX, 0, blockShapeShiftZ);
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