package com.superworldsun.superslegend.world;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.mobs.GoldSkulltulaEntity;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/** Performs a few weighted surface searches per night instead of using vanilla mob spawning. */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class GoldSkulltulaSpawnHandler {
    private static final int SEARCH_INTERVAL_TICKS = 100;
    private static final int DISTANT_CHECK_INTERVAL_TICKS = 20;
    private static final int CANDIDATE_COLUMNS = 36;
    private static final int UNDERWATER_SPAWN_CHANCE = 12;
    private static final Direction[] ATTACHMENTS = {
            Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST
    };
    private static final TagKey<Structure> HIGH_PRIORITY_STRUCTURES = TagKey.create(
            Registries.STRUCTURE,
            new ResourceLocation(SupersLegendMain.MOD_ID, "gold_skulltula_high_priority"));
    private static final TagKey<net.minecraft.world.level.block.Block> SPAWN_HAZARDS = TagKey.create(
            Registries.BLOCK,
            new ResourceLocation(SupersLegendMain.MOD_ID, "gold_skulltula_spawn_hazards"));

    private GoldSkulltulaSpawnHandler() {
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.level instanceof ServerLevel level)
                || !Config.goldSkulltulasEnabled()
                || (level.dimension() != Level.OVERWORLD && level.dimension() != Level.NETHER)) {
            return;
        }

        ServerLevel overworld = level.getServer().overworld();
        boolean isOverworldDay = overworld.isDay();
        GoldSkulltulaSpawnData data = GoldSkulltulaSpawnData.get(level);
        long night = data.updateNightCycle(isOverworldDay,
                Math.floorDiv(overworld.getDayTime(), 24000L));
        if (isOverworldDay) {
            return;
        }

        // Check relocation much more often than the relatively expensive spawn
        // search. This keeps a Skulltula from slipping into an unloaded chunk
        // before its distant replacement has been queued.
        if (level.getGameTime() % DISTANT_CHECK_INTERVAL_TICKS == 0L) {
            recycleDistantSkulltulas(level, data, night);
        }
        if (level.getGameTime() % SEARCH_INTERVAL_TICKS != 0L) {
            return;
        }

        data.cleanOldEntries(night, Config.goldSkulltulaChunkCooldownNights());

        for (ServerPlayer player : level.players()) {
            if (player.isSpectator() || !player.isAlive()) {
                continue;
            }

            GoldSkulltulaSpawnData.PlayerNightState state = data.stateFor(
                    player.getUUID(), night, Config.goldSkulltulaMinimumPerPlayer(),
                    Config.goldSkulltulaMaximumPerPlayer(), level.random);

            restoreRememberedSites(level, player, data, state, night);
            if (!state.canSpawnMore() || countNearby(level, player) >= state.limit()) {
                continue;
            }

            data.recordAttempt(state);
            SpawnCandidate candidate = findCandidate(level, player, data, night);
            boolean replacement = state.hasPendingReplacement();
            if (candidate != null && spawn(level, candidate, player, night, replacement)) {
                data.recordSpawn(candidate.pos, candidate.attachment, player.getUUID(), night,
                        state, replacement);
            }
        }
    }

    private static void restoreRememberedSites(ServerLevel level, ServerPlayer player,
                                                GoldSkulltulaSpawnData data,
                                                GoldSkulltulaSpawnData.PlayerNightState state,
                                                long night) {
        if (!state.canRestoreMore()) {
            return;
        }

        if (!data.beginRememberedSiteRestore(state)) {
            return;
        }

        for (GoldSkulltulaSpawnData.RememberedSite site : data.rememberedSites(
                player.getUUID(), night, Config.goldSkulltulaChunkCooldownNights())) {
            if (!state.canRestoreMore()) {
                return;
            }

            BlockPos pos = site.pos();
            if (!level.hasChunkAt(pos)) {
                // Reserve the remembered slot and create only a temporary nearby
                // replacement. This prevents travel from accumulating permanent sites.
                data.recordUnavailableRememberedSite(state);
                continue;
            }
            if (hasSkulltulaAt(level, pos)) {
                data.recordRestoredSpawn(state);
                continue;
            }
            if (!isRememberedSiteStillValid(level, site)) {
                data.forgetSite(pos);
                continue;
            }

            SpawnCandidate candidate = new SpawnCandidate(pos, site.attachment(), 1);
            if (spawn(level, candidate, player, night, false, true)) {
                data.recordRestoredSpawn(state);
            } else {
                data.recordUnavailableRememberedSite(state);
            }
        }
    }

    private static boolean hasSkulltulaAt(ServerLevel level, BlockPos pos) {
        return !level.getEntitiesOfClass(GoldSkulltulaEntity.class,
                new AABB(pos).inflate(0.5D), entity -> entity.isAlive()).isEmpty();
    }

    private static boolean isRememberedSiteStillValid(ServerLevel level,
                                                       GoldSkulltulaSpawnData.RememberedSite site) {
        BlockPos pos = site.pos();
        BlockState space = level.getBlockState(pos);
        if (!space.getCollisionShape(level, pos).isEmpty()
                || (!space.getFluidState().isEmpty() && !space.getFluidState().is(FluidTags.WATER))
                || hasNearbyHazard(level, pos)) {
            return false;
        }

        BlockPos supportPos = pos.relative(site.attachment());
        BlockState support = level.getBlockState(supportPos);
        return support.isFaceSturdy(level, supportPos, site.attachment().getOpposite())
                || (site.attachment() == Direction.UP && support.is(BlockTags.LEAVES));
    }

    private static void recycleDistantSkulltulas(ServerLevel level, GoldSkulltulaSpawnData data,
                                                  long night) {
        // Match relocation to the configured spawn radius. The old extra
        // 32-block buffer made distant replacements feel inconsistent.
        double recycleDistance = Config.goldSkulltulaMaximumPlayerDistance();
        double recycleDistanceSquared = recycleDistance * recycleDistance;
        List<GoldSkulltulaEntity> distant = new ArrayList<>();

        for (net.minecraft.world.entity.Entity loadedEntity : level.getAllEntities()) {
            if (!(loadedEntity instanceof GoldSkulltulaEntity skulltula)
                    || skulltula.getSpawnOwner() == null || skulltula.getSpawnNight() != night) {
                continue;
            }

            boolean nearPlayer = level.players().stream().anyMatch(player ->
                    !player.isSpectator() && player.isAlive()
                            && player.distanceToSqr(skulltula) <= recycleDistanceSquared);
            if (nearPlayer && skulltula.isDormant()) {
                skulltula.setDormant(false);
                data.cancelDistantReplacement(skulltula.getSpawnOwner(), night);
                level.getServer().getPlayerList().broadcastSystemMessage(
                        Component.literal("Original Gold Skulltula restored at "
                                        + skulltula.getBlockX() + ", "
                                        + skulltula.getBlockY() + ", "
                                        + skulltula.getBlockZ())
                                .withStyle(ChatFormatting.AQUA),
                        false);
            } else if (!nearPlayer && !skulltula.isDormant()) {
                distant.add(skulltula);
            }
        }

        for (GoldSkulltulaEntity skulltula : distant) {
            data.recordDistantDespawn(skulltula.getSpawnOwner(), night);
            level.getServer().getPlayerList().broadcastSystemMessage(
                    Component.literal("Gold Skulltula despawned at "
                                    + skulltula.getBlockX() + ", "
                                    + skulltula.getBlockY() + ", "
                                    + skulltula.getBlockZ()
                                    + " (too far away; replacement queued)")
                            .withStyle(ChatFormatting.YELLOW),
                    false);
            // Preserve the original at this exact location in a non-rendering,
            // non-interactive state so returning to the area restores it.
            skulltula.setDormant(true);
        }
    }

    private static int countNearby(ServerLevel level, ServerPlayer player) {
        int range = Config.goldSkulltulaMaximumPlayerDistance() + 16;
        return level.getEntitiesOfClass(GoldSkulltulaEntity.class,
                new AABB(player.blockPosition()).inflate(range),
                entity -> entity.isAlive() && !entity.isDormant()).size();
    }

    private static SpawnCandidate findCandidate(ServerLevel level, ServerPlayer player,
                                                GoldSkulltulaSpawnData data, long night) {
        RandomSource random = level.random;
        int minDistance = Config.goldSkulltulaMinimumPlayerDistance();
        int maxDistance = Math.max(minDistance + 1, Config.goldSkulltulaMaximumPlayerDistance());
        List<SpawnCandidate> candidates = new ArrayList<>();

        for (int sample = 0; sample < CANDIDATE_COLUMNS; sample++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double distance = minDistance + random.nextDouble() * (maxDistance - minDistance);
            int x = player.getBlockX() + (int) Math.round(Math.cos(angle) * distance);
            int z = player.getBlockZ() + (int) Math.round(Math.sin(angle) * distance);
            if (!level.hasChunkAt(new BlockPos(x, player.getBlockY(), z))) {
                continue;
            }

            int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            int minY = Math.max(level.getMinBuildHeight() + 2, Math.min(player.getBlockY() - 40, surfaceY - 48));
            int maxY = Math.min(level.getMaxBuildHeight() - 2, Math.max(player.getBlockY() + 32, surfaceY + 4));
            if (maxY <= minY) {
                continue;
            }

            // Sampling several heights per column finds cave ceilings as well as forest overhangs.
            for (int verticalSample = 0; verticalSample < 4; verticalSample++) {
                int y = verticalSample == 0
                        ? Math.max(minY, Math.min(maxY, surfaceY - 1))
                        : minY + random.nextInt(maxY - minY + 1);
                BlockPos pos = new BlockPos(x, y, z);
                Direction attachment = findAttachment(level, pos, random);
                boolean underwater = level.getFluidState(pos).is(FluidTags.WATER);
                if (attachment == null
                        || (underwater && random.nextInt(UNDERWATER_SPAWN_CHANCE) != 0)
                        || !validCandidate(level, data, pos, night)) {
                    continue;
                }
                candidates.add(new SpawnCandidate(pos.immutable(), attachment,
                        candidateWeight(level, player, pos)));
            }
        }
        return chooseWeighted(candidates, random);
    }

    private static boolean validCandidate(ServerLevel level, GoldSkulltulaSpawnData data,
                                          BlockPos pos, long night) {
        BlockState space = level.getBlockState(pos);
        if (!space.getCollisionShape(level, pos).isEmpty()
                || (!space.getFluidState().isEmpty() && !space.getFluidState().is(FluidTags.WATER))) {
            return false;
        }

        // A Skulltula may touch a wall, but its underside must remain unsupported by the ground.
        BlockState below = level.getBlockState(pos.below());
        if (below.isFaceSturdy(level, pos.below(), Direction.UP) || hasNearbyHazard(level, pos)) {
            return false;
        }
        if (level.getBrightness(LightLayer.BLOCK, pos) > Config.goldSkulltulaMaxLight()
                || level.getMaxLocalRawBrightness(pos) > Config.goldSkulltulaMaxLight()) {
            return false;
        }

        double minimumPlayerDistanceSquared = (double) Config.goldSkulltulaMinimumPlayerDistance()
                * Config.goldSkulltulaMinimumPlayerDistance();
        for (ServerPlayer player : level.players()) {
            if (!player.isSpectator() && player.distanceToSqr(Vec3.atCenterOf(pos)) < minimumPlayerDistanceSquared) {
                return false;
            }
        }

        int separation = Config.goldSkulltulaMinimumSeparation();
        if (!level.getEntitiesOfClass(GoldSkulltulaEntity.class,
                new AABB(pos).inflate(separation), entity -> entity.isAlive()).isEmpty()) {
            return false;
        }
        return data.canSpawnAt(pos, night, Config.goldSkulltulaChunkCooldownNights(),
                Config.goldSkulltulaMemoryRadius());
    }

    private static boolean isHazardous(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return level.getFluidState(pos).is(FluidTags.LAVA)
                || state.is(SPAWN_HAZARDS)
                || state.is(BlockTags.FIRE)
                || state.is(Blocks.CACTUS)
                || state.is(Blocks.MAGMA_BLOCK)
                || state.is(Blocks.CAMPFIRE)
                || state.is(Blocks.SOUL_CAMPFIRE)
                || state.is(Blocks.SWEET_BERRY_BUSH)
                || state.is(Blocks.WITHER_ROSE)
                || state.is(Blocks.POWDER_SNOW);
    }

    private static boolean hasNearbyHazard(ServerLevel level, BlockPos pos) {
        if (isHazardous(level, pos)) {
            return true;
        }
        for (Direction direction : Direction.values()) {
            if (isHazardous(level, pos.relative(direction))) {
                return true;
            }
        }
        return false;
    }

    private static Direction findAttachment(ServerLevel level, BlockPos pos, RandomSource random) {
        int start = random.nextInt(ATTACHMENTS.length);
        for (int i = 0; i < ATTACHMENTS.length; i++) {
            Direction attachment = ATTACHMENTS[(start + i) % ATTACHMENTS.length];
            BlockPos supportPos = pos.relative(attachment);
            BlockState support = level.getBlockState(supportPos);
            if (support.isFaceSturdy(level, supportPos, attachment.getOpposite())
                    || (attachment == Direction.UP && support.is(BlockTags.LEAVES))) {
                return attachment;
            }
        }
        return null;
    }

    private static int candidateWeight(ServerLevel level, ServerPlayer player, BlockPos pos) {
        int weight;
        if (level.structureManager().getStructureWithPieceAt(pos, HIGH_PRIORITY_STRUCTURES).isValid()) {
            weight = 12;
        } else {
            Holder<net.minecraft.world.level.biome.Biome> biome = level.getBiome(pos);
            boolean caveOrOverhang = !level.canSeeSky(pos) && pos.getY() < level.getSeaLevel();
            weight = caveOrOverhang || biome.is(BiomeTags.IS_FOREST) ? 4 : 1;
        }

        HitResult sight = level.clip(new ClipContext(player.getEyePosition(), Vec3.atCenterOf(pos),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (sight.getType() != HitResult.Type.MISS) {
            weight *= 3;
        }
        return weight;
    }

    private static SpawnCandidate chooseWeighted(List<SpawnCandidate> candidates, RandomSource random) {
        int total = candidates.stream().mapToInt(SpawnCandidate::weight).sum();
        if (total <= 0) {
            return null;
        }
        int roll = random.nextInt(total);
        for (SpawnCandidate candidate : candidates) {
            roll -= candidate.weight;
            if (roll < 0) {
                return candidate;
            }
        }
        return candidates.get(candidates.size() - 1);
    }

    private static boolean spawn(ServerLevel level, SpawnCandidate candidate,
                                 ServerPlayer player, long night, boolean replacement) {
        return spawn(level, candidate, player, night, replacement, false);
    }

    private static boolean spawn(ServerLevel level, SpawnCandidate candidate,
                                 ServerPlayer player, long night, boolean replacement,
                                 boolean remembered) {
        GoldSkulltulaEntity entity = EntityTypeInit.GOLD_SKULLTULA.get().create(level);
        if (entity == null) {
            return false;
        }
        entity.moveTo(candidate.pos.getX() + 0.5D, candidate.pos.getY(), candidate.pos.getZ() + 0.5D,
                0.0F, 0.0F);
        entity.setAttachmentDirection(candidate.attachment);
        entity.setSpawnTracking(player.getUUID(), night);
        entity.setPersistenceRequired();
        entity.finalizeSpawn(level, level.getCurrentDifficultyAt(candidate.pos),
                MobSpawnType.NATURAL, null, null);
        // A remembered original belongs at its recorded location even when the
        // returning player is now inside the fresh-spawn exclusion radius (or
        // standing very close to it). The remembered-site validation above has
        // already checked its supporting surface and hazards. Fresh and
        // replacement spawns retain the full collision/distance behavior.
        if (!remembered && !level.noCollision(entity)) {
            return false;
        }
        if (!level.addFreshEntity(entity)) {
            return false;
        }

        level.getServer().getPlayerList().broadcastSystemMessage(
                Component.literal((remembered
                                ? "Remembered Gold Skulltula returned at "
                                : replacement
                                ? "Replacement Gold Skulltula spawned at "
                                : "Gold Skulltula spawned at ")
                                + candidate.pos.getX() + ", "
                                + candidate.pos.getY() + ", "
                                + candidate.pos.getZ())
                        .withStyle(ChatFormatting.GOLD),
                false);
        return true;
    }

    private record SpawnCandidate(BlockPos pos, Direction attachment, int weight) {
    }
}
