package com.superworldsun.superslegend.interfaces;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.LivingEntity;

public interface TameableEntity {
	public Optional<LivingEntity> getOwner();

	public void setOwner(@Nullable LivingEntity owner);

	public Optional<UUID> getOwnerUniqueId();

	public default boolean hasOwner() {
		return getOwner().isPresent();
	}
}
