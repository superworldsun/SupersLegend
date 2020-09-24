package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowBomb extends ArrowEntity {

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("zeldamod:textures/entity/arrows/arrow.png");

    public EntityArrowBomb(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityArrowBomb(World worldIn, LivingEntity shooter) {
        super(worldIn, shooter);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.bomb_arrow);
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        if (!world.isRaining() && !world.isThundering()) {
            world.createExplosion((Entity) null, this.prevPosX, this.prevPosY, this.prevPosZ, 4.5f, Explosion.Mode.NONE);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround) {
            if (!world.isRaining() && !world.isThundering() && !this.isInWater()) {
                world.createExplosion((Entity) null, this.prevPosX, this.prevPosY, this.prevPosZ, 3.0f, Explosion.Mode.NONE);
                this.remove();
            } else {
                this.remove();
            }
        }
    }
}

