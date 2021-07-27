package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.tile.FanTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class FanBlock extends DirectionalBlock
{
	private static final VoxelShape SHAPE = VoxelShapes.or(VoxelShapes.box(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.8125D),
			VoxelShapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), VoxelShapes.box(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D),
			VoxelShapes.box(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D), VoxelShapes.box(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	
	public FanBlock()
	{
		super(Properties.of(Material.STONE).strength(10.0F, 1000.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE));
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.UP));
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new FanTileEntity();
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
	{
		return rotateShape(Direction.SOUTH, state.getValue(FACING), SHAPE);
	}
	
	// This should be in some helper class, it is super useful
	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape)
	{
		VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };
		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		
		for (int i = 0; i < times; i++)
		{
			buffer[0].forAllBoxes(
					(minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1], VoxelShapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}
		
		return buffer[0];
	}
}
