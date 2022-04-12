package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MagicLightArrowEntity extends AbstractArrowEntity
{
	public MagicLightArrowEntity(EntityType<? extends MagicLightArrowEntity> type, World world)
	{
		super(type, world);
	}
	
	public MagicLightArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.MAGIC_LIGHT_ARROW.get(), shooter, worldIn);
	}

	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		setBaseDamage(6.0D);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemInit.MAGIC_LIGHT_ARROW.get());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult rayTraceResult)
	{
		Entity entity = rayTraceResult.getEntity();

		applyResistanceAndWeakness(entity);

		super.onHitEntity(rayTraceResult);
		
		// There is a small time frame after an entity is hurt that gives
		// immunity to damage. And we already damaged it with common damage from
		// an arrow. To deal damage 2 times in a row, we have to reset it.
		entity.invulnerableTime = 0;
		entity.hurt(DamageSource.MAGIC, 5.0F);

		if (entity instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity) entity;

			this.getBaseDamage();
			if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
				livingentity.setArrowCount(livingentity.getArrowCount() - 1);
			}
		}
	}

	private void applyResistanceAndWeakness(Entity entity) {
		if (TagInit.WEAK_TO_LIGHT.contains(entity.getType()))
			setBaseDamage(getBaseDamage() * 2);
		if (TagInit.RESISTANT_TO_LIGHT.contains(entity.getType()))
			setBaseDamage(getBaseDamage() / 2);
	}
}
