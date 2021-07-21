package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class IceArrowEntity extends AbstractArrowEntity
{

    public IceArrowEntity(EntityType<? extends IceArrowEntity> type, World world) {
        super(type, world);
    }

    public IceArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntityTypeInit.ICE_ARROW.get(), shooter, worldIn);
        this.setBaseDamage(this.getBaseDamage() + 2.0F);
    }

    public IceArrowEntity(World worldIn, double x, double y, double z) {
        super(EntityTypeInit.ICE_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemInit.ICE_ARROW.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        living.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 70, 255));
        BlockPos currentPos = living.blockPosition();
        living.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE.get(), SoundCategory.PLAYERS, 1f, 1f);
        if(living.equals(EntityType.BLAZE)||living.equals(EntityType.MAGMA_CUBE)||living.equals(EntityType.HUSK))
        {
            this.setBaseDamage(this.getBaseDamage()*2);
        }
        if(living.equals(EntityType.POLAR_BEAR)||living.equals(EntityType.STRAY))
        {
            this.setBaseDamage(this.getBaseDamage()/2);
        }
        super.doPostHurtEffects(living);
    }



    @Override
    public void tick() {
        super.tick();
        if(this.inGround){
        		if (level.isEmptyBlock(this.blockPosition()))
                level.setBlock(this.blockPosition(), Blocks.SNOW.defaultBlockState(), 11);


                BlockPos currentPos = this.blockPosition();
                this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE.get(), SoundCategory.PLAYERS, 1f, 1f);

                this.remove();
            }

        if (!this.inGround)
        {
            this.level.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
            this.level.addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
        }


        if(this.isInWater())
        {
        	level.setBlock(this.blockPosition(), Blocks.FROSTED_ICE.defaultBlockState(), 11);

        	BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE.get(), SoundCategory.PLAYERS, 1f, 1f);

        	this.remove();
        }

        if(this.isInLava())
        {

            level.setBlockAndUpdate(this.getOnPos(), Blocks.COBBLESTONE.defaultBlockState());

            BlockPos currentPos = this.blockPosition();
            this.level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE.get(), SoundCategory.PLAYERS, 1f, 1f);

            this.remove();
        }

    }

    //Removes Block Beneath it sometimes instead of just the fire
    /*@Override
    protected void onInsideBlock(BlockState state) {
        if (!this.isAirBorne) {
            BlockState block = world.getBlockState(getPosition());
            if(block == Blocks.FIRE.getDefaultState())
            {
                world.setBlockState(this.getOnPosition(), Blocks.AIR.getDefaultState());
                this.remove();
            }
        }
    }*/




}
