package com.superworldsun.superslegend.entities;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityPoisonArrow extends AbstractArrowEntity {

    public EntityPoisonArrow(EntityType<? extends EntityPoisonArrow> type, World world) {
        super(type, world);
    }

    public EntityPoisonArrow(World worldIn, LivingEntity shooter) {
        super(EntityTypeInit.POISON_ARROW.get(), shooter, worldIn);
    }

    public EntityPoisonArrow(World worldIn, double x, double y, double z) {
        super(EntityTypeInit.POISON_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.POISON_ARROW.get());
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();

        if (!level.isClientSide() && !this.inGround || !level.isClientSide() && !this.isInWaterOrRain() || !level.isClientSide() && !this.isInWater()) {
            this.level.addParticle(ParticleTypes.COMPOSTER, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
            this.level.addParticle(ParticleTypes.COMPOSTER, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        BlockPos currentPos = this.blockPosition();
        living.addEffect(new EffectInstance(Effects.POISON, 1200, 1));
        super.doPostHurtEffects(living);
    }

}