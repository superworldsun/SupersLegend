package com.superworldsun.superslegend.items.block;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.ShadowBlock;
import com.superworldsun.superslegend.registries.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
			BlockState clickedBlockState = useContext.getLevel().getBlockState(useContext.getClickedPos());
			
			if (clickedBlockState.getBlock() instanceof ShadowBlock)
			{
				return ActionResultType.FAIL;
			}
			
			if (!Block.isShapeFullBlock(clickedBlockState.getShape(useContext.getLevel(), useContext.getClickedPos())))
			{
				return ActionResultType.FAIL;
			}
			
			saveDisguiseInStack(useContext.getItemInHand(), useContext.getLevel().getBlockState(useContext.getClickedPos()));
			return ActionResultType.SUCCESS;
		}
		
		return super.useOn(useContext);
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
	{
		if (playerEntity.isCrouching())
		{
			ItemStack stackInHand = playerEntity.getItemInHand(hand).getStack();
			
			if (stackInHand.hasTag())
			{
				stackInHand.setTag(null);
				return ActionResult.success(stackInHand);
			}
		}
		
		return super.use(world, playerEntity, hand);
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
