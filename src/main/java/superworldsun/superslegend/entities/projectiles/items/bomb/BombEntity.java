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
    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(BombEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> field_226571_aq_ = EntityDataManager.createKey(BombEntity.class, DataSerializers.BOOLEAN);
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
        this.dataManager.set(field_226571_aq_, thrownStackIn.hasEffect());
    }

    @OnlyIn(Dist.CLIENT)
    public BombEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(EntityInit.BOMBENTITY.get(), worldIn);
        this.setPosition(x, y, z);
        double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.tntPlacedBy = igniter;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(field_226571_aq_, false);
        this.dataManager.register(FUSE, 80);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {

        if(this.ticksExisted % 22 == 0)
        {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        if(this.isInWater())
        {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_DEFUSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.remove();
        }

        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(1.0D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
                this.explode();
            }
        } else {
            this.func_233566_aG_();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.6D, this.getPosZ(), 0.0D, 0.1D, 0.0D);
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.6D, this.getPosZ(), 0.0D, 0.1D, 0.0D);
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.6D, this.getPosZ(), 0.0D, 0.1D, 0.0D);
            }
        }

    }

    protected void explode() {
        float f = 4.0F;
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.1f), this.getPosZ(), 4.0F, Explosion.Mode.NONE);
    }

    protected ItemStack getArrowStack() {
        return this.thrownStack.copy();
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
        return this.dealtDamage ? null : super.rayTraceEntities(startVec, endVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = 8.0F;

        Entity entity1 = this.func_234616_v_();
        DamageSource damagesource = DamageSource.causeThrownDamage(this, entity1);
        this.dealtDamage = true;
        if (entity.attackEntityFrom(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                this.explode();
            }

            if (entity instanceof LivingEntity) {
                this.explode();
            }
        }

        this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
    }


    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Bomb", 10)) {
            this.thrownStack = ItemStack.read(compound.getCompound("Bomb"));
        }
        this.setFuse(compound.getShort("Fuse"));

        this.dealtDamage = compound.getBoolean("DealtDamage");
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("Bomb", this.thrownStack.write(new CompoundNBT()));
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
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }

    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }


    protected float getWaterDrag() {
        return 0.99F;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return true;
    }
}

