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
        this.setDamage(this.getDamage() + 2.0F);
    }

    public EntityArrowBomb(World worldIn, double x, double y, double z) {
        super(EntityInit.BOMB_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.bomb_arrow);
    }


    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
            if (!this.isInWater()) {
                world.createExplosion((Entity) null, this.prevPosX, this.prevPosY, this.prevPosZ, 3.0f, Explosion.Mode.NONE);
                this.remove();
            } else {
                this.remove();
            }
        }
        if (!this.isWet() && !this.inGround) {
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D,
                    0.0D);
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D,
                    0.0D);
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D,
                    0.0D);
        }
        if(this.inWater)
        {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_DEFUSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.remove();
        }
        if(this.isBurning() || this.isInLava())
        {
            world.createExplosion((Entity) null, this.prevPosX, this.prevPosY, this.prevPosZ, 3.0f, Explosion.Mode.NONE);
            this.remove();
        }
        if(this.ticksExisted % 9 == 0)
        {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
}

