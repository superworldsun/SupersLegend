package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.util.Functions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static com.superworldsun.superslegend.util.Functions.repeat;

public class BombArrowEntity extends AbstractArrowEntity {

    public BombArrowEntity(EntityType<? extends BombArrowEntity> type, World world) {
        super(type, world);
    }

    public BombArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntityTypeInit.BOMB_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 2.0F);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.BOMB_ARROW.get());
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        // TODO could be thundering without rain. Maybe use isInRainOrWater
        if (!level.isRaining() && !level.isThundering()) {
            level.explode(null, this.xo, this.yo, this.zo, 4.5f, Explosion.Mode.NONE);
        }
    }


    @Override
    public void tick() {
        super.tick();
        if (this.inGround) {
            if (!this.isInWater())
                level.explode(null, this.xo, this.yo, this.zo, 3.0f, Explosion.Mode.NONE);
            this.remove();
        }
        addSmokeToFlightPath();
        defuseInWater();
        explodeInHeat();
        playFuseSoundEveryNinthTick();
    }

    private void playFuseSoundEveryNinthTick() {
        if(this.tickCount % 9 == 0)
        {
            BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

    private void explodeInHeat() {
        if(this.isOnFire() || this.isInLava())
        {
            level.explode(null, this.xo, this.yo, this.zo, 3.0f, Explosion.Mode.NONE);
            this.remove();
        }
    }

    private void defuseInWater() {
        if(this.wasTouchingWater)
        {
            BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_DEFUSE.get(), SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.remove();
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;

            this.getBaseDamage();
            if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }
    }

    private void addSmokeToFlightPath() {
        if (!this.isInWaterOrRain() && !this.inGround) {
            repeat(3, () -> this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D));
        }
    }
}

