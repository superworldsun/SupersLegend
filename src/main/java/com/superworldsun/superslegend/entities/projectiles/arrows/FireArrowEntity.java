package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireArrowEntity extends AbstractArrowEntity
{
	public FireArrowEntity(EntityType<? extends FireArrowEntity> type, World world)
	{
		super(type, world);
	}
	
	public FireArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.FIRE_ARROW.get(), shooter, worldIn);
	}

	public FireArrowEntity(EntityType<? extends FireArrowEntity> type, World worldIn, LivingEntity shooter)
	{
		super(type, shooter, worldIn);
	}
	
	public FireArrowEntity(EntityType<? extends FireArrowEntity> type, World worldIn, double x, double y, double z)
	{
		super(type, x, y, z, worldIn);
	}
	
	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		setBaseDamage(4.0D);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemInit.FIRE_ARROW.get());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult rayTraceResult)
	{
		Entity entity = rayTraceResult.getEntity();
		
		if (entity.isAlive()) {
			entity.setSecondsOnFire(6);
			applyResistanceAndWeakness(entity);
		}
		
		super.onHitEntity(rayTraceResult);
	}

	private void applyResistanceAndWeakness(Entity entity) {
		if (TagInit.RESISTANT_TO_FIRE.contains(entity.getType()))
			setBaseDamage(getBaseDamage() / 2f);
		else if (TagInit.WEAK_TO_FIRE.contains(entity.getType()))
			setBaseDamage(getBaseDamage() * 2f);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity)
	{
		super.doPostHurtEffects(entity);
		playSound(SoundInit.ARROW_HIT_FIRE.get(), 1f, 1f);
	}
	
	@Override
	public void tick()
	{
		super.tick();

		addFireParticlesToFlightPath();
		burnGroundOnImpact();
		extinguishInWater();
	}

	private void burnGroundOnImpact() {
		if (this.inGround)
		{
			if (!this.isInWaterOrRain())
			{
				if (level.isEmptyBlock(this.blockPosition()))
					level.setBlock(this.blockPosition(), Blocks.FIRE.defaultBlockState(), 11);

				playSoundAtBlockPosition(SoundInit.ARROW_HIT_FIRE.get());

				this.remove();

				setHorizontallyAdjacentBlocksAblaze();
			}
		}
	}

	private void addFireParticlesToFlightPath() {
		if (!this.isInWaterOrRain() && !this.inGround)
		{
			this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	private void extinguishInWater() {
		// TODO shouldn't this include rain?
		if (this.isInWater())
		{
			playSoundAtBlockPosition(SoundEvents.FIRE_EXTINGUISH);
			this.remove();
		}
	}

	private void playSoundAtBlockPosition(SoundEvent soundEvent) {
		BlockPos currentPos = this.blockPosition();
		this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), soundEvent, SoundCategory.PLAYERS, 1f,
				1f);
	}

	private void setHorizontallyAdjacentBlocksAblaze()
	{
		if (isEmptyBlock(Direction.WEST))
			level.setBlock(this.blockPosition().west(), Blocks.FIRE.defaultBlockState(), 11);
		if (isEmptyBlock(Direction.EAST))
			level.setBlock(this.blockPosition().east(), Blocks.FIRE.defaultBlockState(), 11);
		if (isEmptyBlock(Direction.NORTH))
			level.setBlock(this.blockPosition().north(), Blocks.FIRE.defaultBlockState(), 11);
		if (isEmptyBlock(Direction.SOUTH))
			level.setBlock(this.blockPosition().south(), Blocks.FIRE.defaultBlockState(), 11);
	}

	private boolean isEmptyBlock(Direction dir) {
		return level.isEmptyBlock(this.blockPosition().relative(dir));
	}
}
