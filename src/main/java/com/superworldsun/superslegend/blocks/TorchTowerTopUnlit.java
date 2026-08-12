package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.entities.projectiles.arrows.FireArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.arrows.MagicFireArrowEntity;
import com.superworldsun.superslegend.entities.projectiles.boomerang.AbstractBoomerangEntity;
import com.superworldsun.superslegend.entities.projectiles.magic.FireballEntity;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TorchTowerTopUnlit extends Block implements SimpleWaterloggedBlock

{
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 9.0D, 13.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TorchTowerTopUnlit(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state)
    {
        return RenderShape.INVISIBLE;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        pos = pos.below();
        return level.getBlockState(pos).getBlock().getCloneItemStack(state, target, level, pos, player);
    }

    public boolean canSurvive(BlockState state, LevelAccessor world, BlockPos pos)
    {
        return !world.isEmptyBlock(pos.below()) && (world.getBlockState(pos.below()).is(BlockInit.TORCH_TOWER.get()));
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, @NotNull BlockState pState) {
        level.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide || BaseTorchTower.isSubmerged(state) || !canLightTower(entity)) {
            return;
        }

        BaseTorchTower.setTopLit(level, pos, true);
        ModAdvancementHelper.recordTorchTowerLit(entity);
        level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    private static boolean canLightTower(Entity entity) {
        // These projectiles inherently carry fire, even if their vanilla fire timer is not set.
        if (entity instanceof FireArrowEntity
                || entity instanceof MagicFireArrowEntity
                || entity instanceof FireballEntity) {
            return true;
        }

        // Covers vanilla/modded arrows and all current boomerangs: standard, magic, and Sea Breeze.
        return entity.isOnFire()
                && (entity instanceof AbstractArrow || entity instanceof AbstractBoomerangEntity);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockstate, @NotNull Level worldIn, @NotNull BlockPos pos, Player playerentity, @NotNull InteractionHand hand, @NotNull BlockHitResult blocktrace) {
        ItemStack itemstack = playerentity.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE &&  item != ItemInit.BLUE_CANDLE.get() && item != ItemInit.RED_CANDLE.get())
        {
            return super.use(blockstate, worldIn, pos, playerentity, hand, blocktrace);
        }
        else
        {
            if (BaseTorchTower.isSubmerged(blockstate)) {
                return InteractionResult.FAIL;
            }
            if (!worldIn.isClientSide) {
                BaseTorchTower.setTopLit(worldIn, pos, true);
                worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            if (!worldIn.isClientSide && !playerentity.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, playerentity, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(hand);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, level, pos, oldState, moving);
        BaseTorchTower.topPlaced(level, pos, oldState, state, false);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState();
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return LoggedFluid.isSupported(fluid.getType()) ? LoggedFluid.fill(state, fluid.getType()) : state;
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction,
                                            @NotNull BlockState neighborState, @NotNull LevelAccessor level,
                                            @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (BaseTorchTower.isSubmerged(state)) {
            FluidState fluid = LoggedFluid.getFluidState(state);
            level.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos neighborPos, boolean flag)
    {
        //TODO, probably not needed but couldnt port
        //DebugPacketSender.sendNeighborsUpdatePacket(world, pos);

        if (!canSurvive(state, world, pos))
        {
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }
}
