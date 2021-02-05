package superworldsun.superslegend.entities.projectiles.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.lists.ItemList;

public class EntityBomb extends ThrowableEntity {


    private static final LivingEntity throwerIn = null;
    private static final World worldIn = null;

    public EntityBomb(World worldIn, LivingEntity throwerIn) {
        super(EntityType.SNOWBALL, throwerIn, worldIn);
    }

    private int fuse = 80;

    //Uses Custom Bomb Entity, Currently has No model or render
    /*public EntityBomb(EntityType<EntityBomb> entityBombEntityType, World world) {
        super(EntityInit.BOMBENTITY.get(), world);
    }*/


    @Override
    protected void registerData() {

    }

    public void tick() {
        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.25D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.0D, 0.0D, 0.0D));
        }

        /*if (!this.onGround) {
            //this.setMotion(this.getMotion().mul(0.0D, -0.01D, 0.0D));
            this.setMotion(0f,-0.1f,0f);
        }*/

        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
                this.explode();
            }
        } else {
            this.func_233566_aG_();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void explode() {
        float f = 4.0F;
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 4.0F, Explosion.Mode.BREAK);
    }

    /*@Override
    public void tick() {
        super.tick();
        {
            if (!this.isInWater()) {
                world.createExplosion((Entity) null, this.prevPosX, this.prevPosY, this.prevPosZ, 3.0f, Explosion.Mode.NONE);
                this.remove();
            } else {
                this.remove();
            }
        }
    }*/
}

