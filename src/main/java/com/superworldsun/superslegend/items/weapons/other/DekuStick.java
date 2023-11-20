package com.superworldsun.superslegend.items.weapons.other;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.superworldsun.superslegend.items.customclass.NonEnchantItem;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DekuStick extends NonEnchantItem {
	public DekuStick() {
		super(new Properties().stacksTo(16).durability(1));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
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
}
