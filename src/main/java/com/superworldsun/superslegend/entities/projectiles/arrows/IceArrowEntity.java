package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.util.BuildingHelper;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.util.List;

public class IceArrowEntity extends AbstractArrow
{
    public IceArrowEntity(EntityType<? extends IceArrowEntity> type, Level level)
    {
        super(type, level);
    }

    public IceArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.ICE_ARROW.get(), shooter, worldIn);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        setBaseDamage(4.0D);
    }

    @Override
    protected @NotNull ItemStack getPickupItem()
    {
        return new ItemStack(ItemInit.ICE_ARROW.get());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    //TODO, needs to be finished porting
    /*@Override
    public void tick()
    {
        if (!inGround)
        {
            level().addParticle(ParticleTypes.ITEM_SNOWBALL, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.SPIT, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
        }

        if (!leftOwner)
        {
            leftOwner = checkLeftOwner();
        }

        if (!level().isClientSide)
        {
            setSharedFlag(6, isGlowing());
        }

        baseTick();

        boolean flag = isNoPhysics();
        Vec3 vector3d = getDeltaMovement();
        if (xRotO == 0.0F && yRotO == 0.0F)
        {
            float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
            yRotO = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
            xRotO = (float) (MathHelper.atan2(vector3d.y, (double) f) * (double) (180F / (float) Math.PI));
            yRotO = yRotO;
            xRotO = xRotO;
        }

        BlockPos blockpos = blockPosition();
        BlockState blockstate = level().getBlockState(blockpos);

        if (!blockstate.getBlock().isAir(blockstate, level(), blockpos) && !flag)
        {
            VoxelShape voxelshape = blockstate.getCollisionShape(level(), blockpos);
            if (!voxelshape.isEmpty())
            {
                Vec3 vector3d1 = position();

                for (AxisAlignedBB axisalignedbb : voxelshape.toAabbs())
                {
                    if (axisalignedbb.move(blockpos).contains(vector3d1))
                    {
                        inGround = true;
                        break;
                    }
                }
            }
        }

        if (shakeTime > 0)
        {
            --shakeTime;
        }

        if (isInWaterOrRain())
        {
            clearFire();
        }

        if (inGround && !flag)
        {
            if (lastState != blockstate && shouldFall())
            {
                startFalling();
            }
            else if (!level().isClientSide)
            {
                tickDespawn();
            }

            ++inGroundTime;
        }
        else
        {
            inGroundTime = 0;
            Vec3 vector3d2 = position();
            Vec3 vector3d3 = vector3d2.add(vector3d);
            RayTraceResult raytraceresult = level()
                    .clip(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS)
            {
                vector3d3 = raytraceresult.getLocation();
            }

            while (isAlive())
            {
                EntityRayTraceResult entityraytraceresult = findHitEntity(vector3d2, vector3d3);
                if (entityraytraceresult != null)
                {
                    raytraceresult = entityraytraceresult;
                }

                if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY)
                {
                    Entity entity = ((EntityRayTraceResult) raytraceresult).getEntity();
                    Entity entity1 = getOwner();
                    if (entity instanceof Player && entity1 instanceof Player && !((Player) entity1).canHarmPlayer((Player) entity))
                    {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }

                if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag
                        && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
                {
                    onHit(raytraceresult);
                    hasImpulse = true;
                }

                if (entityraytraceresult == null || getPierceLevel() <= 0)
                {
                    break;
                }

                raytraceresult = null;
            }

            vector3d = getDeltaMovement();
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;
            if (isCritArrow())
            {
                for (int i = 0; i < 4; ++i)
                {
                    level().addParticle(ParticleTypes.CRIT, getX() + d3 * (double) i / 4.0D, getY() + d4 * (double) i / 4.0D, getZ() + d0 * (double) i / 4.0D,
                            -d3, -d4 + 0.2D, -d0);
                }
            }

            double d5 = getX() + d3;
            double d1 = getY() + d4;
            double d2 = getZ() + d0;
            float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
            if (flag)
            {
                yRotO = (float) (MathHelper.atan2(-d3, -d0) * (double) (180F / (float) Math.PI));
            }
            else
            {
                yRotO = (float) (MathHelper.atan2(d3, d0) * (double) (180F / (float) Math.PI));
            }

            xRotO = (float) (MathHelper.atan2(d4, (double) f1) * (double) (180F / (float) Math.PI));
            xRotO = lerpRotation(xRotO, xRotO);
            yRotO = lerpRotation(yRotO, yRotO);
            float f2 = 0.99F;
            if (isInWater())
            {
                for (int j = 0; j < 4; ++j)
                {
                    level().addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }

                f2 = getWaterInertia();
            }

            setDeltaMovement(vector3d.scale((double) f2));
            if (!isNoGravity() && !flag)
            {
                Vec3 vector3d4 = getDeltaMovement();
                setDeltaMovement(vector3d4.x, vector3d4.y - (double) 0.05F, vector3d4.z);
            }

            setPos(d5, d1, d2);
            checkInsideBlocks();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result)
    {
        super.onHitBlock(result);
        BlockState blockHit = level().getBlockState(result.getBlockPos());

        if (blockHit.getBlock() == Blocks.WATER)
        {
            List<BlockPos> platformShape = BuildingHelper.createRoundPlatformShape(result.getBlockPos(), 4);
            // We want to replace only water
            platformShape.removeIf(pos -> !level().getBlockState(pos).is(Blocks.WATER));
            platformShape.forEach(pos -> level().setBlockAndUpdate(pos, Blocks.FROSTED_ICE.defaultBlockState()));
            this.discard();
        }
        else if (blockHit.is(Blocks.LAVA))
        {
            // If source block
            if (blockHit.getValue(ForgeFlowingFluid.Flowing.LEVEL) == 0)
            {
                level().setBlockAndUpdate(result.getBlockPos(), Blocks.OBSIDIAN.defaultBlockState());
            }
            else
            {
                level().setBlockAndUpdate(result.getBlockPos(), Blocks.COBBLESTONE.defaultBlockState());
            }

            this.discard();
        }
        else
        {
            BlockPos hitPos = result.getBlockPos().relative(result.getDirection());

            if (level().isEmptyBlock(hitPos))
            {
                level().setBlock(hitPos, Blocks.SNOW.defaultBlockState(), 11);
            }

            this.discard();
        }

        playSound(SoundInit.ARROW_HIT_ICE.get(), 1f, 1f);
        super.onHitBlock(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult rayTraceResult)
    {
        super.onHitEntity(rayTraceResult);
        Entity entity = rayTraceResult.getEntity();

        /*if (TagInit.WEAK_TO_ICE.contains(entity.getType()))
        {
            setBaseDamage(getBaseDamage() * 2);
        }

        if (TagInit.RESISTANT_TO_ICE.contains(entity.getType()))
        {
            setBaseDamage(getBaseDamage() / 2);
        }

        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;

            this.getBaseDamage();
            if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }

        super.onHitEntity(rayTraceResult);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity)
    {
        super.doPostHurtEffects(entity);
        playSound(SoundInit.ARROW_HIT_ICE.get(), 1f, 1f);

        if (!entity.hasEffect(EffectInit.FREEZE.get()))
        {
            entity.addEffect(new EffectInstance(EffectInit.FREEZE.get(), 70, 1, false, false, false));
        }
    }

    private boolean shouldFall()
    {
        return this.inGround && this.level().noCollision((new AxisAlignedBB(this.position(), this.position())).inflate(0.06D));
    }

    private void startFalling()
    {
        this.inGround = false;
        Vec3 vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.multiply((double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F),
                (double) (this.random.nextFloat() * 0.2F)));
    }

    private boolean checkLeftOwner()
    {
        Entity entity = this.getOwner();
        if (entity != null)
        {
            for (Entity entity1 : this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D),
                    entity2 -> !entity2.isSpectator() && entity2.isPickable()))
            {
                if (entity1.getRootVehicle() == entity.getRootVehicle())
                {
                    return false;
                }
            }
        }

        return true;
    }*/
}
