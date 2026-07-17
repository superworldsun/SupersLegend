package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.util.BuildingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MagicIceArrowEntity extends AbstractArrow
{
    public MagicIceArrowEntity(EntityType<? extends MagicIceArrowEntity> type, Level level)
    {
        super(type, level);
    }
    private BlockState lastStateChecked;
    private int life;

    public MagicIceArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.MAGIC_ICE_ARROW.get(), shooter, worldIn);
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
    @Override
    public void tick() {
        baseTick();

        if (!this.inGround) {
            this.level().addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            this.level().addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

        BlockPos blockPos = getOnPos();
        if (isInWater()) {
            List<BlockPos> platformShape = BuildingHelper.createRoundPlatformShape(blockPos, 4);
            platformShape.removeIf(pos -> !this.level().getBlockState(pos).getFluidState().is(FluidTags.WATER));
            platformShape.forEach(pos -> this.level().setBlockAndUpdate(pos, Blocks.FROSTED_ICE.defaultBlockState()));
            this.discard();
        } if (isInLava()) {
            if (level().getBlockState(blockPos).getValue(LiquidBlock.LEVEL) == 0) {
                this.level().setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
            } else {
                this.level().setBlockAndUpdate(blockPos, Blocks.COBBLESTONE.defaultBlockState());
            }
            this.discard();
        }

        if (!this.level().isClientSide) {
            this.setSharedFlag(6, this.isCurrentlyGlowing());
        }

        boolean noPhysics = this.isNoPhysics();
        Vec3 movement = this.getDeltaMovement();

        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double f = movement.horizontalDistance();
            this.setYRot((float)(Mth.atan2(movement.x, movement.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(movement.y, f) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level().getBlockState(blockpos);

        if (!blockstate.isAir() && !noPhysics) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level(), blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 position = this.position();

                for(AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(position)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shakeTime > 0) {
            --this.shakeTime;
        }

        if (this.isInWaterOrRain()) {
            this.clearFire();
        }

        if (this.inGround && !noPhysics) {
            if (this.shouldFallOutOfGround(blockstate)) {
                this.startFalling();
            } else if (!this.level().isClientSide) {
                this.tickDespawn();
            }

            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
            Vec3 pos = this.position();
            Vec3 nextPos = pos.add(movement);
            HitResult hitresult = this.level().clip(new ClipContext(pos, nextPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

            if (hitresult.getType() != HitResult.Type.MISS) {
                nextPos = hitresult.getLocation();
            }

            while(this.isAlive()) {
                EntityHitResult entityhitresult = this.findHitEntity(pos, nextPos);
                if (entityhitresult != null) {
                    hitresult = entityhitresult;
                }

                if (hitresult != null && hitresult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)hitresult).getEntity();
                    Entity owner = this.getOwner();
                    if (entity instanceof Player && owner instanceof Player && !((Player)owner).canHarmPlayer((Player)entity)) {
                        hitresult = null;
                        entityhitresult = null;
                    }
                }

                if (hitresult != null && !noPhysics) {
                    this.onHit(hitresult);
                    this.hasImpulse = true;
                }

                if (entityhitresult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                hitresult = null;
            }

            movement = this.getDeltaMovement();
            double dx = movement.x;
            double dy = movement.y;
            double dz = movement.z;

            if (this.isCritArrow()) {
                for(int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.CRIT,
                            this.getX() + dx * (double)i / 4.0D,
                            this.getY() + dy * (double)i / 4.0D,
                            this.getZ() + dz * (double)i / 4.0D,
                            -dx, -dy + 0.2D, -dz);
                }
            }

            double newX = this.getX() + dx;
            double newY = this.getY() + dy;
            double newZ = this.getZ() + dz;

            float horizontalDistance = Mth.sqrt((float)movement.horizontalDistanceSqr());

            if (noPhysics) {
                this.setYRot((float)(Mth.atan2(-dx, -dz) * (double)(180F / (float)Math.PI)));
            } else {
                this.setYRot((float)(Mth.atan2(dx, dz) * (double)(180F / (float)Math.PI)));
            }

            this.setXRot((float)(Mth.atan2(dy, horizontalDistance) * (double)(180F / (float)Math.PI)));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));

            float inertia = 0.99F;

            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j) {
                    this.level().addParticle(ParticleTypes.BUBBLE,
                            newX - dx * 0.25D,
                            newY - dy * 0.25D,
                            newZ - dz * 0.25D,
                            dx, dy, dz);
                }

                inertia = this.getWaterInertia();
            }

            this.setDeltaMovement(movement.scale(inertia));

            if (!this.isNoGravity() && !noPhysics) {
                Vec3 vec3 = this.getDeltaMovement();
                this.setDeltaMovement(vec3.x, vec3.y - 0.05F, vec3.z);
            }

            this.setPos(newX, newY, newZ);
            this.checkInsideBlocks();
        }
    }

    private boolean shouldFallOutOfGround(BlockState currentState) {
        if (this.lastStateChecked == null) {
            this.lastStateChecked = currentState;
            return false;
        }
        if (currentState != this.lastStateChecked) {
            this.lastStateChecked = currentState;
            return true;
        }
        return false;
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 1200) {
            this.discard();
        }
    }

    private void startFalling() {
        this.inGround = false;
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.multiply((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F)));
        this.life = 0;
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
        // Reset the damage immunity from the arrow hit so the bonus magic damage applies
        entity.invulnerableTime = 0;
        entity.hurt(damageSources().magic(), 5.0F);

        if (entity.isAlive())
        {
            if (entity.getType().is(TagInit.WEAK_TO_ICE))
            {
                setBaseDamage(getBaseDamage() * 2);
            }

            if (entity.getType().is(TagInit.RESISTANT_TO_ICE))
            {
                setBaseDamage(getBaseDamage() / 2);
            }
        }

        if (entity instanceof LivingEntity livingentity) {
            if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity)
    {
        super.doPostHurtEffects(entity);
        playSound(SoundInit.ARROW_HIT_ICE.get(), 1f, 1f);

        if (!entity.hasEffect(EffectInit.FREEZE.get()))
        {
            entity.addEffect(new MobEffectInstance(EffectInit.FREEZE.get(), 70, 1, false, false, false));
        }
    }

    private boolean shouldFall()
    {
        return this.inGround && this.level().noCollision((new AABB(this.position(), this.position())).inflate(0.06D));
    }
}
