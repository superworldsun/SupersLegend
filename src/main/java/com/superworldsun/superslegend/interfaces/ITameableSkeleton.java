package com.superworldsun.superslegend.interfaces;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.LivingEntity;

public interface ITameableSkeleton
{
	@Nullable
	public LivingEntity getOwner();
	
	public void setOwner(@Nullable LivingEntity owner);
	
	@Nullable
	public UUID getOwnerUniqueId();
	
	public boolean hasOwner();
}
