package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

public class TorchTowerTopLit extends Block implements SimpleWaterloggedBlock

{
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 13.0D, 9.0D, 13.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TorchTowerTopLit(Properties properties) {
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

    public void animateTick(@NotNull BlockState pState, Level level, BlockPos pos, @NotNull RandomSource rand)
    {
        if (BaseTorchTower.isSubmerged(pState)) {
            return;
        }
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.5D;
        double d2 = (double)pos.getZ() + 0.5D;
        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);

        double d3 = (double)pos.getX() + 0.6D;
        double d4 = (double)pos.getY() + 0.4D;
        double d5 = (double)pos.getZ() + 0.6D;
        level.addParticle(ParticleTypes.SMOKE, d3, d4, d5, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D);

        double d6 = (double)pos.getX() + 0.4D;
        double d7 = (double)pos.getY() + 0.4D;
        double d8 = (double)pos.getZ() + 0.4D;
        level.addParticle(ParticleTypes.SMOKE, d6, d7, d8, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d6, d7, d8, 0.0D, 0.0D, 0.0D);

        double  d9 = (double)pos.getX() + 0.6D;
        double d10 = (double)pos.getY() + 0.4D;
        double d11 = (double)pos.getZ() + 0.4D;
        level.addParticle(ParticleTypes.SMOKE, d9, d10, d11, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d9, d10, d11, 0.0D, 0.0D, 0.0D);

        double d12 = (double)pos.getX() + 0.4D;
        double d13 = (double)pos.getY() + .4D;
        double d14 = (double)pos.getZ() + 0.6D;
        level.addParticle(ParticleTypes.SMOKE, d12, d13, d14, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, d12, d13, d14, 0.0D, 0.0D, 0.0D);
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

    //TODO, add back when these entities are re added
    /*public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if(entityIn instanceof ArrowEntity || (entityIn instanceof AbstractArrowEntity && !(entityIn instanceof IceArrowEntity || entityIn instanceof MagicIceArrowEntity) || entityIn instanceof BoomerangEntity
		|| entityIn instanceof MagicBoomerangEntity || entityIn instanceof WWBoomerangEntity))
		{
			entityIn.setSecondsOnFire(15);
		}
		if(entityIn instanceof IceArrowEntity || entityIn instanceof MagicIceArrowEntity || entityIn instanceof IceballEntity)
		{
			worldIn.setBlock(pos , BlockInit.TORCH_TOWER_TOP_UNLIT.get().defaultBlockState(), 1);
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}*/
    //TODO add some particles like a camp fire
    public @NotNull InteractionResult use(@NotNull BlockState blockstate, @NotNull Level worldIn, @NotNull BlockPos pos, Player playerentity, @NotNull InteractionHand hand, @NotNull BlockHitResult blocktrace) {
        ItemStack itemstack = playerentity.getItemInHand(hand);
        Item item = itemstack.getItem();
        return super.use(blockstate, worldIn, pos, playerentity, hand, blocktrace);
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

    @Override
    public void onPlace(@NotNull BlockState blockState, Level world, BlockPos blockPos, @NotNull BlockState oldBlockState, boolean b) {
        super.onPlace(blockState, world, blockPos, oldBlockState, b);
        if (!world.isClientSide && BaseTorchTower.isSubmerged(blockState)) {
            BaseTorchTower.setTopLit(world, blockPos, false);
            return;
        }
        BaseTorchTower.topPlaced(world, blockPos, oldBlockState, blockState, true);
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
}
