package com.superworldsun.superslegend.entities.projectiles.boomerang;

import com.superworldsun.superslegend.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractBoomerangEntity extends Entity {
    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(AbstractBoomerangEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_ID = SynchedEntityData.defineId(AbstractBoomerangEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    protected final List<ItemEntity> pickedItems = new ArrayList<>();
    protected ItemStack selfStack;
    protected BlockPos activatedPos;
    protected boolean bouncing;
    protected boolean turningBack;
    protected double bounceFactor;
    protected float prevRotation;
    protected int turnBackTimer;
    protected float getDefaultSpeed;

    public AbstractBoomerangEntity(EntityType<? extends AbstractBoomerangEntity> type, Level level) {
        super(type, level);
        bounceFactor = 0.85D;
        turningBack = false;
        bouncing = false;
        turnBackTimer = 30;
        getDefaultSpeed = 0.5f;
    }

    public AbstractBoomerangEntity(EntityType<? extends AbstractBoomerangEntity> type, Player owner, ItemStack stack) {
        this(type, owner.level());
        this.selfStack = stack;
        setRot(owner.yRotO, owner.xRotO);
        setDeltaMovement(owner.getLookAngle().x * getDefaultSpeed, owner.getLookAngle().y * getDefaultSpeed, owner.getLookAngle().z * getDefaultSpeed);
        setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        xo = getX();
        yo = getY();
        zo = getZ();
        setOwner(owner);
    }

    @Override
    @Deprecated
    public void tick() {
        Player owner = getOwner();
        if (owner == null || owner.isDeadOrDying()) {
            kill();
            return;
        }
        if (tickCount % 4 == 0) {
            level().playSound(null, getX(), getY(), getZ(), getFlyLoopSound(), SoundSource.PLAYERS, 1f, 1f);
        }
        updateCollision();
        updateMotion();
        determineRotation();
        prevRotation = getRotation();
        setRotation(Mth.wrapDegrees(getRotation() + 36F));
        updateCarriedItems();
        super.tick();
    }

    protected void updateCarriedItems() {
        for (ItemEntity item : pickedItems) {
            item.setDeltaMovement(0, 0, 0);
            if (item.isAlive()) {
                item.setPos(this.position());
            }
        }
    }

    protected void updateCollision() {
        BlockHitResult hitResult = getBlockHit();
        if (hitResult.getType() == BlockHitResult.Type.BLOCK) {
            onBlockHit(hitResult);
        }
        AABB aoe = getBoundingBox().inflate(0.5D);
        List<Entity> list = level().getEntities(this, aoe);
        for (Entity entity : list) {
            onEntityHit(entity);
        }
    }

    private void updateMotion() {
        Player owner = getOwner();
        if (!turningBack) {
            Vec3 movement = getDeltaMovement();
            move(MoverType.SELF, movement);
            Vec3 newMotion = this.getDeltaMovement();
            double newX = newMotion.x;
            double newY = newMotion.y;
            double newZ = newMotion.z;
            boolean changedMotion = false;
            if (newMotion.x != movement.x) {
                newX = -movement.x;
                changedMotion = true;
            }
            if (newMotion.y != movement.y) {
                newY = -movement.y;
                changedMotion = true;
            }
            if (newMotion.z != movement.z) {
                newZ = -movement.z;
                changedMotion = true;
            }
            if (changedMotion) {
                bouncing = true;
                setDeltaMovement(new Vec3(newX, newY, newZ).multiply(bounceFactor, bounceFactor, bounceFactor));
            }
            if (owner != null) {
                this.beforeTurnAround(owner);
                if (turnBackTimer-- <= 0) {
                    turningBack = true;
                }
            }

        } else {
            if (owner == null) return;
            double x = owner.getX() - getX();
            double y = owner.getEyeY() - 0.1 - getY();
            double z = owner.getZ() - getZ();
            double distanceToOwner = Math.sqrt(x * x + y * y + z * z);
            if (distanceToOwner < 1.5D) {
                onReturnToOwner();
                return;
            }
            double motionX = (0.9D * x) / distanceToOwner;
            double motionY = (0.5D * y) / distanceToOwner;
            double motionZ = (0.9D * z) / distanceToOwner;
            setDeltaMovement(motionX, motionY, motionZ);
            double xPos = getX() + getDeltaMovement().x;
            double yPos = getY() + getDeltaMovement().y;
            double zPos = getZ() + getDeltaMovement().z;
            setPos(xPos, yPos, zPos);
        }
    }

    private void onBlockHit(BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level().getBlockState(pos);
        if (canBreakBlock(state.getBlock())) {
            level().destroyBlock(pos, true);
        }
        if (canActivateBlock(state.getBlock(), pos)) {
            activateBlock(state, hitResult);
        }
    }

    protected boolean canBreakBlock(Block block) {
        return block.defaultDestroyTime() == 0f && Config.doBoomerangsBreakSoftBlocks();
    }

    protected boolean canActivateBlock(Block block, BlockPos pos) {
        if (activatedPos != null && activatedPos.equals(pos)) return false;
        if (block instanceof LeverBlock && Config.doBoomerangsActivateLevers()) return true;
        if (block instanceof ButtonBlock && Config.doBoomerangsActivateButtons()) return true;
        if (block instanceof PressurePlateBlock && Config.doBoomerangsActivatePressurePlates()) return true;
        return block instanceof TripWireBlock && Config.doBoomerangsActivateTripWires();
    }

    protected void activateBlock(BlockState state, BlockHitResult hitResult) {
        if (turnBackTimer > 0 && Config.doBoomerangsTurnBackOnHit()) {
            turnBackTimer = 0;
        }
        if (getOwner() == null) return;
        state.use(level(), getOwner(), InteractionHand.MAIN_HAND, hitResult);
        activatedPos = hitResult.getBlockPos();
    }

    @NotNull
    private BlockHitResult getBlockHit() {
        Vec3 destination = position().add(getDeltaMovement());
        ClipContext context = new ClipContext(position(), destination, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null);
        return level().clip(context);
    }

    protected void beforeTurnAround(Player player) {
        // NO-OP
    }

    public void onEntityHit(Entity entity) {
        Player owner = getOwner();
        if (entity instanceof ItemEntity item) {
            pickedItems.add(item);
        } else if (entity instanceof LivingEntity && entity != owner) {
            entity.hurt(getDamageSource(owner), getDamage());
        } else {
            return;
        }
        if (turnBackTimer > 0 && Config.doBoomerangsTurnBackOnHit()) {
            turnBackTimer = 0;
        }
    }

    public void onReturnToOwner() {
        Player owner = getOwner();
        if (owner != null) {
            if (selfStack != null) {
                if (owner.getMainHandItem().isEmpty()) {
                    owner.setItemInHand(InteractionHand.MAIN_HAND, selfStack);
                    selfStack = null;
                } else if (owner.getOffhandItem().isEmpty()) {
                    owner.setItemInHand(InteractionHand.OFF_HAND, selfStack);
                    selfStack = null;
                }
            }
        }
        kill();
    }

    @Override
    public void kill() {
        if (selfStack != null && !this.level().isClientSide) {
            spawnAtLocation(selfStack);
        }
        super.kill();
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(ROTATION, 0f);
        getEntityData().define(OWNER_ID, Optional.empty());
    }

    public float getRotation() {
        return getEntityData().get(ROTATION);
    }

    public void setRotation(float rotation) {
        getEntityData().set(ROTATION, rotation);
    }

    @Nullable
    public Player getOwner() {
        UUID ownerId = getOwnerId();
        if (ownerId == null) return null;
        return level().getPlayerByUUID(ownerId);
    }

    public void setOwner(Player player) {
        getEntityData().set(OWNER_ID, Optional.of(player.getUUID()));
    }

    protected @Nullable UUID getOwnerId() {
        return getEntityData().get(OWNER_ID).orElse(null);
    }

    public void setOwnerId(@Nullable UUID id) {
        getEntityData().set(OWNER_ID, Optional.ofNullable(id));
    }

    public void determineRotation() {
        Vec3 motion = getDeltaMovement();
        yRotO = -57.29578f * (float) Mth.atan2(motion.x, motion.z);
        float d1 = Mth.sqrt((float) (motion.z * motion.z + motion.x * motion.x));
        xRotO = -57.29578f * (float) Mth.atan2(motion.y, d1);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.bouncing = tag.getBoolean("IsBouncing");
        this.bounceFactor = tag.getDouble("BounceFactor");
        this.prevRotation = tag.getFloat("PrevBoomerangRotation");
        this.setRotation(tag.getFloat("BoomerangRotation"));
        this.turningBack = tag.getBoolean("TurningAround");
        this.turnBackTimer = tag.getInt("TimeBeforeTurnAround");
        if (tag.contains("xPos") && tag.contains("yPos") && tag.contains("zPos")) {
            this.activatedPos = new BlockPos(tag.getInt("xPos"), tag.getInt("yPos"), tag.getInt("zPos"));
        }

        if (tag.contains("OwnerId", Tag.TAG_STRING)) {
            this.setOwnerId(UUID.fromString(tag.getString("OwnerId")));
        }

        this.selfStack = ItemStack.of(tag.getCompound("SelfStack"));

//        ListTag itemsGathered = tag.getList("ItemsPickedUp", Constants.NBT.TAG_COMPOUND);
//        for (int i = 0; i < itemsGathered.size(); i++) {
//            CompoundTag tag = itemsGathered.getCompound(Integer.parseInt(String.valueOf(i)));
//            ItemEntity item = new ItemEntity(level(), 0, 0, 0);
//            item.readAdditionalSaveData(tag);
//            this.itemsPickedUp.add(item);
//        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("IsBouncing", bouncing);
        compound.putDouble("BounceFactor", bounceFactor);
        compound.putFloat("PrevBoomerangRotation", prevRotation);
        compound.putFloat("BoomerangRotation", this.getRotation());
        compound.putBoolean("TurningAround", turningBack);
        compound.putInt("TimeBeforeTurnAround", turnBackTimer);
        if (activatedPos != null) {
            compound.putInt("xPos", activatedPos.getX());
            compound.putInt("yPos", activatedPos.getY());
            compound.putInt("zPos", activatedPos.getZ());
        }
        if (getOwnerId() == null) {
            compound.putString("OwnerId", "");
        } else {
            compound.putString("OwnerId", getOwnerId().toString());
        }
        CompoundTag selfStackTag = new CompoundTag();
        selfStack.save(selfStackTag);
        compound.put("SelfStack", selfStackTag);

//        ListTag pickedItemsTag = new ListTag();
//        for (ItemEntity pickedItem : pickedItems) {
//            if (pickedItem != null) {
//                CompoundTag tag = new CompoundTag();
//                pickedItem.addAdditionalSaveData(compound);
//                pickedItemsTag.add(tag);
//            }
//        }
//
//        compound.put("ItemsPickedUp", pickedItemsTag);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    public DamageSource getDamageSource(Player owner) {
        return level().damageSources().mobProjectile(this, owner);
    }

    protected abstract SoundEvent getFlyLoopSound();

    protected abstract float getDamage();
}
