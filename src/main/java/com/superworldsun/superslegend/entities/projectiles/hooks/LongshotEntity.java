package com.superworldsun.superslegend.entities.projectiles.hooks;


import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.capability.hookshot.HookModel;
import com.superworldsun.superslegend.entities.HeartEntity;
import com.superworldsun.superslegend.entities.GoldSkulltulaTokenEntity;
import com.superworldsun.superslegend.entities.LargeMagicJarEntity;
import com.superworldsun.superslegend.entities.MagicJarEntity;
import com.superworldsun.superslegend.events.HookshotPullPoseEvents;
import com.superworldsun.superslegend.items.hookshot.HookshotItem;
import com.superworldsun.superslegend.items.hookshot.LongshotItem;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

import static com.superworldsun.superslegend.items.hookshot.LongshotItem.LONG_SPRITE;
import static com.superworldsun.superslegend.util.HookBlockList.isHookable;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LongshotEntity extends AbstractArrow {
    private static final int RETURN_FINISH_DELAY_TICKS = 1;
    private static final int NETWORK_UPDATE_INTERVAL_TICKS = 5;
    private static final double CARRIED_RETURN_SPEED_MULTIPLIER = 0.65D;
    private static final double TARGET_FOLLOW_CORRECTION = 0.75D;
    private static final double TARGET_ARRIVAL_DISTANCE_SQR = 0.04D;
    private static final double ADVANCEMENT_PULL_DISTANCE_SQR = 30.0D * 30.0D;

    /**
     * useBlockList serves to enable or disable the block list that can be hooked on the hook.
     * isPulling activates the player's movement. Do not touch this value.
     * motionUp lets you know when the player goes up to get off the hook. Do not touch this value.
     */
    private static final EntityDataAccessor<Integer> HOOKED_ENTITY_ID = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER_ENTITY_ID = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FIRED_FROM_OFFHAND = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RETRIEVING = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> RETURN_SPEED = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> RETURN_ARRIVAL_TICKS = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PULLING = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> VISUAL_DIRECTION_X = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> VISUAL_DIRECTION_Y = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> VISUAL_DIRECTION_Z = SynchedEntityData.defineId(LongshotEntity.class, EntityDataSerializers.FLOAT);
    boolean useBlockList = true;
    private double maxRange = 0D;
    private double maxSpeed = 0D;
    private boolean isPulling = false;
    private Player owner;
    private Entity hookedEntity;
    private ItemStack stack;
    private Vec3 launchPosition;
    private boolean motionUp = false;
    private double prevDistance = 30D;
    private Vec3 advancementPullStart;


    public LongshotEntity(EntityType<? extends AbstractArrow> type, LivingEntity owner, Level world) {
        super(type, owner, world);
        this.setSoundEvent(SoundInit.HOOKSHOT_TARGET.get());
        this.setNoGravity(true);
        this.setBaseDamage(0);
        this.entityData.set(OWNER_ENTITY_ID, owner.getId());
        if (owner instanceof Player player) {
            this.owner = player;
        }

    }

    public LongshotEntity(EntityType<LongshotEntity> longshotEntityEntityType, Level world) {
        super(EntityTypeInit.LONGSHOT_ENTITY.get(), world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HOOKED_ENTITY_ID, 0);
        this.entityData.define(OWNER_ENTITY_ID, -1);
        this.entityData.define(FIRED_FROM_OFFHAND, false);
        this.entityData.define(RETRIEVING, false);
        this.entityData.define(RETURN_SPEED, 0.1F);
        this.entityData.define(RETURN_ARRIVAL_TICKS, 0);
        this.entityData.define(PULLING, false);
        this.entityData.define(VISUAL_DIRECTION_X, 0.0F);
        this.entityData.define(VISUAL_DIRECTION_Y, 0.0F);
        this.entityData.define(VISUAL_DIRECTION_Z, -1.0F);
    }

    @Override
    protected AABB makeBoundingBox() {
        double halfWidth = getBbWidth() * 0.5D;
        double halfHeight = getBbHeight() * 0.5D;
        return new AABB(getX() - halfWidth, getY() - halfHeight, getZ() - halfWidth,
                getX() + halfWidth, getY() + halfHeight, getZ() + halfWidth);
    }

    /**
     * This is where everything related to movement happens.
     */
    @Override
    public void tick() {
        Player resolvedOwner = getHookOwner();
        if (resolvedOwner != null) {
            owner = resolvedOwner;
        }
        if (isRetrieving() && owner != null) {
            updateReturnMotion();
        }

        if (!level().isClientSide && !isRetrieving()) {
            Entity retrievalTarget = HookshotRetrievalTarget.findAlongPath(this);
            if (retrievalTarget != null) {
                beginRetrieving(retrievalTarget);
            }
        }

        super.tick();
        if (!isAlive()) {
            return;
        }

        if(this.tickCount % 3 == 0)
        {
            BlockPos currentPos = this.blockPosition();
            this.level().playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.HOOKSHOT_EXTENDED.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        if (isRetrieving()) {
            tickRetrieval();
            return;
        }

        if (owner != null) {
            if (isPulling && tickCount % 2 == 0) { //This is the sound that sounds when the hook is moving you.
                //level.playSound(null, owner.blockPosition(), SoundEvents.AXE_STRIP, SoundCategory.PLAYERS, 1F, 1F);
            }
            if (!level().isClientSide) {
                if (this.hookedEntity != null) { //In case the mob you are hooked to dies while you go towards it ..
                    if (!this.hookedEntity.isAlive()) {
                        this.hookedEntity = null;
                        beginReturning();
                        return;
                    } else {
                        this.absMoveTo(this.hookedEntity.getX(), this.hookedEntity.getY(0.8D), this.hookedEntity.getZ());
                    }
                }

                if (owner.isDeadOrDying() ||
                        !(owner.getMainHandItem().getItem() instanceof LongshotItem ||
                                owner.getOffhandItem().getItem() instanceof LongshotItem)) {
                    LONG_SPRITE = false;
                    kill();
                    return;
                }

                if (this.tickCount >= 65 || !HookModel.get(owner).getHasHook() || (!isPulling && hasReachedMaxRange())) {
                    beginReturning();
                    return;
                }

                if (owner.getMainHandItem() == stack || owner.getOffhandItem() == stack) {
                    if (isPulling) { //Movement start
                        checkPullTravelAdvancement();
                        Entity target = owner;
                        Entity origin = this;

                        if (owner.isCrouching() && hookedEntity != null) {
                            target = hookedEntity;
                            origin = owner;
                            owner.setNoGravity(true);
                        }

                        double brakeZone = ((6D * (maxSpeed)) / 10); //5
                        double pullSpeed = (maxSpeed) / 9D;
                        Vec3 targetPullOrigin = target == owner
                                ? HookshotPullPoseEvents.getPullOrigin(owner)
                                : target.position().add(0, target.getBbHeight() / 2, 0);
                        Vec3 distance = origin.position().subtract(targetPullOrigin);
                        if (hookedEntity == null && distance.lengthSqr() <= 2.25D) {
                            beginReturning();
                            return;
                        }
                        double reduction = (pullSpeed); //Get motion reduction.
                        Vec3 motion = distance.normalize().multiply(reduction, reduction, reduction); //Get last motion.

                        //In case the movement is at ground level.
                        if (Math.abs(distance.y) < 0.1D) {
                            motion = new Vec3(motion.x, 0, motion.z);
                        }
                        //In case the movement is only upwards.
                        else if (new Vec3(distance.x, 0, distance.z).length() < new Vec3(target.getBbWidth() / 2, 0, target.getBbWidth() / 2).length() / 1.4) {
                            motion = new Vec3(0, motion.y, 0);
                            motionUp = true;
                        }

                        if (target == owner && hookedEntity == null) {
                            Vec3 horizontalMotion = new Vec3(motion.x, 0.0D, motion.z);
                            boolean blockedAhead = horizontalMotion.lengthSqr() > 1.0E-7D
                                    && !level().noCollision(owner,
                                    owner.getBoundingBox().deflate(1.0E-4D).move(horizontalMotion));
                            if (blockedAhead || owner.horizontalCollision) {
                                motion = new Vec3(motion.x, Math.max(motion.y, 0.32D), motion.z);
                            }
                        }

                        target.fallDistance = 0; //Cancel Fall Damage

                        target.setDeltaMovement(motion); //Set motion.
                        target.hurtMarked = true; //Make motion works, this is necessary.

                        //Makes you off the hook early if entity.
                        if(hookedEntity != null){
                            motion = owner.getDeltaMovement();
                            if (distance.length() > prevDistance && prevDistance < 1){
                                beginReturning();
                                return;
                            }
                            //Timer if the entity if too BIG.
                            if(tickCount > 50){
                                beginReturning();
                                return;
                            }
                        }
                        //Makes you off the hook early if block.
                        if(hookedEntity == null) {
                            motion = owner.getDeltaMovement();
                            if (distance.length() > prevDistance && prevDistance < 1){
                                beginReturning();
                                return;
                            } else if (new Vec3(distance.x, 0, distance.z).length() < 0.3D) {
                                beginReturning();
                                return;
                            }
                        }
                        prevDistance = distance.length();

                        //Take the entity if it is an item and check that it is in your inventory to kill the hook.
                        if(hookedEntity instanceof ItemEntity item){
                            boolean collected;
                            if (item instanceof GoldSkulltulaTokenEntity token) {
                                collected = token.collectWithRemoteTool(owner);
                            } else {
                                int countBeforePickup = item.getItem().getCount();
                                item.setNoPickUpDelay();
                                item.playerTouch(owner);
                                collected = !item.isAlive() || item.getItem().getCount() < countBeforePickup;
                            }
                            if(collected) {
                                if (owner instanceof ServerPlayer serverPlayer) {
                                    ModAdvancementHelper.award(serverPlayer, "hooked_delivery", "picked_up_item");
                                }
                                LONG_SPRITE = false;
                                HookModel.get(owner).setHasHook(false);
                                kill();

                            }
                        }

                    }

                } else {
                    beginReturning();
                    return;
                }
            }
        } else if (!level().isClientSide) {
            kill();
        }
    }

    private void beginRetrieving(Entity target) {
        if (target instanceof GoldSkulltulaTokenEntity token) {
            token.markRemoteToolPickup();
        }
        hookedEntity = target;
        entityData.set(HOOKED_ENTITY_ID, target.getId() + 1);
        entityData.set(RETRIEVING, true);
        entityData.set(RETURN_ARRIVAL_TICKS, 0);
        setPulling(false);
        noPhysics = true;
        setNoGravity(true);
        setPos(target.getX(), target.getY(0.5D), target.getZ());
        updateReturnMotion();
        hasImpulse = true;
        prepareRetrievalTarget(target);
        updateRetrievalTargetMotion(target);
    }

    private void beginReturning() {
        if (isRetrieving()) {
            return;
        }

        hookedEntity = null;
        entityData.set(HOOKED_ENTITY_ID, 0);
        entityData.set(RETRIEVING, true);
        entityData.set(RETURN_ARRIVAL_TICKS, 0);
        setPulling(false);
        noPhysics = true;
        setNoGravity(true);
        if (owner != null) {
            owner.setNoGravity(false);
            HookModel.get(owner).setHasHook(true);
        }
        updateReturnMotion();
        hasImpulse = true;
    }

    private void tickRetrieval() {
        noPhysics = true;
        setNoGravity(true);
        Entity target = getRetrievalTarget();
        if (target != null && target.isAlive()) {
            prepareRetrievalTarget(target);
        } else {
            hookedEntity = null;
            entityData.set(HOOKED_ENTITY_ID, 0);
        }

        if (owner == null) {
            if (!level().isClientSide) {
                kill();
            }
            return;
        }

        Vec3 destination = getReturnDestination();
        if (position().distanceToSqr(destination) <= 0.01D) {
            setPos(destination.x, destination.y, destination.z);
            setDeltaMovement(Vec3.ZERO);
            if (target != null && target.isAlive()) {
                Vec3 attachmentPosition = getRetrievalAttachmentPosition(target);
                updateRetrievalTargetMotion(target);
                if (target.position().distanceToSqr(attachmentPosition) > TARGET_ARRIVAL_DISTANCE_SQR) {
                    if (!level().isClientSide) {
                        entityData.set(RETURN_ARRIVAL_TICKS, 0);
                    }
                    return;
                }
                target.setPos(attachmentPosition.x, attachmentPosition.y, attachmentPosition.z);
                target.setDeltaMovement(Vec3.ZERO);
            }
            if (!level().isClientSide) {
                int arrivalTicks = entityData.get(RETURN_ARRIVAL_TICKS) + 1;
                entityData.set(RETURN_ARRIVAL_TICKS, arrivalTicks);
                if (arrivalTicks >= RETURN_FINISH_DELAY_TICKS) {
                    finishRetrieval();
                }
            }
            return;
        }
        if (!level().isClientSide) {
            entityData.set(RETURN_ARRIVAL_TICKS, 0);
        }
        updateReturnMotion();
        if (target != null && target.isAlive()) {
            updateRetrievalTargetMotion(target);
        }
    }

    private Vec3 getReturnDestination() {
        return owner == null
                ? position()
                : HookshotLaunchPosition.getFiringHandPosition(owner, getFiredHand());
    }

    private boolean hasReachedMaxRange() {
        if (launchPosition == null) {
            launchPosition = position();
        }
        return launchPosition.distanceToSqr(position()) >= maxRange * maxRange;
    }

    private void updateReturnMotion() {
        if (owner == null) {
            return;
        }
        Vec3 destination = getReturnDestination();
        Vec3 direction = destination.subtract(position());
        if (direction.lengthSqr() > 1.0E-7D) {
            double returnSpeed = Math.max(0.1D, entityData.get(RETURN_SPEED));
            if (getRetrievalTarget() != null) {
                returnSpeed *= CARRIED_RETURN_SPEED_MULTIPLIER;
            }
            setDeltaMovement(direction.normalize().scale(Math.min(returnSpeed, direction.length())));
        } else {
            setDeltaMovement(Vec3.ZERO);
        }
    }

    private Entity getRetrievalTarget() {
        if (hookedEntity != null && hookedEntity.isAlive()) {
            return hookedEntity;
        }
        int targetId = entityData.get(HOOKED_ENTITY_ID) - 1;
        if (targetId >= 0) {
            Entity syncedTarget = level().getEntity(targetId);
            if (HookshotRetrievalTarget.canRetrieve(syncedTarget)) {
                hookedEntity = syncedTarget;
                return syncedTarget;
            }
        }
        return null;
    }

    private void prepareRetrievalTarget(Entity target) {
        target.noPhysics = true;
        target.setNoGravity(true);
        target.hurtMarked = true;
        if (target instanceof HeartEntity heart) {
            heart.age = 0;
        } else if (target instanceof LargeMagicJarEntity largeMagicJar) {
            largeMagicJar.age = 0;
        } else if (target instanceof MagicJarEntity magicJar) {
            magicJar.age = 0;
        }
    }

    private Vec3 getRetrievalAttachmentPosition(Entity target) {
        return new Vec3(getX(), getY() - target.getBbHeight() * 0.5D, getZ());
    }

    private void updateRetrievalTargetMotion(Entity target) {
        Vec3 correction = getRetrievalAttachmentPosition(target).subtract(target.position());
        target.setDeltaMovement(getDeltaMovement().add(correction.scale(TARGET_FOLLOW_CORRECTION)));
        target.hasImpulse = true;
        target.hurtMarked = true;
    }

    private void finishRetrieval() {
        Entity target = getRetrievalTarget();
        if (target != null) {
            releaseRetrievalTarget(target);
            Vec3 attachmentPosition = getRetrievalAttachmentPosition(target);
            target.setPos(attachmentPosition.x, attachmentPosition.y, attachmentPosition.z);
            if (target instanceof ItemEntity item) {
                int countBeforePickup = item.getItem().getCount();
                boolean collected;
                if (item instanceof GoldSkulltulaTokenEntity token) {
                    collected = token.collectWithRemoteTool(owner);
                } else {
                    item.setNoPickUpDelay();
                    item.playerTouch(owner);
                    collected = !item.isAlive() || item.getItem().getCount() < countBeforePickup;
                }
                if (collected && owner instanceof ServerPlayer serverPlayer) {
                    ModAdvancementHelper.award(serverPlayer, "hooked_delivery", "picked_up_item");
                }
            } else if (target instanceof HeartEntity heart) {
                heart.throwTime = 0;
                heart.playerTouch(owner);
            } else if (target instanceof LargeMagicJarEntity largeMagicJar) {
                largeMagicJar.throwTime = 0;
                largeMagicJar.playerTouch(owner);
            } else if (target instanceof MagicJarEntity magicJar) {
                magicJar.throwTime = 0;
                magicJar.playerTouch(owner);
            }
        }
        hookedEntity = null;
        entityData.set(HOOKED_ENTITY_ID, 0);
        entityData.set(RETRIEVING, false);
        entityData.set(RETURN_ARRIVAL_TICKS, 0);
        noPhysics = false;
        kill();
    }

    private void releaseRetrievalTarget(Entity target) {
        target.noPhysics = false;
        target.setNoGravity(false);
        target.setDeltaMovement(Vec3.ZERO);
        target.hurtMarked = true;
    }

    //Prevents a crash. Name self-explanatory.
    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    public void kill() {
        setPulling(false);
        Entity retrievalTarget = isRetrieving() ? getRetrievalTarget() : null;
        if (retrievalTarget != null) {
            releaseRetrievalTarget(retrievalTarget);
        }
        entityData.set(RETRIEVING, false);
        entityData.set(RETURN_ARRIVAL_TICKS, 0);
        entityData.set(HOOKED_ENTITY_ID, 0);
        hookedEntity = null;
        noPhysics = false;

        if (!level().isClientSide && owner != null) {
            HookModel.get(owner).setHasHook(false);
            owner.setNoGravity(false);
            owner.setDeltaMovement(0, 0, 0);
        }
        if (owner != null) {
            owner.hurtMarked = true;
        }
        super.kill();
    }

    @Override
    public void remove(RemovalReason reason) {
        Entity retrievalTarget = isRetrieving() ? getRetrievalTarget() : null;
        if (retrievalTarget != null) {
            releaseRetrievalTarget(retrievalTarget);
        }
        super.remove(reason);
        if (this.getOwner() instanceof Player player) {
            LongshotItem.resetSprite(player);
        }
    }

    /**
     * This function is used to make the hook go slower or faster in water.
     * Currently it has no value.
     */
    @Override
    protected float getWaterInertia() {
        return 1.0F;
    }

    /**
     * This function is used to detect when the hook hits an object.
     * It is also used to collect items from the ground.
     * @param blockHitResult
     */
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (!level().isClientSide) {
            boolean hookable = !useBlockList
                    || isHookable(level().getBlockState(blockHitResult.getBlockPos()));
            level().playSound(
                    null,
                    blockHitResult.getLocation().x,
                    blockHitResult.getLocation().y,
                    blockHitResult.getLocation().z,
                    hookable ? SoundEvents.CHAIN_PLACE : SoundEvents.SHIELD_BLOCK,
                    SoundSource.PLAYERS,
                    1.0F,
                    hookable ? 0.85F : 1.15F
            );
        }
        if (!level().isClientSide && isBeyondMaxRange(blockHitResult.getLocation())) {
            setPulling(false);
            beginReturning();
            return;
        }
        if (!level().isClientSide && useBlockList
                && !isHookable(level().getBlockState(blockHitResult.getBlockPos()))) {
            setPulling(false);
            beginReturning();
            return;
        }

        super.onHitBlock(blockHitResult);

        if (!level().isClientSide && owner != null && hookedEntity == null) {
            setPulling(true);
            owner.setNoGravity(true);
            if (owner instanceof ServerPlayer serverPlayer) {
                ModAdvancementHelper.award(serverPlayer, "hooked_a_block", "hooked_block");
            }
        }
    }

    /**
     * This function is used to detect when the hook hits an entity.
     * @param entityHitResult
     */
    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!level().isClientSide && HookshotRetrievalTarget.canRetrieve(entityHitResult.getEntity())) {
            beginRetrieving(entityHitResult.getEntity());
            return;
        }
        if (!level().isClientSide && owner != null && entityHitResult.getEntity() != owner) {
            if((entityHitResult.getEntity() instanceof LivingEntity || entityHitResult.getEntity() instanceof EnderDragonPart) && hookedEntity == null) {
                hookedEntity = entityHitResult.getEntity();
                entityData.set(HOOKED_ENTITY_ID, hookedEntity.getId() + 1);
                setPulling(true);
                owner.setNoGravity(true);

            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        maxRange = tag.getDouble("maxRange");
        maxSpeed = tag.getDouble("maxSpeed");
        setPulling(tag.getBoolean("isPulling"));
        entityData.set(RETRIEVING, tag.getBoolean("retrieving"));
        noPhysics = isRetrieving();
        entityData.set(FIRED_FROM_OFFHAND, tag.getBoolean("firedFromOffhand"));
        entityData.set(RETURN_SPEED, tag.contains("returnSpeed") ? tag.getFloat("returnSpeed") : (float) (maxSpeed * 0.15D));
        if (tag.contains("visualDirectionX")) {
            entityData.set(VISUAL_DIRECTION_X, tag.getFloat("visualDirectionX"));
            entityData.set(VISUAL_DIRECTION_Y, tag.getFloat("visualDirectionY"));
            entityData.set(VISUAL_DIRECTION_Z, tag.getFloat("visualDirectionZ"));
        }
        if (tag.contains("launchX")) {
            launchPosition = new Vec3(tag.getDouble("launchX"), tag.getDouble("launchY"), tag.getDouble("launchZ"));
        }
        stack = ItemStack.of(tag.getCompound("hookshotItem"));

        entityData.set(OWNER_ENTITY_ID, tag.getInt("owner"));
        if (level().getEntity(tag.getInt("owner")) instanceof Player)
            owner = (Player) level().getEntity(tag.getInt("owner"));
    }



    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putDouble("maxRange", maxRange);
        tag.putDouble("maxSpeed", maxSpeed);
        tag.putBoolean("isPulling", isPulling);
        tag.putBoolean("retrieving", isRetrieving());
        tag.putBoolean("firedFromOffhand", entityData.get(FIRED_FROM_OFFHAND));
        tag.putFloat("returnSpeed", entityData.get(RETURN_SPEED));
        tag.putFloat("visualDirectionX", entityData.get(VISUAL_DIRECTION_X));
        tag.putFloat("visualDirectionY", entityData.get(VISUAL_DIRECTION_Y));
        tag.putFloat("visualDirectionZ", entityData.get(VISUAL_DIRECTION_Z));
        if (launchPosition != null) {
            tag.putDouble("launchX", launchPosition.x);
            tag.putDouble("launchY", launchPosition.y);
            tag.putDouble("launchZ", launchPosition.z);
        }
        tag.put("hookshotItem", stack.save(new CompoundTag()));
        tag.putInt("owner", owner.getId());
    }

    /**
     * Used to get the properties from the item.
     */
    public void setProperties(ItemStack stack, double maxRange, double maxVelocity, float pitch, float yaw, float roll, float modifierZ) {
        float f = 0.017453292F;
        float x = -Mth.sin(yaw * f) * Mth.cos(pitch * f);
        float y = -Mth.sin((pitch + roll) * f);
        float z = Mth.cos(yaw * f) * Mth.cos(pitch * f);
        this.shoot(x, y, z, modifierZ, 0);
        captureVisualDirection();

        this.stack = stack;
        this.maxRange = maxRange;
        this.maxSpeed = maxVelocity;
        this.entityData.set(RETURN_SPEED, (float) this.getDeltaMovement().length());
    }

    private void captureVisualDirection() {
        Vec3 movement = getDeltaMovement();
        if (movement.lengthSqr() <= 1.0E-7D) {
            return;
        }
        Vec3 directionToPlayer = movement.normalize().scale(-1.0D);
        entityData.set(VISUAL_DIRECTION_X, (float) directionToPlayer.x);
        entityData.set(VISUAL_DIRECTION_Y, (float) directionToPlayer.y);
        entityData.set(VISUAL_DIRECTION_Z, (float) directionToPlayer.z);
    }

    public Vec3 getVisualDirection() {
        return new Vec3(
                entityData.get(VISUAL_DIRECTION_X),
                entityData.get(VISUAL_DIRECTION_Y),
                entityData.get(VISUAL_DIRECTION_Z)
        );
    }

    public void setFiredHand(InteractionHand hand) {
        entityData.set(FIRED_FROM_OFFHAND, hand == InteractionHand.OFF_HAND);
        if (getOwner() instanceof LivingEntity livingOwner) {
            entityData.set(OWNER_ENTITY_ID, livingOwner.getId());
            HookshotLaunchPosition.moveToFiringHand(this, livingOwner, hand, maxRange);
            captureVisualDirection();
            launchPosition = livingOwner.getEyePosition();
        }
    }

    private boolean isBeyondMaxRange(Vec3 location) {
        return launchPosition != null && launchPosition.distanceToSqr(location) > maxRange * maxRange;
    }

    public Player getHookOwner() {
        if (getOwner() instanceof Player player) {
            return player;
        }
        Entity syncedOwner = level().getEntity(entityData.get(OWNER_ENTITY_ID));
        return syncedOwner instanceof Player player ? player : null;
    }

    public InteractionHand getFiredHand() {
        return entityData.get(FIRED_FROM_OFFHAND) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    public boolean isRetrieving() {
        return entityData.get(RETRIEVING);
    }

    public boolean isPullingPlayer() {
        return level().isClientSide ? entityData.get(PULLING) : isPulling;
    }

    public boolean isLatchedToBlock() {
        return isPullingPlayer()
                && !isRetrieving()
                && entityData.get(HOOKED_ENTITY_ID) == 0;
    }

    private void setPulling(boolean pulling) {
        if (pulling && !isPulling && owner != null) {
            advancementPullStart = owner.position();
        } else if (!pulling) {
            advancementPullStart = null;
        }
        isPulling = pulling;
        entityData.set(PULLING, pulling);
        if (owner != null) {
            if (pulling) {
                HookshotPullPoseEvents.apply(owner);
            } else {
                HookshotPullPoseEvents.release(owner);
            }
        }
    }

    private void checkPullTravelAdvancement() {
        if (hookedEntity == null && advancementPullStart != null
                && owner instanceof ServerPlayer serverPlayer
                && owner.position().distanceToSqr(advancementPullStart) >= ADVANCEMENT_PULL_DISTANCE_SQR) {
            ModAdvancementHelper.award(serverPlayer, "hooked_on_travel", "thirty_blocks");
            advancementPullStart = null;
        }
    }

    public float getReturnArrivalProgress(float partialTick) {
        int arrivalTicks = entityData.get(RETURN_ARRIVAL_TICKS);
        if (!isRetrieving() || arrivalTicks <= 0) {
            return 0.0F;
        }
        if (RETURN_FINISH_DELAY_TICKS <= 1) {
            return 1.0F;
        }
        return Mth.clamp((arrivalTicks - 1.0F + partialTick) / (RETURN_FINISH_DELAY_TICKS - 1.0F), 0.0F, 1.0F);
    }

    //Disable ChangeDimensions.
    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    //Make the entity appear in the level.
    @Override
    @Nonnull
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static EntityType<LongshotEntity> createEntityType()
    {
        return EntityType.Builder.<LongshotEntity>of(LongshotEntity::new, MobCategory.MISC)
                .sized(0.2F, 0.2F)
                .clientTrackingRange(4)
                .updateInterval(NETWORK_UPDATE_INTERVAL_TICKS)
                .build(SupersLegendMain.MOD_ID + ":longshot");
    }
}
