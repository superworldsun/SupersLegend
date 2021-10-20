package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.util.RailStateGetter;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RidgedWallRail extends AbstractRailBlock
{
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE;
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);

    public RidgedWallRail(AbstractBlock.Properties p_i48371_1_)
    {
        super(false, p_i48371_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(SHAPE, RailShape.NORTH_SOUTH));
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        switch(p_220053_1_.getValue(FACING))
        {
            case NORTH:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case WEST:
                return WEST_AABB;
            case EAST:
            default:
                return EAST_AABB;
        }
    }

    private boolean canAttachTo(IBlockReader p_196471_1_, BlockPos p_196471_2_, Direction p_196471_3_) {
        BlockState blockstate = p_196471_1_.getBlockState(p_196471_2_);
        return blockstate.isFaceSturdy(p_196471_1_, p_196471_2_, p_196471_3_);
    }


    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        Direction direction = p_196260_1_.getValue(FACING);
        return this.canAttachTo(p_196260_2_, p_196260_3_.relative(direction.getOpposite()), direction);
    }

    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (p_196271_2_.getOpposite() == p_196271_1_.getValue(FACING) && !p_196271_1_.canSurvive(p_196271_4_, p_196271_5_)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (p_196271_1_.getValue(WATERLOGGED)) {
                p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
            }

            return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        if (!p_196258_1_.replacingClickedOnBlock()) {
            BlockState blockstate = p_196258_1_.getLevel().getBlockState(p_196258_1_.getClickedPos().relative(p_196258_1_.getClickedFace().getOpposite()));
            if (blockstate.is(this) && blockstate.getValue(FACING) == p_196258_1_.getClickedFace()) {
                return null;
            }
        }

        BlockState blockstate1 = this.defaultBlockState();
        IWorldReader iworldreader = p_196258_1_.getLevel();
        BlockPos blockpos = p_196258_1_.getClickedPos();
        FluidState fluidstate = p_196258_1_.getLevel().getFluidState(p_196258_1_.getClickedPos());

        for(Direction direction : p_196258_1_.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockstate1 = blockstate1.setValue(FACING, direction.getOpposite());
                if (blockstate1.canSurvive(iworldreader, blockpos)) {
                    return blockstate1.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
                }
            }
        }

        return null;
    }

    @Override
    public Property<RailShape> getShapeProperty()
    {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(SHAPE, FACING, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    @Override
    public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
        if (!p_220069_2_.isClientSide && p_220069_2_.getBlockState(p_220069_3_).is(this))
        {
            RailShape railshape = getRailDirection(p_220069_1_, p_220069_2_, p_220069_3_, null);
                this.updateState(p_220069_1_, p_220069_2_, p_220069_3_, p_220069_4_);
        }
    }

    protected void updateState(BlockState p_189541_1_, World p_189541_2_, BlockPos p_189541_3_, Block p_189541_4_)
    {
        if (p_189541_4_.defaultBlockState().isSignalSource() && (new RailStateGetter(p_189541_2_, p_189541_3_, p_189541_1_))
                .countPotentialConnections() == 3)
        {
            this.updateDir(p_189541_2_, p_189541_3_, p_189541_1_, false);
        }
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));

        switch(p_185499_2_) {
            case CLOCKWISE_180:
                switch(p_185499_1_.getValue(SHAPE)) {
                    case ASCENDING_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.NORTH_WEST);
                    case SOUTH_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_SOUTH: //Forge fix: MC-196102
                    case EAST_WEST:
                        return p_185499_1_;
                }
            case COUNTERCLOCKWISE_90:
                switch((RailShape)p_185499_1_.getValue(SHAPE)) {
                    case ASCENDING_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_NORTH:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_SOUTH:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case SOUTH_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_SOUTH:
                        return p_185499_1_.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.NORTH_SOUTH);
                }
            case CLOCKWISE_90:
                switch((RailShape)p_185499_1_.getValue(SHAPE)) {
                    case ASCENDING_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_NORTH:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_SOUTH:
                        return p_185499_1_.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case SOUTH_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return p_185499_1_.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_SOUTH:
                        return p_185499_1_.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return p_185499_1_.setValue(SHAPE, RailShape.NORTH_SOUTH);
                }
            default:
                return p_185499_1_;
        }
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));

        RailShape railshape = p_185471_1_.getValue(SHAPE);
        switch(p_185471_2_) {
            case LEFT_RIGHT:
                switch(railshape) {
                    case ASCENDING_NORTH:
                        return p_185471_1_.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return p_185471_1_.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return p_185471_1_.setValue(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return p_185471_1_.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return p_185471_1_.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return p_185471_1_.setValue(SHAPE, RailShape.SOUTH_EAST);
                    default:
                        return super.mirror(p_185471_1_, p_185471_2_);
                }
            case FRONT_BACK:
                switch(railshape) {
                    case ASCENDING_EAST:
                        return p_185471_1_.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return p_185471_1_.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                    case ASCENDING_SOUTH:
                    default:
                        break;
                    case SOUTH_EAST:
                        return p_185471_1_.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return p_185471_1_.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return p_185471_1_.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return p_185471_1_.setValue(SHAPE, RailShape.NORTH_WEST);
                }
        }

        return super.mirror(p_185471_1_, p_185471_2_);
    }
}
