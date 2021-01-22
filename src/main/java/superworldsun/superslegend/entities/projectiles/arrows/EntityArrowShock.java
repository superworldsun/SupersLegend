package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

import javax.annotation.Nonnull;


// TODO Remove Fire from lightning strike
public class EntityArrowShock extends ArrowEntity
{


    public EntityArrowShock(World worldIn, LivingEntity shooter){
        super(worldIn, shooter);
    }

    @Nonnull
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.shock_arrow);
    }

    protected void arrowHit(LivingEntity entity) {
        BlockPos currentPos = entity.getPosition();
    	System.out.println("Client:" + entity.world.isRemote);

    	LightningBoltEntity l1 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, entity.world);
    	l1.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), 0, 0);
    	entity.world.addEntity(l1);

        entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_SHOCK, SoundCategory.PLAYERS, 1f, 1f);

        entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 50, false, true));

    }


}
