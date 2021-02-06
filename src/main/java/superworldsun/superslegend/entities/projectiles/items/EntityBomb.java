package superworldsun.superslegend.entities.projectiles.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.lists.ItemList;

import javax.annotation.Nonnull;

public class EntityBomb extends ProjectileItemEntity {


    private static final LivingEntity throwerIn = null;
    private static final World worldIn = null;

    public EntityBomb(EntityType<? extends EntityBomb> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public EntityBomb(World worldIn, LivingEntity throwerIn) {
        super(EntityInit.BOMBENTITY.get(), throwerIn, worldIn);
    }

    public EntityBomb(World worldIn, double x, double y, double z) {
        super(EntityInit.BOMBENTITY.get(), x, y, z, worldIn);
    }

    @Override
    @Nonnull
    protected Item getDefaultItem() {
        return ItemList.bomb;
    }

    @OnlyIn(Dist.CLIENT)
    private IParticleData makeParticle() {
        ItemStack itemstack = this.func_213882_k();
        return (IParticleData)(itemstack.isEmpty() ? ParticleTypes.EXPLOSION_EMITTER : new ItemParticleData(ParticleTypes.ITEM, itemstack));
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            IParticleData iparticledata = this.makeParticle();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(iparticledata, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    private int fuse = 80;


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
        }

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
        }*/

    }


    /**
     * Called when the bomb hits an entity.
     * No fuss when hitting entity!
     */
    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        Entity entity = p_213868_1_.getEntity();
        int i = entity instanceof BlazeEntity ? 3 : 0;
        this.explode();
        entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), (float)i);
    }

    /**
     * Called when this EntityBomb hits a block or entity.
     * Fuse for explosion due to not directly hitting entity.
     * AKA AOE damage with countdown.
     */
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            --this.fuse;
            if (this.fuse <= 0) {
                this.remove();
                if (!this.world.isRemote) {
                    this.explode();
                }
            }
            this.remove();
        }

    }

    protected void explode() {
        float f = 4.0F;
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 4.0F, Explosion.Mode.NONE);
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

