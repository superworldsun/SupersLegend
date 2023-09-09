package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SilverArrowEntity extends AbstractArrow
{
    public SilverArrowEntity(EntityType<? extends SilverArrowEntity> type, Level level)
    {
        super(type, level);
    }

    public SilverArrowEntity(net.minecraft.world.level.Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.SILVER_ARROW.get(), shooter, worldIn);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        setBaseDamage(4.0D);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.SILVER_ARROW.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitEntity(EntityHitResult result)
    {
        Mob entity = (Mob) result.getEntity();

        var isType = entity.getType().is(TagInit.WEAK_TO_LIGHT);
        System.out.println(isType);
        if (entity.getType().is(TagInit.WEAK_TO_LIGHT) || entity.getMobType() == MobType.UNDEAD)
        {
            setBaseDamage(getBaseDamage() * 8);
        }
        else if (!entity.getType().is(TagInit.WEAK_TO_LIGHT))
        {

        }
        super.onHitEntity(result);
        LivingEntity livingentity = entity;
            this.getBaseDamage();
            if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }
}
