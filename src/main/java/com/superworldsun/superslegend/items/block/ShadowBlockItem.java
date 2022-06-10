package com.superworldsun.superslegend.items.block;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class ShadowBlockItem extends BlockItem
{
	public ShadowBlockItem()
	{
		super(BlockInit.SHADOW_BLOCK.get(), new Item.Properties().tab(SupersLegendMain.BLOCKS));
	}
	
	protected ShadowBlockItem(Block block)
	{
		super(block, new Item.Properties().tab(SupersLegendMain.BLOCKS));
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext useContext)
	{
		if (useContext.getPlayer().isCrouching())
		{
			saveDisguiseInStack(useContext.getItemInHand(), useContext.getLevel().getBlockState(useContext.getClickedPos()));
			return ActionResultType.SUCCESS;
		}
		
		return super.useOn(useContext);
	}
	
	public void saveDisguiseInStack(ItemStack itemStack, @Nullable BlockState disguise)
	{
		itemStack.getOrCreateTag().putInt("disguise", Block.getId(disguise));
	}
	
	@Nullable
	public BlockState loadDisguiseFromStack(ItemStack itemStack)
	{
		if (!itemStack.hasTag() || !itemStack.getTag().contains("disguise"))
		{
			return null;
		}
		
		return Block.stateById(itemStack.getOrCreateTag().getInt("disguise"));
	}
}
