package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowSilver extends AbstractArrowEntity
{

    public EntityArrowSilver(EntityType<? extends EntityArrowSilver> type, World world) {
        super(type, world);
    }

    public EntityArrowSilver(World worldIn, LivingEntity shooter) {
        super(EntityInit.SILVER_ARROW.get(), shooter, worldIn);
        this.setDamage(this.getDamage() + 2.0F);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.silver_arrow);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        if(living instanceof WitherSkeletonEntity)
        {
            this.setDamage(this.getDamage() *100);
        }
        super.arrowHit(living);
    }

    @Override
    public void setHitSound(SoundEvent soundIn) {
        super.setHitSound(soundIn);
    }

    /*@Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        if(entity.isEntityUndead()){
            this.setDamage(this.getDamage()*20);
            }
        if(!entity.isEntityUndead())
        {
            this.setDamage(this.getDamage()*1.5);
        }
    }*/
}
