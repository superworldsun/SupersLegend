package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.entity.GossipStoneBlockEntity;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.client.screen.GossipStoneScreen;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;

public class GossipStoneBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 21.0D, 14.0D);

    public GossipStoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState()
                : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.above()).is(BlockInit.GOSSIP_STONE_TOP.get()) || world.isEmptyBlock(pos.above()));
    }

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
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        world.setBlock(pos.above(), BlockInit.GOSSIP_STONE_TOP.get().defaultBlockState().setValue(FACING, state.getValue(FACING)), 3);
        if (entity instanceof Player && world.isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> showGossipStoneScreen(pos));
        }
    }

    @Override
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
        if (!world.isClientSide) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GossipStoneBlockEntity gossipStoneBlockEntity) {
                player.sendSystemMessage((gossipStoneBlockEntity).getMessage(player));
                if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer
                        && top.theillusivec4.curios.api.CuriosApi.getCuriosHelper()
                        .findEquippedCurio(com.superworldsun.superslegend.registries.ItemInit.MASK_MASKOFTRUTH.get(), player).isPresent()) {
                    ModAdvancementHelper.award(serverPlayer, "stone_whisperer", "spoke_to_stone");
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GossipStoneBlockEntity(pos, state);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean flag) {
        world.setBlockAndUpdate(pos.above(), BlockInit.GOSSIP_STONE_TOP.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
    }

    @OnlyIn(Dist.CLIENT)
    private void showGossipStoneScreen(BlockPos pos) {
        Minecraft client = Minecraft.getInstance();
        client.setScreen(new GossipStoneScreen(pos));
    }
}
