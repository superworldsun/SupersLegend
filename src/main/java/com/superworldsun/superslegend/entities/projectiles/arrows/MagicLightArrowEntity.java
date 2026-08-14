package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class MagicLightArrowEntity extends AbstractArrow
{
    public MagicLightArrowEntity(EntityType<? extends MagicLightArrowEntity> type, Level level)
    {
        super(type, level);
    }

    public MagicLightArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.MAGIC_LIGHT_ARROW.get(), shooter, worldIn);
    }

    @Override
    public void onAddedToWorld()
    {
        super.onAddedToWorld();
        setBaseDamage(4.0D);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(ItemInit.SILVER_ARROW.get());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitEntity(EntityHitResult result)
    {
        Entity entity = result.getEntity();

        if (!level().isClientSide && entity instanceof LivingEntity living
                && living.getMobType() == MobType.UNDEAD && getOwner() instanceof ServerPlayer player) {
            ModAdvancementHelper.award(player, "light_in_the_dark", "hit_undead");
        }

        applyResistanceAndWeakness(entity);

        super.onHitEntity(result);

        // There is a small time frame after an entity is hurt that gives
        // immunity to damage. And we already damaged it with common damage from
        // an arrow. To deal damage 2 times in a row, we have to reset it.
        entity.invulnerableTime = 0;
        entity.hurt(level().damageSources().magic(), 5.0F);

        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;

            this.getBaseDamage();
            if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }
    }

    @Override
    public void tick()
    {


        addParticlesToFlightPath();
        super.tick();
        GroundOnImpact();
    }

    private void GroundOnImpact() {
        if (this.inGround)
        {
            playSound(SoundInit.MAGIC_ARROW_HIT_LIGHT.get(), 1f, 1f);
            this.discard();
        }
    }

    private void addParticlesToFlightPath() {
        if (!this.inGround) {
            Vec3 motion = this.getDeltaMovement();

            for (int i = 0; i < 2; i++) {
                // Random offsets for particle spread
                double offsetX = (this.random.nextDouble() - 0.5D) * 0.1D;
                double offsetY = (this.random.nextDouble() - 0.5D) * 0.1D;
                double offsetZ = (this.random.nextDouble() - 0.5D) * 0.1D;

                this.level().addParticle(
                        ParticleTypes.TOTEM_OF_UNDYING,
                        this.getX() - motion.x * 0.25D + offsetX,
                        this.getY() - motion.y * 0.25D + offsetY,
                        this.getZ() - motion.z * 0.25D + offsetZ,
                        -motion.x * 0.15D + offsetX,
                        -motion.y * 0.15D + offsetY,
                        -motion.z * 0.15D + offsetZ
                );
            }

            for (int i = 0; i < 1; i++) {
                this.level().addParticle(
                        ParticleTypes.END_ROD,  // Additional particle type for glow effect
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        (this.random.nextDouble() - 0.5D) * 0.1D,
                        (this.random.nextDouble() - 0.5D) * 0.1D,
                        (this.random.nextDouble() - 0.5D) * 0.1D
                );
            }

            this.level().addParticle(
                    ParticleTypes.INSTANT_EFFECT,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0, 0, 0
            );
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity)
    {
        super.doPostHurtEffects(entity);
        playSound(SoundInit.MAGIC_ARROW_HIT_LIGHT.get(), 1f, 1f);
    }

    private void applyResistanceAndWeakness(Entity entity) {
        if (entity.getType().is(TagInit.WEAK_TO_LIGHT))
            setBaseDamage(getBaseDamage() * 8);
        if (entity.getType().is(TagInit.RESISTANT_TO_LIGHT))
            setBaseDamage(getBaseDamage() / 2);
    }

    public static EntityType<MagicLightArrowEntity> createEntityType()
    {
        return EntityType.Builder.<MagicLightArrowEntity>of(MagicLightArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build(SupersLegendMain.MOD_ID + ":magic_light_arrow");
    }
}
