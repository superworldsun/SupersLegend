package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowBomb extends AbstractArrowEntity {

    public EntityArrowBomb(EntityType<? extends EntityArrowBomb> type, World world) {
        super(type, world);
    }

    public EntityArrowBomb(World worldIn, LivingEntity shooter) {
        super(EntityInit.BOMB_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 2.0F);
    }

    public EntityArrowBomb(World worldIn, double x, double y, double z) {
        super(EntityInit.BOMB_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemList.bomb_arrow);
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        if (!level.isRaining() && !level.isThundering()) {
            level.explode((Entity) null, this.xo, this.yo, this.zo, 4.5f, Explosion.Mode.NONE);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround) {
            if (!this.isInWater()) {
                level.explode((Entity) null, this.xo, this.yo, this.zo, 3.0f, Explosion.Mode.NONE);
                this.remove();
            } else {
                this.remove();
            }
        }
        if (!this.isInWaterOrRain() && !this.inGround) {
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
        }
        if(this.wasTouchingWater)
        {
            BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_DEFUSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.remove();
        }
        if(this.isOnFire() || this.isInLava())
        {
            level.explode((Entity) null, this.xo, this.yo, this.zo, 3.0f, Explosion.Mode.NONE);
            this.remove();
        }
        if(this.tickCount % 9 == 0)
        {
            BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
}

