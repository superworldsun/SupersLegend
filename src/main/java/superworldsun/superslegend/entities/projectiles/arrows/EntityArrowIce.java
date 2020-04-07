package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowIce extends ArrowEntity
{

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("zeldamod:textures/entity/arrows/arrow.png");

    public EntityArrowIce(World worldIn, double x, double y, double z){
        super(worldIn, x, y, z);
    }

    public EntityArrowIce(World worldIn, LivingEntity shooter){
        super(worldIn, shooter);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.ice_arrow);
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        entity.setLastAttackedEntity(null);
        entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 255, false, false));
        entity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 200, 255, false, false));
        
        BlockPos currentPos = entity.getPosition();
        entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
    }
    
    
    @Override
    public void tick() {
        super.tick();
        if(this.inGround){
            if(!world.isRaining() && !world.isThundering()){
                world.setBlockState(this.getPosition(), Blocks.SNOW.getDefaultState(), 11);
                
                BlockPos currentPos = this.getPosition();
                this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
                
                this.remove();
            }

        }
        if(this.isInWater())
        {
        	world.setBlockState(this.getPosition(), Blocks.ICE.getDefaultState(), 11);
        	
        	BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
        	
        	this.remove();
        }
    }


}
