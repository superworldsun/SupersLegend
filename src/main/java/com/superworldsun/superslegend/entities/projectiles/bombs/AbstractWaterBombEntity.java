package com.superworldsun.superslegend.entities.projectiles.bombs;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.time.Instant;
import java.util.List;

public class AbstractWaterBombEntity extends ThrowableItemProjectile {
    //Bomb rendering, entity and logic code credited to Spelunkcraft contributor ntfwc
    private static final int TICKS_PER_SECOND = 20;
    private static final double MOTION_STOP_THRESHOLD = 0.02;

    /**
     * This timestamp should be used only for rendering, so it is only available on
     * the client side. It is unsuitable for deciding when to explode since the user
     * may pause the game.
     */
    private final Instant creationTimestamp;

    /**
     * The number of ticks to wait before exploding.
     */
    private final int ticksToExplode;

    /**
     * The number of ticks to wait before flashing rapidly.
     */
    private final int ticksToFlashRapidly;
    private final int explosionPower;

    /**
     * How much to dampen the bounce. Lower values mean less bounce.
     */
    private final double bounceDampeningFactor;
    private static final double CHAIN_REACTION_RADIUS = 8.0;
    public boolean explodedByChainReaction = false;
    private boolean exploding = false;


    public AbstractWaterBombEntity(EntityType<? extends AbstractWaterBombEntity> type, Level level, float secondsToExplode, float secondsToFlashRapidly, int explosionPower, double bounceDampeningFactor) {
        super(type, level);
        creationTimestamp = initCreationTimestamp(level);
        this.ticksToExplode = toTicks(secondsToExplode);
        this.ticksToFlashRapidly = toTicks(secondsToFlashRapidly);
        this.explosionPower = explosionPower;
        this.bounceDampeningFactor = bounceDampeningFactor;
    }

    public AbstractWaterBombEntity(EntityType<? extends AbstractWaterBombEntity> entityType, LivingEntity shooter, Level world, float secondsToExplode, float secondsToFlashRapidly, int explosionPower, double bounceDampeningFactor) {
        super(entityType, shooter, world);
        creationTimestamp = initCreationTimestamp(world);
        this.ticksToExplode = toTicks(secondsToExplode);
        this.ticksToFlashRapidly = toTicks(secondsToFlashRapidly);
        this.explosionPower = explosionPower;
        this.bounceDampeningFactor = bounceDampeningFactor;
    }

    private Instant initCreationTimestamp(Level world) {
        return world.isClientSide ? Instant.now() : null;
    }

    private static int toTicks(float seconds) {
        return (int) (seconds * TICKS_PER_SECOND);
    }

    @Override
    protected void onHit(HitResult result) {
        HitResult.Type lvt_2_1_ = result.getType();
        if (lvt_2_1_ == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult)result);
        } else if (lvt_2_1_ == HitResult.Type.BLOCK) {
            this.onHitBlock((BlockHitResult)result);
        }
    }
    protected boolean inGround;

    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        Vec3 normal = new Vec3(result.getDirection().getNormal().getX(),
                result.getDirection().getNormal().getY(),
                result.getDirection().getNormal().getZ());

        Vec3 velocity = this.getDeltaMovement();

        double dot = velocity.dot(normal);
        Vec3 reflection = velocity.subtract(normal.multiply(2.0D * dot, 2.0D * dot, 2.0D * dot));

        double factor = 1.0 - bounceDampeningFactor;
        reflection = reflection.multiply(factor, factor, factor);

        this.setDeltaMovement(reflection);

        Vec3 pos = result.getLocation();
        this.setPos(pos.x, pos.y, pos.z);
    }

    @Override
    public void tick() {
        if(this.tickCount % 11 == 0  && !explodedByChainReaction)
        {
            BlockPos currentPos = this.blockPosition();
            this.level().playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        if (this.isOnFire() || (this.ticksToExplode <= this.tickCount && !explodedByChainReaction))
        {
            explode();
        }

        if (!this.level().isClientSide) {
            Vec3 previousPosition = position();
            super.tick();
            Vec3 newPosition = position();

            // Handle collisions
            BlockHitResult rayTraceResult = rayTrace(previousPosition, newPosition);
            if (rayTraceResult.getType() == HitResult.Type.BLOCK)
                onBlockImpact(rayTraceResult, previousPosition, newPosition);

            if (this.ticksToExplode <= this.tickCount) {
                explode();
            }
            spawnParticles(previousPosition, newPosition);

            BlockHitResult blockRTR = rayTrace(previousPosition, newPosition);
        }
        else
        {
            super.tick();
        }
    }

    public void spawnParticles(Vec3 currentPos, Vec3 newPos) {
        if (!this.firstTick) {

            double x = currentPos.x;
            double y = currentPos.y;
            double z = currentPos.z;
            double dx = newPos.x - x;
            double dy = newPos.y - y;
            double dz = newPos.z - z;
            int s = 4;
            for (int i = 0; i < s; ++i) {
                double j = i / (double) s;
                ((ServerLevel) this.getCommandSenderWorld()).sendParticles(ParticleTypes.SMOKE, x + dx * j, 0.5 + y + dy * j, z + dz * j, 1, 0, 0.02, 0 ,0.01);
            }
        }
    }

    private void onBlockImpact(BlockHitResult result, Vec3 previousPosition, Vec3 attemptedNewPosition) {
//        setDeltaMovement(getDeltaMovement().multiply(0,0,0));
//        setPos(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public void explode() {
        if (!this.level().isClientSide() && !exploding) {
            exploding = true;
            BlockPos explosionPos = this.blockPosition();
            triggerNearbyBombs();

            int radius = (int) Math.ceil(explosionPower);
            if (Config.explosivegriefing()) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, Level.ExplosionInteraction.TNT);
                for (BlockPos pos : BlockPos.betweenClosed(explosionPos.offset(-radius, -radius, -radius), explosionPos.offset(radius, radius, radius))) {
                    BlockState blockState = this.level().getBlockState(pos);
                    Block block = blockState.getBlock();
                    if (!blockState.isAir() && block.getExplosionResistance() < Float.MAX_VALUE && !blockState.is(Blocks.BEDROCK)) {
                        double distance = new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)
                                .distance(new Vector3d(explosionPos.getX() + 0.5, explosionPos.getY() + 0.5, explosionPos.getZ() + 0.5));
                        // closer to center - higher the chance
                        double destructionChance = radius / 2F / distance;
                        if (distance <= radius && random.nextFloat() < destructionChance) {
                            this.level().destroyBlock(pos, true);
                        }
                    }
                }
            } else {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, Level.ExplosionInteraction.TNT);
                for (BlockPos pos : BlockPos.betweenClosed(explosionPos.offset(-radius, -radius, -radius), explosionPos.offset(radius, radius, radius))) {
                    BlockState blockState = this.level().getBlockState(pos);
                    Block block = blockState.getBlock();
                    if (block == BlockInit.CRACKED_BOMB_WALL.get()) {
                        this.level().destroyBlock(pos, true);
                    }
                }
            }
        }
        this.discard();
    }

    private void triggerNearbyBombs() {
        AABB explosionBox = new AABB(
                this.getX() - CHAIN_REACTION_RADIUS,
                this.getY() - CHAIN_REACTION_RADIUS,
                this.getZ() - CHAIN_REACTION_RADIUS,
                this.getX() + CHAIN_REACTION_RADIUS,
                this.getY() + CHAIN_REACTION_RADIUS,
                this.getZ() + CHAIN_REACTION_RADIUS
        );

        // Get and trigger regular bombs
        List<AbstractWaterBombEntity> nearbyBombs = this.level().getEntitiesOfClass(
                AbstractWaterBombEntity.class,
                explosionBox
        );
        for (AbstractWaterBombEntity waterBomb : nearbyBombs) {
            if (waterBomb == null || waterBomb == this || waterBomb.explodedByChainReaction || waterBomb.isRemoved()) {
                continue;
            }
            waterBomb.explodedByChainReaction = true;
            waterBomb.explode();
        }

        // Get and trigger water bombs
        List<AbstractBombEntity> nearbyWaterBombs = this.level().getEntitiesOfClass(
                AbstractBombEntity.class,
                explosionBox
        );
        for (AbstractBombEntity bomb : nearbyWaterBombs) {
            if (bomb == null || bomb.explodedByChainReaction || bomb.isRemoved()) {
                continue;
            }
            bomb.explodedByChainReaction = true;
            bomb.explode();
        }
    }

    public Instant getCreationTime() {
        return this.creationTimestamp;
    }

    public boolean shouldFlashRapidly() {
        return this.tickCount >= this.ticksToFlashRapidly;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemInit.WATER_BOMB.get();
    }

    private BlockHitResult rayTrace(Vec3 position, Vec3 nextPosition) {
        return level().clip(new ClipContext(position, nextPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
    }

    private static class BounceSolution {
        Vector3d motion;
        Vector3d position;

        public BounceSolution(Vector3d motion, Vector3d position) {
            this.motion = motion;
            this.position = position;
        }
    }
}