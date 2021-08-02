package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AncientArrowEntity extends AbstractArrowEntity
{

    public AncientArrowEntity(EntityType<? extends AncientArrowEntity> type, World world) {
        super(type, world);
    }

    public AncientArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntityTypeInit.ANCIENT_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 30.0F);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.ANCIENT_ARROW.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        playSoundAtBlockPosition(entity, SoundInit.ARROW_HIT_ANCIENT.get());

        /* This method contained the following if-statement
            if (entity.canChangeDimensions())
                entity.setHealth(0);
            else
                entity.setHealth(entity.getHealth() - 45);
         */
    }

    private void playSoundAtBlockPosition(LivingEntity entity, SoundEvent sound) {
        BlockPos currentPos = entity.blockPosition();
        entity.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), sound, SoundCategory.PLAYERS, 1f, 1f);
    }
}
