package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.config.SupersLegendConfig;
import superworldsun.superslegend.entities.projectiles.items.boomerang.BoomerangEntity;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

import java.util.ArrayList;

/*public class EntityIceBeam extends Entity
{


    public EntityIceBeam(EntityType<BoomerangEntity> boomerangEntityEntityType, World world) {
    }

    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private int fuse = 90;


    //Bomb Code
    /*public void tick() {

        this.canBeCollidedWith();
        if(this.isInWater())
        {
            world.setBlockState(this.getPosition(), Blocks.FROSTED_ICE.getDefaultState(), 11);
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
        if(this.isInLava())
        {
            world.setBlockState(this.getOnPosition(), Blocks.COBBLESTONE.getDefaultState());
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
        if(this.collidedHorizontally)
        {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
        if(!this.inGround)
        {
            this.setNoGravity(true);
        }
        /*if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }*/

        /*this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(1.0D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
                this.remove();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SPIT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.1D, 0.0D);
            }
        }

    }*/

    /*@Override
    protected void registerData() {

    }

    //Arrow Code
    @Override
    public void tick() {
        super.tick();

        if(this.collidedHorizontally)
        {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(1.0D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
        if(!this.onGround)
        {
            this.setNoGravity(true);
            //this.setNoGravity(true);
            //BlockPos currentPos = this.getPosition();
            //this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            //this.setVelocity(1, 1, 1);
            --this.fuse;
            if (this.fuse <= 0) {
                this.remove();
            }
        }
        if(this.isInWater())
        {
        	world.setBlockState(this.getPosition(), Blocks.FROSTED_ICE.getDefaultState(), 11);
        	BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
        	this.remove();
        }
        if(this.isInLava())
        {
            world.setBlockState(this.getOnPosition(), Blocks.COBBLESTONE.getDefaultState());
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
        else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SPIT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.1D, 0.0D);
            }
        }
    }

    //public void shootFromRotation(PlayerEntity player, float rotationPitch, float rotationYaw, float v, float v1, float v2) {
    //}

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




//}
