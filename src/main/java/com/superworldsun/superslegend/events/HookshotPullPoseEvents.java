package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.hooks.HookshotEntity;
import com.superworldsun.superslegend.entities.projectiles.hooks.LongshotEntity;
import com.superworldsun.superslegend.interfaces.IHookshotSwimAnimation;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class HookshotPullPoseEvents {
    private static final double HOOK_SEARCH_RANGE = 64.0D;
    private static final int RELEASE_CLEANUP_TICKS = 5;
    private static final Map<Player, SavedPose> PREVIOUS_POSES = Collections.synchronizedMap(new WeakHashMap<>());
    private static final Map<Player, PendingRestore> PENDING_RESTORES = Collections.synchronizedMap(new WeakHashMap<>());
    private static final Map<Player, Boolean> MANUAL_CRAWLING = Collections.synchronizedMap(new WeakHashMap<>());

    private HookshotPullPoseEvents() {
    }

    @SubscribeEvent
    public static void maintainPullPose(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;
        if (hasActiveBlockPull(player)) {
            player.setShiftKeyDown(false);
        }
        if (hasActivePull(player)) {
            apply(player);
        } else {
            release(player);
            maintainRestoredPose(player);
        }
    }

    public static void apply(Player player) {
        boolean blockPull = hasActiveBlockPull(player);
        if (blockPull) {
            player.setShiftKeyDown(false);
        }
        synchronized (PENDING_RESTORES) {
            PENDING_RESTORES.remove(player);
        }
        synchronized (PREVIOUS_POSES) {
            if (!PREVIOUS_POSES.containsKey(player)) {
                Pose forcedPose = player.getForcedPose();
                boolean manualCrawling;
                synchronized (MANUAL_CRAWLING) {
                    manualCrawling = MANUAL_CRAWLING.getOrDefault(player, false);
                }
                Pose externalForcedPose = forcedPose == Pose.SWIMMING ? null : forcedPose;
                Pose originalPose = manualCrawling
                        ? Pose.SWIMMING
                        : player.getPose() == Pose.SWIMMING ? Pose.STANDING : player.getPose();
                if (blockPull && originalPose == Pose.CROUCHING) {
                    originalPose = Pose.STANDING;
                }
                PREVIOUS_POSES.put(player, new SavedPose(externalForcedPose, originalPose));
            }
        }
        if (player.getForcedPose() != Pose.SWIMMING) {
            player.setForcedPose(Pose.SWIMMING);
            player.setPose(Pose.SWIMMING);
            player.refreshDimensions();
        }
    }

    public static void release(Player player) {
        SavedPose saved;
        synchronized (PREVIOUS_POSES) {
            saved = PREVIOUS_POSES.remove(player);
        }
        if (saved == null) {
            return;
        }

        restore(player, saved);
        synchronized (PENDING_RESTORES) {
            PENDING_RESTORES.put(player, new PendingRestore(saved, RELEASE_CLEANUP_TICKS));
        }
    }

    public static Vec3 getPullOrigin(Player player) {
        return player.position().add(0.0D, getPullEyeHeight(player), 0.0D);
    }

    public static boolean toggleManualCrawling(Player player) {
        boolean crawling = !isManualCrawling(player);
        setManualCrawling(player, crawling);
        return crawling;
    }

    public static void setManualCrawling(Player player, boolean crawling) {
        synchronized (MANUAL_CRAWLING) {
            MANUAL_CRAWLING.put(player, crawling);
        }
        synchronized (PENDING_RESTORES) {
            PENDING_RESTORES.remove(player);
        }
        synchronized (PREVIOUS_POSES) {
            if (PREVIOUS_POSES.containsKey(player)) {
                return;
            }
        }
        applyManualCrawlingPose(player, crawling);
    }

    public static boolean isManualCrawling(Player player) {
        synchronized (MANUAL_CRAWLING) {
            return MANUAL_CRAWLING.getOrDefault(player, false);
        }
    }

    public static boolean isHookPullPoseActive(Player player) {
        synchronized (PREVIOUS_POSES) {
            return PREVIOUS_POSES.containsKey(player);
        }
    }

    public static double getPullEyeHeight(Player player) {
        SavedPose saved;
        synchronized (PREVIOUS_POSES) {
            saved = PREVIOUS_POSES.get(player);
        }
        Pose originalPose = saved != null ? saved.pose() : Pose.STANDING;
        if (originalPose == Pose.SWIMMING) {
            originalPose = Pose.STANDING;
        }
        return player.getEyeHeight(originalPose);
    }

    private static void maintainRestoredPose(Player player) {
        PendingRestore pending;
        synchronized (PENDING_RESTORES) {
            pending = PENDING_RESTORES.get(player);
        }
        if (pending == null) {
            return;
        }

        restore(player, pending.saved());
        synchronized (PENDING_RESTORES) {
            if (pending.ticksRemaining() <= 1) {
                PENDING_RESTORES.remove(player);
            } else {
                PENDING_RESTORES.put(player, new PendingRestore(pending.saved(), pending.ticksRemaining() - 1));
            }
        }
    }

    private static void restore(Player player, SavedPose saved) {
        boolean manualCrawling = isManualCrawling(player);
        player.setForcedPose(manualCrawling ? Pose.SWIMMING : saved.forcedPose());
        player.setSwimming(false);
        Pose restoredPose = manualCrawling
                ? Pose.SWIMMING
                : saved.forcedPose() != null ? saved.forcedPose() : saved.pose();
        if (!manualCrawling && restoredPose == Pose.SWIMMING) {
            restoredPose = Pose.STANDING;
        }
        player.setPose(restoredPose);
        player.refreshDimensions();
        if (!manualCrawling && player instanceof IHookshotSwimAnimation animation) {
            animation.superslegend$resetHookshotSwimAnimation();
        }
    }

    private static void applyManualCrawlingPose(Player player, boolean crawling) {
        player.setForcedPose(crawling ? Pose.SWIMMING : null);
        player.setSwimming(false);
        player.setPose(crawling ? Pose.SWIMMING : Pose.STANDING);
        player.refreshDimensions();
    }

    private static boolean hasActivePull(Player player) {
        var searchArea = player.getBoundingBox().inflate(HOOK_SEARCH_RANGE);
        for (HookshotEntity hook : player.level().getEntitiesOfClass(HookshotEntity.class, searchArea,
                entity -> entity.isAlive() && entity.isPullingPlayer())) {
            if (hook.getHookOwner() == player) {
                return true;
            }
        }
        for (LongshotEntity hook : player.level().getEntitiesOfClass(LongshotEntity.class, searchArea,
                entity -> entity.isAlive() && entity.isPullingPlayer())) {
            if (hook.getHookOwner() == player) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasActiveBlockPull(Player player) {
        var searchArea = player.getBoundingBox().inflate(HOOK_SEARCH_RANGE);
        for (HookshotEntity hook : player.level().getEntitiesOfClass(HookshotEntity.class, searchArea,
                entity -> entity.isAlive() && entity.isLatchedToBlock())) {
            if (hook.getHookOwner() == player) {
                return true;
            }
        }
        for (LongshotEntity hook : player.level().getEntitiesOfClass(LongshotEntity.class, searchArea,
                entity -> entity.isAlive() && entity.isLatchedToBlock())) {
            if (hook.getHookOwner() == player) {
                return true;
            }
        }
        return false;
    }

    private record SavedPose(Pose forcedPose, Pose pose) {
    }

    private record PendingRestore(SavedPose saved, int ticksRemaining) {
    }
}
