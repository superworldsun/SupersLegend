package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
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
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;

public class EntityIceBeamOld extends AbstractArrowEntity
{

    public EntityIceBeamOld(EntityType<? extends EntityIceBeamOld> type, World world) {
        super(type, world);
    }

    //public EntityIceBeamOld(World worldIn, LivingEntity shooter) {
        //super(EntityInit.ICE_BEAM.get(), shooter, worldIn);
        //this.setDamage(this.getDamage() + 2.0F);
    //}

    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private int fuse = 90;


    @Override
    protected void arrowHit(LivingEntity living) {
        living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 30, 255));
        BlockPos currentPos = living.getPosition();
        living.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);
        if(living.equals(EntityType.BLAZE)||living.equals(EntityType.MAGMA_CUBE)||living.equals(EntityType.HUSK))
        {
            this.setDamage(this.getDamage()*2);
        }
        if(living.equals(EntityType.POLAR_BEAR)||living.equals(EntityType.STRAY))
        {
            this.setDamage(this.getDamage()/2);
        }
        super.arrowHit(living);
    }

    @Override
    protected ItemStack getArrowStack() {
        return null;
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
            this.func_233566_aG_();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SPIT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.1D, 0.0D);
            }
        }

    }*/

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
        if(!this.inGround)
        {
            this.setVelocity(-1f,0f,-1f);
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
            this.func_233566_aG_();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SPIT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.1D, 0.0D);
            }
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
