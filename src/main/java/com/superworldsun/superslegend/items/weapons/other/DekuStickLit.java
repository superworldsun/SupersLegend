package com.superworldsun.superslegend.items.weapons.other;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public class DekuStickLit extends Item {
	public DekuStickLit() {
		super(new Properties().stacksTo(1).durability(400));
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean selected) {
		if (!(entity instanceof Player player)) return;
		if (player.tickCount % 20 != 0) return;
		stack.hurtAndBreak(20, player, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		if (context.getPlayer() == null) return InteractionResult.FAIL;
		BlockPos clickedPos = context.getClickedPos();
		FluidState clickedFluid = context.getLevel().getFluidState(clickedPos.offset(context.getClickedFace().getNormal()));
		if (clickedFluid.is(FluidTags.WATER)) {
			return useOnWater(context.getPlayer(), context.getItemInHand());
		}
		BlockState clickedBlock = context.getLevel().getBlockState(clickedPos);
		if (clickedBlock.is(Blocks.COBWEB)) {
			return useOnCobweb(context.getPlayer(), clickedPos);
		}
		return InteractionResult.FAIL;
	}

	private InteractionResult useOnCobweb(Player player, BlockPos pos) {
		player.level().playSound(null, player, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1f, 1f);
		player.level().setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
		return InteractionResult.SUCCESS;
	}

	private InteractionResult useOnWater(Player player, ItemStack item) {
		player.level().playSound(null, player, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1f, 1f);
		player.getInventory().removeItem(item);
		player.addItem(new ItemStack(ItemInit.DEKU_STICK.get()));
		return InteractionResult.SUCCESS;
	}
}
