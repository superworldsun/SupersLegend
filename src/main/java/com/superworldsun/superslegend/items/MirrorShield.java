package com.superworldsun.superslegend.items;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MirrorShield extends ExtendedShieldItem
{
	public MirrorShield()
	{
		super(new Properties().stacksTo(1).tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	protected void onShieldBlock(World level, PlayerEntity player, LivingEntity attacker, Entity projectile, DamageSource damage)
	{
		if (projectile != null && projectile instanceof ProjectileEntity)
		{
			projectile.remove();
			projectile = projectile.getType().create(level);
			projectile.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
			projectile.setDeltaMovement(player.getLookAngle());
			((ProjectileEntity) projectile).setOwner(player);
			level.addFreshEntity(projectile);
		}
	}
}
