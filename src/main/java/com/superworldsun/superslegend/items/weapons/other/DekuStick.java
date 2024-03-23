package com.superworldsun.superslegend.items.weapons.other;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class DekuStick extends NonEnchantItem {
	public DekuStick() {
		super(new Properties().stacksTo(16));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
		attacker.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		stack.shrink(1);
		if (stack.isEmpty()) {
			attacker.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
		}
		return true;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		if (slot != EquipmentSlot.MAINHAND) return ImmutableMultimap.of();
		return ImmutableMultimap.of(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "DekuStick", 7, AttributeModifier.Operation.ADDITION));
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if (player == null) return InteractionResult.FAIL;
		BlockState clickedBlock = context.getLevel().getBlockState(context.getClickedPos());
		if (!clickedBlock.is(BlockTags.FIRE)) return InteractionResult.FAIL;
		player.level().playSound(null, player, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1f, 1f);
		ItemStack item = context.getItemInHand();
		item.shrink(1);
		if (item.isEmpty()) {
			player.getInventory().removeItem(item);
			player.addItem(new ItemStack(ItemInit.DEKU_STICK_LIT.get()));
		}
		return InteractionResult.SUCCESS;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		if(!Screen.hasShiftDown()) {
			tooltip.add(Component.literal("A stick").withStyle(ChatFormatting.YELLOW));
			tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
		}
		else if(Screen.hasShiftDown()) {
			tooltip.add(Component.literal("Can be used as a weapon, will break on 1 use").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
			tooltip.add(Component.literal("Right+Click a source of fire to ignite the Deku stick").withStyle(ChatFormatting.RED));
		}
		super.appendHoverText(stack, level, tooltip, flag);
	}
}
