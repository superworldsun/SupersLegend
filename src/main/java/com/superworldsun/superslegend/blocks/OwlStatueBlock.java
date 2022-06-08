package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.tile.OwlStatueTileEntity;
import com.superworldsun.superslegend.client.screen.WaypointCreationScreen;
import com.superworldsun.superslegend.waypoints.IWaypoints;
import com.superworldsun.superslegend.waypoints.Waypoint;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;
import com.superworldsun.superslegend.waypoints.WaypointsServerData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

public class OwlStatueBlock extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 20.0D, 16D);
	
	public OwlStatueBlock(Properties properties)
	{
		super(properties);
		this.registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity entity, ItemStack stack)
	{
		if (entity instanceof PlayerEntity && world.isClientSide)
		{
			BlockPos waypointPos = blockPos.relative(blockState.getValue(FACING));
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> showWaypointCreationScreen(waypointPos));
		}
	}
	
	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		BlockPos waypointPos = blockPos.relative(blockState.getValue(FACING));
		Waypoint waypoint = WaypointsServerData.instance().getWaypoint(waypointPos);
		IWaypoints savedWaypoints = WaypointsProvider.get(player);
		
		// if a waypoint exist on server
		if (waypoint != null)
		{
			// if it is not saved by player
			if (savedWaypoints.getWaypoint(waypointPos) == null)
			{
				if (!world.isClientSide)
				{
					// if already maximum waypoints
					if (savedWaypoints.getWaypoints().size() == savedWaypoints.getMaxWaypoints())
					{
						player.sendMessage(new TranslationTextComponent("block.superslegend.owl_statue.maximum", savedWaypoints.getMaxWaypoints()), null);
					}
					else
					{
						savedWaypoints.addWaypoint(waypoint);
						WaypointsProvider.sync((ServerPlayerEntity) player);
						player.sendMessage(new TranslationTextComponent("block.superslegend.owl_statue.saved", waypoint.getName()), null);
					}
				}
				
				return ActionResultType.SUCCESS;
			}
		}
		else
		{
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> showWaypointCreationScreen(waypointPos));
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.FAIL;
	}
	
	@Override
	public void destroy(IWorld world, BlockPos blockPos, BlockState blockState)
	{
		BlockPos waypointPos = blockPos.relative(blockState.getValue(FACING));
		Waypoint waypoint = WaypointsServerData.instance().getWaypoint(waypointPos);
		
		// if a waypoint exist on server
		if (waypoint != null)
		{
			WaypointsServerData.instance().removeWaypoint(waypointPos);
		}
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new OwlStatueTileEntity();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		return !worldIn.isEmptyBlock(pos.below());
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_)
	{
		return VoxelShapes.box(0, 0, 0, 1, 1.25, 1);
	}
	
	@OnlyIn(Dist.CLIENT)
	private void showWaypointCreationScreen(BlockPos blockPos)
	{
		Minecraft client = Minecraft.getInstance();
		client.setScreen(new WaypointCreationScreen(blockPos));
	}
	
	/*
	 * public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) { return canSupportCenter(worldIn, pos.down(), Direction.UP); }
	 */
}
