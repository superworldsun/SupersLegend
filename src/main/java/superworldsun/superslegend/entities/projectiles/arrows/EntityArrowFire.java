package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowFire extends ArrowEntity
{

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("zeldamod:textures/entity/arrows/arrow_fire.png");

    public EntityArrowFire(World worldIn, double x, double y, double z){
        super(worldIn, x, y, z);
    }

    public EntityArrowFire(World worldIn, LivingEntity shooter){
        super(worldIn, shooter);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.fire_arrow);
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        if(!world.isRaining() && !world.isThundering()){
            entity.setFire(6);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.inGround){
            if(!world.isRaining() && !world.isThundering()){
                world.setBlockState(this.getPosition(), Blocks.FIRE.getDefaultState(), 11);
                this.remove();
            }

        }
    }

}
