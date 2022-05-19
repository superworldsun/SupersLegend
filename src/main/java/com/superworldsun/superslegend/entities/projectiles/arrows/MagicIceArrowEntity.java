package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;
import com.superworldsun.superslegend.util.BuildingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class MagicIceArrowEntity extends AbstractArrowEntity
{
	public MagicIceArrowEntity(EntityType<? extends MagicIceArrowEntity> type, World world)
	{
		super(type, world);
	}

	public MagicIceArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.MAGIC_ICE_ARROW.get(), shooter, worldIn);
	}

	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return null;
	}

	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		setBaseDamage(6.0D);
	}

	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemInit.MAGIC_ICE_ARROW.get());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult rayTraceResult)
	{
		super.onHitEntity(rayTraceResult);
		// There is a small time frame after an entity is hurt that gives
		// immunity to damage. And we already damaged it with common damage from
		// an arrow. To deal damage 2 times in a row, we have to reset it.
		rayTraceResult.getEntity().invulnerableTime = 0;
		rayTraceResult.getEntity().hurt(DamageSource.MAGIC, 5.0F);

		Entity entity = rayTraceResult.getEntity();
		if (entity.isAlive()) {
			applyResistanceAndWeakness(entity);
		}
		if (entity instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity) entity;

			this.getBaseDamage();
			if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
				livingentity.setArrowCount(livingentity.getArrowCount() - 1);
			}
		}
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity)
	{
		super.doPostHurtEffects(entity);
		playSound(SoundInit.MAGIC_ARROW_HIT_ICE.get(), 1f, 1f);
		// TODO: create actual FREEZE effect
		entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 70, 255));
	}

	private void applyResistanceAndWeakness(Entity entity) {
		if (TagInit.RESISTANT_TO_ICE.contains(entity.getType()))
			setBaseDamage(getBaseDamage() / 2f);
		else if (TagInit.WEAK_TO_ICE.contains(entity.getType()))
			setBaseDamage(getBaseDamage() * 2f);
	}

	@Override
	public void tick()
	{
		if (!inGround)
		{
			level.addParticle(ParticleTypes.ITEM_SNOWBALL, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
			level.addParticle(ParticleTypes.SPIT, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
		}

		if (!leftOwner)
		{
			leftOwner = checkLeftOwner();
		}

		if (!level.isClientSide)
		{
			setSharedFlag(6, isGlowing());
		}

		baseTick();

		boolean flag = isNoPhysics();
		Vector3d vector3d = getDeltaMovement();
		if (xRotO == 0.0F && yRotO == 0.0F)
		{
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, (double) f) * (double) (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		BlockPos blockpos = blockPosition();
		BlockState blockstate = level.getBlockState(blockpos);

		if (!blockstate.getBlock().isAir(blockstate, level, blockpos) && !flag)
		{
			VoxelShape voxelshape = blockstate.getCollisionShape(level, blockpos);
			if (!voxelshape.isEmpty())
			{
				Vector3d vector3d1 = position();

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
			else if (!level.isClientSide)
			{
				tickDespawn();
			}

			++inGroundTime;
		}
		else
		{
			inGroundTime = 0;
			Vector3d vector3d2 = position();
			Vector3d vector3d3 = vector3d2.add(vector3d);
			RayTraceResult raytraceresult = level
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
					if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity) entity1).canHarmPlayer((PlayerEntity) entity))
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
					level.addParticle(ParticleTypes.CRIT, getX() + d3 * (double) i / 4.0D, getY() + d4 * (double) i / 4.0D, getZ() + d0 * (double) i / 4.0D,
							-d3, -d4 + 0.2D, -d0);
				}
			}

			double d5 = getX() + d3;
			double d1 = getY() + d4;
			double d2 = getZ() + d0;
			float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			if (flag)
			{
				yRot = (float) (MathHelper.atan2(-d3, -d0) * (double) (180F / (float) Math.PI));
			}
			else
			{
				yRot = (float) (MathHelper.atan2(d3, d0) * (double) (180F / (float) Math.PI));
			}

			xRot = (float) (MathHelper.atan2(d4, (double) f1) * (double) (180F / (float) Math.PI));
			xRot = lerpRotation(xRotO, xRot);
			yRot = lerpRotation(yRotO, yRot);
			float f2 = 0.99F;
			if (isInWater())
			{
				for (int j = 0; j < 4; ++j)
				{
					level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
				}

				f2 = getWaterInertia();
			}

			setDeltaMovement(vector3d.scale((double) f2));
			if (!isNoGravity() && !flag)
			{
				Vector3d vector3d4 = getDeltaMovement();
				setDeltaMovement(vector3d4.x, vector3d4.y - (double) 0.05F, vector3d4.z);
			}

			setPos(d5, d1, d2);
			checkInsideBlocks();
		}
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult rayTraceResult)
	{
		BlockState blockHit = level.getBlockState(rayTraceResult.getBlockPos());

		if (blockHit.getMaterial() == Material.WATER)
		{
			List<BlockPos> platformShape = BuildingHelper.createRoundPlatformShape(rayTraceResult.getBlockPos(), 4);
			// We want to replace only water
			platformShape.removeIf(pos -> !level.getBlockState(pos).is(Blocks.WATER));
			platformShape.forEach(pos -> level.setBlockAndUpdate(pos, Blocks.FROSTED_ICE.defaultBlockState()));
			remove();
		}
		else if (blockHit.is(Blocks.LAVA))
		{
			// If source block
			if (blockHit.getValue(FlowingFluidBlock.LEVEL) == 0)
			{
				level.setBlockAndUpdate(rayTraceResult.getBlockPos(), Blocks.OBSIDIAN.defaultBlockState());
			}
			else
			{
				level.setBlockAndUpdate(rayTraceResult.getBlockPos(), Blocks.COBBLESTONE.defaultBlockState());
			}

			remove();
		}
		else
		{
			BlockPos hitPos = rayTraceResult.getBlockPos().relative(rayTraceResult.getDirection());

			if (level.isEmptyBlock(hitPos))
			{
				level.setBlock(hitPos, Blocks.SNOW.defaultBlockState(), 11);
			}

			remove();
		}

		playSound(SoundInit.MAGIC_ARROW_HIT_ICE.get(), 1f, 1f);
		super.onHitBlock(rayTraceResult);
	}

	private boolean shouldFall()
	{
		return this.inGround && this.level.noCollision((new AxisAlignedBB(this.position(), this.position())).inflate(0.06D));
	}

	private void startFalling()
	{
		this.inGround = false;
		Vector3d vector3d = this.getDeltaMovement();
		this.setDeltaMovement(vector3d.multiply((double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F),
				(double) (this.random.nextFloat() * 0.2F)));
		this.life = 0;
	}

	private boolean checkLeftOwner()
	{
		Entity entity = this.getOwner();
		if (entity != null)
		{
			for (Entity entity1 : this.level.getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D),
					entity2 -> !entity2.isSpectator() && entity2.isPickable()))
			{
				if (entity1.getRootVehicle() == entity.getRootVehicle())
				{
					return false;
				}
			}
		}

		return true;
	}

}
