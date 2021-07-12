package superworldsun.superslegend.entities.projectiles.beam;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import superworldsun.superslegend.config.SupersLegendConfig;
import superworldsun.superslegend.init.EntityInit;

public class EntitySwordBeam extends AbstractArrowEntity
{

    public EntitySwordBeam(EntityType<? extends EntitySwordBeam> type, World world) {
        super(type, world);
    }

    private int fuse = 40;

    public EntitySwordBeam(World worldIn, LivingEntity shooter) {
        super(EntityInit.SWORD_BEAM.get(), shooter, worldIn);
        //todo fix asap
        //this.getBaseDamage(this.getBaseDamage() + 1.0F);
        this.setSoundEvent(null);
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        if(living instanceof WitherSkeletonEntity)
        {
            this.remove();
        }
        super.doPostHurtEffects(living);
    }

    @Override
    protected float getWaterInertia() {
        return 1.0f;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.isOnGround()) {
            this.setNoGravity(true);
        }

        if(this.inGround) {
                this.remove();
        }

        if(this.isInWater() && this.inGround)
        {
            this.remove();
        }

        if(this.tickCount % 2 == 0) {
            this.level.addParticle(ParticleTypes.CRIT, this.getRandomX(1), this.getRandomY() * 1, this.getRandomZ(1), 0.0D, 0.0D, 0.0D);
        }

        Vector3d vec3d1 = this.position();
        Vector3d vec3d = this.position().add(this.getDeltaMovement());
        RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vec3d1, vec3d, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null));

        if (raytraceresult != null) {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos pos = new BlockPos(raytraceresult.getLocation());
                BlockState state = level.getBlockState(pos);

                if (state.getMaterial() == Material.PLANT && SupersLegendConfig.COMMON.breaksFlowers.get()) {
                    level.destroyBlock(pos, true);
                }
                if ((state.getMaterial() == Material.REPLACEABLE_PLANT) && SupersLegendConfig.COMMON.breaksTallGrass.get()) {
                    level.destroyBlock(pos, true);
                }
            }
        }
        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.level.isClientSide) {
                this.remove();
            }
        }
    }


    /*@Override
    protected void arrowHit(LivingEntity entity) {
        super.arrowHit(entity);
        if(entity.isEntityUndead()){
            this.setDamage(this.getDamage()*20);
            }
        if(!entity.isEntityUndead())
        {
            this.setDamage(this.getDamage()*1.5);
        }
    }*/
}
