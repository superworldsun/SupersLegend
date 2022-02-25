package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static net.minecraft.entity.CreatureAttribute.UNDEAD;

public class PelletEntity extends AbstractArrowEntity {

    public PelletEntity(EntityType<? extends PelletEntity> type, World world)
    {
        super(type, world);
    }

    public PelletEntity(World worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.PELLET.get(), shooter, worldIn);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        setBaseDamage(2.0D);
    }

    @Override
    protected ItemStack getPickupItem()
    {
        return new ItemStack(ItemInit.DEKU_SEEDS.get());
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.BAMBOO_BREAK;
    }

    @Override
    public IPacket<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (inGround) {
            this.remove();
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
}