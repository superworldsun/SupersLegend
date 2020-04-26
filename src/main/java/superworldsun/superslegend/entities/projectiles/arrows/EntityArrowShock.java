package superworldsun.superslegend.entities.projectiles.arrows;

import java.util.Random;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowShock extends ArrowEntity
{

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("superslegend:textures/models/arrow/shock_arrow.png");

    public EntityArrowShock(World worldIn, double x, double y, double z){
        super(worldIn, x, y, z);
    }

    public EntityArrowShock(World worldIn, LivingEntity shooter){
        super(worldIn, shooter);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.shock_arrow);
    }

    @Override
    protected void arrowHit(LivingEntity entity) {

    	System.out.println("Client:" + entity.world.isRemote);
    	
    	Random rand = entity.world.rand;
        for (int i = 0; i < 45; i++)
        {
        	entity.world.addParticle(ParticleTypes.CLOUD,
        			entity.posX + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 1,
        			entity.posY + rand.nextFloat() * 3 - 2,
        			entity.posZ + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 1) * 1,
                    0, 0.105D, 0);
        }
        
        
        BlockPos currentPos = entity.getPosition();
        entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_SHOCK, SoundCategory.PLAYERS, 1f, 1f);
        
        
        entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 255, false, false));

    }

}
