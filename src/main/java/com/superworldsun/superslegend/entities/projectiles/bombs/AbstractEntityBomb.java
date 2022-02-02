package com.superworldsun.superslegend.entities.projectiles.bombs;

import com.superworldsun.superslegend.config.SupersLegendConfig;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.util.VecUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
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

    private boolean settingPositionOnUpdate = false;

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
        // Do nothing, we will do our own block impact detection
    }

    @Override
    public void tick() {
        settingPositionOnUpdate = true;


        Vector3d previousPosition = position();
        super.tick();
        Vector3d newPosition = position();

        // Handle collisions
        BlockRayTraceResult rayTraceResult = rayTrace(previousPosition, newPosition);
        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
            onBlockImpact(rayTraceResult, previousPosition, newPosition);

        if (!this.level.isClientSide) {
            if (this.ticksToExplode <= this.tickCount) {
                explode();
            }
        }

        BlockRayTraceResult blockRTR = rayTrace(previousPosition, newPosition);
        if(this.level.getBlockState(blockRTR.getBlockPos()) == Blocks.LAVA.defaultBlockState()||
                this.level.getBlockState(blockRTR.getBlockPos()) == Blocks.FIRE.defaultBlockState()||
                this.level.getBlockState(blockRTR.getBlockPos()) == Blocks.SOUL_FIRE.defaultBlockState())
        {
            explode();
        }
        if(this.level.getBlockState(blockRTR.getBlockPos()) == Blocks.WATER.defaultBlockState())
        {
            this.playSound(SoundInit.BOMB_DEFUSE.get(), 1.0F, 1.0F);
            this.remove();
        }

        settingPositionOnUpdate = false;
    }

    private void onBlockImpact(BlockRayTraceResult result, Vector3d previousPosition, Vector3d attemptedNewPosition) {
        BounceSolution solution = calculateBounceSolution(result, previousPosition, attemptedNewPosition);
        setPos(solution.position.x, solution.position.y, solution.position.z);
        setDeltaMovement(solution.motion);
    }

    /**
     * Calculates a bounce solution. Will handle corner bounces correctly.
     *
     * @param initialRayResult The initial ray collision.
     * @param previousPosition The previous position of the entity.
     * @param attemptedNewPosition The position the entity attempted to reach that caused the collision.
     * @return The bounce solution.
     */
    private BounceSolution calculateBounceSolution(BlockRayTraceResult initialRayResult, Vector3d previousPosition, Vector3d attemptedNewPosition) {
        Function<BlockRayTraceResult, Vector3d> getDirectionVector = (rayTraceResult) -> new Vector3d(rayTraceResult.getDirection().step());

        Vector3d directionVector1 = getDirectionVector.apply(initialRayResult);
        Vector3d dampenedInitialMotionVector = getDeltaMovement().scale(this.bounceDampeningFactor);
        Vector3d initialHitVec = initialRayResult.getLocation();
        if (dampenedInitialMotionVector.length() < MOTION_STOP_THRESHOLD)
        {
            return new BounceSolution(dampenedInitialMotionVector, initialHitVec);
        }
        double remainingDistanceToTravel = initialHitVec.distanceTo(attemptedNewPosition) * this.bounceDampeningFactor;

        BiFunction<Vector3d, Vector3d, Vector3d> calculateNextPosition = (hitVec, reflectionVector) -> hitVec.add(reflectionVector.normalize().scale(remainingDistanceToTravel));

        Vector3d reflectionVector = VecUtil.calculateReflection(dampenedInitialMotionVector, directionVector1);
        Vector3d nextPosition = calculateNextPosition.apply(initialHitVec, reflectionVector);

        // Make sure we won't go into another block
        BlockRayTraceResult rayResult = rayTrace(previousPosition, nextPosition);
        if (rayResult.getType() != RayTraceResult.Type.BLOCK) {
            return new BounceSolution(reflectionVector, nextPosition);
        }

        // We must have hit a corner. Try reflecting off combined direction vectors.
        Vector3d directionVector2 = getDirectionVector.apply(rayResult);
        Vector3d combinedDirectionVector = directionVector1.add(directionVector2).normalize();
        reflectionVector = VecUtil.calculateReflection(dampenedInitialMotionVector, combinedDirectionVector);

        Vector3d hitVec = VecUtil.getMiddle(initialHitVec, rayResult.getLocation());
        nextPosition = calculateNextPosition.apply(hitVec, reflectionVector);

        rayResult = rayTrace(previousPosition, nextPosition);
        if (rayResult.getType() != RayTraceResult.Type.BLOCK) {
            return new BounceSolution(reflectionVector, nextPosition);
        }

        // One more time for a 3 block corner
        Vector3d directionVector3 = getDirectionVector.apply(rayResult);
        combinedDirectionVector = directionVector1.add(directionVector2).add(directionVector3).normalize();
        reflectionVector = VecUtil.calculateReflection(dampenedInitialMotionVector, combinedDirectionVector);

        hitVec = VecUtil.getMiddle(hitVec, rayResult.getLocation());
        nextPosition = calculateNextPosition.apply(hitVec, reflectionVector);

        rayResult = rayTrace(previousPosition, nextPosition);
        if (rayResult.getType() != RayTraceResult.Type.BLOCK) {
            return new BounceSolution(reflectionVector, nextPosition);
        }

        // Fall back to inverting the motion
        reflectionVector = dampenedInitialMotionVector.reverse();
        nextPosition = calculateNextPosition.apply(initialHitVec, reflectionVector);
        return new BounceSolution(reflectionVector, nextPosition);
    }

    private void setPositionToVec(Vector3d vector) {
        setPos(vector.x, vector.y, vector.z);
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

    @Override
    public void setPos(double x, double y, double z) {
        if (this.level.isClientSide && !this.settingPositionOnUpdate)
            handleRemotePositionUpdate(x, y, z);
        else {
            super.setPos(x, y, z);
        }
    }

    private void handleRemotePositionUpdate(double x, double y, double z) {
        /*
         * Position updates from the server are inaccurate. If you always take them they
         * cause jitter and weird positioning (like putting things underground). So only
         * take updates that are significantly different.
         */
        if (Math.abs(getX() - x) > 2.0 || Math.abs(getY() - y) > 2.0 || Math.abs(getZ() - z) > 2.0) {
            super.setPos(x, y, z);
        }
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

