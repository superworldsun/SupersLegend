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

public class FireArrowEntity extends AbstractArrow
{
    public FireArrowEntity(EntityType<? extends FireArrowEntity> type, Level level)
    {
        super(type, level);
    }

    public FireArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.FIRE_ARROW.get(), shooter, worldIn);
    }

    public FireArrowEntity(Level worldIn, double x, double y, double z) {
        super(EntityTypeInit.FIRE_ARROW.get(), x, y, z, worldIn);
    }

    public FireArrowEntity(EntityType<? extends FireArrowEntity> type, Level worldIn, LivingEntity shooter)
    {
        super(type, shooter, worldIn);
    }

    public FireArrowEntity(EntityType<? extends FireArrowEntity> type, Level levelIn, double x, double y, double z)
    {
        super(type, x, y, z, levelIn);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        setBaseDamage(4.0D);
    }

    @Override
    protected ItemStack getPickupItem()
    {
        return new ItemStack(ItemInit.FIRE_ARROW.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /*private void applyResistanceAndWeakness(Entity entity) {
        if (TagInit.RESISTANT_TO_FIRE.contains(entity.getType()))
            setBaseDamage(getBaseDamage() / 2f);
        else if (TagInit.WEAK_TO_FIRE.contains(entity.getType()))
            setBaseDamage(getBaseDamage() * 2f);
    }*/

    @Override
    protected void doPostHurtEffects(LivingEntity entity)
    {
        super.doPostHurtEffects(entity);
        entity.setSecondsOnFire(6);
        playSound(SoundInit.ARROW_HIT_FIRE.get(), 1f, 1f);
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
