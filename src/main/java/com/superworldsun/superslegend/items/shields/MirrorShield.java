package com.superworldsun.superslegend.items.shields;

import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MirrorShield extends ExtendedShieldItem {
	public MirrorShield() {
		super(new Properties().stacksTo(1).tab(ItemGroupInit.RESOURCES));
	}

	@Override
	protected void onShieldBlock(World level, PlayerEntity player, LivingEntity attacker, Entity projectile, DamageSource damage) {
		boolean wasAttackedWithProjectile = projectile != null && projectile instanceof ProjectileEntity;

		if (!level.isClientSide && wasAttackedWithProjectile) {
			projectile.remove();
			projectile = projectile.getType().create(level);
			projectile.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
			projectile.setDeltaMovement(player.getLookAngle());
			((ProjectileEntity) projectile).setOwner(player);
			level.addFreshEntity(projectile);
		}
	}
}
