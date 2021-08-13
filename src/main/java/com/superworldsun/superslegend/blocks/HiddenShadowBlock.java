package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.tile.HiddenShadowTileEntity;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.PropertiesInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class HiddenShadowBlock extends Block
{
	public HiddenShadowBlock()
	{
		super(PropertiesInit.WRECKAGE);
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new HiddenShadowTileEntity();
	}
	
	@Override
	public VoxelShape getOcclusionShape(BlockState state, IBlockReader world, BlockPos pos)
	{
		return VoxelShapes.empty();
	}
	
	@OnlyIn(value = Dist.CLIENT)
	@SubscribeEvent
	public static void onDrawBlockHighlight(DrawHighlightEvent.HighlightBlock event)
	{
		Minecraft client = Minecraft.getInstance();
		
		// Do not render if player is not using lens
		if (!client.player.isUsingItem() || client.player.getUseItem().getItem() != ItemInit.LENS_OF_TRUTH.get())
		{
			event.setCanceled(true);
		}
	}
}