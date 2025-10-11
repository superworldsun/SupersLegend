package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.entities.ai.FollowSkeletonOwnerGoal;
import com.superworldsun.superslegend.entities.ai.SkeletonOwnerHurtByTargetGoal;
import com.superworldsun.superslegend.entities.ai.SkeletonOwnerHurtTargetGoal;
import com.superworldsun.superslegend.interfaces.TameableEntity;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.scores.Team;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;
import java.util.UUID;

@Mixin(AbstractSkeleton.class)
public abstract class MixinAbstractSkeletonEntity extends Monster implements TameableEntity {
	private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(AbstractSkeleton.class, EntityDataSerializers.OPTIONAL_UUID);

	protected MixinAbstractSkeletonEntity() {
		super(null, null);
	}

	@Inject(method = "registerGoals", at = @At("HEAD"))
	private void injectRegisterGoals(CallbackInfo callbackInfo) {
		goalSelector.addGoal(4, new FollowSkeletonOwnerGoal<>(this, 1D, 10F, 2F));
		targetSelector.addGoal(2, new SkeletonOwnerHurtByTargetGoal<>(this));
		targetSelector.addGoal(3, new SkeletonOwnerHurtTargetGoal<>(this));
	}

	@Override
	public void setTarget(LivingEntity target) {
		if (target != null) {
			boolean targetHasCaptainsHat = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_CAPTAINSHAT.get(), target).isPresent();

			if (targetHasCaptainsHat && !hasOwner()) {
				setOwner(target);
				return;
			}
		}
		super.setTarget(target);
	}

	@Override
	public void tick() {
		if (hasOwner()) {
			boolean ownerHasCaptainsHat = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_CAPTAINSHAT.get(), getOwner().orElse(null)).isPresent();
			if (!ownerHasCaptainsHat) {
				setOwner(null);
			}
		}
		super.tick();
	}

	@Override
	protected void defineSynchedData() {
		entityData.define(OWNER_UUID, Optional.empty());
		super.defineSynchedData();
	}

	@Override
	public boolean canAttack(LivingEntity entity) {
		return isOwner(entity) ? false : super.canAttack(entity);
	}

	@Override
	public Team getTeam() {
		return getOwner().map(Entity::getTeam).orElse(super.getTeam());
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		return getOwner().map(this::isEntityAlliedToOwner).orElse(super.isAlliedTo(entity));
	}

	@Override
	public Optional<LivingEntity> getOwner() {
		return getOwnerUniqueId().map(this.level()::getPlayerByUUID);
	}

	@Override
	public void setOwner(LivingEntity owner) {
		Optional<UUID> ownerUniqueId = Optional.ofNullable(owner).map(Entity::getUUID);
		entityData.set(OWNER_UUID, ownerUniqueId);
	}

	@Override
	public Optional<UUID> getOwnerUniqueId() {
		return entityData.get(OWNER_UUID);
	}

	private boolean isOwner(Entity entity) {
		return getOwner().filter(e -> e == entity).isPresent();
	}

	protected boolean isEntityAlliedToOwner(Entity entity) {
		boolean isAlliedToOwner = getOwner().filter(e -> entity.isAlliedTo(e)).isPresent();
		return isOwner(entity) || isAlliedToOwner;
	}
}
