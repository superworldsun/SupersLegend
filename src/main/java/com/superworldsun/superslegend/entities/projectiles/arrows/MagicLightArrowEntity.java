package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class MagicLightArrowEntity extends AbstractArrow
{
    public MagicLightArrowEntity(EntityType<? extends MagicLightArrowEntity> type, Level level)
    {
        super(type, level);
    }

    public MagicLightArrowEntity(Level worldIn, LivingEntity shooter)
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
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(ItemInit.SILVER_ARROW.get());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
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
