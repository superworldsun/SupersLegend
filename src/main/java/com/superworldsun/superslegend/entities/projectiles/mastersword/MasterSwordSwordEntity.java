package com.superworldsun.superslegend.entities.projectiles.mastersword;

import static net.minecraft.entity.CreatureAttribute.UNDEAD;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.boomerang.BoomerangEntity;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Optional;

public class MasterSwordSwordEntity extends AbstractArrowEntity
{
    private float time;
    private double motionX;
    private double motionZ;
    private double motionY;
    private float prevBoomerangRotation;
    private static final DataParameter<Float> ROTATION = EntityDataManager.defineId(MasterSwordSwordEntity.class, DataSerializers.FLOAT);

    private static final DataParameter<Float> Time = EntityDataManager.defineId(MasterSwordSwordEntity.class, DataSerializers.FLOAT);
    public MasterSwordSwordEntity(EntityType<? extends MasterSwordSwordEntity> type, World world) {
        super(type, world);
        motionX = 1;
        motionZ = 1;
        motionY = 1;
    }

    public MasterSwordSwordEntity(World worldIn, LivingEntity shooter) {
        super(EntityTypeInit.MASTERSWORD_SWORD_ENTITY.get(), shooter, worldIn);
    }

    public float getBoomerangRotation() {
        return this.getEntityData().get(ROTATION);
    }

    public void setBoomerangRotation(float rotationIn) {
        this.getEntityData().set(ROTATION, rotationIn);
    }



    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        setBaseDamage(4.0D);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        if(living instanceof WitherSkeletonEntity)
            this.setBaseDamage(this.getBaseDamage() * 100);
        super.doPostHurtEffects(living);
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        super.setSoundEvent(soundIn);
    }

    /*@Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        if(entity.isEntityUndead()){
            this.setDamageValue(this.getDamage()*20);
            }
        if(!entity.isEntityUndead())
        {
            this.setDamageValue(this.getDamage()*1.5);
        }
    }*/
    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(Time, 0.0F);
        this.getEntityData().define(ROTATION, 0.0F);
        super.defineSynchedData();
    }

    public Float getTime() {
        return this.entityData.get(Time);
    }

    public void setTime(Float time) {
        this.entityData.set(Time, time);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public boolean isUnderWater() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        determineRotation();
        prevBoomerangRotation = getBoomerangRotation();
        for (this.setBoomerangRotation(this.getBoomerangRotation() + 36F); this.getBoomerangRotation() > 360F; this.setBoomerangRotation(this.getBoomerangRotation() - 360F)) {
        }

        if (getTime() > 80f) {
            this.kill();
        }
        else {
            setTime(getTime()+1f);

            Vector3d vec3d1 = this.position();
            Vector3d vec3d = this.position().add(this.getDeltaMovement());
            if (this.inGround) {
                this.kill();
            }


        }
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
    public void readAdditionalSaveData(CompoundNBT compound) {
        this.setTime(compound.getFloat("time"));
        this.prevBoomerangRotation = compound.getFloat("PrevBoomerangRotation");
        this.setBoomerangRotation(compound.getFloat("BoomerangRotation"));
        super.readAdditionalSaveData(compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        compound.putFloat("time", time);
        compound.putFloat("PrevBoomerangRotation", prevBoomerangRotation);
        compound.putFloat("BoomerangRotation", this.getBoomerangRotation());
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void playerTouch(PlayerEntity player) {

    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        if (getTime()>2) {
            if (entity instanceof CreatureEntity) {
                LivingEntity lentity = (CreatureEntity) result.getEntity();

                if (lentity.getMobType().equals(UNDEAD)) {
                    lentity.hurt(DamageSource.arrow(this, lentity), (float) (this.getBaseDamage() * 2));
                } else {
                    lentity.hurt(DamageSource.arrow(this, lentity), (float) (this.getBaseDamage()));
                }
                kill();
                super.onHitEntity(result);
                return;
            }
            entity.hurt(DamageSource.arrow(this, entity), (float) (this.getBaseDamage()));
            kill();
            super.onHitEntity(result);
        }

        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;
            if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() + 0);
            }

        }
    }
	
	public static EntityType<MasterSwordSwordEntity> createEntityType()
	{
		return EntityType.Builder.<MasterSwordSwordEntity>of(MasterSwordSwordEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":mastersword_sword");
	}
}
