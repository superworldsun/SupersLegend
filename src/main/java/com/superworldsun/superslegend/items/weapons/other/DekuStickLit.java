package com.superworldsun.superslegend.items.weapons.other;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

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
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		if (slot != EquipmentSlot.MAINHAND) return ImmutableMultimap.of();
		return ImmutableMultimap.of(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "DekuStick", 7, AttributeModifier.Operation.ADDITION));
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

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		if(!Screen.hasShiftDown()) {
			tooltip.add(Component.literal("A stick with fire on the end").withStyle(ChatFormatting.YELLOW));
			tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
		}
		else if(Screen.hasShiftDown()) {
			tooltip.add(Component.literal("Can be used as a weapon, will break on 1 use").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
			tooltip.add(Component.literal("Will slowly burn away and break").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
			tooltip.add(Component.literal("Right+Click webs to burn them away or ignite torch towers").withStyle(ChatFormatting.RED));
		}
		super.appendHoverText(stack, level, tooltip, flag);
	}
}
