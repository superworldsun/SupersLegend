package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShockArrowEntity extends AbstractArrowEntity {

    public ShockArrowEntity(EntityType<? extends ShockArrowEntity> type, World world) {
        super(type, world);
    }

    public ShockArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntityTypeInit.SHOCK_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 2.0F);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.SHOCK_ARROW.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    public void tick() {
        super.tick();

        if (!this.inGround) {
            this.level.addParticle(ParticleTypes.BUBBLE_POP, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;

            this.getBaseDamage();
            if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }
    }

    protected void doPostHurtEffects(LivingEntity entity) {
        BlockPos currentPos = entity.blockPosition();
    	//System.out.println("Client:" + entity.world.isRemote);

    	//LightningBoltEntity l1 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, entity.world);
    	//l1.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), 0, 0);
    	//entity.world.addEntity(l1);

        entity.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_SHOCK.get(), SoundCategory.PLAYERS, 1f, 1f);

        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 50, false, true));

	}
	
	public static EntityType<ShockArrowEntity> createEntityType()
	{
		return EntityType.Builder.<ShockArrowEntity>of(ShockArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":shock_arrow");
	}
}
