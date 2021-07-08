package superworldsun.superslegend.entities.projectiles.items.bomb;

import net.minecraft.block.TNTBlock;
import net.minecraft.client.renderer.entity.TNTRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

import javax.annotation.Nullable;


public class BombEntity extends AbstractArrowEntity  {
    private static final DataParameter<Integer> FUSE = EntityDataManager.defineId(BombEntity.class, DataSerializers.INT);
    public static final DataParameter<Boolean> ID_FOIL = EntityDataManager.defineId(BombEntity.class, DataSerializers.BOOLEAN);
    public ItemStack thrownStack = new ItemStack(ItemList.bomb);
    private boolean dealtDamage;
    @Nullable
    private LivingEntity tntPlacedBy;
    private int fuse = 80;

    public BombEntity(EntityType<? extends BombEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BombEntity(World worldIn, PlayerEntity thrower, ItemStack thrownStackIn) {
        super(EntityInit.BOMBENTITY.get(), thrower, worldIn);
        this.thrownStack = thrownStackIn.copy();
        this.entityData.set(ID_FOIL, thrownStackIn.hasFoil());
    }

    @OnlyIn(Dist.CLIENT)
    public BombEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(EntityInit.BOMBENTITY.get(), worldIn);
        this.setPos(x, y, z);
        double d0 = worldIn.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.tntPlacedBy = igniter;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
        this.entityData.define(FUSE, 80);
    }
    protected boolean isMovementNoisy() {
        return false;
    }

    //Was Causing Crash
    /*public boolean canBeCollidedWith() {
        return !this.removed;
    }*/

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {

        if(this.tickCount % 22 == 0)
        {
            BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        if(this.isInWater())
        {
            BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_DEFUSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.remove();
        }

        if(this.isOnFire())
        {
            this.explode();
            this.remove();
        }

        if(this.isInLava())
        {
            this.isOnFire();
            this.explode();
            this.remove();
        }

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(1.0D));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.6D, this.getZ(), 0.0D, 0.1D, 0.0D);
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.6D, this.getZ(), 0.0D, 0.1D, 0.0D);
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.6D, this.getZ(), 0.0D, 0.1D, 0.0D);
            }
        }

    }

    protected void explode() {
        float f = 4.0F;
        this.level.explode(this, this.getX(), this.getY(0.1f), this.getZ(), 4.0F, Explosion.Mode.NONE);
    }

    protected ItemStack getPickupItem() {
        return this.thrownStack.copy();
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityRayTraceResult findHitEntity(Vector3d startVec, Vector3d endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = 8.0F;

        Entity entity1 = this.getOwner();
        DamageSource damagesource = DamageSource.thrown(this, entity1);
        this.dealtDamage = true;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                this.explode();
            }

            if (entity instanceof LivingEntity) {
                this.explode();
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }


    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Bomb", 10)) {
            this.thrownStack = ItemStack.of(compound.getCompound("Bomb"));
        }
        this.setFuse(compound.getShort("Fuse"));

        this.dealtDamage = compound.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Bomb", this.thrownStack.save(new CompoundNBT()));
        compound.putBoolean("DealtDamage", this.dealtDamage);
        compound.putShort("Fuse", (short)this.getFuse());
    }

    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.15F;
    }

    /**
     * returns null or the entityliving it was placed or ignited by
     */
    @Nullable
    public LivingEntity getTntPlacedBy() {
        return this.tntPlacedBy;
    }

    public void setFuse(int fuseIn) {
        this.entityData.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }

    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuseDataManager() {
        return this.entityData.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }


    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}

