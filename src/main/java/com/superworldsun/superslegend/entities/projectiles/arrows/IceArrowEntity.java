package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.util.BuildingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class IceArrowEntity extends AbstractArrow {

    private BlockState lastStateChecked;
    private int inGroundTime;
    private int life;

    public IceArrowEntity(EntityType<? extends IceArrowEntity> type, Level level) {
        super(type, level);
    }

    public IceArrowEntity(Level level, LivingEntity shooter) {
        super(EntityTypeInit.ICE_ARROW.get(), shooter, level);
    }

    public IceArrowEntity(Level level, double x, double y, double z) {
        super(EntityTypeInit.ICE_ARROW.get(), x, y, z, level);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        BlockState blockHit = this.level().getBlockState(result.getBlockPos());
        if (blockHit.is(Blocks.WATER)) {
            List<BlockPos> platformShape = BuildingHelper.createRoundPlatformShape(result.getBlockPos(), 4);
            platformShape.removeIf(pos -> !this.level().getBlockState(pos).getFluidState().is(FluidTags.WATER));
            platformShape.forEach(pos -> this.level().setBlockAndUpdate(pos, Blocks.FROSTED_ICE.defaultBlockState()));
            this.discard();
        } else if (blockHit.is(Blocks.LAVA)) {
            if (blockHit.getValue(LiquidBlock.LEVEL) == 0) {
                this.level().setBlockAndUpdate(result.getBlockPos(), Blocks.OBSIDIAN.defaultBlockState());
            } else {
                this.level().setBlockAndUpdate(result.getBlockPos(), Blocks.COBBLESTONE.defaultBlockState());
            }
            this.discard();
        } else {
            BlockPos hitPos = result.getBlockPos().relative(result.getDirection());
            if (this.level().isEmptyBlock(hitPos)) {
                this.level().setBlock(hitPos, Blocks.SNOW.defaultBlockState(), 11);
            }
            this.discard();
        }

        this.playSound(SoundInit.ARROW_HIT_ICE.get(), 1f, 1f);
        super.onHitBlock(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        if (entity.getType().is(TagInit.WEAK_TO_ICE)) {
            setBaseDamage(getBaseDamage() * 2);
        }

        if (entity.getType().is(TagInit.RESISTANT_TO_ICE)) {
            setBaseDamage(getBaseDamage() / 2);
        }

        if (entity instanceof LivingEntity livingEntity) {
            if (!level().isClientSide() && getPierceLevel() <= 0) {
                livingEntity.setArrowCount(livingEntity.getArrowCount() - 1);
            }
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        playSound(SoundInit.ARROW_HIT_ICE.get(), 1f, 1f);

        if (!entity.hasEffect(EffectInit.FREEZE.get())) {
            entity.addEffect(new MobEffectInstance(EffectInit.FREEZE.get(), 70, 1, false, false, false));
        }
    }

    @Override
    public void tick() {
        baseTick();
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
        if (!this.inGround) {
            this.level().addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            this.level().addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

        if (!this.level().isClientSide) {
            this.setSharedFlag(6, this.isCurrentlyGlowing());
        }

        Vec3 movement = this.getDeltaMovement();
        double horizontalSpeed = movement.horizontalDistance();

        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            this.setYRot((float)(Mth.atan2(movement.x, movement.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(movement.y, horizontalSpeed) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level().getBlockState(blockpos);
        if (!blockstate.isAir() && !this.isNoPhysics()) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level(), blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vec31 = this.position();
                for(AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(vec31)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shakeTime > 0) {
            --this.shakeTime;
        }

        if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW)) {
            this.clearFire();
        }

        if (this.inGround) {
            if (this.shouldFallOutOfGround(blockstate)) {
                this.startFalling();
            } else if (!this.level().isClientSide) {
                this.tickDespawn();
            }
            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
            Vec3 vec32 = this.position();
            Vec3 vec33 = vec32.add(movement);
            HitResult hitresult = this.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitresult.getType() != HitResult.Type.MISS) {
                vec33 = hitresult.getLocation();
            }

            while(!this.isRemoved()) {
                EntityHitResult entityhitresult = this.findHitEntity(vec32, vec33);
                if (entityhitresult != null) {
                    hitresult = entityhitresult;
                }

                if (hitresult != null && hitresult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)hitresult).getEntity();
                    Entity entity1 = this.getOwner();
                    if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
                        hitresult = null;
                        entityhitresult = null;
                    }
                }

                if (hitresult != null && hitresult.getType() != HitResult.Type.MISS && !this.isNoPhysics() && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                    this.onHit(hitresult);
                    this.hasImpulse = true;
                }

                if (entityhitresult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                hitresult = null;
            }

            movement = this.getDeltaMovement();
            double d3 = movement.x;
            double d4 = movement.y;
            double d0 = movement.z;

            if (this.isCritArrow()) {
                for(int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.CRIT, this.getX() + d3 * (double)i / 4.0D, this.getY() + d4 * (double)i / 4.0D, this.getZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
                }
            }

            double d5 = this.getX() + d3;
            double d1 = this.getY() + d4;
            double d2 = this.getZ() + d0;
            float f1 = (float)movement.horizontalDistance();
            if (this.isNoPhysics()) {
                this.setYRot((float)(Mth.atan2(-d3, -d0) * (double)(180F / (float)Math.PI)));
            } else {
                this.setYRot((float)(Mth.atan2(d3, d0) * (double)(180F / (float)Math.PI)));
            }

            this.setXRot((float)(Mth.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI)));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            float f2 = 0.99F;
            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j) {
                    this.level().addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }

                f2 = this.getWaterInertia();
            }

            this.setDeltaMovement(movement.scale((double)f2));
            if (!this.isNoGravity() && !this.isNoPhysics()) {
                Vec3 vec34 = this.getDeltaMovement();
                this.setDeltaMovement(vec34.x, vec34.y - 0.05F, vec34.z);
            }

            this.setPos(d5, d1, d2);
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

    private void startFalling() {
        this.inGround = false;
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.multiply((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F)));
        this.life = 0;
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 1200) {
            this.discard();
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.ICE_ARROW.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static EntityType<IceArrowEntity> createEntityType() {
        return EntityType.Builder.<IceArrowEntity>of(IceArrowEntity::new, MobCategory.MISC)
                .sized(0.5F, 0.5F)
                .build(SupersLegendMain.MOD_ID + ":ice_arrow");
    }
}