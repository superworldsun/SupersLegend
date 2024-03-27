package com.superworldsun.superslegend.entities.projectiles.magic;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class FireballEntity extends AbstractHurtingProjectile
{
    // 5 seconds of max flight time
    protected int maxAge = 100;
    protected int age;

    public FireballEntity(Vec3 postition, Vec3 motion, Level world, Player owner)
    {
        super(EntityTypeInit.FIREBALL.get(), world);
        setPos(postition.x, postition.y, postition.z);
        setDeltaMovement(motion);
        setOwner(owner);
    }


    public FireballEntity(EntityType<FireballEntity> fireballEntityEntityType, Level level) {
        super(fireballEntityEntityType, level);
    }

    @Override
    protected void onHit(HitResult result)
    {
        if (!level().isClientSide)
        {
            int particlesDensity = 40;
            int secondsOnFire = 3;
            // 0 radius means no damage, only visual effects
            float explosionRadius = 0F;
            float particlesSpeed = 0.4F;
            float particlesSpread = 0.2F;
            float effectRadius = 3F;
            // 20% of blocks will be set on fire
            float fireChance = 0.2F;
            level().explode(this, getX(), getY(), getZ(), explosionRadius, Level.ExplosionInteraction.NONE);

            for (int i = 0; i < particlesDensity; i++)
            {
                double particleX = getX() + (random.nextFloat() * 2 - 1) * particlesSpread;
                double particleY = getY() + (random.nextFloat() * 2 - 1) * particlesSpread;
                double particleZ = getZ() + (random.nextFloat() * 2 - 1) * particlesSpread;
                double particleMotionX = (random.nextFloat() * 2 - 1) * particlesSpeed;
                double particleMotionY = (random.nextFloat() * 2 - 1) * particlesSpeed;
                double particleMotionZ = (random.nextFloat() * 2 - 1) * particlesSpeed;
                ParticleOptions particle = random.nextBoolean() ? ParticleTypes.FLAME : ParticleTypes.SMOKE;
                level().addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
            }

            // we want to only attack living entities
            Predicate<Entity> canHit = e -> e instanceof LivingEntity;
            Predicate<Entity> isInRadius = e -> distanceTo(e) <= effectRadius;
            List<Entity> entitiesInRadius = level().getEntities(this, getBoundingBox().inflate(effectRadius), canHit.and(isInRadius));
            entitiesInRadius.forEach(entity ->
            {
                entity.setSecondsOnFire(secondsOnFire);
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
                        if (blockPosition().distSqr(pos) <= effectRadius * effectRadius)
                        {
                            if (level().getBlockState(pos).is(TagInit.CAN_MELT))
                            {
                                // replaces meltable blocks with air
                                level().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                            }
                            else if (level().getBlockState(pos).isCollisionShapeFullBlock(level(), pos) && level().getBlockState(pos.above()).is(Blocks.AIR) && random.nextFloat() < fireChance)
                            {
                                // sets other blocks on fire
                                if (FireBlock.canBePlacedAt(level(), pos.above(), Direction.UP))
                                {
                                    BlockState fireBlockState = FireBlock.getState(level(), pos.above());
                                    level().setBlock(pos.above(), fireBlockState, 11);
                                }
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
            ParticleOptions particle = random.nextBoolean() ? ParticleTypes.FLAME : ParticleTypes.SMOKE;
            level().addParticle(particle, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
        }

        super.tick();
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
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
