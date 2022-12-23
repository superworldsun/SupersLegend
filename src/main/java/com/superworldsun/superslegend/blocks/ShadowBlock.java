package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.blocks.tile.ShadowTileEntity;
import com.superworldsun.superslegend.items.block.ShadowBlockBaseItem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ShadowBlock extends Block
{
	public ShadowBlock()
	{
		super(Block.Properties.of(Material.METAL).noOcclusion().strength(4.0F, 3.25F).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops());
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
		return new ShadowTileEntity();
	}
	
	@Override
	public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack)
	{
		ShadowTileEntity tileEntity = (ShadowTileEntity) world.getBlockEntity(blockPos);
		
		if (itemStack.getItem() instanceof ShadowBlockBaseItem)
		{
			ShadowBlockBaseItem shadowBlockItem = (ShadowBlockBaseItem) itemStack.getItem();
			tileEntity.setDisguise(shadowBlockItem.loadDisguiseFromStack(itemStack));
		}
	}
}
