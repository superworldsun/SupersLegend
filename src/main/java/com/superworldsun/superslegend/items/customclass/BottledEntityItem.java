package com.superworldsun.superslegend.items.customclass;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BottledEntityItem extends Item {

	private final EntityType<? extends LivingEntity> entityType;

	public BottledEntityItem(EntityType<? extends LivingEntity> entityType) {
		super(new Item.Properties().stacksTo(1));
		this.entityType = entityType;
	}


	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (level.isClientSide) {
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
		}
		player.playSound(SoundInit.BOTTLE_POP.get(), 1f, 1f);
		LivingEntity entity = entityType.create(player.level());
		entity.load(stack.getOrCreateTag().getCompound("EntityData"));
		Objects.requireNonNull(entity).moveTo(player.getX(), player.getY(), player.getZ(), player.yRotO, 0f);
		player.level().addFreshEntity(entity);
		player.getCooldowns().addCooldown(this, 6);
		stack.shrink(1);
		if (stack.isEmpty()) {
			player.getInventory().removeItem(stack);
		}
		player.addItem(new ItemStack(Items.GLASS_BOTTLE));
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}
}
