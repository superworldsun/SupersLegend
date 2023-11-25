package com.superworldsun.superslegend.items.customclass;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class MultishotBowItem extends BowItem {
	public final int arrowsShot;

	public MultishotBowItem(Properties properties, int arrowsShot) {
		super(properties);
		this.arrowsShot = arrowsShot;
	}

	@Override
	public void releaseUsing(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity, int timeLeft) {
		if (!(entity instanceof Player player)) return;
		ItemStack arrowStack = player.getProjectile(stack);
		int charge = getUseDuration(stack) - timeLeft;
		charge = ForgeEventFactory.onArrowLoose(stack, level, player, charge, !arrowStack.isEmpty());
		if (charge < 0) return;
		if (arrowStack.isEmpty()) arrowStack = new ItemStack(Items.ARROW);
		float power = getPowerForTime(charge);
		if (power < 0.1) return;
		boolean infiniteArrows = player.getAbilities().instabuild || (arrowStack.getItem() instanceof ArrowItem && ((ArrowItem) arrowStack.getItem()).isInfinite(arrowStack, stack, player));
		shootArrows(stack, level, player, arrowStack, power, infiniteArrows);
		level.playSound(null, player.xo, player.yo, player.zo, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (entity.getRandom()
				.nextFloat() * 0.4F + 1.2F) + power * 0.5F);
		if (!infiniteArrows && !player.getAbilities().instabuild) {
			arrowStack.shrink(1);
			if (arrowStack.isEmpty()) player.getInventory().removeItem(arrowStack);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
	}

	private void shootArrows(@NotNull ItemStack stack, @NotNull Level level, Player player, ItemStack arrowStack, float power, boolean infiniteArrows) {
		if (level.isClientSide) return;
		float velocity = 2.95f;
		float inaccuracy = 1f;
		float spread = 5f;
		for (int i = 0; i < arrowsShot; i++) {
			ArrowItem arrowitem = (ArrowItem) (arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);
			AbstractArrow arrow = arrowitem.createArrow(level, arrowStack, player);
			arrow = customArrow(arrow);
			shootArrow(arrow, player, power * velocity, inaccuracy, spread * (i - arrowsShot / 2));
			if (power == 1) {
				arrow.setCritArrow(true);
			}
			stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
			if (infiniteArrows && (arrowStack.getItem() == Items.SPECTRAL_ARROW || arrowStack.getItem() == Items.TIPPED_ARROW) || i != arrowsShot / 2) {
				arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			}
			level.addFreshEntity(arrow);
		}
	}

	private void shootArrow(AbstractArrow arrow, Entity shooter, float velocity, float inaccuracy, float spread) {
		float xRotation = shooter.xRotO * Mth.PI / 180f;
		float yRotation = (shooter.yRotO + spread) * Mth.PI / 180f;
		float motionX = -Mth.sin(yRotation) * Mth.cos(xRotation);
		float motionY = -Mth.sin(xRotation);
		float motionZ = Mth.cos(yRotation) * Mth.cos(xRotation);
		RandomSource random = RandomSource.create();
		float deviationX = (float) random.triangle(0.0f, 0.0172275f * inaccuracy);
		float deviationY = (float) random.triangle(0.0f, 0.0172275f * inaccuracy);
		float deviationZ = (float) random.triangle(0.0f, 0.0172275f * inaccuracy);
		Vec3 motion = new Vec3(motionX, motionY, motionZ).normalize().add(deviationX, deviationY, deviationZ).scale(velocity);
		arrow.setDeltaMovement(motion);
		arrow.setYRot((float) (Mth.atan2(motion.x, motion.z) * 180F / Mth.PI));
		arrow.setXRot((float) (Mth.atan2(motion.y, motion.horizontalDistance()) * 180F / Mth.PI));
		arrow.yRotO = arrow.getYRot();
		arrow.xRotO = arrow.getXRot();
		Vec3 shooterMotion = shooter.getDeltaMovement();
		arrow.setDeltaMovement(arrow.getDeltaMovement().add(shooterMotion.x, shooter.onGround() ? 0 : shooterMotion.y, shooterMotion.z));
	}
}
