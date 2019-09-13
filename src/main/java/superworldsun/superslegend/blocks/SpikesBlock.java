package superworldsun.superslegend.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class SpikesBlock extends Block 

	{
	   protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

	   public SpikesBlock(Block.Properties properties) {
	      super(properties);
	   }

	   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }

	   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		  entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	   }

	   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
	      worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	   }

	   public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
	      return true;
	   }

	   /**
	    * How many world ticks before ticking
	    */
	   public int tickRate(IWorldReader worldIn) {
	      return 20;
	   }

	   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
	      worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	   }

	   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
	      return false;
	   }

	   public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
	      return true;
	   }
	   public BlockRenderLayer getRenderLayer() {
		    return BlockRenderLayer.CUTOUT;
	   }
}
	