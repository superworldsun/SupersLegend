package com.superworldsun.superslegend.entities.projectiles.arrows;

import static com.superworldsun.superslegend.util.Functions.repeat;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.config.SupersLegendConfig;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BombArrowEntity extends AbstractArrowEntity
{
	public BombArrowEntity(EntityType<? extends BombArrowEntity> type, World world)
	{
		super(type, world);
	}
	
	public BombArrowEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.BOMB_ARROW.get(), shooter, worldIn);
		this.setBaseDamage(this.getBaseDamage() + 2.0F);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemInit.BOMB_ARROW.get());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void doPostHurtEffects(LivingEntity entity)
	{
		super.doPostHurtEffects(entity);
		
		// TODO could be thundering without rain. Maybe use isInRainOrWater
		if (!level.isClientSide)
		{
			if (!level.isRaining() && !level.isThundering())
			{
				level.explode(null, this.xo, this.yo, this.zo, 4.5f, Mode.NONE);
			}
		}
	}

	@Override
	public void tick()
	{
		super.tick();

		if (!level.isClientSide)
		{
			if (inGround)
			{
				if (!isInWater())
					if(SupersLegendConfig.getInstance().explosivegriefing()){
						this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3.0f, Mode.BREAK);
						remove();
					}
					else
					{
						BlockPos explosionPos = this.blockPosition();
						this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3.0f, Explosion.Mode.NONE);

						int radius = 3;
						for (BlockPos pos : BlockPos.betweenClosed(explosionPos.offset(-radius, -radius, -radius), explosionPos.offset(radius, radius, radius))) {
							Block block = this.level.getBlockState(pos).getBlock();
							if (block == BlockInit.CRACKED_BOMB_WALL.get()) {
								this.level.destroyBlock(pos, false);
							}
						}
					}
				remove();
			}
		}

		addSmokeToFlightPath();
		defuseInWater();
		explodeInHeat();
		playFuseSoundEveryNinthTick();
	}

	private void playFuseSoundEveryNinthTick()
	{
		if (this.tickCount % 9 == 0)
		{
			BlockPos currentPos = this.blockPosition();
			this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE.get(), SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
	}
	
	private void explodeInHeat()
	{
		if (this.isOnFire() || this.isInLava())
		{
			level.explode(null, this.xo, this.yo, this.zo, 3.0f, Explosion.Mode.NONE);
			this.remove();
		}
	}
	
	private void defuseInWater()
	{
		if (this.wasTouchingWater)
		{
			BlockPos currentPos = this.blockPosition();
			this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_DEFUSE.get(), SoundCategory.PLAYERS, 1.0f, 1.0f);
			this.remove();
		}
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult result)
	{
		super.onHitEntity(result);
		Entity entity = result.getEntity();
		if (entity instanceof LivingEntity)
		{
			LivingEntity livingentity = (LivingEntity) entity;
			
			this.getBaseDamage();
			if (!this.level.isClientSide && this.getPierceLevel() <= 0)
			{
				livingentity.setArrowCount(livingentity.getArrowCount() - 1);
			}
		}
	}
	
	private void addSmokeToFlightPath()
	{
		if (!this.isInWaterOrRain() && !this.inGround)
		{
			repeat(3, () -> this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D));
		}
	}
	
	public static EntityType<BombArrowEntity> createEntityType()
	{
		return EntityType.Builder.<BombArrowEntity>of(BombArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":bomb_arrow");
	}
}
