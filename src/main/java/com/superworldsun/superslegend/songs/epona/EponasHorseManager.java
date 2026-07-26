package com.superworldsun.superslegend.songs.epona;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public final class EponasHorseManager {
    private static final String MARKED_OWNER_TAG = SupersLegendMain.MOD_ID + ":eponas_horse_owner";
    private static final int SUMMON_LOAD_TIMEOUT = 100;
    private static final int RUN_APPROACH_TIMEOUT = 200;
    private static final int WALK_APPROACH_TIMEOUT = 80;
    private static final double RUN_APPROACH_SPEED = 1.35D;
    private static final double FAR_RUN_APPROACH_SPEED = 1.75D;
    private static final double WALK_APPROACH_SPEED = 0.55D;
    private static final double WALK_TRANSITION_DISTANCE_SQR = 25.0D;
    private static final double ARRIVAL_DISTANCE_SQR = 2.25D;
    private static final Map<UUID, PendingSummon> PENDING_SUMMONS = new HashMap<>();
    private static final Map<UUID, ApproachTarget> APPROACHING_HORSES = new HashMap<>();

    private EponasHorseManager() {
    }

    @SubscribeEvent
    public static void onMount(EntityMountEvent event) {
        if (!event.isMounting() || !(event.getEntityMounting() instanceof ServerPlayer player)
                || !(event.getEntityBeingMounted() instanceof Horse horse)) {
            return;
        }
        tryMark(player, horse, true);
    }

    @SubscribeEvent
    public static void onHorseTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Horse horse) || horse.level().isClientSide
                || !(horse.level() instanceof ServerLevel level)) {
            return;
        }

        UUID markedOwner = getMarkedOwner(horse);
        if (markedOwner != null) {
            EponasHorseSavedData data = EponasHorseSavedData.get(level.getServer());
            if (!horse.isAlive() || !horse.isTamed() || !horse.isSaddled()
                    || !data.matches(markedOwner, horse.getUUID())) {
                clearHorseMarker(horse);
                data.removeHorse(markedOwner, horse.getUUID());
            } else if (horse.tickCount % 20 == 0) {
                data.updateHorse(markedOwner, createRecord(horse));
            }
        } else if (horse.isTamed() && horse.isSaddled()
                && horse.getFirstPassenger() instanceof ServerPlayer rider) {
            // Covers a saddle being equipped while the player is already mounted.
            tryMark(rider, horse, false);
        }
    }

    @SubscribeEvent
    public static void onHorseDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Horse horse) || horse.level().isClientSide
                || !(horse.level() instanceof ServerLevel level)) {
            return;
        }
        UUID ownerId = getMarkedOwner(horse);
        if (ownerId != null) {
            EponasHorseSavedData.get(level.getServer()).removeHorse(ownerId, horse.getUUID());
            clearHorseMarker(horse);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        tickPendingSummons(event.getServer());
        tickApproachingHorses(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        PENDING_SUMMONS.clear();
        APPROACHING_HORSES.clear();
    }

    private static boolean tryMark(ServerPlayer player, Horse horse, boolean notifyConflict) {
        if (!horse.isAlive() || !horse.isTamed() || !horse.isSaddled()) {
            return false;
        }

        EponasHorseSavedData data = EponasHorseSavedData.get(player.server);
        UUID existingOwner = getMarkedOwner(horse);
        if (existingOwner != null && !existingOwner.equals(player.getUUID())) {
            if (data.matches(existingOwner, horse.getUUID())) {
                if (notifyConflict) {
                    player.displayClientMessage(
                            Component.translatable("message.superslegend.eponas_song.already_marked"), true);
                }
                return false;
            }
            clearHorseMarker(horse);
        }

        EponasHorseSavedData.MarkedHorse oldMark = data.getHorse(player.getUUID());
        if (oldMark != null && !oldMark.horseId().equals(horse.getUUID())) {
            ServerLevel oldLevel = player.server.getLevel(oldMark.dimension());
            if (oldLevel != null) {
                Entity oldEntity = oldLevel.getEntity(oldMark.horseId());
                if (oldEntity instanceof Horse oldHorse) {
                    clearHorseMarker(oldHorse);
                }
            }
        }

        setMarkedOwner(horse, player.getUUID());
        data.setHorse(player.getUUID(), createRecord(horse));
        return true;
    }

    public static void summonMarkedHorse(ServerPlayer player, boolean rearFacingCamera) {
        EponasHorseSavedData data = EponasHorseSavedData.get(player.server);
        EponasHorseSavedData.MarkedHorse marked = data.getHorse(player.getUUID());
        if (marked == null) {
            player.displayClientMessage(Component.translatable("message.superslegend.eponas_song.no_horse"), false);
            return;
        }

        if (!marked.dimension().equals(player.level().dimension())) {
            sendCannotHear(player, marked.customName());
            return;
        }

        ServerLevel horseLevel = player.server.getLevel(marked.dimension());
        if (horseLevel == null) {
            sendCannotHear(player, marked.customName());
            return;
        }

        Entity loadedEntity = horseLevel.getEntity(marked.horseId());
        if (loadedEntity instanceof Horse loadedHorse) {
            finishSummon(player, loadedHorse, rearFacingCamera);
            return;
        }

        PendingSummon previous = PENDING_SUMMONS.remove(player.getUUID());
        if (previous != null) {
            removeTicket(player.server, previous);
        }
        ChunkPos chunk = new ChunkPos(marked.position());
        horseLevel.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunk, 1, player.getId());
        horseLevel.getChunk(marked.position());
        Entity newlyLoadedEntity = horseLevel.getEntity(marked.horseId());
        if (newlyLoadedEntity instanceof Horse newlyLoadedHorse) {
            horseLevel.getChunkSource().removeRegionTicket(
                    TicketType.POST_TELEPORT, chunk, 1, player.getId());
            finishSummon(player, newlyLoadedHorse, rearFacingCamera);
            return;
        }
        PENDING_SUMMONS.put(player.getUUID(),
                new PendingSummon(marked.horseId(), marked.dimension(), chunk, player.getId(),
                        rearFacingCamera, SUMMON_LOAD_TIMEOUT));
    }

    private static void tickPendingSummons(MinecraftServer server) {
        Iterator<Map.Entry<UUID, PendingSummon>> iterator = PENDING_SUMMONS.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, PendingSummon> entry = iterator.next();
            ServerPlayer player = server.getPlayerList().getPlayer(entry.getKey());
            PendingSummon pending = entry.getValue();
            ServerLevel level = server.getLevel(pending.dimension());
            if (player == null || level == null || !player.level().dimension().equals(pending.dimension())) {
                removeTicket(server, pending);
                iterator.remove();
                continue;
            }

            // POST_TELEPORT tickets expire quickly, so refresh this one while entity data finishes loading.
            level.getChunkSource().addRegionTicket(
                    TicketType.POST_TELEPORT, pending.chunk(), 1, pending.ticketId());

            Entity entity = level.getEntity(pending.horseId());
            if (entity instanceof Horse horse) {
                removeTicket(server, pending);
                iterator.remove();
                finishSummon(player, horse, pending.rearFacingCamera());
            } else if (pending.ticksRemaining() <= 1) {
                removeTicket(server, pending);
                iterator.remove();
                EponasHorseSavedData data = EponasHorseSavedData.get(server);
                EponasHorseSavedData.MarkedHorse mark = data.getHorse(player.getUUID());
                if (mark != null && mark.horseId().equals(pending.horseId())) {
                    data.removeHorse(player.getUUID(), pending.horseId());
                }
                player.displayClientMessage(Component.translatable("message.superslegend.eponas_song.no_horse"), false);
            } else {
                entry.setValue(pending.withTicksRemaining(pending.ticksRemaining() - 1));
            }
        }
    }

    private static void finishSummon(ServerPlayer player, Horse horse, boolean rearFacingCamera) {
        EponasHorseSavedData data = EponasHorseSavedData.get(player.server);
        UUID markedOwner = getMarkedOwner(horse);
        if (!horse.isAlive() || !horse.isTamed() || !horse.isSaddled()
                || markedOwner == null || !markedOwner.equals(player.getUUID())
                || !data.matches(player.getUUID(), horse.getUUID())) {
            if (markedOwner != null && markedOwner.equals(player.getUUID())) {
                clearHorseMarker(horse);
            }
            data.removeHorse(player.getUUID(), horse.getUUID());
            player.displayClientMessage(Component.translatable("message.superslegend.eponas_song.no_horse"), false);
            return;
        }
        if (!horse.getPassengers().isEmpty()) {
            sendCannotHear(player, customName(horse));
            return;
        }

        SummonPosition summonPosition = findSafeSummonPosition(
                player.serverLevel(), player, horse, rearFacingCamera);
        if (summonPosition == null) {
            player.displayClientMessage(Component.translatable("message.superslegend.eponas_song.no_safe_space"), false);
            return;
        }
        Vec3 destination = summonPosition.position();

        float yaw = (float) (Mth.atan2(player.getZ() - destination.z, player.getX() - destination.x)
                * (180.0D / Math.PI)) - 90.0F;
        horse.moveTo(destination.x, destination.y, destination.z, yaw, 0.0F);
        horse.setYHeadRot(yaw);
        horse.setYBodyRot(yaw);
        horse.setDeltaMovement(Vec3.ZERO);
        horse.fallDistance = 0.0F;
        horse.getNavigation().stop();
        moveHorseTowardPlayer(horse, player, summonPosition.runSpeed(), summonPosition.longDistance());
        APPROACHING_HORSES.put(horse.getUUID(),
                new ApproachTarget(player.getUUID(), RUN_APPROACH_TIMEOUT, false,
                        summonPosition.runSpeed(), summonPosition.longDistance()));
        data.updateHorse(player.getUUID(), createRecord(horse));
    }

    private static void tickApproachingHorses(MinecraftServer server) {
        Iterator<Map.Entry<UUID, ApproachTarget>> iterator = APPROACHING_HORSES.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, ApproachTarget> entry = iterator.next();
            ApproachTarget target = entry.getValue();
            ServerPlayer player = server.getPlayerList().getPlayer(target.playerId());
            if (player == null) {
                iterator.remove();
                continue;
            }
            Entity entity = player.serverLevel().getEntity(entry.getKey());
            if (!(entity instanceof Horse horse) || !horse.isAlive() || target.ticksRemaining() <= 1) {
                if (entity instanceof Horse horse) {
                    horse.getNavigation().stop();
                }
                iterator.remove();
                continue;
            }

            double distanceSqr = horse.distanceToSqr(player);
            if (!target.walking() && distanceSqr <= WALK_TRANSITION_DISTANCE_SQR) {
                target = target.beginWalking();
                horse.getNavigation().stop();
                horse.getNavigation().moveTo(player, WALK_APPROACH_SPEED);
            }

            if (target.walking() && (distanceSqr <= ARRIVAL_DISTANCE_SQR
                    || horse.getBoundingBox().inflate(0.2D).intersects(player.getBoundingBox()))) {
                horse.getNavigation().stop();
                iterator.remove();
                continue;
            }

            double approachSpeed = target.walking() ? WALK_APPROACH_SPEED : target.runSpeed();
            if (target.ticksRemaining() % 10 == 0 || horse.getNavigation().isDone()) {
                moveHorseTowardPlayer(horse, player, approachSpeed,
                        target.longDistance() && !target.walking());
            }
            entry.setValue(target.withTicksRemaining(target.ticksRemaining() - 1));
        }
    }

    @Nullable
    private static SummonPosition findSafeSummonPosition(ServerLevel level, ServerPlayer player, Horse horse,
                                                          boolean rearFacingCamera) {
        double yawRadians = Math.toRadians(player.getYRot());
        Vec3 forward = new Vec3(-Math.sin(yawRadians), 0.0D, Math.cos(yawRadians));
        Vec3 preferredDirection = rearFacingCamera ? forward : forward.scale(-1.0D);
        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x);

        // First choice: behind the player (or in front while using the mirrored third-person camera, so the
        // appearance remains hidden from view).
        Vec3 safe = findSafePositionAlong(level, player, horse, preferredDirection, right,
                new int[]{10, 9, 11, 8, 12, 7}, new double[]{0.0D, 1.5D, -1.5D});
        if (safe != null) {
            return new SummonPosition(safe, RUN_APPROACH_SPEED, false);
        }

        // Second choice: try both true sides before placing the horse anywhere the player is facing.
        int[] sideDistances = {10, 9, 11, 8, 12, 7};
        double[] sideDepthOffsets = {0.0D, -1.5D, 1.5D, -3.0D, 3.0D};
        safe = findSafePositionAlong(level, player, horse, right, forward,
                sideDistances, sideDepthOffsets);
        if (safe == null) {
            safe = findSafePositionAlong(level, player, horse, right.scale(-1.0D), forward,
                    sideDistances, sideDepthOffsets);
        }
        if (safe != null) {
            return new SummonPosition(safe, RUN_APPROACH_SPEED, false);
        }

        // Last resort: appear well ahead and make a faster, longer run toward the player.
        safe = findSafePositionAlong(level, player, horse, forward, right,
                new int[]{40, 39, 41, 38, 42},
                new double[]{0.0D, 2.0D, -2.0D, 4.0D, -4.0D, 6.0D, -6.0D});
        return safe == null ? null : new SummonPosition(safe, FAR_RUN_APPROACH_SPEED, true);
    }

    @Nullable
    private static Vec3 findSafePositionAlong(ServerLevel level, ServerPlayer player, Horse horse,
                                               Vec3 direction, Vec3 lateralDirection,
                                               int[] distances, double[] lateralOffsets) {
        for (int distance : distances) {
            for (double lateralOffset : lateralOffsets) {
                Vec3 wanted = player.position().add(direction.scale(distance))
                        .add(lateralDirection.scale(lateralOffset));
                Vec3 safe = findSafeGround(level, horse, Mth.floor(wanted.x), Mth.floor(wanted.z), player.getY());
                if (safe != null && safe.distanceToSqr(player.position()) >= 12.0D) {
                    return safe;
                }
            }
        }
        return null;
    }

    private static void moveHorseTowardPlayer(Horse horse, ServerPlayer player, double speed,
                                               boolean longDistance) {
        Vec3 difference = player.position().subtract(horse.position());
        double horizontalDistanceSqr = difference.x * difference.x + difference.z * difference.z;
        if (longDistance && horizontalDistanceSqr > 196.0D) {
            // Horse navigation normally only searches within its follow range. Feed it a nearer waypoint until
            // the player enters that range so a 40-block fallback can still make continuous forward progress.
            Vec3 horizontalDirection = new Vec3(difference.x, 0.0D, difference.z).normalize();
            Vec3 waypoint = horse.position().add(horizontalDirection.scale(12.0D));
            horse.getNavigation().moveTo(waypoint.x, player.getY(), waypoint.z, speed);
        } else {
            horse.getNavigation().moveTo(player, speed);
        }
    }

    @Nullable
    private static Vec3 findSafeGround(ServerLevel level, Horse horse, int x, int z, double playerY) {
        // Search around the player's floor instead of using the dimension heightmap. In dimensions with a
        // ceiling (especially the Nether), the heightmap points at the bedrock roof rather than the walkable
        // cavern containing the player.
        int baseGroundY = Mth.floor(playerY - 0.01D);
        int[] verticalOffsets = {0, -1, 1, -2, 2, -3, 3, -4, 4};
        for (int verticalOffset : verticalOffsets) {
            int groundY = baseGroundY + verticalOffset;
            if (groundY < level.getMinBuildHeight() || groundY >= level.getMaxBuildHeight() - 1) {
                continue;
            }
            BlockPos groundPos = new BlockPos(x, groundY, z);
            BlockState ground = level.getBlockState(groundPos);
            if (!isSafeGround(level, groundPos, ground)) {
                continue;
            }
            VoxelShape groundShape = ground.getCollisionShape(level, groundPos);
            double y = groundPos.getY() + groundShape.max(Direction.Axis.Y);
            Vec3 candidate = new Vec3(x + 0.5D, y, z + 0.5D);
            AABB box = horse.getDimensions(Pose.STANDING).makeBoundingBox(candidate).deflate(0.02D);
            if (level.noCollision(horse, box) && !level.containsAnyLiquid(box)) {
                return candidate;
            }
        }
        return null;
    }

    private static boolean isSafeGround(ServerLevel level, BlockPos pos, BlockState state) {
        if (state.getCollisionShape(level, pos).isEmpty() || !state.isFaceSturdy(level, pos, Direction.UP)) {
            return false;
        }
        return !state.is(BlockTags.FIRE) && !state.is(Blocks.CACTUS) && !state.is(Blocks.MAGMA_BLOCK)
                && !state.is(Blocks.CAMPFIRE) && !state.is(Blocks.SOUL_CAMPFIRE)
                && !state.is(Blocks.POWDER_SNOW) && !state.is(Blocks.SWEET_BERRY_BUSH);
    }

    private static void removeTicket(MinecraftServer server, PendingSummon pending) {
        ServerLevel level = server.getLevel(pending.dimension());
        if (level != null) {
            level.getChunkSource().removeRegionTicket(
                    TicketType.POST_TELEPORT, pending.chunk(), 1, pending.ticketId());
        }
    }

    private static EponasHorseSavedData.MarkedHorse createRecord(Horse horse) {
        return new EponasHorseSavedData.MarkedHorse(
                horse.getUUID(), horse.level().dimension(), horse.blockPosition(), customName(horse));
    }

    private static String customName(Horse horse) {
        return horse.hasCustomName() ? horse.getCustomName().getString() : "";
    }

    private static void sendCannotHear(ServerPlayer player, String customName) {
        if (customName == null || customName.isBlank()) {
            player.displayClientMessage(
                    Component.translatable("message.superslegend.eponas_song.cant_hear"), false);
        } else {
            player.displayClientMessage(
                    Component.translatable("message.superslegend.eponas_song.named_cant_hear", customName), false);
        }
    }

    @Nullable
    private static UUID getMarkedOwner(Horse horse) {
        String owner = horse.getPersistentData().getString(MARKED_OWNER_TAG);
        if (owner.isEmpty()) {
            return null;
        }
        try {
            return UUID.fromString(owner);
        } catch (IllegalArgumentException ignored) {
            horse.getPersistentData().remove(MARKED_OWNER_TAG);
            return null;
        }
    }

    private static void setMarkedOwner(Horse horse, UUID ownerId) {
        horse.getPersistentData().putString(MARKED_OWNER_TAG, ownerId.toString());
    }

    private static void clearHorseMarker(Horse horse) {
        horse.getPersistentData().remove(MARKED_OWNER_TAG);
    }

    private record PendingSummon(UUID horseId, net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> dimension,
                                 ChunkPos chunk, int ticketId, boolean rearFacingCamera, int ticksRemaining) {
        private PendingSummon withTicksRemaining(int ticks) {
            return new PendingSummon(horseId, dimension, chunk, ticketId, rearFacingCamera, ticks);
        }
    }

    private record SummonPosition(Vec3 position, double runSpeed, boolean longDistance) {
    }

    private record ApproachTarget(UUID playerId, int ticksRemaining, boolean walking,
                                  double runSpeed, boolean longDistance) {
        private ApproachTarget beginWalking() {
            return new ApproachTarget(playerId, WALK_APPROACH_TIMEOUT, true, runSpeed, longDistance);
        }

        private ApproachTarget withTicksRemaining(int ticks) {
            return new ApproachTarget(playerId, ticks, walking, runSpeed, longDistance);
        }
    }
}