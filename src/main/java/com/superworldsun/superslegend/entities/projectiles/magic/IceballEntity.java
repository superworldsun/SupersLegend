package com.superworldsun.superslegend.entities.projectiles.magic;

import com.superworldsun.superslegend.util.IceExtinguishUtil;

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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class IceballEntity extends AbstractHurtingProjectile {
    protected static final int MAX_AGE = 100;
    protected static final int SLOW_DURATION = 200;
    protected static final float EXPLOSION_RADIUS = 0F;
    protected static final float PARTICLE_SPEED = 0.4F;
    protected static final float PARTICLE_SPREAD = 0.2F;
    protected static final float EFFECT_RADIUS = 3F;
    protected static final float SNOW_CHANCE = 0.2F;
    protected static final float DAMAGE = 5F;

    protected int age;

    public IceballEntity(Vec3 position, Vec3 motion, Level world, Player owner) {
        super(EntityTypeInit.ICEBALL.get(), world);
        setPos(position.x, position.y, position.z);
        setDeltaMovement(motion);
        setOwner(owner);
    }

    public IceballEntity(EntityType<IceballEntity> iceballEntityType, Level level) {
        super(iceballEntityType, level);
    }

    @Override
    protected void onHit(HitResult result) {
        if (!level().isClientSide) {
            triggerEffects();
        }
        this.discard();
    }

    protected void triggerEffects() {
        level().explode(this, getX(), getY(), getZ(), EXPLOSION_RADIUS, Level.ExplosionInteraction.NONE);
        createParticles();

        Predicate<Entity> canHit = e -> e instanceof LivingEntity;
        Predicate<Entity> isInRadius = e -> distanceTo(e) <= EFFECT_RADIUS;
        List<Entity> entitiesInRadius = level().getEntities(this, getBoundingBox().inflate(EFFECT_RADIUS), canHit.and(isInRadius));

        entitiesInRadius.forEach(entity -> {
            if (entity.hurt(level().damageSources().playerAttack(Objects.requireNonNull(getOwner())), DAMAGE)) {
                IceExtinguishUtil.extinguishIfBurning(entity);
            }
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SLOW_DURATION));
        });

        modifySurroundingBlocks();
    }

    protected void createParticles() {
        int particlesDensity = 40;
        for (int i = 0; i < particlesDensity; i++) {
            double particleX = getX() + (random.nextFloat() * 2 - 1) * PARTICLE_SPREAD;
            double particleY = getY() + (random.nextFloat() * 2 - 1) * PARTICLE_SPREAD;
            double particleZ = getZ() + (random.nextFloat() * 2 - 1) * PARTICLE_SPREAD;
            double particleMotionX = (random.nextFloat() * 2 - 1) * PARTICLE_SPEED;
            double particleMotionY = (random.nextFloat() * 2 - 1) * PARTICLE_SPEED;
            double particleMotionZ = (random.nextFloat() * 2 - 1) * PARTICLE_SPEED;
            level().addParticle(ParticleTypes.SPIT, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
        }
    }

    protected void modifySurroundingBlocks() {
        for (int x = (int) -EFFECT_RADIUS; x <= EFFECT_RADIUS; x++) {
            for (int y = (int) -EFFECT_RADIUS; y <= EFFECT_RADIUS; y++) {
                for (int z = (int) -EFFECT_RADIUS; z <= EFFECT_RADIUS; z++) {
                    BlockPos pos = blockPosition().offset(x, y, z);
                    if (blockPosition().distSqr(pos) <= EFFECT_RADIUS * EFFECT_RADIUS) {
                        BlockPos[] directions = { pos.north(), pos.south(), pos.east(), pos.west(), pos.above(), pos.below() };
                        for (BlockPos adjacentPos : directions) {
                            if (level().getBlockState(adjacentPos).isAir()) {
                                if (level().getFluidState(pos).is(FluidTags.WATER)) {
                                    level().setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
                                    break;
                                } else if (level().getFluidState(pos).is(FluidTags.LAVA)) {
                                    level().setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
                                    level().playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1F, 1F);
                                    break;
                                }
                            }
                        }
                        if (Blocks.SNOW.defaultBlockState().canSurvive(level(), pos.above()) && level().getBlockState(pos.above()).is(Blocks.AIR) && random.nextFloat() < SNOW_CHANCE) {
                            level().setBlock(pos.above(), Blocks.SNOW.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        if (age > MAX_AGE) {
            this.discard();
            return;
        }
        if (isInWater() || isInLava() || isInFluidType()) {
            if (!level().isClientSide) {
                triggerEffects();
            }
            this.discard();
        }

        age++;
        createParticlesTrail();
        super.tick();
    }

    protected void createParticlesTrail() {
        int particlesDensity = 3;
        float particlesSpeed = 0.1F;
        for (int i = 0; i < particlesDensity; i++) {
            double particleX = getX() + (random.nextFloat() * 2 - 1) * PARTICLE_SPREAD;
            double particleY = getY() + (random.nextFloat() * 2 - 1) * PARTICLE_SPREAD;
            double particleZ = getZ() + (random.nextFloat() * 2 - 1) * PARTICLE_SPREAD;
            double particleMotionX = (random.nextFloat() * 2 - 1) * particlesSpeed;
            double particleMotionY = (random.nextFloat() * 2 - 1) * particlesSpeed;
            double particleMotionZ = (random.nextFloat() * 2 - 1) * particlesSpeed;
            level().addParticle(ParticleTypes.SPIT, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SPIT;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        return false;
    }

    @Nullable
    @Override
    public Player getOwner() {
        return (Player) super.getOwner();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected float getInertia() {
        return 1F;
    }
}
