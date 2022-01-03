package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class IceBeamEntity extends Entity 
{


    public IceBeamEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return null;
    }

    protected void readAdditional(CompoundNBT compound) {

    }

    protected void writeAdditional(CompoundNBT compound) {

    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }



    protected void registerData() {

    }

    @Override
    protected void defineSynchedData() {

    }

    //Arrow Code
    @Override
    public void tick() {
        super.tick();

        if (this.horizontalCollision) {
            BlockPos currentPos = this.getOnPos();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE.get(), SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(1.0D));
        if (this.onGround) {
            //this.setDeltaMovement(this.getDeltaMovement().mul(0.7D, -0.5D, 0.7D));
            BlockPos currentPos = this.getOnPos();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE.get(), SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
        if (!this.onGround) {
            this.setNoGravity(true);
            //this.setNoGravity(true);
            //BlockPos currentPos = this.getOnPos();
            //this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            //this.setVelocity(1, 1, 1);
        }
        if (this.isInWater()) {
            level.setBlockAndUpdate(this.getOnPos(), Blocks.FROSTED_ICE.defaultBlockState());
            BlockPos currentPos = this.getOnPos();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE.get(), SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
        if (this.isInLava()) {
            level.setBlockAndUpdate(this.getOnPos(), Blocks.COBBLESTONE.defaultBlockState());
            BlockPos currentPos = this.getOnPos();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE.get(), SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.1D, 0.0D);
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {

    }
}



     