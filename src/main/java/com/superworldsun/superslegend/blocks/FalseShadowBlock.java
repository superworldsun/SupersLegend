package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.tile.FalseShadowTileEntity;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
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
public class FalseShadowBlock extends ShadowBlock
{
	public FalseShadowBlock(Properties properties) {
		super(properties);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new FalseShadowTileEntity();
	}
	
	@Override
	public VoxelShape getOcclusionShape(BlockState state, IBlockReader world, BlockPos pos)
	{
		return VoxelShapes.empty();
	}
	
	public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_)
	{
		return true;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_)
	{
		return 1.0F;
	}
	
	@Override
	public VoxelShape getVisualShape(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_, ISelectionContext p_230322_4_)
	{
		return VoxelShapes.empty();
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_)
	{
		return VoxelShapes.empty();
	}
	
	@OnlyIn(value = Dist.CLIENT)
	@SubscribeEvent
	public static void onDrawBlockHighlight(DrawHighlightEvent.HighlightBlock event)
	{
		Minecraft client = Minecraft.getInstance();
		
		// Applied only to false shadow blocks
		if (!(client.level.getBlockState(event.getTarget().getBlockPos()).getBlock() instanceof FalseShadowBlock))
		{
			return;
		}
		
		// Do not render if player is using lens
		if (client.player.isUsingItem() && client.player.getUseItem().getItem() == ItemInit.LENS_OF_TRUTH.get())
		{
			event.setCanceled(true);
		}
	}
}