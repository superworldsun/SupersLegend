package com.superworldsun.superslegend.entities.projectiles.arrows;

import java.util.List;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;
import com.superworldsun.superslegend.util.BuildingHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MagicFireArrowEntity extends AbstractArrowEntity
{
	public MagicFireArrowEntity(EntityType<? extends MagicFireArrowEntity> type, World world)
	{
		super(type, world);
	}

	public MagicFireArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.MAGIC_FIRE_ARROW.get(), shooter, worldIn);
	}

	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return null;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity)
	{
		super.doPostHurtEffects(entity);
		playSound(SoundInit.MAGIC_ARROW_HIT_FIRE.get(), 1f, 1f);
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
		return new ItemStack(ItemInit.MAGIC_FIRE_ARROW.get());
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
			entity.setSecondsOnFire(6);
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

	private void applyResistanceAndWeakness(Entity entity) {
		if (TagInit.RESISTANT_TO_FIRE.contains(entity.getType()))
			setBaseDamage(getBaseDamage() / 2f);
		else if (TagInit.WEAK_TO_FIRE.contains(entity.getType()))
			setBaseDamage(getBaseDamage() * 2f);
	}

	@Override
	public boolean isInWater() {
		return false;
	}

	@Override
	public boolean isUnderWater() {
		return false;
	}

	@Override
	public void tick()
	{
		super.tick();

		addFireParticlesToFlightPath();
		burnGroundOnImpact();
	}

	private void burnGroundOnImpact() {
		if (this.inGround)
		{
			if (!this.isInWater())
			{
				if (level.isEmptyBlock(this.blockPosition()))
					level.setBlock(this.blockPosition(), Blocks.FIRE.defaultBlockState(), 11);

				playSound(SoundInit.MAGIC_ARROW_HIT_FIRE.get(), 1f, 1f);

				this.remove();

				setHorizontallyAdjacentBlocksAblaze();
			}
		}
	}

	private void addFireParticlesToFlightPath() {
		if (!this.inGround)
		{
			this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult rayTraceResult)
	{
		BlockState blockHit = level.getBlockState(rayTraceResult.getBlockPos());
		
		if (blockHit.getMaterial() == Material.ICE || blockHit.getMaterial() == Material.ICE_SOLID)
		{
			List<BlockPos> platformShape = BuildingHelper.createRoundPlatformShape(rayTraceResult.getBlockPos(), 3);
			// We want to replace only ice
			platformShape.removeIf(pos -> level.getBlockState(pos).getMaterial() != Material.ICE && level.getBlockState(pos).getMaterial() != Material.ICE_SOLID);
			platformShape.forEach(pos -> level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState()));
			remove();
		}
		
		playSound(SoundInit.MAGIC_ARROW_HIT_FIRE.get(), 1f, 1f);
		super.onHitBlock(rayTraceResult);
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
	
	public static EntityType<MagicFireArrowEntity> createEntityType()
	{
		return EntityType.Builder.<MagicFireArrowEntity>of(MagicFireArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":magic_fire_arrow");
	}
}
