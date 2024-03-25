package com.superworldsun.superslegend.entities.projectiles.bombs;

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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.time.Instant;

public class AbstractBombEntity extends ThrowableItemProjectile {
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

    public AbstractBombEntity(EntityType<? extends AbstractBombEntity> type, Level level, float secondsToExplode, float secondsToFlashRapidly, int explosionPower, double bounceDampeningFactor) {
        super(type, level);
        creationTimestamp = initCreationTimestamp(level);
        this.ticksToExplode = toTicks(secondsToExplode);
        this.ticksToFlashRapidly = toTicks(secondsToFlashRapidly);
        this.explosionPower = explosionPower;
        this.bounceDampeningFactor = bounceDampeningFactor;
    }

    public AbstractBombEntity(EntityType<? extends AbstractBombEntity> entityType, LivingEntity shooter, Level world, float secondsToExplode, float secondsToFlashRapidly, int explosionPower, double bounceDampeningFactor) {
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
        Vec3 vector3d = result.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vector3d);
        Vec3 vector3d1 = vector3d.normalize().scale(0.05F);
        this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.inGround = true;
    }

    @Override
    public void tick() {
        if(this.tickCount % 11 == 0)
        {
            BlockPos currentPos = this.blockPosition();
            this.level().playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        if (this.isOnFire())
        {
            explode();
        }

        if (this.isInWater())
        {
            this.playSound(SoundInit.BOMB_DEFUSE.get(), 1.0F, 1.0F);
            this.discard();
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
        setDeltaMovement(getDeltaMovement().multiply(0,0,0));
        setPos(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    private void explode() {
        {
            BlockPos explosionPos = this.blockPosition();
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, Level.ExplosionInteraction.NONE);

            int radius = (int) Math.ceil(explosionPower);
            for (BlockPos pos : BlockPos.betweenClosed(explosionPos.offset(-radius, -radius, -radius), explosionPos.offset(radius, radius, radius))) {
                Block block = this.level().getBlockState(pos).getBlock();
                if (block == BlockInit.CRACKED_BOMB_WALL.get()) {
                    this.level().destroyBlock(pos, false);
                }
            }
        }
        this.discard();
    }

    /*private void explode() {
        if(SupersLegendConfig.getInstance().explosivegriefing()){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
        else
        {
            BlockPos explosionPos = this.blockPosition();
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, Level.ExplosionInteraction.NONE);

            int radius = (int) Math.ceil(explosionPower);
            for (BlockPos pos : BlockPos.betweenClosed(explosionPos.offset(-radius, -radius, -radius), explosionPos.offset(radius, radius, radius))) {
                Block block = this.level().getBlockState(pos).getBlock();
                if (block == BlockInit.CRACKED_BOMB_WALL.get()) {
                    this.level().destroyBlock(pos, false);
                }
            }
        }
        this.discard();
    }*/

    public Instant getCreationTime() {
        return this.creationTimestamp;
    }

    public boolean shouldFlashRapidly() {
        return this.tickCount >= this.ticksToFlashRapidly;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemInit.BOMB.get();
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