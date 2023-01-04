package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.entity.LightPrismTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class LightPrismBlock extends Block
{
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	
	public LightPrismBlock()
	{
		super(Properties.of(Material.STONE).lightLevel(s -> s.getValue(LIT) ? 15 : 0));
	}
	
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		LightPrismTileEntity lightPrism = (LightPrismTileEntity) world.getBlockEntity(pos);
		lightPrism.targetRotation += Math.PI / 8;
		return ActionResultType.SUCCESS;
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state)
	{
		state.add(LIT);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx)
	{
		return defaultBlockState().setValue(LIT, false);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new LightPrismTileEntity();
	}
}
