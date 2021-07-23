package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
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
		this.setBaseDamage(this.getBaseDamage() + 2.0F);
	}
	
	public MagicLightArrowEntity(World worldIn, double x, double y, double z)
	{
		super(EntityTypeInit.MAGIC_LIGHT_ARROW.get(), x, y, z, worldIn);
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
}
