package com.superworldsun.superslegend.entities;

import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * Non-colliding visual which follows a player for the duration of Nayru's Love.
 * The protection itself remains on the player as a potion effect; this entity is
 * deliberately only responsible for the synchronized crystal presentation.
 */
public class NayrusLoveCrystalEntity extends Entity implements GeoEntity {
    public static final int EFFECT_DURATION_TICKS = 300;
    public static final int FLASH_DURATION_TICKS = 40;
    private static final int FLASH_INTERVAL_TICKS = 3;

    private static final EntityDataAccessor<Optional<UUID>> OWNER_ID = SynchedEntityData.defineId(
            NayrusLoveCrystalEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> REMAINING_TICKS = SynchedEntityData.defineId(
            NayrusLoveCrystalEntity.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    public NayrusLoveCrystalEntity(EntityType<? extends NayrusLoveCrystalEntity> type, Level level) {
        super(type, level);
        noPhysics = true;
        setNoGravity(true);
        setInvulnerable(true);
    }

    public NayrusLoveCrystalEntity(Level level, Player owner) {
        this(EntityTypeInit.NAYRUS_LOVE_CRYSTAL.get(), level);
        entityData.set(OWNER_ID, Optional.of(owner.getUUID()));
        entityData.set(REMAINING_TICKS, EFFECT_DURATION_TICKS);
        moveTo(owner.getX(), owner.getY(), owner.getZ(), 0.0F, 0.0F);
    }

    public static void spawnFor(ServerLevel level, Player owner) {
        // Recasting refreshes the visual rather than leaving overlapping crystals.
        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof NayrusLoveCrystalEntity crystal && crystal.isFor(owner.getUUID())) {
                crystal.discard();
            }
        }
        level.addFreshEntity(new NayrusLoveCrystalEntity(level, owner));
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(OWNER_ID, Optional.empty());
        entityData.define(REMAINING_TICKS, EFFECT_DURATION_TICKS);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            Player owner = getOwnerPlayer();
            if (owner != null) {
                // The server position keeps tracking/culling correct. The renderer anchors the
                // visual directly to the owner's interpolated position so the client does not
                // display the one-packet delay from moving this separate entity.
                setPos(owner.getX(), owner.getY(), owner.getZ());
            }

            if (owner == null || !owner.isAlive() || !hasNayrusLoveProtection(owner)) {
                discard();
                return;
            }

            int remaining = entityData.get(REMAINING_TICKS) - 1;
            entityData.set(REMAINING_TICKS, remaining);
            if (remaining <= 0) {
                discard();
            }
        }
    }

    private static boolean hasNayrusLoveProtection(Player owner) {
        MobEffectInstance resistance = owner.getEffect(MobEffects.DAMAGE_RESISTANCE);
        return resistance != null && resistance.getAmplifier() >= 99 && resistance.getDuration() > 0;
    }

    public Player getOwnerPlayer() {
        return entityData.get(OWNER_ID)
                .map(level()::getPlayerByUUID)
                .orElse(null);
    }

    private boolean isFor(UUID playerId) {
        return entityData.get(OWNER_ID).filter(playerId::equals).isPresent();
    }

    /** The final two seconds alternate visible/hidden at one steady rate. */
    public boolean isFlashHidden() {
        int remaining = entityData.get(REMAINING_TICKS);
        return remaining > 0
                && remaining <= FLASH_DURATION_TICKS
                && (remaining / FLASH_INTERVAL_TICKS & 1) == 0;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.hasUUID("Owner")) {
            entityData.set(OWNER_ID, Optional.of(tag.getUUID("Owner")));
        }
        entityData.set(REMAINING_TICKS, tag.getInt("RemainingTicks"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        entityData.get(OWNER_ID).ifPresent(uuid -> tag.putUUID("Owner", uuid));
        tag.putInt("RemainingTicks", entityData.get(REMAINING_TICKS));
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Rotation and flashing are renderer-driven; the model has no skeletal animation yet.
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
