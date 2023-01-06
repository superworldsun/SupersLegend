package com.superworldsun.superslegend.mixin;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.superworldsun.superslegend.entities.ai.FollowSkeletonOwnerGoal;
import com.superworldsun.superslegend.entities.ai.SkeletonOwnerHurtByTargetGoal;
import com.superworldsun.superslegend.entities.ai.SkeletonOwnerHurtTargetGoal;
import com.superworldsun.superslegend.interfaces.TameableEntity;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(AbstractSkeletonEntity.class)
public abstract class MixinAbstractSkeletonEntity extends MonsterEntity implements TameableEntity {
	private static final DataParameter<Optional<UUID>> OWNER_UUID = EntityDataManager.defineId(AbstractSkeletonEntity.class, DataSerializers.OPTIONAL_UUID);

	// This constructor is fake and never used
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
			}
		}

		super.setTarget(target);
	}

	@Override
	public void tick() {
		if (hasOwner()) {
			boolean ownerHasCaptainsHat = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_CAPTAINSHAT.get(), getOwner().get()).isPresent();

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
		return entity == getOwner().get() ? false : super.canAttack(entity);
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
		return getOwnerUniqueId().map(level::getPlayerByUUID);
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

	protected boolean isEntityAlliedToOwner(Entity entity) {
		LivingEntity owner = getOwner().get();
		return entity == owner || entity.isAlliedTo(owner);
	}
}
