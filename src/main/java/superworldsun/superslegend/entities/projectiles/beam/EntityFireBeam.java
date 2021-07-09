package superworldsun.superslegend.entities.projectiles.beam;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;

public class EntityFireBeam extends AbstractArrowEntity
{
    public EntityFireBeam(EntityType<? extends EntityFireBeam> type, World world) {
        super(type, world);
    }

    public EntityFireBeam(World worldIn, LivingEntity shooter) {
        super(EntityInit.FIRE_BEAM.get(), shooter, worldIn);
        this.setDamage(this.getDamage() + 2.0F);
        this.setHitSound(null);
    }

    public EntityFireBeam(World worldIn, double x, double y, double z) {
        super(EntityInit.FIRE_BEAM.get(), x, y, z, worldIn);
    }

    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private int fuse = 90;

    @Override
    protected ItemStack getArrowStack() {
        return null;
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        BlockPos currentPos = this.getPosition();
        this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_FIRE, SoundCategory.PLAYERS, 1f, 1f);
        super.arrowHit(entity);
        if (entity.isAlive()) {
            entity.setFire(6);
        }
        if (entity.equals(EntityType.BLAZE) || entity.equals(EntityType.MAGMA_CUBE) || entity.equals(EntityType.HUSK)) {
            entity.setFire(6);
            this.setDamage(this.getDamage() / 5f);
        }
        if (entity.equals(EntityType.POLAR_BEAR) || entity.equals(EntityType.STRAY) || entity.equals(EntityType.SNOW_GOLEM)) {
            entity.setFire(6);
            this.setDamage(this.getDamage() * 2f);
        }
    }


    @Override
    public void tick() {
        super.tick();

        if(!this.isOnGround())
        {
            //this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.03D, 0.0D);
            this.setNoGravity(true);
        }

        if (this.inGround) {
            if (!this.isWet() || !this.isInWater()) {
                if (world.isAirBlock(this.getPosition()))
                    world.setBlockState(this.getPosition(), Blocks.FIRE.getDefaultState(), 11);

                BlockPos currentPos = this.getPosition();
                this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_FIRE, SoundCategory.PLAYERS, 1f, 1f);

                this.remove();
            }
        }

        if (this.isInWater()) {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }
    }

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

    //Arrow Code
    /*@Override
    public void tick() {
        super.tick();

        /*if(this.collidedHorizontally)
        {
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.remove();
        }*/

        //this.move(MoverType.SELF, this.getMotion());
        //this.setMotion(this.getMotion().scale(1.0D));
        /*if (this.onGround) {
            //this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
            BlockPos currentPos = this.getPosition();
            this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
            this.setNoGravity(false);
            this.remove();
        }
        if(!this.inGround)
        {
            //this.setVelocity(-1f,0f,-1f);
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
        this.world.addParticle(ParticleTypes.SPIT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.1D, 0.0D);
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
