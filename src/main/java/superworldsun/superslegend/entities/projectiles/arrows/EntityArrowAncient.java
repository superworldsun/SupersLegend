package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowAncient extends AbstractArrowEntity
{

    public EntityArrowAncient(EntityType<? extends EntityArrowAncient> type, World world) {
        super(type, world);
    }

    public EntityArrowAncient(World worldIn, LivingEntity shooter) {
        super(EntityInit.ANCIENT_ARROW.get(), shooter, worldIn);
        this.setDamage(this.getDamage() + 30.0F);
    }

    public EntityArrowAncient(World worldIn, double x, double y, double z) {
        super(EntityInit.ANCIENT_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.ancient_arrow);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        if(entity.isNonBoss()){
        	BlockPos currentPos = entity.getPosition();
            entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ANCIENT, SoundCategory.PLAYERS, 1f, 1f);
            //entity.setHealth(0);
        }else {
        	BlockPos currentPos = entity.getPosition();
            entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ANCIENT, SoundCategory.PLAYERS, 1f, 1f);
            //entity.setHealth(entity.getHealth()-45);
        }
    }
}
