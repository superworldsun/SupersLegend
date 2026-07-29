package com.superworldsun.superslegend.mixin;

import com.google.common.util.concurrent.AtomicDouble;
import com.superworldsun.superslegend.events.HookshotPullPoseEvents;
import com.superworldsun.superslegend.items.ammobags.AmmoContainerItem;
import com.superworldsun.superslegend.registries.ItemInit;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.superworldsun.superslegend.interfaces.IHoveringEntity;
import com.superworldsun.superslegend.interfaces.IPlayerAnimationState;
import com.superworldsun.superslegend.interfaces.JumpingEntity;
import com.superworldsun.superslegend.registries.EffectInit;

import net.minecraft.stats.Stats;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraftforge.common.ForgeHooks;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Player.class)
public abstract class MixinPlayerEntity extends LivingEntity implements IHoveringEntity, JumpingEntity, IPlayerAnimationState {
    private static final EntityDataAccessor<Integer> SUPERSLEGEND_ANIMATION_FLAGS =
            SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SUPERSLEGEND_HOVER_TICKS =
            SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SUPERSLEGEND_HOVERING =
            SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SUPERSLEGEND_HAZARD_HOVERING =
            SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SUPERSLEGEND_BOUNCE_HOVERING =
            SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    public @Shadow @Final Abilities abilities;
    private float targetScale = 1.0F;
    private float scale = 1.0F;
    private float targetRenderScale = 1.0F;
    private float renderScale = 1.0F;
    private float prevRenderScale = 1.0F;
    private double hoverHeight;
    private boolean jumpedFromGround;
    private boolean wasWearingHoverBoots;
    private boolean wasOnStickyHoverBlock;
    private boolean stickyBouncePending;
    private int normalHoverDeadline = Integer.MIN_VALUE;
    private boolean isLit;
    //private EntityLightEmitter lightEmitter = new EntityLightEmitter(() -> level, this::getLightRayVector, this::getLightRayPosition, this);

    // This constructor is fake and never used
    protected MixinPlayerEntity() {
        super(null, null);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void superslegend$defineAnimationData(org.spongepowered.asm.mixin.injection.callback.CallbackInfo callbackInfo) {
        entityData.define(SUPERSLEGEND_ANIMATION_FLAGS, 0);
        entityData.define(SUPERSLEGEND_HOVER_TICKS, 0);
        entityData.define(SUPERSLEGEND_HOVERING, false);
        entityData.define(SUPERSLEGEND_HAZARD_HOVERING, false);
        entityData.define(SUPERSLEGEND_BOUNCE_HOVERING, false);
    }

    @Override
    public int superslegend$getAnimationFlags() {
        return entityData.get(SUPERSLEGEND_ANIMATION_FLAGS);
    }

    @Override
    public void superslegend$setAnimationFlags(int animationFlags) {
        entityData.set(SUPERSLEGEND_ANIMATION_FLAGS, animationFlags);
    }

    @Inject(method = "getStandingEyeHeight", at = @At("HEAD"), cancellable = true)
    private void superslegend$keepHookshotEyeHeight(Pose pose, EntityDimensions dimensions,
                                                    CallbackInfoReturnable<Float> callbackInfo) {
        Player player = (Player) (Object) this;
        if (pose == Pose.SWIMMING && HookshotPullPoseEvents.isHookPullPoseActive(player)) {
            callbackInfo.setReturnValue(player.getEyeHeight(Pose.STANDING));
        }
    }

//    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
//    private void scaleEntitySize(Pose pose, CallbackInfoReturnable<EntitySize> callbackInfo) {
//        callbackInfo.setReturnValue(callbackInfo.getReturnValue().scale(getScale()));
//    }
//
//    @Inject(method = "getStandingEyeHeight", at = @At("RETURN"), cancellable = true)
//    private void getStandingEyeHeight(Pose pose, EntitySize size, CallbackInfoReturnable<Float> callbackInfo) {
//        callbackInfo.setReturnValue(callbackInfo.getReturnValue() * getScale());
//    }

    /*@Inject(method = "tick", at = @At("HEAD"))
    private void injectTick(CallbackInfo ci) {
        targetScale = 1.0F;
        targetRenderScale = 1.0F;

        getArmorSlots().forEach(stack -> {
            if (stack.getItem() instanceof IEntityResizer) {
                targetScale *= ((IEntityResizer) stack.getItem()).getScale((Player) getEntity());
                targetRenderScale *= ((IEntityResizer) stack.getItem()).getRenderScale((Player) getEntity());
            }
        });

        CuriosApi.getCuriosHelper().getEquippedCurios((Player) getEntity()).ifPresent(curios -> {
            for (int i = 0; i < curios.getSlots(); i++) {
                ItemStack curioStack = curios.getStackInSlot(i);

                if (!curioStack.isEmpty() && curioStack.getItem() instanceof IEntityResizer) {
                    targetScale *= ((IEntityResizer) curioStack.getItem()).getScale((Player) getEntity());
                    targetRenderScale *= ((IEntityResizer) curioStack.getItem()).getRenderScale((Player) getEntity());
                }
            }
        });

        prevRenderScale = renderScale;

        if (targetRenderScale > renderScale) {
            renderScale = Math.min(targetRenderScale, renderScale + 0.05F);
        }

        if (targetRenderScale < renderScale) {
            renderScale = Math.max(targetRenderScale, renderScale - 0.05F);
        }

        if (targetScale > scale) {
            setScale(Math.min(targetScale, scale + 0.05F));
        }

        if (targetScale < scale) {
            setScale(Math.max(targetScale, scale - 0.05F));
        }

        // Increase movement if we are bigger, decrease if we are smaller
        if (scale > 1) {
            float bonusSpeed = scale - 1;
            move(MoverType.SELF, getDeltaMovement().multiply(bonusSpeed, 1D, bonusSpeed));
        } else if (scale < 1) {
            float bonusSpeed = (scale - 1) / 2;
            setBoundingBox(getBoundingBox().move(getDeltaMovement().multiply(bonusSpeed, bonusSpeed, bonusSpeed)));
        }

        lightEmitter.tick();
    }*/

    @Inject(method = "getProjectile", at = @At(value = "HEAD"), cancellable = true)
    private void getProjectileFromQuiver(ItemStack weaponStack, CallbackInfoReturnable<ItemStack> callbackInfo) {
        if (!(weaponStack.getItem() instanceof ProjectileWeaponItem)) {
            return;
        }

        Player player = (Player) (Object) this;
        ProjectileWeaponItem shootableItem = (ProjectileWeaponItem) weaponStack.getItem();

        CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curios -> {
            for (int i = 0; i < curios.getSlots(); i++) {
                ItemStack curioStack = curios.getStackInSlot(i);

                if (!curioStack.isEmpty() && curioStack.getItem() instanceof AmmoContainerItem quiverItem) {
                    Pair<ItemStack, Integer> quiverContents = quiverItem.getContents(curioStack);

                    if (quiverContents == null) {
                        continue;
                    }

                    int arrowsCount = quiverContents.getRight();

                    if (arrowsCount == 0) {
                        continue;
                    }

                    ItemStack arrowsStack = quiverContents.getLeft();

                    // if our weapon can shoot items inside of the quiver
                    if (shootableItem.getSupportedHeldProjectiles().test(arrowsStack)) {
                        if (player.level().isClientSide()) {
                            callbackInfo.setReturnValue(ItemStack.EMPTY);
                        } else {
                            callbackInfo.setReturnValue(arrowsStack);
                        }

                        return;
                    }
                }
            }
        });
    }

    @Override
    protected float getJumpPower() {
        float jumpPower = super.getJumpPower();
        AtomicDouble jumpPowerMultiplier = new AtomicDouble(1F);

//        getArmorSlots().forEach(stack -> {
//            if (stack.getItem() instanceof IEntityResizer) {
//                float itemJumpPowerMultiplier = ((IEntityResizer) stack.getItem()).getJumpPowerMultiplier();
//                jumpPowerMultiplier.set(itemJumpPowerMultiplier);
//            }
//        });

//        CuriosApi.getCuriosHelper().getEquippedCurios((Player) getEntity()).ifPresent(curios -> {
//            for (int i = 0; i < curios.getSlots(); i++) {
//                ItemStack curioStack = curios.getStackInSlot(i);
//
//                if (!curioStack.isEmpty() && curioStack.getItem() instanceof IEntityResizer) {
//                    float itemJumpPowerMultiplier = ((IEntityResizer) curioStack.getItem()).getJumpPowerMultiplier();
//                    jumpPowerMultiplier.set(itemJumpPowerMultiplier);
//                }
//            }
//        });

        jumpPower *= jumpPowerMultiplier.get();
        return jumpPower;
    }

    @Override
    public void doubleJump() {
        Player player = (Player) (Object) this;
        double jumpPower = getJumpPower();

        if (player.hasEffect(MobEffects.JUMP)) {
            jumpPower += 0.1 * (player.getEffect(MobEffects.JUMP).getAmplifier() + 1);
        }

        Vec3 movementVector = player.getDeltaMovement();
        Vec3 jumpVector = new Vec3(0, jumpPower - movementVector.y, 0);
        player.setDeltaMovement(movementVector.add(jumpVector));
        player.hasImpulse = true;
        player.awardStat(Stats.JUMP);
        ForgeHooks.onLivingJump(player);

        if (player.isSprinting()) {
            player.causeFoodExhaustion(0.2F);
        } else {
            player.causeFoodExhaustion(0.05F);
        }
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        refreshDimensions();
    }

//    @Override
//    public AABB getBoundingBoxForCulling() {
//        return getBoundingBoxForPose(getPose());
//    }

    @Override
    protected void pushEntities() {
        if (!hasEffect(EffectInit.CLOAKED.get())) {
            super.pushEntities();
        }
    }

    @Override
    public boolean isPushable() {
        return !hasEffect(EffectInit.CLOAKED.get()) && super.isPushable();
    }

//    @Override
//    public float getScaleForRender(float partialTick) {
//        return prevRenderScale + (renderScale - prevRenderScale) * partialTick;
//    }

    @Override
    public int getHoverTime() {
        return entityData.get(SUPERSLEGEND_HOVER_TICKS);
    }

    @Override
    public void setHoverTime(int amount) {
        entityData.set(SUPERSLEGEND_HOVER_TICKS, amount);
    }

    @Override
    public int increaseHoverTime() {
        int hoverTime = getHoverTime();
        setHoverTime(hoverTime + 1);
        return hoverTime;
    }

    @Override
    public void setHoverHeight(double height) {
        hoverHeight = height;
    }

    @Override
    public double getHoverHeight() {
        return hoverHeight;
    }

    @Override
    public void setHovering(boolean hovering) {
        entityData.set(SUPERSLEGEND_HOVERING, hovering);
    }

    @Override
    public boolean isHovering() {
        return entityData.get(SUPERSLEGEND_HOVERING);
    }

    @Override
    public void setHazardHovering(boolean hazardHovering) {
        entityData.set(SUPERSLEGEND_HAZARD_HOVERING, hazardHovering);
    }

    @Override
    public boolean isHazardHovering() {
        return entityData.get(SUPERSLEGEND_HAZARD_HOVERING);
    }

    @Override
    public void setBounceHovering(boolean bounceHovering) {
        entityData.set(SUPERSLEGEND_BOUNCE_HOVERING, bounceHovering);
    }

    @Override
    public boolean isBounceHovering() {
        return entityData.get(SUPERSLEGEND_BOUNCE_HOVERING);
    }

    @Override
    public void setStickyBouncePending(boolean stickyBouncePending) {
        this.stickyBouncePending = stickyBouncePending;
    }

    @Override
    public boolean isStickyBouncePending() {
        return stickyBouncePending;
    }

    @Override
    public void setWasWearingHoverBoots(boolean wearingHoverBoots) {
        wasWearingHoverBoots = wearingHoverBoots;
    }

    @Override
    public boolean wasWearingHoverBoots() {
        return wasWearingHoverBoots;
    }

    @Override
    public void setWasOnStickyHoverBlock(boolean onStickyHoverBlock) {
        wasOnStickyHoverBlock = onStickyHoverBlock;
    }

    @Override
    public boolean wasOnStickyHoverBlock() {
        return wasOnStickyHoverBlock;
    }

    @Override
    public void setNormalHoverDeadline(int deadlineTick) {
        normalHoverDeadline = deadlineTick;
    }

    @Override
    public int getNormalHoverDeadline() {
        return normalHoverDeadline;
    }

    @Override
    public boolean jumpedFromBlock() {
        return jumpedFromGround;
    }

    @Override
    public void setJumpedFromBlock(boolean state) {
        jumpedFromGround = state;
    }

    @Override
    public boolean isSwimming() {
        return canSwim() && !abilities.flying && !this.isSpectator() && super.isSwimming();
    }

    @Override
    public boolean isJumping() {
        return jumping;
    }

//    @Override
//    public void receiveLight() {
//        isLit = true;
//    }

//    @Override
//    public void stopReceivingLight() {
//        isLit = false;
//
//        if (lightEmitter.litObject instanceof ILightReceiver && ((ILightReceiver) lightEmitter.litObject).isLit()) {
//            ((ILightReceiver) lightEmitter.litObject).stopReceivingLight();
//        }
//    }

//    @Override
//    public boolean isLit() {
//        return isLit;
//    }
//
//    @Override
//    public AbstractLightEmitter getLightEmitter() {
//        return lightEmitter;
//    }
//
//    private Vector3d getLightRayVector() {
//        if (isLit && isBlocking() && getItemInHand(getUsedItemHand()).getItem() instanceof MirrorShield) {
//            return getLookAngle();
//        }
//
//        return Vector3d.ZERO;
//    }
//
//    private Vector3d getLightRayPosition() {
//        return position().add(0, getEyeHeight() * 0.8, 0);
//    }
//
//    private void setScale(float scale) {
//        this.scale = scale;
//        maxUpStep = 0.6F * scale;
//        updateEyeHeight();
//        refreshDimensions();
//    }
//
    private boolean canSwim() {
        boolean hasIronBoots = getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.IRON_BOOTS.get();
        boolean hasGoronMask = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), (Player) (Object) this).isPresent();

        if (hasIronBoots || hasGoronMask)
            return false;

        return true;
    }
//
//    private void updateEyeHeight() {
//        eyeHeight = getDimensions(getPose()).height * 0.85F;
//    }
//
//    public @Shadow ItemStack getItemBySlot(EquipmentSlotType slot) { return null; }
}
