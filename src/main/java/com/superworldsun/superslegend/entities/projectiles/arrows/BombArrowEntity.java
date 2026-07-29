package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.util.ProjectileFluidCollisionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import static com.superworldsun.superslegend.util.Functions.repeat;

public class BombArrowEntity extends AbstractArrow
{
    public BombArrowEntity(EntityType<? extends BombArrowEntity> type, Level level)
    {
        super(type, level);
    }

    public BombArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.BOMB_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 2.0F);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
    }

    @Override
    protected @NotNull ItemStack getPickupItem()
    {
        return new ItemStack(ItemInit.BOMB_ARROW.get());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity entity)
    {
        super.doPostHurtEffects(entity);
        if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
            entity.setArrowCount(entity.getArrowCount() - 1);
        }
    }

    @Override
    public void tick()
    {
        Vec3 previousPosition = this.position();
        super.tick();
        if (!level().isClientSide)
        {
            if (this.level().dimension().location().equals(level().NETHER.location()))
            {
                if(Config.explosivegriefing())
                {
                    this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0f, Level.ExplosionInteraction.BLOCK);
                    this.discard();
                }
                else
                {
                    BlockPos explosionPos = this.blockPosition();
                    this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0f, Level.ExplosionInteraction.NONE);

                    int radius = 3;
                    for (BlockPos pos : BlockPos.betweenClosed(explosionPos.offset(-radius, -radius, -radius), explosionPos.offset(radius, radius, radius))) {
                        Block block = this.level().getBlockState(pos).getBlock();
                        if (block == BlockInit.CRACKED_BOMB_WALL.get()) {
                            this.level().destroyBlock(pos, false);
                        }
                    }
                }
            }
            if (inGround)
            {
                if (!isInWater())
                    if(Config.explosivegriefing())
                    {
                        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0f, Level.ExplosionInteraction.BLOCK);
                        this.discard();
                    }
                    else
                    {
                        BlockPos explosionPos = this.blockPosition();
                        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0f, Level.ExplosionInteraction.NONE);

                        int radius = 3;
                        for (BlockPos pos : BlockPos.betweenClosed(explosionPos.offset(-radius, -radius, -radius), explosionPos.offset(radius, radius, radius))) {
                            Block block = this.level().getBlockState(pos).getBlock();
                            if (block == BlockInit.CRACKED_BOMB_WALL.get()) {
                                this.level().destroyBlock(pos, false);
                            }
                        }
                    }
                this.discard();
            }
        }

        if (defuseAtWaterSurface(previousPosition)) {
            return;
        }
        addSmokeToFlightPath();
        explodeInHeat();
        playFuseSoundEveryNinthTick();
    }

    private void addSmokeToFlightPath()
    {
        if (!this.isInWaterOrRain() && !this.inGround)
        {
            repeat(3, () -> this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D));
        }
    }

    private boolean defuseAtWaterSurface(Vec3 previousPosition)
    {
        if (this.level().isClientSide || this.isRemoved()) {
            return false;
        }

        var contact = ProjectileFluidCollisionHelper.findContact(
                this.level(), this, previousPosition, this.position(), FluidTags.WATER, false);
        if (contact == null) {
            return false;
        }

        this.level().playSound(null, contact.location().x, contact.location().y, contact.location().z,
                SoundInit.BOMB_DEFUSE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        this.discard();
        return true;
    }

    private void explodeInHeat()
    {
        if (this.isOnFire() || this.isInLava())
        {
            level().explode(null, this.xo, this.yo, this.zo, 3.0f, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    private void playFuseSoundEveryNinthTick()
    {
        if (this.tickCount % 9 == 0)
        {
            BlockPos currentPos = this.blockPosition();
            this.level().playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOMB_FUSE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

}
