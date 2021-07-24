package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.tile.PedestalTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.state.BooleanProperty;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class PedestalBlock extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty IS_REVERSE = BooleanProperty.create("is_reverse");
	protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);
	
	public PedestalBlock()
	{
		super(Properties.of(Material.STONE).strength(10.0F, 1000.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE));
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(IS_REVERSE, Boolean.FALSE));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
	{
		return SHAPE;
	}
	
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		ItemStack stackInHand = player.getItemInHand(hand);
		TileEntity tileentity = world.getBlockEntity(pos);
		
		if (tileentity instanceof PedestalTileEntity)
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) world.getBlockEntity(pos);
			
			if (stackInHand.getItem() instanceof SwordItem && pedestal.getSword().isEmpty())
			{
				pedestal.setSword(stackInHand);
				player.setItemInHand(hand, ItemStack.EMPTY);
				return ActionResultType.SUCCESS;
			}
			else if (stackInHand.isEmpty() && !pedestal.getSword().isEmpty())
			{
				player.setItemInHand(hand, pedestal.getSword());
				pedestal.setSword(ItemStack.EMPTY);
				return ActionResultType.SUCCESS;
			}
		}
		
		return ActionResultType.FAIL;
	}
	
	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean moving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileentity = world.getBlockEntity(pos);
			
			if (tileentity instanceof PedestalTileEntity)
			{
				PedestalTileEntity pedestal = (PedestalTileEntity) tileentity;
				pedestal.dropSword();
			}
			
			world.removeBlockEntity(pos);
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
		return new PedestalTileEntity();
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(IS_REVERSE, Boolean.FALSE);
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, IS_REVERSE);
	}
}
