package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowShock extends ArrowEntity
{

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("zeldamod:textures/entity/arrows/shock_arrow.png");

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

        int i = this.getColor();

        double d0 = (double)(i >> 16 & 255) / 255.0D;
        double d1 = (double)(i >> 8 & 255) / 255.0D;
        double d2 = (double)(i >> 0 & 255) / 255.0D;

        world.addParticle(ParticleTypes.CRIT, entity.posX + (this.rand.nextDouble() - 0.5D) * (double)entity.getWidth(), entity.posY + this.rand.nextDouble() * (double)entity.getHeight(), entity.posZ + (this.rand.nextDouble() - 0.5D) * (double)entity.getWidth(), d0, d1, d2);
        entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 40, 255, false, false));

    }

}
