package superworldsun.superslegend.entities.projectiles.arrows;

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
import superworldsun.superslegend.init.EntityInit;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ItemList;

public class EntityArrowShock extends AbstractArrowEntity {

    public EntityArrowShock(EntityType<? extends EntityArrowShock> type, World world) {
        super(type, world);
    }

    public EntityArrowShock(World worldIn, LivingEntity shooter) {
        super(EntityInit.SHOCK_ARROW.get(), shooter, worldIn);
        this.setDamage(this.getDamage() + 2.0F);
    }

    public EntityArrowShock(World worldIn, double x, double y, double z) {
        super(EntityInit.SHOCK_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemList.shock_arrow);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    public void tick() {
        super.tick();

        if (!this.inGround) {
            this.world.addParticle(ParticleTypes.BUBBLE_POP, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D,
                    0.0D);
        }
    }


    protected void arrowHit(LivingEntity entity) {
        BlockPos currentPos = entity.getPosition();
    	//System.out.println("Client:" + entity.world.isRemote);

    	//LightningBoltEntity l1 = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, entity.world);
    	//l1.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), 0, 0);
    	//entity.world.addEntity(l1);

        entity.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_SHOCK, SoundCategory.PLAYERS, 1f, 1f);

        entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 50, false, true));

    }


}
