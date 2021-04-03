package superworldsun.superslegend.entities.projectiles.arrows;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowIce extends AbstractArrowEntity
{

    public EntityArrowIce(EntityType<? extends EntityArrowIce> type, World world) {
        super(type, world);
    }

    public EntityArrowIce(World worldIn, LivingEntity shooter) {
        super(EntityInit.ICE_ARROW.get(), shooter, worldIn);
        this.setDamage(this.getDamage() + 2.0F);
    }

    public EntityArrowIce(World worldIn, double x, double y, double z) {
        super(EntityInit.ICE_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.ice_arrow);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    protected void arrowHit(LivingEntity living) {
        living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 70, 255));
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
    public void tick() {
        super.tick();
        if(this.inGround){
        		if (world.isAirBlock(this.getPosition()))
                world.setBlockState(this.getPosition(), Blocks.SNOW.getDefaultState(), 11);


                BlockPos currentPos = this.getPosition();
                this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_ICE, SoundCategory.PLAYERS, 1f, 1f);

                this.remove();
            }

        if (!this.inGround)
        {
            this.world.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D,
                    0.0D);
            this.world.addParticle(ParticleTypes.SPIT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D,
                    0.0D);
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
