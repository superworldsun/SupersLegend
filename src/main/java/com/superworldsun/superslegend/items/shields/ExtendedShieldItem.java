package com.superworldsun.superslegend.items.shields;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public abstract class ExtendedShieldItem extends ShieldItem {
	public ExtendedShieldItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return true;
	}

	@SubscribeEvent
	public static void onLivingAttacked(LivingAttackEvent event) {
		if (!(event.getEntity() instanceof PlayerEntity)) {
			return;
		}

		PlayerEntity player = (PlayerEntity) event.getEntity();

		if (!player.isBlocking()) {
			return;
		}

		Item currentItem = player.getItemInHand(player.getUsedItemHand()).getItem();

		if (!(currentItem instanceof ExtendedShieldItem)) {
			return;
		}

		if (isDamageBlocked(event.getSource(), event.getEntityLiving())) {
			ExtendedShieldItem shieldItem = (ExtendedShieldItem) currentItem;
			LivingEntity attackingLivingEntity = getDamagingLivingEntity(event);
			Entity projectile = getDamagingProjectile(event);
			shieldItem.onShieldBlock(player.level, player, attackingLivingEntity, projectile, event.getSource());
		}
	}

	@Nullable
	private static Entity getDamagingProjectile(LivingAttackEvent event) {
		Entity damagingEntity = event.getSource().getEntity();
		Entity directDamagingEntity = event.getSource().getDirectEntity();
		
		if (directDamagingEntity != null && directDamagingEntity != damagingEntity) {
			return directDamagingEntity;
		} else {
			return null;
		}
	}

	@Nullable
	private static LivingEntity getDamagingLivingEntity(LivingAttackEvent event) {
		Entity damagingEntity = event.getSource().getEntity();

		if (damagingEntity instanceof LivingEntity) {
			return (LivingEntity) damagingEntity;
		} else {
			return null;
		}
	}

	public static boolean isDamageBlocked(DamageSource damageSource, LivingEntity targetEntity) {
		if (!targetEntity.isBlocking()) {
			return false;
		}

		if (damageSource.isBypassArmor()) {
			return false;
		}

		Entity entityDealingDamage = damageSource.getDirectEntity();

		if (entityDealingDamage instanceof AbstractArrowEntity) {
			AbstractArrowEntity arrow = (AbstractArrowEntity) entityDealingDamage;

			if (arrow.getPierceLevel() > 0) {
				return false;
			}
		}

		return isDamageSourceInFrontOf(targetEntity, damageSource);
	}

	private static boolean isDamageSourceInFrontOf(LivingEntity entity, DamageSource damageSource) {
		Vector3d damageSourcePosition = damageSource.getSourcePosition();

		if (damageSourcePosition == null) {
			return false;
		}

		Vector3d targetViewVector = entity.getViewVector(1);
		Vector3d vectorFromSourceToTarget = damageSourcePosition.vectorTo(entity.position()).normalize();
		vectorFromSourceToTarget = new Vector3d(vectorFromSourceToTarget.x, 0, vectorFromSourceToTarget.z);
		return vectorFromSourceToTarget.dot(targetViewVector) < 0;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	protected abstract void onShieldBlock(World level, PlayerEntity player, @Nullable LivingEntity attacker, @Nullable Entity projectile, DamageSource damage);
}
