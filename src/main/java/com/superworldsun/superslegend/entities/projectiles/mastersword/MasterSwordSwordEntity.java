package com.superworldsun.superslegend.entities.projectiles.mastersword;

import com.superworldsun.superslegend.entities.projectiles.boomerang.BoomerangEntity;
import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.Vector;

import static net.minecraft.entity.CreatureAttribute.UNDEAD;

public class MasterSwordSwordEntity extends AbstractArrowEntity
{
    private float time;
    private double motionX;
    private double motionZ;
    private double motionY;

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

    @Override
    public void load(CompoundNBT compound) {
        super.load(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        this.setTime(compound.getFloat("time"));
        super.readAdditionalSaveData(compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        compound.putFloat("time", time);
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


    }
}
