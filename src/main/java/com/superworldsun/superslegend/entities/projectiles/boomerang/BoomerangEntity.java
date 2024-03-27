package com.superworldsun.superslegend.entities.projectiles.boomerang;

import com.mojang.math.Constants;
import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.shadowed.eliotlash.mclib.utils.MathHelper;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BoomerangEntity extends Entity {

    private BlockPos activatedPos;
    protected boolean isBouncing;
    private double bounceFactor;
    private float prevBoomerangRotation;
    private boolean turningAround;
    protected int timeBeforeTurnAround;
    List<ItemEntity> itemsPickedUp;
    private ItemStack selfStack;
    private InteractionHand hand;
    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(BoomerangEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Optional<UUID>> RETURN_UNIQUE_ID = SynchedEntityData.defineId(BoomerangEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public BoomerangEntity(EntityType<BoomerangEntity> type, Level level) {
        super(type, level);
        this.bounceFactor = 0.84999999999999998D;
        this.turningAround = false;
        this.timeBeforeTurnAround = 30;
        this.itemsPickedUp = new ArrayList<ItemEntity>();
        this.hand = InteractionHand.MAIN_HAND;
    }

    public BoomerangEntity(EntityType<BoomerangEntity> type, Level level, Player entity, ItemStack itemstack, InteractionHand hand) {
        this(type, level);
        this.selfStack = itemstack;
        this.setRot(entity.yRotO, entity.xRotO);
        double x = -MathHelper.wrapDegrees((entity.yRotO * 3.141593F) / 180F);
        double z = MathHelper.wrapDegrees((entity.yRotO * 3.141593F) / 180F);

        double motionX = +0.5D * x * (double) MathHelper.wrapDegrees((entity.xRotO / 180F) * 3.141593F);
        double motionY = -0.5D * (double) MathHelper.wrapDegrees((entity.xRotO / 180F) * 3.141593F);
        double motionZ = +0.5D * z * (double) MathHelper.wrapDegrees((entity.xRotO / 180F) * 3.141593F);
        this.setDeltaMovement(new Vec3(+motionX, motionY, +motionZ));
        setPos(entity.getX(), this.getReturnEntityY(entity), entity.getZ());
        xo = getX();
        yo = getY();
        zo = getZ();
        this.isBouncing = false;
        this.turningAround = false;
        this.hand = hand;
        this.setReturnToId(entity.getUUID());
    }

    public double getReturnEntityY(Player entity) {
        return entity.getY() + entity.getEyeHeight() - 0.10000000149011612D;
    }

    public void setEntityDeadWithPlayerDeadOrMissing() {
        if (selfStack != null && !this.level().isClientSide) {
            ItemEntity itementity = new ItemEntity(level(), this.getX(), this.getY(), this.getZ(), selfStack);
            itementity.setDefaultPickUpDelay();
            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level().random.nextFloat() - level().random.nextFloat()) * 0.1F), (double) (level().random.nextFloat() * 0.05F), (double) ((level().random.nextFloat() - level().random.nextFloat()) * 0.1F)));
            level().addFreshEntity(itementity);
        }
        super.removeAfterChangingDimensions();
    }

    @Override
    @Deprecated
    public void tick() {
        Player player = this.getReturnTo();

        //BlockPos currentPos = this.getPosition();
        //this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_FLY_LOOP, SoundCategory.PLAYERS, 1f, 1f);

        if(player == null){
            setEntityDeadWithPlayerDeadOrMissing();
        }

        if(player != null && player.isDeadOrDying()){
            setEntityDeadWithPlayerDeadOrMissing();
        }

        if(this.tickCount % 4 == 0)
        {
            BlockPos currentPos = this.blockPosition();
            this.level().playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ALTTP_BOOMERANG_FLY_LOOP.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        //TODO FINISH BOOMERANG
        /*Vec3 vec3d1 = this.position();
        Vec3 vec3d = this.position().add(this.getDeltaMovement());
        BlockHitResult raytraceresult = this.level().clip(new BlockHitResult(vec3d1, vec3d, HitResult.Type.BLOCK, HitResult.Type.MISS, null));

        if (raytraceresult != null) {
            if (raytraceresult.getType() == BlockHitResult.Type.BLOCK) {
                BlockPos pos = new BlockPos(raytraceresult.getLocation());
                BlockState state = level().getBlockState(pos);

                /*if (state.getMaterial() == Material.PLANT && Config.getInstance().breaksFlowers.get()) {
                    level().destroyBlock(pos, true);
                }*/

                /*if ((state.getBlock() instanceof GrassBlock) && Config.getInstance().breaksGrass.get()) {
                    world.destroyBlock(pos, true);
                }*/

                /*if ((state.getMaterial() == Material.REPLACEABLE_PLANT) && Config.getInstance().breaksTallGrass.get()) {
                    level().destroyBlock(pos, true);
                }
                if ((state.getBlock() instanceof TorchBlock) && Config.getInstance().breaksTorches.get()) {
                    level().destroyBlock(pos, true);
                }
                if ((state.getBlock() instanceof LeverBlock) && Config.getInstance().activatesLevers.get()) {
                    if (timeBeforeTurnAround > 0 && Config.getInstance().turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().use(state, level(), pos, player, InteractionHand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof AbstractButtonBlock) && Config.getInstance().activatesButtons.get()) {
                    if (timeBeforeTurnAround > 0 && Config.getInstance().turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().use(state, level(), pos, player, InteractionHand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof PressurePlateBlock) && Config.getInstance().activatesPressurePlates.get()) {
                    if (timeBeforeTurnAround > 0 && Config.getInstance().turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().use(state, level(), pos, player, InteractionHand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof TripWireBlock) && Config.getInstance().activatesTripWire.get()) {
                    if (timeBeforeTurnAround > 0 && Config.getInstance().turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().use(state, level(), pos, player, InteractionHand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
            }
        }*/

        if (!turningAround) {
            Vec3 motionBefore = this.getDeltaMovement();
            this.move(MoverType.SELF, motionBefore);

            Vec3 motionAfter = this.getDeltaMovement();
            double newX = motionAfter.x;
            double newY = motionAfter.y;
            double newZ = motionAfter.z;

            boolean flag = false;
            if (motionAfter.x != motionBefore.x) {
                newX = -motionBefore.x;
                flag = true;
            }
            if (motionAfter.y != motionBefore.y) {
                newY = -motionBefore.y;
                flag = true;
            }
            if (motionAfter.z != motionBefore.z) {
                newZ = -motionBefore.z;
                flag = true;
            }
            if (flag) {
                isBouncing = true;
                this.setDeltaMovement(new Vec3(newX, newY, newZ).multiply(bounceFactor, bounceFactor, bounceFactor));
            }
            if (player != null) {
                this.beforeTurnAround(player);

                if (timeBeforeTurnAround-- <= 0) {
                    turningAround = true;
                }
            }
        } else if (player != null) {
            double x = player.getX() - this.getX();
            double y = this.getReturnEntityY(player) - this.getY();
            double z = player.getZ() - this.getZ();
            double d = Math.sqrt(x * x + y * y + z * z);
            if (d < 1.5D) {
                setEntityDead();
            }
            this.setDeltaMovement((0.9D * x) / d, (0.5D * y) / d, (0.9D * z) / d);
            this.setPos(this.getX() + this.getDeltaMovement().x, this.getY() + this.getDeltaMovement().y, this.getZ() + this.getDeltaMovement().z);
        }

        determineRotation();
        prevBoomerangRotation = getBoomerangRotation();
        for (this.setBoomerangRotation(this.getBoomerangRotation() + 36F); this.getBoomerangRotation() > 360F; this.setBoomerangRotation(this.getBoomerangRotation() - 360F)) {
        }
        //TODO FINISH BOOMERANG
        /*List<Entity> list = level().getEntities(this, this.getBoundingBox().expandTowards(0.5D, 0.5D, 0.5D));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity instanceof ItemEntity) {
                itemsPickedUp.add((ItemEntity) entity);
                if (timeBeforeTurnAround > 0 && Config.turnItemAround) {
                    timeBeforeTurnAround = 0;
                }
                continue;
            }
            if (!(entity instanceof LivingEntity) || entity == player) {
                continue;
            }

            this.onEntityHit(entity, player);

            if (timeBeforeTurnAround > 0 && Config.turnItemAround) {
                timeBeforeTurnAround = 0;
            }
        }
        Iterator<ItemEntity> iterator = itemsPickedUp.iterator();
        while (iterator.hasNext()) {
            ItemEntity item = iterator.next();
            item.setDeltaMovement(0, 0, 0);
            if (item.isAlive()) {
                Vec3 pos = this.position();
                item.setPos(pos.x, pos.y, pos.z);
            }
        }*/
        super.tick();
    }

    public void beforeTurnAround(Player player) {
        // NO-OP
    }

    public void onEntityHit(Entity hitEntity, Player player) {
        hitEntity.hurt(causeNewDamage(this, player), getDamage(hitEntity, player));
    }

    protected abstract int getDamage(Entity hitEntity, Player player);

    public abstract DamageSource causeNewDamage(BoomerangEntity entityboomerang, Entity entity);

    public void setEntityDead() {
        if (this.getReturnTo() != null) {
            if (selfStack != null) {
                if (!this.getReturnTo() .getMainHandItem().getItem().equals(Items.AIR)) {
                    if (!this.getReturnTo() .getOffhandItem().getItem().equals(Items.AIR)) {
                        this.getReturnTo() .drop(selfStack,true);
                    }
                    else {
                        this.getReturnTo() .setItemInHand(InteractionHand.OFF_HAND,selfStack);
                    }
                }
                else {
                    this.getReturnTo() .setItemInHand(InteractionHand.MAIN_HAND,selfStack);
                }
            }
        }
        super.removeAfterChangingDimensions();
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(ROTATION, 0.0F);
        this.getEntityData().define(RETURN_UNIQUE_ID, Optional.empty());
    }

    public float getBoomerangRotation() {
        return this.getEntityData().get(ROTATION);
    }

    public void setBoomerangRotation(float rotationIn) {
        this.getEntityData().set(ROTATION, rotationIn);
    }

    @Nullable
    public UUID getReturnToId() {
        return this.entityData.get(RETURN_UNIQUE_ID).orElse(null);
    }

    public void setReturnToId(@Nullable UUID uuid) {
        this.entityData.set(RETURN_UNIQUE_ID, Optional.ofNullable(uuid));
    }

    @Nullable
    public Player getReturnTo() {
        try {
            UUID uuid = this.getReturnToId();
            return uuid == null ? null : this.level().getPlayerByUUID(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean isReturnTo(LivingEntity entityIn) {
        return entityIn == this.getReturnTo();
    }

    public void determineRotation() {
        Vec3 motion = this.getDeltaMovement();

        yRotO = -57.29578F * (float) Math.atan2(motion.x, motion.z);
        double d1 = Math.sqrt(motion.z * motion.z + motion.x * motion.x);
        xRotO = -57.29578F * (float) Math.atan2(motion.y, d1);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.isBouncing = compound.getBoolean("IsBouncing");
        this.bounceFactor = compound.getDouble("BounceFactor");
        this.prevBoomerangRotation = compound.getFloat("PrevBoomerangRotation");
        this.setBoomerangRotation(compound.getFloat("BoomerangRotation"));
        this.turningAround = compound.getBoolean("TurningAround");
        this.timeBeforeTurnAround = compound.getInt("TimeBeforeTurnAround");
        if (compound.contains("xPos") && compound.contains("yPos") && compound.contains("zPos"))
            this.activatedPos = new BlockPos(compound.getInt("xPos"), compound.getInt("yPos"), compound.getInt("zPos"));

        if (compound.contains("ReturnToUUID", 8)) {
            try {
                this.setReturnToId(UUID.fromString(compound.getString("ReturnToUUID")));
            } catch (Throwable t) {
                // NO-OP
            }
        }

        this.selfStack = ItemStack.of(compound.getCompound("SelfStack"));

        //TODO FINISH BOOMERANG
        /*ListTag itemsGathered = compound.getList("ItemsPickedUp", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsGathered.size(); i++) {
            CompoundTag tag = itemsGathered.getCompound(Integer.parseInt(String.valueOf(i)));
            ItemEntity item = new ItemEntity(level(), 0, 0, 0);
            item.readAdditionalSaveData(tag);
            this.itemsPickedUp.add(item);
        }*/

        this.hand = InteractionHand.valueOf(compound.getString("hand"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("IsBouncing", isBouncing);
        compound.putDouble("BounceFactor", bounceFactor);
        compound.putFloat("PrevBoomerangRotation", prevBoomerangRotation);
        compound.putFloat("BoomerangRotation", this.getBoomerangRotation());
        compound.putBoolean("TurningAround", turningAround);
        compound.putInt("TimeBeforeTurnAround", timeBeforeTurnAround);
        if (activatedPos != null) {
            compound.putInt("xPos", activatedPos.getX());
            compound.putInt("yPos", activatedPos.getY());
            compound.putInt("zPos", activatedPos.getZ());
        }

        if (this.getReturnToId() == null) {
            compound.putString("ReturnToUUID", "");
        } else {
            compound.putString("ReturnToUUID", this.getReturnToId().toString());
        }

        CompoundTag selfStackTag = new CompoundTag();
        selfStack.save(selfStackTag);
        compound.put("SelfStack", selfStackTag);

        ListTag itemsGathered = new ListTag();
        for (int i = 0; i < itemsPickedUp.size(); i++) {
            if (itemsPickedUp.get(i) != null) {
                CompoundTag tag = new CompoundTag();
                itemsPickedUp.get(i).addAdditionalSaveData(compound);
                itemsGathered.equals(tag);
            }
        }

        compound.put("ItemsPickedUp", itemsGathered);
        compound.putString("hand", this.hand.toString());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }
}
