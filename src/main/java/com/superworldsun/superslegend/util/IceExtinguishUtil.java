package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/** Shared server-side behavior for ice attacks striking a burning target. */
public final class IceExtinguishUtil {
    private static final String FIRE_ROD_IGNITER = SupersLegendMain.MOD_ID + ":FireRodIgniter";

    private IceExtinguishUtil() {
    }

    public static void extinguishIfBurning(Entity target) {
        extinguishIfBurning(target, null);
    }

    public static void markFireRodIgnition(Entity target, Player player) {
        if (!target.level().isClientSide && target.isOnFire()) {
            target.getPersistentData().putUUID(FIRE_ROD_IGNITER, player.getUUID());
        }
    }

    public static void extinguishIfBurning(Entity target, Player iceRodUser) {
        if (target.level().isClientSide || !target.isOnFire()) {
            return;
        }
        boolean completedCombo = iceRodUser instanceof ServerPlayer
                && target.getPersistentData().hasUUID(FIRE_ROD_IGNITER)
                && target.getPersistentData().getUUID(FIRE_ROD_IGNITER).equals(iceRodUser.getUUID());
        target.clearFire();
        target.getPersistentData().remove(FIRE_ROD_IGNITER);
        target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (completedCombo) {
            ModAdvancementHelper.award((ServerPlayer) iceRodUser, "fire_and_ice", "rod_combo");
        }
    }
}
