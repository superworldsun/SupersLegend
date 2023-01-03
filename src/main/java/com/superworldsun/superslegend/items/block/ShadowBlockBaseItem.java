package com.superworldsun.superslegend.items.block;

import java.util.concurrent.Callable;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.blocks.ShadowBlock;
import com.superworldsun.superslegend.client.render.ister.ShadowBlockIster;
import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShadowBlockBaseItem extends BlockItem {
	protected ShadowBlockBaseItem(Block block) {
		super(block, new Properties().tab(ItemGroupInit.BLOCKS).setISTER(ShadowBlockBaseItem::createISTER));
	}

	@OnlyIn(Dist.CLIENT)
	public static Callable<ItemStackTileEntityRenderer> createISTER() {
		return ShadowBlockIster::new;
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
