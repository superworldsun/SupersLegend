package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SilverArrowEntity extends AbstractArrowEntity
{

    public SilverArrowEntity(EntityType<? extends SilverArrowEntity> type, World world) {
        super(type, world);
    }

    public SilverArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntityTypeInit.SILVER_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 2.0F);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.SILVER_ARROW.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        if(living instanceof WitherSkeletonEntity)
            this.setBaseDamage(this.getBaseDamage() * 100);
        super.doPostHurtEffects(living);
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        super.setSoundEvent(soundIn);
    }

    /*@Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        if(entity.isEntityUndead()){
            this.setDamageValue(this.getDamage()*20);
            }
        if(!entity.isEntityUndead())
        {
            this.setDamageValue(this.getDamage()*1.5);
        }
    }*/
}
