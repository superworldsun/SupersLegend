package superworldsun.superslegend.objects.fluids;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;
import superworldsun.superslegend.SupersLegend.RegistryEvents;
import superworldsun.superslegend.lists.BlockList;
import superworldsun.superslegend.lists.FluidList;
import superworldsun.superslegend.lists.ItemList;

public abstract class FluidPoison extends FlowingFluid{

	@Override
	public Fluid getFlowingFluid() {
		return FluidList.flowing_poison;
	}

	@Override
	public Fluid getStillFluid() {
		return FluidList.poison;
	}

	@Override
	protected boolean canSourcesMultiply() {
		return true;
	}

	@Override
	protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {	
	}

	@Override
	protected int getSlopeFindDistance(IWorldReader worldIn) {
		return 4;
	}

	@Override
	protected int getLevelDecreasePerBlock(IWorldReader worldIn) {
		return 8;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return null;
	}

	@Override
	public Item getFilledBucket() {
		return ItemList.poison_bucket;
	}

	@Override
	protected boolean func_215665_a(IFluidState state, IBlockReader world, BlockPos pos,Fluid fluid, Direction direction) {
		return direction == Direction.DOWN && !fluid.isIn(FluidList.Tags.POISON);
	}

	@Override
	public int getTickRate(IWorldReader p_205569_1_) 
	{
		return 60;
	}

	@Override
	protected float getExplosionResistance() {
		return 100;
	}

	@Override
	protected BlockState getBlockState(IFluidState state) {
		return BlockList.poison.getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
	}
	
	@Override
	public boolean isEquivalentTo(Fluid fluidIn) 
	{
		return fluidIn == FluidList.poison || fluidIn == FluidList.flowing_poison;
	}
	
	@Override
	protected FluidAttributes createAttributes() 
	{
		return FluidAttributes.builder(RegistryEvents.location("block/poison_still"), RegistryEvents.location("block/poison_flow"))
		.translationKey("block.superslegend.poison")
		.build(this);
	}
	
	public static class Flowing extends FluidPoison
	{
		
		@Override
		protected void fillStateContainer(Builder<Fluid, IFluidState> builder) {
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		@Override
		public boolean isSource(IFluidState state) {
			return false;
		}

		@Override
		public int getLevel(IFluidState state) {
			return state.get(FluidPoison.LEVEL_1_8);
		}
		
	}
	
	public static class Source extends FluidPoison
	{

		@Override
		public boolean isSource(IFluidState state) {
			return true;
		}

		@Override
		public int getLevel(IFluidState state) {
			return 8;
		}
		
	}
	
}
