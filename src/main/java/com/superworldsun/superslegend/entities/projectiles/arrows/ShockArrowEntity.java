package com.superworldsun.superslegend.entities.projectiles.arrows;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.interfaces.IShockChargeableCreeper;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.registries.TagInit;
import com.superworldsun.superslegend.util.ProjectileFluidCollisionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

public class ShockArrowEntity extends AbstractArrow
{
    // The radius, in blocks, of a discharge caused by source water or a wet target.
    private static final double WATER_SHOCK_RADIUS = 8.0D;
    // Three damage equals one and a half hearts before armor and other reductions.
    private static final float WATER_SHOCK_DAMAGE = 3.0F;

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    private boolean hasWaterDischarged;
    private boolean suppressDirectHitSound;

    public ShockArrowEntity(EntityType<? extends ShockArrowEntity> type, Level level)
    {
        super(type, level);
        setBaseDamage(4.0D);
    }

    public ShockArrowEntity(Level worldIn, LivingEntity shooter)
    {
        super(EntityTypeInit.SHOCK_ARROW.get(), shooter, worldIn);
        setBaseDamage(4.0D);
    }

    @Override
    protected @NotNull ItemStack getPickupItem()
    {
        return new ItemStack(ItemInit.SHOCK_ARROW.get());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        Vec3 previousPosition = this.position();
        super.tick();

        if (!this.level().isClientSide && !this.isRemoved() && !this.hasWaterDischarged) {
            Vec3 waterContact = findSourceWaterContact(previousPosition, this.position());
            if (waterContact != null) {
                dischargeWaterShock(waterContact);
                this.discard();
                return;
            }
        }

        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D,
                    0.0D);
        }
    }

    private Vec3 findSourceWaterContact(Vec3 start, Vec3 end) {
        var contact = ProjectileFluidCollisionHelper.findContact(
                this.level(), this, start, end, FluidTags.WATER, true);
        return contact == null ? null : contact.location();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        boolean hitWetTarget = entity instanceof LivingEntity livingEntity
                && livingEntity.isInWaterRainOrBubble();
        double normalBaseDamage = getBaseDamage();
        if (entity instanceof LivingEntity livingentity) {
            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GREEN_HOLY_RING.get(), livingentity).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            if (stack.isEmpty() && entity.isAlive()) {
                int armorPieces = getMetalicArmorPieces(livingentity);
                setBaseDamage(normalBaseDamage * getMetalicArmorDamageMultiplier(armorPieces));
                if (armorPieces == 4 && getOwner() instanceof ServerPlayer player) {
                    ModAdvancementHelper.award(player, "fully_conductive", "shocked_full_metal_armor");
                }
            }
        }

        this.suppressDirectHitSound = hitWetTarget;
        try {
            super.onHitEntity(result);
        } finally {
            // Piercing arrows may hit several entities. Restore the base damage
            // so each target is calculated independently instead of compounding.
            setBaseDamage(normalBaseDamage);
            this.suppressDirectHitSound = false;
        }

        if (hitWetTarget && !this.level().isClientSide && !this.hasWaterDischarged) {
            dischargeWaterShock(entity.getBoundingBox().getCenter());
        }

    }

    private void dischargeWaterShock(Vec3 center) {
        if (this.hasWaterDischarged || !(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        this.hasWaterDischarged = true;
        this.level().playSound(null, center.x, center.y, center.z, SoundInit.ARROW_HIT_SHOCK.get(),
                SoundSource.PLAYERS, 1.25F, 0.9F + this.random.nextFloat() * 0.2F);

        spawnWaterShockParticles(serverLevel, center);
        serverLevel.sendParticles(ParticleTypes.FLASH,
                center.x, center.y, center.z,
                2, 0.35D, 0.35D, 0.35D, 0.0D);

        AABB affectedArea = AABB.ofSize(center,
                WATER_SHOCK_RADIUS * 2.0D,
                WATER_SHOCK_RADIUS * 2.0D,
                WATER_SHOCK_RADIUS * 2.0D);
        double radiusSquared = WATER_SHOCK_RADIUS * WATER_SHOCK_RADIUS;

        int entitiesHit = 0;
        for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, affectedArea,
                LivingEntity::isAlive)) {
            Vec3 targetCenter = target.getBoundingBox().getCenter();
            if (targetCenter.distanceToSqr(center) <= radiusSquared) {
                // Do not exclude the owner, firing a Shock Arrow at nearby
                // water is intentionally dangerous to its user as well.
                if (target.hurt(this.damageSources().arrow(this, this.getOwner()), WATER_SHOCK_DAMAGE)) {
                    entitiesHit++;
                    if (getMetalicArmorPieces(target) == 4 && getOwner() instanceof ServerPlayer player) {
                        ModAdvancementHelper.award(player, "fully_conductive", "shocked_full_metal_armor");
                    }
                }
            }
        }
        if (entitiesHit >= 5 && getOwner() instanceof ServerPlayer player) {
            ModAdvancementHelper.award(player, "chain_reaction", "five_entity_water_shock");
        }
    }

    private void spawnWaterShockParticles(ServerLevel serverLevel, Vec3 center) {
        // Vanilla's multi-particle spread is Gaussian and can place particles
        // beyond its supplied offsets. Generate bounded points instead so the
        // visible electric cloud precisely occupies the damaging sphere.
        for (int i = 0; i < 500; i++) {
            double verticalDirection = this.random.nextDouble() * 2.0D - 1.0D;
            double horizontalDirection = Math.sqrt(1.0D - verticalDirection * verticalDirection);
            double angle = this.random.nextDouble() * Math.PI * 2.0D;

            // Cube-root sampling distributes points evenly throughout a sphere
            // instead of clustering most of them around its center.
            double distance = WATER_SHOCK_RADIUS * Math.cbrt(this.random.nextDouble());
            double x = center.x + Math.cos(angle) * horizontalDirection * distance;
            double y = center.y + verticalDirection * distance;
            double z = center.z + Math.sin(angle) * horizontalDirection * distance;

            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.02D);
        }
    }

    private static int getMetalicArmorPieces(LivingEntity entity) {
        int pieces = 0;
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            if (entity.getItemBySlot(slot).is(TagInit.METALIC_ARMOR)) {
                pieces++;
            }
        }
        return pieces;
    }

    private static double getMetalicArmorDamageMultiplier(int armorPieces) {
        return switch (armorPieces) {
            case 1 -> 3.0D;
            case 2 -> 4.0D;
            case 3 -> 5.0D;
            case 4 -> 6.5D;
            default -> 1.0D;
        };
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);

        if (entity.level().isClientSide) {
            return;
        }

        BlockPos currentPos = entity.blockPosition();
        ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.GREEN_HOLY_RING.get(), entity).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (stack.isEmpty()) {
            if (!this.suppressDirectHitSound) {
                entity.level().playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.ARROW_HIT_SHOCK.get(), SoundSource.PLAYERS, 1f, 1f);
            }
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 50, false, true, false));
        }

        if (entity instanceof Creeper creeper && Config.shockArrowCreeper()) {
            ((IShockChargeableCreeper) creeper).superslegend$setShockCharged();
        }

        // Shock arrows should not remain visibly embedded in a target after a
        // successful non-piercing hit. Clamp the value in case another mod has
        // already adjusted the target's embedded-arrow count.
        if (this.getPierceLevel() <= 0) {
            entity.setArrowCount(Math.max(0, entity.getArrowCount() - 1));
        }
    }
}
