package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MagicFireArrowEntity extends FireArrowEntity
{
	public MagicFireArrowEntity(EntityType<? extends MagicFireArrowEntity> type, World world)
	{
		super(type, world);
	}
	
	public MagicFireArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.MAGIC_FIRE_ARROW.get(), worldIn, shooter);
	}
	
	public MagicFireArrowEntity(World worldIn, double x, double y, double z)
	{
		super(EntityTypeInit.MAGIC_FIRE_ARROW.get(), worldIn, x, y, z);
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
		return new ItemStack(ItemInit.MAGIC_FIRE_ARROW.get());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult rayTraceResult)
	{
		super.onHitEntity(rayTraceResult);
		// There is a small time frame after an entity is hurt that gives
		// immunity to damage. And we already damaged it with common damage from
		// an arrow. To deal damage 2 times in a row, we have to reset it.
		rayTraceResult.getEntity().invulnerableTime = 0;
		rayTraceResult.getEntity().hurt(DamageSource.MAGIC, 5.0F);
	}
}
