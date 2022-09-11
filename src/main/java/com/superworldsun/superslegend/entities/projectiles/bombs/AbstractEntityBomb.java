package com.superworldsun.superslegend.entities.projectiles.bombs;

import com.superworldsun.superslegend.config.SupersLegendConfig;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.util.VecUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.time.Instant;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("EntityConstructor")
public abstract class AbstractEntityBomb extends ProjectileItemEntity {
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

    public AbstractEntityBomb(EntityType<? extends AbstractEntityBomb> type, World world, float secondsToExplode, float secondsToFlashRapidly, int explosionPower, double bounceDampeningFactor) {
        super(type, world);
        creationTimestamp = initCreationTimestamp(world);
        this.ticksToExplode = toTicks(secondsToExplode);
        this.ticksToFlashRapidly = toTicks(secondsToFlashRapidly);
        this.explosionPower = explosionPower;
        this.bounceDampeningFactor = bounceDampeningFactor;
    }

    public AbstractEntityBomb(EntityType<? extends AbstractEntityBomb> entityType, LivingEntity shooter, World world, float secondsToExplode, float secondsToFlashRapidly, int explosionPower, double bounceDampeningFactor) {
        super(entityType, shooter, world);
        creationTimestamp = initCreationTimestamp(world);
        this.ticksToExplode = toTicks(secondsToExplode);
        this.ticksToFlashRapidly = toTicks(secondsToFlashRapidly);
        this.explosionPower = explosionPower;
        this.bounceDampeningFactor = bounceDampeningFactor;
    }

    private Instant initCreationTimestamp(World world) {
        return world.isClientSide ? Instant.now() : null;
    }

    private static int toTicks(float seconds) {
        return (int) (seconds * TICKS_PER_SECOND);
    }


    @Override
    protected void onHit(RayTraceResult result) {
        RayTraceResult.Type lvt_2_1_ = result.getType();
        if (lvt_2_1_ == RayTraceResult.Type.ENTITY) {
            this.onHitEntity((EntityRayTraceResult)result);
        } else if (lvt_2_1_ == RayTraceResult.Type.BLOCK) {
            this.onHitBlock((BlockRayTraceResult)result);
        }
    }
    protected boolean inGround;

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        Vector3d vector3d = p_230299_1_.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.inGround = true;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {


            Vector3d previousPosition = position();
            super.tick();
            Vector3d newPosition = position();

            // Handle collisions
            BlockRayTraceResult rayTraceResult = rayTrace(previousPosition, newPosition);
            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
                onBlockImpact(rayTraceResult, previousPosition, newPosition);

            if (this.ticksToExplode <= this.tickCount) {
                explode();
            }

            spawnParticles(previousPosition, newPosition);

            BlockRayTraceResult blockRTR = rayTrace(previousPosition, newPosition);
            if (this.level.getBlockState(blockRTR.getBlockPos()) == Blocks.LAVA.defaultBlockState() ||
                    this.level.getBlockState(blockRTR.getBlockPos()) == Blocks.FIRE.defaultBlockState() ||
                    this.level.getBlockState(blockRTR.getBlockPos()) == Blocks.SOUL_FIRE.defaultBlockState()) {
                explode();
            }
            if (this.level.getBlockState(blockRTR.getBlockPos()) == Blocks.WATER.defaultBlockState()) {
                this.playSound(SoundInit.BOMB_DEFUSE.get(), 1.0F, 1.0F);
                this.remove();
            }

        }
    }

    public void spawnParticles(Vector3d currentPos, Vector3d newPos) {
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
                ((ServerWorld) this.getCommandSenderWorld()).sendParticles(ParticleTypes.SMOKE, x + dx * j, 0.5 + y + dy * j, z + dz * j, 1, 0, 0.02, 0 ,0.01);
            }
        }
    }

    private void onBlockImpact(BlockRayTraceResult result, Vector3d previousPosition, Vector3d attemptedNewPosition) {
        setDeltaMovement(getDeltaMovement().multiply(0,0,0));
        setPos(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    private void explode() {
        if(SupersLegendConfig.getInstance().explosivegriefing())
        {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, Explosion.Mode.DESTROY);
            remove();
        }
        else
        {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, Explosion.Mode.NONE);
            remove();
        }
    }

    public Instant getCreationTime() {
        return this.creationTimestamp;
    }

    public boolean shouldFlashRapidly() {
        return this.tickCount >= this.ticksToFlashRapidly;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.BOMB.get();
    }

    private BlockRayTraceResult rayTrace(Vector3d position, Vector3d nextPosition) {
        return level.clip(new RayTraceContext(position, nextPosition, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
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

