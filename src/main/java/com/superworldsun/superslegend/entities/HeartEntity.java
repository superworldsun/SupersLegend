package com.superworldsun.superslegend.entities;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class HeartEntity extends Entity {

    public int tickCount;
    public int age;
    public int throwTime;
    //Default amount of healing.
    public int value = 2;
    private int health = 5;

    public HeartEntity(World p_i1585_1_, double p_i1585_2_, double p_i1585_4_, double p_i1585_6_) {
        this(EntityTypeInit.HEART.get(), p_i1585_1_);
        this.setPos(p_i1585_2_, p_i1585_4_, p_i1585_6_);
        this.yRot = (float) (this.random.nextDouble() * 360.0D);
        this.setDeltaMovement((this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D, this.random.nextDouble() * 0.2D * 2.0D, (this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D);
    }

    public HeartEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
    }

    //Movement of entity when Spawn.
    @Override
    public void tick() {
        super.tick();
        if (this.throwTime > 0) {
            --this.throwTime;
        }

        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();
        if (this.isEyeInFluid(FluidTags.WATER)) {
            this.setUnderwaterMovement();
        } else if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
        }

        if (this.level.getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
            this.setDeltaMovement((this.random.nextFloat() - this.random.nextFloat()) * 0.2F, 0.2F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            this.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
        }

        if (!this.level.noCollision(this.getBoundingBox())) {
            this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getZ());
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        float f = 0.98F;
        if (this.onGround) {
            BlockPos pos = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            f = this.level.getBlockState(pos).getSlipperiness(this.level, pos, this) * 0.98F;
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(f, 0.98D, f));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));
        }

        ++this.tickCount;
        ++this.age;
        if (this.age >= 400) {
            this.remove();
        }

    }

    private void setUnderwaterMovement() {
        Vector3d vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.x * (double) 0.99F, Math.min(vector3d.y + (double) 5.0E-4F, 0.06F), vector3d.z * (double) 0.99F);
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.level.isClientSide || !isAlive()) return false;
        if (this.isInvulnerableTo(p_70097_1_)) {
            return false;
        } else {
            this.markHurt();
            this.health = (int) ((float) this.health - p_70097_2_);
            if (this.health <= 0) {
                this.remove();
            }

            return false;
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        this.health = p_70037_1_.getShort("Health");
        this.age = p_70037_1_.getShort("Age");
        this.value = p_70037_1_.getShort("Value");
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        p_213281_1_.putShort("Health", (short) this.health);
        p_213281_1_.putShort("Age", (short) this.age);
        p_213281_1_.putShort("Value", (short) this.value);
    }

    /**
     * This function allows the entity to be "consumed" and heal the player.
     */
    @Override
    public void playerTouch(PlayerEntity player) {
        if (!this.level.isClientSide) {
            if (this.throwTime == 0 && player.getHealth() != player.getMaxHealth()) {

                player.heal(value);

                this.remove();
            }

        }
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public int getValue() {
        return this.value;
    }
	
	public static EntityType<HeartEntity> createEntityType()
	{
		return EntityType.Builder.<HeartEntity>of(HeartEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":heart");
	}
}
