package com.superworldsun.superslegend.items.block;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.ShadowBlock;
import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ShadowBlockBaseItem extends BlockItem {
	protected ShadowBlockBaseItem(Block block) {
		super(block, SupersLegendMain.PROXY.getShadowBlockProperties().tab(ItemGroupInit.BLOCKS));
	}

	@Override
	public ActionResultType useOn(ItemUseContext useContext) {
		if (useContext.getPlayer().isCrouching()) {
			BlockState clickedBlockState = useContext.getLevel().getBlockState(useContext.getClickedPos());
			boolean clickedShadowBlock = clickedBlockState.getBlock() instanceof ShadowBlock;
			boolean clickedFullBlock = Block.isShapeFullBlock(clickedBlockState.getShape(useContext.getLevel(), useContext.getClickedPos()));

			if (clickedShadowBlock || !clickedFullBlock) {
				return ActionResultType.FAIL;
			}

			saveDisguiseInStack(useContext.getItemInHand(), clickedBlockState);
			useContext.getLevel().playSound(null, useContext.getPlayer(), SoundEvents.LODESTONE_PLACE, SoundCategory.PLAYERS, 1F, 1F);
			return ActionResultType.SUCCESS;
		}

		return super.useOn(useContext);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		if (playerEntity.isCrouching()) {
			ItemStack stackInHand = playerEntity.getItemInHand(hand).getStack();

			if (stackInHand.hasTag()) {
				stackInHand.setTag(null);
				world.playSound(null, playerEntity, SoundEvents.LODESTONE_BREAK, SoundCategory.PLAYERS, 1F, 1F);
				return ActionResult.success(stackInHand);
			}
		}

		return super.use(world, playerEntity, hand);
	}

	public static void saveDisguiseInStack(ItemStack itemStack, @Nullable BlockState disguise) {
		itemStack.getOrCreateTag().putInt("disguise", Block.getId(disguise));
	}

	@Nullable
	public static BlockState loadDisguiseFromStack(ItemStack itemStack) {
		if (!itemStack.hasTag() || !itemStack.getTag().contains("disguise")) {
			return null;
		}

		return Block.stateById(itemStack.getOrCreateTag().getInt("disguise"));
	}
}
