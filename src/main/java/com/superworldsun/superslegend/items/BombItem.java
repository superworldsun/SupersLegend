package com.superworldsun.superslegend.items;

import java.util.List;

import com.superworldsun.superslegend.entities.projectiles.bombs.EntityBomb;
import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BombItem extends NonEnchantItem {
	// Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
	public BombItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack bombStack = player.getItemInHand(hand);

		if (!world.isClientSide) {
			if (player.isShiftKeyDown()) {
				EntityBomb bomb = new EntityBomb(EntityTypeInit.BOMB.get(), world);
				bomb.setPos(player.getX(), player.getY(), player.getZ());
				world.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.addFreshEntity(bomb);
			} else {
				EntityBomb bombEntity = new EntityBomb(player, world);
				float pitch = 0;
				float throwingForce = 0.7F;
				bombEntity.shootFromRotation(player, player.xRot, player.yRot, pitch, throwingForce, 0.9F);
				world.playSound(null, player.blockPosition(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.addFreshEntity(bombEntity);
			}
			if (!player.isCreative()) {
				bombStack.shrink(1);
			}
		}

		return ActionResult.consume(bombStack);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.BLUE + "Overpower your enemies with an explosive blast"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Right-Click to throw"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Sneak+Right-Click to Drop Bomb"));
	}

	/*
	 * if(!player.isCreative() && player.getOffhandItem().getItem().equals(ItemInit.BOMB)) { player.getOffhandItem().shrink(1); } else if(!player.isCreative() &&
	 * player.getMainHandItem().getItem().equals(ItemInit.BOMB)) { player.getMainHandItem().shrink(1); }
	 */
}
