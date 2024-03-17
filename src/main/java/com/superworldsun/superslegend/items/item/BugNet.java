package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BugNet extends Item {

	public BugNet(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
		if (!canCatchEntity(target)) return InteractionResult.FAIL;
		if (!findAndConsumeGlassBottle(player)) return InteractionResult.FAIL;
		player.swing(hand);
		player.addItem(getBottledEntityItem(target));
		target.remove(Entity.RemovalReason.DISCARDED);
		player.getCooldowns().addCooldown(this, 18);
		return InteractionResult.SUCCESS;
	}

	private @NotNull ItemStack getBottledEntityItem(LivingEntity entity) {
		ItemStack item = ItemStack.EMPTY;
		if (entity instanceof Bee) {
			item = new ItemStack(ItemInit.BOTTLED_BEE.get());
		} else if (entity instanceof Silverfish) {
			item = new ItemStack(ItemInit.BOTTLED_SILVERFISH.get());
		} else if (entity instanceof Endermite) {
			item = new ItemStack(ItemInit.BOTTLED_ENDERMITE.get());
		}
		if (!item.isEmpty()) {
			CompoundTag entityTag = new CompoundTag();
			entity.save(entityTag);
			item.getOrCreateTag().put("EntityData", entityTag);
		}
		return item;
	}

	private boolean canCatchEntity(LivingEntity entity) {
		return entity instanceof Bee || entity instanceof Endermite || entity instanceof Silverfish;
	}

	private boolean findAndConsumeGlassBottle(Player player) {
		for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
			ItemStack stack = player.getInventory().getItem(i);
			if (stack.getItem() == Items.GLASS_BOTTLE) {
				stack.shrink(1);
				return true;
			}
		}
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		if(!Screen.hasShiftDown()) {
			tooltip.add(Component.literal("Used to bottle small critters").withStyle(ChatFormatting.WHITE));
			tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
		}
		else if(Screen.hasShiftDown()) {
			tooltip.add(Component.literal("Right-Click small bugs with a empty bottle in your inventory to capture them").withStyle(ChatFormatting.WHITE));
		}
		super.appendHoverText(stack, level, tooltip, flag);
	}
}
