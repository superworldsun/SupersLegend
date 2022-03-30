package com.superworldsun.superslegend.entities.projectiles.boomerang;


import com.superworldsun.superslegend.config.SupersLegendConfig;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.*;

public abstract class MagicBoomerangEntity extends Entity {

    private BlockPos activatedPos;
    protected boolean isBouncing;
    private double bounceFactor;
    private float prevBoomerangRotation;
    private boolean turningAround;
    protected int timeBeforeTurnAround;
    List<ItemEntity> itemsPickedUp;
    private ItemStack selfStack;
    private Hand hand;
    private static final DataParameter<Float> ROTATION = EntityDataManager.defineId(MagicBoomerangEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Optional<UUID>> RETURN_UNIQUE_ID = EntityDataManager.defineId(MagicBoomerangEntity.class, DataSerializers.OPTIONAL_UUID);

    public MagicBoomerangEntity(EntityType<MagicBoomerangEntity> type, World world) {
        super(type, world);
        this.bounceFactor = 0.84999999999999998D;
        this.turningAround = false;
        this.timeBeforeTurnAround = 30;
        this.itemsPickedUp = new ArrayList<ItemEntity>();
        this.hand = Hand.MAIN_HAND;
    }

    public MagicBoomerangEntity(EntityType<MagicBoomerangEntity> type, World worldIn, PlayerEntity entity, ItemStack itemstack, Hand hand) {
        this(type, worldIn);
        this.selfStack = itemstack;
        this.setRot(entity.yRot, entity.xRot);
        double x = -MathHelper.sin((entity.yRot * 3.141593F) / 180F);
        double z = MathHelper.cos((entity.yRot * 3.141593F) / 180F);

        double motionX = +0.5D * x * (double) MathHelper.cos((entity.xRot / 180F) * 3.141593F);
        double motionY = -0.5D * (double) MathHelper.sin((entity.xRot / 180F) * 3.141593F);
        double motionZ = +0.5D * z * (double) MathHelper.cos((entity.xRot / 180F) * 3.141593F);
        this.setDeltaMovement(new Vector3d(+motionX, motionY, +motionZ));
        setPos(entity.getX(), this.getReturnEntityY(entity), entity.getZ());
        xo = getX();
        yo = getY();
        zo = getZ();
        this.isBouncing = false;
        this.turningAround = false;
        this.hand = hand;
        this.setReturnToId(entity.getUUID());
    }

    public double getReturnEntityY(PlayerEntity entity) {
        return entity.getY() + entity.getEyeHeight() - 0.10000000149011612D;
    }

    @Override
    @Deprecated
    public void tick() {
        PlayerEntity player = this.getReturnTo();

        //BlockPos currentPos = this.getPosition();
        //this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_FLY_LOOP, SoundCategory.PLAYERS, 1f, 1f);

        if(this.tickCount % 11 == 0)
        {
            BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_FLY_LOOP.get(), SoundCategory.PLAYERS, 0.4f, 1.0f);
        }

        Vector3d vec3d1 = this.position();
        Vector3d vec3d = this.position().add(this.getDeltaMovement());
        RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vec3d1, vec3d, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null));

        if (raytraceresult != null) {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos pos = new BlockPos(raytraceresult.getLocation());
                BlockState state = level.getBlockState(pos);

                if (state.getMaterial() == Material.PLANT && SupersLegendConfig.getInstance().breaksFlowers.get()) {
                    level.destroyBlock(pos, true);
                }
                /*if ((state.getBlock() instanceof GrassBlock) && SupersLegendConfig.getInstance().breaksGrass.get()) {
                    world.destroyBlock(pos, true);
                }*/
                if ((state.getMaterial() == Material.REPLACEABLE_PLANT) && SupersLegendConfig.getInstance().breaksTallGrass.get()) {
                    level.destroyBlock(pos, true);
                }
                if ((state.getBlock() instanceof TorchBlock) && SupersLegendConfig.getInstance().breaksTorches.get()) {
                    level.destroyBlock(pos, true);
                }
                if ((state.getBlock() instanceof LeverBlock) && SupersLegendConfig.getInstance().activatesLevers.get()) {
                    if (timeBeforeTurnAround > 0 && SupersLegendConfig.getInstance().turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().use(state, level, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof AbstractButtonBlock) && SupersLegendConfig.getInstance().activatesButtons.get()) {
                    if (timeBeforeTurnAround > 0 && SupersLegendConfig.getInstance().turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().use(state, level, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof PressurePlateBlock) && SupersLegendConfig.getInstance().activatesPressurePlates.get()) {
                    if (timeBeforeTurnAround > 0 && SupersLegendConfig.getInstance().turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().use(state, level, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
                if ((state.getBlock() instanceof TripWireBlock) && SupersLegendConfig.getInstance().activatesTripWire.get()) {
                    if (timeBeforeTurnAround > 0 && SupersLegendConfig.getInstance().turnAroundButton.get()) {
                        timeBeforeTurnAround = 0;
                    }
                    if (activatedPos == null || !activatedPos.equals(pos)) {
                        activatedPos = pos;
                        state.getBlock().use(state, level, pos, player, Hand.MAIN_HAND, (BlockRayTraceResult) raytraceresult.hitInfo);
                    }
                }
            }
        }

        if (!turningAround) {
            Vector3d motionBefore = this.getDeltaMovement();
            this.move(MoverType.SELF, motionBefore);

            Vector3d motionAfter = this.getDeltaMovement();
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
                this.setDeltaMovement(new Vector3d(newX, newY, newZ).multiply(bounceFactor, bounceFactor, bounceFactor));
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
        List<Entity> list = level.getEntities(this, this.getBoundingBox().expandTowards(0.5D, 0.5D, 0.5D));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity instanceof ItemEntity) {
                itemsPickedUp.add((ItemEntity) entity);
                if (timeBeforeTurnAround > 0 && SupersLegendConfig.getInstance().turnAroundItem.get()) {
                    timeBeforeTurnAround = 0;
                }
                continue;
            }
            if (!(entity instanceof LivingEntity) || entity == player) {
                continue;
            }

            this.onEntityHit(entity, player);

            if (timeBeforeTurnAround > 0 && SupersLegendConfig.getInstance().turnAroundMob.get()) {
                timeBeforeTurnAround = 0;
            }
        }
        Iterator<ItemEntity> iterator = itemsPickedUp.iterator();
        while (iterator.hasNext()) {
            ItemEntity item = iterator.next();
            item.setDeltaMovement(0, 0, 0);
            if (item.isAlive()) {
                Vector3d pos = this.position();
                item.setPos(pos.x, pos.y, pos.z);
            }
        }


        super.tick();
    }

    public void beforeTurnAround(PlayerEntity player) {
        // NO-OP
    }

    public void onEntityHit(Entity hitEntity, PlayerEntity player) {
        hitEntity.hurt(causeNewDamage(this, player), getDamage(hitEntity, player));
    }

    protected abstract int getDamage(Entity hitEntity, PlayerEntity player);

    public abstract DamageSource causeNewDamage(MagicBoomerangEntity entityboomerang, Entity entity);

    public void setEntityDead() {
        if (this.getReturnTo() != null) {
            if (selfStack != null) {
                if (!this.getReturnTo() .getMainHandItem().getItem().equals(Items.AIR)) {
                    if (!this.getReturnTo() .getOffhandItem().getItem().equals(Items.AIR)) {
                        this.getReturnTo() .drop(selfStack,true);
                    }
                    else {
                        this.getReturnTo() .setItemInHand(Hand.OFF_HAND,selfStack);
                    }
                }
                else {
                    this.getReturnTo() .setItemInHand(Hand.MAIN_HAND,selfStack);
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
    public PlayerEntity getReturnTo() {
        try {
            UUID uuid = this.getReturnToId();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public boolean isReturnTo(LivingEntity entityIn) {
        return entityIn == this.getReturnTo();
    }

    public void determineRotation() {
        Vector3d motion = this.getDeltaMovement();

        yRot = -57.29578F * (float) Math.atan2(motion.x, motion.z);
        double d1 = Math.sqrt(motion.z * motion.z + motion.x * motion.x);
        xRot = -57.29578F * (float) Math.atan2(motion.y, d1);
    }

    @Override
    public void load(CompoundNBT compound) {
        super.load(compound);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
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

        ListNBT itemsGathered = compound.getList("ItemsPickedUp", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsGathered.size(); i++) {
            CompoundNBT tag = itemsGathered.getCompound(i);
            ItemEntity item = new ItemEntity(level, 0, 0, 0);
            item.readAdditionalSaveData(tag);
            this.itemsPickedUp.add(item);
        }

        this.hand = Hand.valueOf(compound.getString("hand"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
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

        CompoundNBT selfStackTag = new CompoundNBT();
        selfStack.save(selfStackTag);
        compound.put("SelfStack", selfStackTag);

        ListNBT itemsGathered = new ListNBT();
        for (int i = 0; i < itemsPickedUp.size(); i++) {
            if (itemsPickedUp.get(i) != null) {
                CompoundNBT tag = new CompoundNBT();
                itemsPickedUp.get(i).addAdditionalSaveData(compound);
                itemsGathered.add(tag);
            }
        }

        compound.put("ItemsPickedUp", itemsGathered);
        compound.putString("hand", this.hand.toString());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }
}
