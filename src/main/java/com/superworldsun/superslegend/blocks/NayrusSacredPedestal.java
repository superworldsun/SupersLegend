package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.entities.projectiles.mastersword.MasterSwordSwordEntity;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class NayrusSacredPedestal extends Block {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	protected static final VoxelShape FACE_EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape FACE_WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape FACE_SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape FACE_NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	   public NayrusSacredPedestal(Properties properties) {
	      super(properties);
		   this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	   }

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch((Direction)state.getValue(FACING)) {
			case EAST:
			default:
				return FACE_EAST_AABB;
			case WEST:
				return FACE_WEST_AABB;
			case SOUTH:
				return FACE_SOUTH_AABB;
			case NORTH:
				return FACE_NORTH_AABB;
		}
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		if(entity instanceof MasterSwordSwordEntity && world.isEmptyBlock(pos.above()))
		{
			world.setBlockAndUpdate(pos.above(), BlockInit.NAYRUS_FLAME.get().defaultBlockState());
			BlockPos currentPos = entity.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.FIRECHARGE_USE, SoundCategory.PLAYERS, 1f, 1f);
			/*double d0 = (double)pos.getX() + 0.5D;
			double d1 = (double)pos.getY() + 0.5D;
			double d2 = (double)pos.getZ() + 0.5D;
			world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);*/
		}
	}

}
	