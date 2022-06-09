package com.superworldsun.superslegend.entities;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FaroresWindEntity extends Entity {

    private int health = 5;

    public FaroresWindEntity(World p_i1585_1_, double p_i1585_2_, double p_i1585_4_, double p_i1585_6_) {
        this(EntityTypeInit.FARORES_WIND.get(), p_i1585_1_);
    }

    public FaroresWindEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
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
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        p_213281_1_.putShort("Health", (short) this.health);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
	
	public static EntityType<FaroresWindEntity> createEntityType()
	{
		return EntityType.Builder.<FaroresWindEntity>of(FaroresWindEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":farores_wind");
	}
}
