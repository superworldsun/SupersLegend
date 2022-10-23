package com.superworldsun.superslegend.entities.projectiles.mastersword;

import static net.minecraft.entity.CreatureAttribute.UNDEAD;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MasterSwordSwordEntity extends AbstractArrowEntity
{
	public float boomerangRotation;
	
	private MasterSwordSwordEntity(EntityType<? extends MasterSwordSwordEntity> type, World world)
	{
		super(type, world);
	}
	
	public MasterSwordSwordEntity(World worldIn, LivingEntity shooter)
	{
		super(EntityTypeInit.MASTERSWORD_SWORD_ENTITY.get(), shooter, worldIn);
	}
	
	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		setBaseDamage(4.0D);
	}
	
	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected ItemStack getPickupItem()
	{
		return null;
	}
	
	@Override
	public boolean isNoGravity()
	{
		return true;
	}
	
	@Override
	public boolean isInWater()
	{
		return false;
	}
	
	@Override
	public boolean isUnderWater()
	{
		return false;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		determineRotation();
		
		for (boomerangRotation = boomerangRotation + 36F; boomerangRotation > 360F; boomerangRotation = boomerangRotation - 360F)
			;
		
		if (tickCount > 80)
		{
			remove();
		}
		else
		{
			if (inGround)
			{
				remove();
			}
		}
	}
	
	public void determineRotation()
	{
		Vector3d motion = this.getDeltaMovement();
		yRot = -57.29578F * (float) Math.atan2(motion.x, motion.z);
		double d1 = Math.sqrt(motion.z * motion.z + motion.x * motion.x);
		xRot = -57.29578F * (float) Math.atan2(motion.y, d1);
	}
	
	@Override
	public void playerTouch(PlayerEntity player)
	{
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult result)
	{
		Entity entity = result.getEntity();
		
		if (tickCount > 2)
		{
			if (entity instanceof CreatureEntity)
			{
				LivingEntity lentity = (CreatureEntity) result.getEntity();
				
				if (lentity instanceof WitherSkeletonEntity)
				{
					setBaseDamage(getBaseDamage() * 100);
				}
				else if (lentity.getMobType().equals(UNDEAD))
				{
					setBaseDamage(getBaseDamage() * 2);
				}
			}
			
			entity.hurt(DamageSource.arrow(this, entity), (float) (this.getBaseDamage()));
			remove();
		}
	}
	
	public static EntityType<MasterSwordSwordEntity> createEntityType()
	{
		return EntityType.Builder.<MasterSwordSwordEntity>of(MasterSwordSwordEntity::new, EntityClassification.MISC).sized(2F, 0.2F).build(SupersLegendMain.MOD_ID + ":mastersword_sword");
	}
}
