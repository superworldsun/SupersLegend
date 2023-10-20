package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class MagicFireArrowEntity extends AbstractArrow
{
    public MagicFireArrowEntity(EntityType<? extends MagicFireArrowEntity> type, Level level)
    {
        super(type, level);
    }

    public MagicFireArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.FIRE_ARROW.get(), shooter, worldIn);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        setBaseDamage(4.0D);
    }

    @Override
    protected @NotNull ItemStack getPickupItem()
    {
        return null;
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
        entity.setSecondsOnFire(6);

        playSound(SoundInit.ARROW_HIT_FIRE.get(), 1f, 1f);

        if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
            entity.setArrowCount(entity.getArrowCount() - 1);
        }
    }

    @Override
    public void tick()
    {
        super.tick();

        addFireParticlesToFlightPath();
        burnGroundOnImpact();
        extinguishInWater();
    }

    private void burnGroundOnImpact() {
        if (this.inGround)
        {
            if (!this.isInWaterOrRain())
            {
                if (level().isEmptyBlock(this.blockPosition()))
                    level().setBlock(this.blockPosition(), Blocks.FIRE.defaultBlockState(), 11);

                playSoundAtBlockPosition(SoundInit.ARROW_HIT_FIRE.get());

                this.discard();

                setHorizontallyAdjacentBlocksAblaze();
            }
        }
    }

    private void addFireParticlesToFlightPath() {
        if (!this.isInWaterOrRain() && !this.inGround)
        {
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    private void extinguishInWater() {
        // TODO shouldn't this include rain?
        if (this.isInWater())
        {
            playSoundAtBlockPosition(SoundEvents.FIRE_EXTINGUISH);
            this.discard();
        }
    }


    private void playSoundAtBlockPosition(SoundEvent soundEvent) {
        BlockPos currentPos = this.blockPosition();
        this.level().playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), soundEvent, SoundSource.PLAYERS, 1f,
                1f);
    }

    private void setHorizontallyAdjacentBlocksAblaze()
    {
        if (isEmptyBlock(Direction.WEST))
            level().setBlock(this.blockPosition().west(), Blocks.FIRE.defaultBlockState(), 11);
        if (isEmptyBlock(Direction.EAST))
            level().setBlock(this.blockPosition().east(), Blocks.FIRE.defaultBlockState(), 11);
        if (isEmptyBlock(Direction.NORTH))
            level().setBlock(this.blockPosition().north(), Blocks.FIRE.defaultBlockState(), 11);
        if (isEmptyBlock(Direction.SOUTH))
            level().setBlock(this.blockPosition().south(), Blocks.FIRE.defaultBlockState(), 11);
    }

    private boolean isEmptyBlock(Direction dir) {
        return level().isEmptyBlock(this.blockPosition().relative(dir));
    }
}
