package com.superworldsun.superslegend.entities.projectiles.magic;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class IceballEntity extends AbstractHurtingProjectile
{
    // 5 seconds of max flight time
    protected int maxAge = 100;
    protected int age;

    public IceballEntity(Vec3 postition, Vec3 motion, Level world, Player owner)
    {
        super(EntityTypeInit.ICEBALL.get(), world);
        setPos(postition.x, postition.y, postition.z);
        setDeltaMovement(motion);
        setOwner(owner);
    }


    public IceballEntity(EntityType<IceballEntity> iceballEntityEntityType, Level level) {
        super(iceballEntityEntityType, level);
    }

    @Override
    protected void onHit(HitResult result)
    {
        if (!level().isClientSide)
        {
            int particlesDensity = 40;
            // in ticks
            int slowDuration = 200;
            // 0 radius means no damage, only visual effects
            float explosionRadius = 0F;
            float particlesSpeed = 0.4F;
            float particlesSpread = 0.2F;
            float effectRadius = 3F;
            // 20% of blocks will be covered in snow
            float snowChance = 0.2F;
            float damage = 5F;
            level().explode(this, getX(), getY(), getZ(), explosionRadius, Level.ExplosionInteraction.NONE);

            for (int i = 0; i < particlesDensity; i++)
            {
                double particleX = getX() + (random.nextFloat() * 2 - 1) * particlesSpread;
                double particleY = getY() + (random.nextFloat() * 2 - 1) * particlesSpread;
                double particleZ = getZ() + (random.nextFloat() * 2 - 1) * particlesSpread;
                double particleMotionX = (random.nextFloat() * 2 - 1) * particlesSpeed;
                double particleMotionY = (random.nextFloat() * 2 - 1) * particlesSpeed;
                double particleMotionZ = (random.nextFloat() * 2 - 1) * particlesSpeed;
                ParticleOptions particle = ParticleTypes.SPIT;
                level().addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
            }

            // we want to only attack living entities
            Predicate<Entity> canHit = e -> e instanceof LivingEntity;
            Predicate<Entity> isInRadius = e -> distanceTo(e) <= effectRadius;
            List<Entity> entitiesInRadius = level().getEntities(this, getBoundingBox().inflate(effectRadius), canHit.and(isInRadius));
            entitiesInRadius.forEach(entity ->
            {
                //TODO, fix this / port it
                //entity.hurt(DamageSource.playerAttack(getOwner()), damage);
                //((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, slowDuration));
            });

            // here we are searching for blocks in radius
            for (int x = (int) -effectRadius; x <= effectRadius; x++)
            {
                for (int y = (int) -effectRadius; y <= effectRadius; y++)
                {
                    for (int z = (int) -effectRadius; z <= effectRadius; z++)
                    {
                        BlockPos pos = blockPosition().north(x).above(y).east(z);

                        // if the block is in radius
                        //TODO, This only changes blocks after it explodes, entity should explode when in contact with these fluids.
                        if (blockPosition().distSqr(pos) <= effectRadius * effectRadius)
                        {
                            if (level().getFluidState(pos).is(FluidTags.WATER))
                            {
                                level().setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
                            }
                            else if (level().getFluidState(pos).is(FluidTags.LAVA))
                            {
                                level().setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
                                level().playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1F, 1F);
                            }
                            else if (Blocks.SNOW.defaultBlockState().canSurvive(level(), pos.above()) && level().getBlockState(pos.above()).is(Blocks.AIR) && random.nextFloat() < snowChance)
                            {
                                BlockState snowBlockState = Blocks.SNOW.defaultBlockState();
                                level().setBlock(pos.above(), snowBlockState, 3);
                            }
                        }
                    }
                }
            }
        }

        // removes fireball from the world to prevent multiple explosions
        this.discard();
    }

    @Override
    protected float getInertia()
    {
        return 1F;
    }

    @Override
    public void tick()
    {
        if (age > maxAge)
        {
            this.discard();
            return;
        }

        age++;

        int particlesDensity = 3;
        float particlesSpeed = 0.1F;
        float particlesSpread = 0.2F;

        for (int i = 0; i < particlesDensity; i++)
        {
            double particleX = getX() + (random.nextFloat() * 2 - 1) * particlesSpread;
            double particleY = getY() + (random.nextFloat() * 2 - 1) * particlesSpread;
            double particleZ = getZ() + (random.nextFloat() * 2 - 1) * particlesSpread;
            double particleMotionX = (random.nextFloat() * 2 - 1) * particlesSpeed;
            double particleMotionY = (random.nextFloat() * 2 - 1) * particlesSpeed;
            double particleMotionZ = (random.nextFloat() * 2 - 1) * particlesSpeed;
            ParticleOptions particle = ParticleTypes.SPIT;
            level().addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
        }

        super.tick();
    }

    @Override
    protected ParticleOptions getTrailParticle()
    {
        return ParticleTypes.SPIT;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage)
    {
        return false;
    }

    @Nullable
    @Override
    public Player getOwner()
    {
        return (Player) super.getOwner();
    }

    @Override
    protected boolean shouldBurn()
    {
        return false;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
