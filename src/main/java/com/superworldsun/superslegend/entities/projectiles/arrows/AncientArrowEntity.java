package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
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
