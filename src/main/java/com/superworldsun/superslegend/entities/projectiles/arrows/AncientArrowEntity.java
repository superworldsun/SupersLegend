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

    public AncientArrowEntity(World worldIn, double x, double y, double z) {
        super(EntityTypeInit.ANCIENT_ARROW.get(), x, y, z, worldIn);
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
        if(entity.canChangeDimensions()){
        	BlockPos currentPos = entity.blockPosition();
            entity.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ANCIENT.get(), SoundCategory.PLAYERS, 1f, 1f);
            //entity.setHealth(0);
        }else {
        	BlockPos currentPos = entity.blockPosition();
            entity.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ANCIENT.get(), SoundCategory.PLAYERS, 1f, 1f);
            //entity.setHealth(entity.getHealth()-45);
        }
    }
}
