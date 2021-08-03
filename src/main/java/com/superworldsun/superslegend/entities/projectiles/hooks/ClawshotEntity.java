package com.superworldsun.superslegend.entities.projectiles.hooks;


import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.items.ClawshotItem;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.util.SupersLegendKeyboardUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.List;

import static com.superworldsun.superslegend.items.ClawshotItem.spriteC;
import static com.superworldsun.superslegend.util.HookBlockList.hookableBlocks;
import static com.superworldsun.superslegend.util.HookBlockList.setHookableBlocks;

public class ClawshotEntity extends AbstractArrowEntity {

    /**
     * useBlockList serves to enable or disable the block list that can be hooked on the hook.
     * isPulling activates the player's movement. Do not touch this value.
     */
    private static final DataParameter<Integer> HOOKED_ENTITY_ID = EntityDataManager.defineId(ClawshotEntity.class, DataSerializers.INT);
    boolean useBlockList = true;
    private double maxRange = 0D;
    private double maxSpeed = 0D;
    private boolean isPulling = false;
    private PlayerEntity owner;
    private Entity hookedEntity;
    private ItemStack stack;


    public ClawshotEntity(EntityType<? extends AbstractArrowEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        this.setNoGravity(true);
        this.setBaseDamage(0);

    }

    public ClawshotEntity(EntityType<ClawshotEntity> clawshotEntityEntityType, World world) {
        super(EntityTypeInit.CLAWSHOT_ENTITY.get(), world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HOOKED_ENTITY_ID, 0);
    }

    /**
     * This is where everything related to movement happens.
     */
    @Override
    public void tick() {
        super.tick();

        if (getOwner() instanceof PlayerEntity) {
            owner = (PlayerEntity) getOwner();

            if (isPulling && tickCount % 2 == 0) { //This is the sound that sounds when the hook is moving you.
                //level.playSound(null, owner.blockPosition(), SoundEvents.AXE_STRIP, SoundCategory.PLAYERS, 1F, 1F);
            }
            if (!level.isClientSide) {
                if (this.hookedEntity != null) { //In case the mob you are hooked to dies while you go towards it ..
                    if (this.hookedEntity.removed) {
                        this.hookedEntity = null;
                        onRemovedFromWorld();
                    } else {
                        this.absMoveTo(this.hookedEntity.getX(), this.hookedEntity.getY(0.8D), this.hookedEntity.getZ());
                    }
                }

                if (owner != null) { //Reasons to remove the hook.
                    if (owner.isDeadOrDying() || !HookModel.get(owner).getHasHook() ||
                            !HookModel.get(owner).getHasHook() ||
                            owner.distanceTo(this) > maxRange ||
                            !(owner.getMainHandItem().getItem() instanceof ClawshotItem ||
                                    owner.getOffhandItem().getItem() instanceof ClawshotItem) ||
                            !HookModel.get(owner).getHasHook()) {

                        spriteC = false;
                        kill();

                    }
                } else {
                    spriteC = false;
                    kill();
                }

                if (owner.getMainHandItem() == stack || owner.getOffhandItem() == stack) {
                    if (isPulling) { //Movement start
                        Entity target = owner;
                        Entity origin = this;

                        if (owner.isCrouching() && hookedEntity != null) { //Change in case you want to bring the entity to you.
                            target = hookedEntity;
                            origin = owner;
                            owner.setNoGravity(true);
                        }

                        double brakeZone = ((6D * (maxSpeed)) / 10);
                        double pullSpeed = (maxSpeed) / 6D;
                        Vector3d distance = origin.position().subtract(target.position().add(0, target.getBbHeight() / 2, 0)); //Get distance.
                        double reduction = (pullSpeed * distance.length() / brakeZone); //Get motion reduction.
                        Vector3d motion = distance.normalize().multiply(reduction, reduction, reduction); //Get last motion.

                        //In case the movement is at ground level.
                        if (Math.abs(distance.y) < 0.1D) {
                            motion = new Vector3d(motion.x, 0, motion.z);
                        }
                        //In case the movement is only upwards.
                        else if (new Vector3d(distance.x, 0, distance.z).length() < new Vector3d(target.getBbWidth() / 2, 0, target.getBbWidth() / 2).length() / 1.4) {
                            motion = new Vector3d(0, motion.y, 0);
                        }
                        //By pressing Space you go down. If you let go, you go back up.
                        if (motion.y > 0 && SupersLegendKeyboardUtil.isHoldingSpace() && new Vector3d(distance.x, 0, distance.z).length() < new Vector3d(target.getBbWidth() / 2, 0, target.getBbWidth() / 2).length() / 1.4) {
                            motion = new Vector3d(0, -motion.y/3, 0);
                        }

                        target.fallDistance = 0; //Cancel Fall Damage

                        target.setDeltaMovement(motion); //Set motion.
                        target.hurtMarked = true; //Make motion works, this is necessary.

                        //Take the entity if it is an item and check that it is in your inventory to kill the hook.
                        if(hookedEntity instanceof ItemEntity){
                            if(owner.inventory.add(((ItemEntity) hookedEntity).getItem())) {
                                spriteC = false;
                                HookModel.get(owner).setHasHook(false);
                                kill();

                            }
                        }

                    }

                } else {
                    spriteC = false;
                    HookModel.get(owner).setHasHook(false);
                    kill();

                }
            }
        }
    }

    //Prevents a crash. Name self-explanatory.
    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void kill() {
        if (!level.isClientSide && owner != null) {
            HookModel.get(owner).setHasHook(false);
            owner.setNoGravity(false);
        }

        super.kill();
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
    protected void onHitBlock(BlockRayTraceResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        isPulling = true;
        setHookableBlocks(); //Loads the list of blocks to which it can be hooked.

        if (!level.isClientSide && owner != null && hookedEntity == null) {
            owner.setNoGravity(true);

            //Initialization of the list of ItemEntities found on the floor
            // and selection of the size of the Bounding Box in which to search for them.
            List<ItemEntity> list = level.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().expandTowards(1D, 0.5D, 1D));

            if (useBlockList) { //If this value is "FALSE" all blocks will be hooked.
                //If the block is not in the list, the hook does not hook.
                if (!hookableBlocks.contains(level.getBlockState(blockHitResult.getBlockPos()).getBlock())) {
                    HookModel.get(owner).setHasHook(false);
                    isPulling = false;
                    onRemovedFromWorld();
                }
                //Catch ItemEntities from ground.
                if(list != null && list.size() > 0){
                    for (Entity entity : list) {
                        hookedEntity = entity;
                    }
                    HookModel.get(owner).setHasHook(true);
                    isPulling = true;
                    onRemovedFromWorld();
                }
            }
        }
    }

    /**
     * This function is used to detect when the hook hits an entity.
     * @param entityHitResult
     */
    @Override
    protected void onHitEntity(EntityRayTraceResult entityHitResult) {
        if (!level.isClientSide && owner != null && entityHitResult.getEntity() != owner) {
          if((entityHitResult.getEntity() instanceof LivingEntity || entityHitResult.getEntity() instanceof EnderDragonPartEntity) && hookedEntity == null) {
                hookedEntity = entityHitResult.getEntity();
                entityData.set(HOOKED_ENTITY_ID, hookedEntity.getId() + 1);
                isPulling = true;
                owner.setNoGravity(true);

          }
        }
    }



    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        maxRange = tag.getDouble("maxRange");
        maxSpeed = tag.getDouble("maxSpeed");
        isPulling = tag.getBoolean("isPulling");
        stack = ItemStack.of(tag.getCompound("hookshotItem"));

        if (level.getEntity(tag.getInt("owner")) instanceof PlayerEntity)
            owner = (PlayerEntity) level.getEntity(tag.getInt("owner"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putDouble("maxRange", maxRange);
        tag.putDouble("maxSpeed", maxSpeed);
        tag.putBoolean("isPulling", isPulling);
        tag.put("hookshotItem", stack.save(new CompoundNBT()));
        tag.putInt("owner", owner.getId());
    }

    /**
     * Used to get the properties from the item.
     */
    public void setProperties(ItemStack stack, double maxRange, double maxVelocity, float pitch, float yaw, float roll, float modifierZ) {
        float f = 0.017453292F;
        float x = -MathHelper.sin(yaw * f) * MathHelper.cos(pitch * f);
        float y = -MathHelper.sin((pitch + roll) * f);
        float z = MathHelper.cos(yaw * f) * MathHelper.cos(pitch * f);
        this.shoot(x, y, z, modifierZ, 0);

        this.stack = stack;
        this.maxRange = maxRange;
        this.maxSpeed = maxVelocity;
    }

    //Disable ChangeDimensions.
    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    //Make the entity appear in the level.
    @Override
    @Nonnull
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
