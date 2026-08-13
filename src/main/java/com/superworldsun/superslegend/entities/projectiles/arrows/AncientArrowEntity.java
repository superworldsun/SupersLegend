package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class AncientArrowEntity extends AbstractArrow
{

    public AncientArrowEntity(EntityType<? extends AncientArrowEntity> type, Level level)
    {
        super(type, level);
        this.setBaseDamage(this.getBaseDamage());
    }

    public AncientArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.ANCIENT_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 30.0F);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity entity)
    {
        playSound(SoundInit.ARROW_HIT_ANCIENT.get(), 1f, 1f);

        if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
            entity.setArrowCount(entity.getArrowCount() - 1);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        boolean fullHealthMob = result.getEntity() instanceof Mob mob
                && mob.isAlive()
                && mob.getHealth() >= mob.getMaxHealth();
        super.onHitEntity(result);
        if (!level().isClientSide && fullHealthMob
                && result.getEntity() instanceof Mob mob
                && !mob.isAlive()
                && getOwner() instanceof ServerPlayer player) {
            ModAdvancementHelper.award(player, "ancient_annihilation", "one_hit_kill");
        }
    }

    @Override
    protected @NotNull ItemStack getPickupItem()
    {
        return new ItemStack(ItemInit.ANCIENT_ARROW.get());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
