package com.superworldsun.superslegend.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

/** Persistent per-dimension memory for exploration-based Skulltula spawning. */
public final class GoldSkulltulaSpawnData extends SavedData {
    private static final String DATA_NAME = "superslegend_gold_skulltula_spawns";

    private final Map<Long, SpawnMemory> chunkMemories = new HashMap<>();
    private final Map<UUID, PlayerNightState> playerNights = new HashMap<>();
    private long nightSequence;
    private boolean dayStateInitialized;
    private boolean wasOverworldDay;

    public static GoldSkulltulaSpawnData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                GoldSkulltulaSpawnData::load, GoldSkulltulaSpawnData::new, DATA_NAME);
    }

    public PlayerNightState stateFor(UUID playerId, long night, int configuredMin,
                                     int configuredMax, RandomSource random) {
        int min = Math.max(0, Math.min(configuredMin, configuredMax));
        int max = Math.max(min, Math.max(configuredMin, configuredMax));
        PlayerNightState state = playerNights.get(playerId);
        if (state == null || state.night != night) {
            int limit = min + (max > min ? random.nextInt(max - min + 1) : 0);
            state = new PlayerNightState(night, limit, 0, 0, 0, 0, false);
            playerNights.put(playerId, state);
            setDirty();
        }
        return state;
    }

    /**
     * Returns a persistent night-session id. Unlike dayTime / 24000, this
     * advances whenever the Overworld actually transitions from day to night,
     * including transitions produced by /time set day followed by /time set night.
     */
    public long updateNightCycle(boolean isOverworldDay, long overworldDayNumber) {
        if (!dayStateInitialized) {
            nightSequence = Math.max(nightSequence, overworldDayNumber);
            wasOverworldDay = isOverworldDay;
            dayStateInitialized = true;
            setDirty();
        } else if (wasOverworldDay != isOverworldDay) {
            if (wasOverworldDay) {
                nightSequence = Math.max(nightSequence + 1L, overworldDayNumber);
            }
            wasOverworldDay = isOverworldDay;
            setDirty();
        }
        return nightSequence;
    }

    public boolean canSpawnAt(BlockPos pos, long night, int cooldownNights, int memoryRadius) {
        SpawnMemory sameChunk = chunkMemories.get(ChunkPos.asLong(pos));
        if (isCoolingDown(sameChunk, night, cooldownNights)) {
            return false;
        }

        double radiusSquared = (double) memoryRadius * memoryRadius;
        for (SpawnMemory memory : chunkMemories.values()) {
            if (isCoolingDown(memory, night, cooldownNights)
                    && memory.pos.distSqr(pos) < radiusSquared) {
                return false;
            }
        }
        return true;
    }

    public void recordSpawn(BlockPos pos, Direction attachment, UUID owner, long night,
                            PlayerNightState playerState, boolean replacement) {
        // A distant replacement is temporary. The original remembered site remains
        // authoritative and will be restored if the player returns or on a later night.
        if (!replacement) {
            chunkMemories.put(ChunkPos.asLong(pos),
                    new SpawnMemory(night, pos.immutable(), attachment, owner));
        }
        if (playerState.replacements > 0) {
            playerState.replacements--;
        } else {
            playerState.spawned++;
        }
        setDirty();
    }

    public List<RememberedSite> rememberedSites(UUID owner, long currentNight, int lifetimeNights) {
        List<RememberedSite> sites = new ArrayList<>();
        for (SpawnMemory memory : chunkMemories.values()) {
            if (owner.equals(memory.owner) && isCoolingDown(memory, currentNight, lifetimeNights)
                    && memory.attachment != null) {
                sites.add(new RememberedSite(memory.pos, memory.attachment));
            }
        }
        return sites;
    }

    public void recordRestoredSpawn(PlayerNightState state) {
        state.spawned++;
        setDirty();
    }

    public boolean beginRememberedSiteRestore(PlayerNightState state) {
        if (state.rememberedSitesProcessed) {
            return false;
        }
        state.rememberedSitesProcessed = true;
        setDirty();
        return true;
    }

    public void recordUnavailableRememberedSite(PlayerNightState state) {
        if (state.spawned < state.limit) {
            state.spawned++;
            state.replacements++;
            setDirty();
        }
    }

    public void forgetSite(BlockPos pos) {
        long chunk = ChunkPos.asLong(pos);
        SpawnMemory memory = chunkMemories.get(chunk);
        if (memory != null && memory.pos.equals(pos)) {
            chunkMemories.remove(chunk);
            setDirty();
        }
    }

    /** Frees a living spawn slot without refunding any Skulltulas killed that night. */
    public void recordDistantDespawn(UUID playerId, long night) {
        PlayerNightState state = playerNights.get(playerId);
        if (state != null && state.night == night && state.spawned > state.killed + state.replacements) {
            state.replacements++;
            setDirty();
        }
    }

    /** Cancels a relocation that has not spawned yet because its original area was revisited. */
    public void cancelDistantReplacement(UUID playerId, long night) {
        PlayerNightState state = playerNights.get(playerId);
        if (state != null && state.night == night && state.replacements > 0) {
            state.replacements--;
            setDirty();
        }
    }

    public void recordKill(UUID playerId, long night, BlockPos entityPos) {
        PlayerNightState state = playerNights.get(playerId);
        if (state != null && state.night == night) {
            state.killed = Math.min(state.spawned, state.killed + 1);
        }

        // Retire this exact remembered location immediately. Temporary distant
        // replacements do not own a memory entry, so killing one cannot erase an
        // unrelated site in the same chunk.
        long chunk = ChunkPos.asLong(entityPos);
        SpawnMemory memory = chunkMemories.get(chunk);
        if (memory != null && memory.pos.equals(entityPos)) {
            chunkMemories.remove(chunk);
        }
        setDirty();
    }

    public void recordAttempt(PlayerNightState state) {
        state.attempts++;
        setDirty();
    }

    public void cleanOldEntries(long currentNight, int cooldownNights) {
        boolean changed = chunkMemories.values().removeIf(memory ->
                currentNight >= memory.night && currentNight - memory.night >= cooldownNights);

        Iterator<Map.Entry<UUID, PlayerNightState>> iterator = playerNights.entrySet().iterator();
        while (iterator.hasNext()) {
            PlayerNightState state = iterator.next().getValue();
            if (currentNight > state.night + 2L) {
                iterator.remove();
                changed = true;
            }
        }
        if (changed) {
            setDirty();
        }
    }

    private static boolean isCoolingDown(SpawnMemory memory, long night, int cooldownNights) {
        if (memory == null) {
            return false;
        }
        long elapsed = night - memory.night;
        return elapsed < 0L || elapsed < cooldownNights;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag memories = new ListTag();
        for (Map.Entry<Long, SpawnMemory> entry : chunkMemories.entrySet()) {
            CompoundTag memoryTag = new CompoundTag();
            memoryTag.putLong("Chunk", entry.getKey());
            memoryTag.putLong("Night", entry.getValue().night);
            memoryTag.putLong("Pos", entry.getValue().pos.asLong());
            if (entry.getValue().attachment != null) {
                memoryTag.putByte("Attachment", (byte) entry.getValue().attachment.get3DDataValue());
            }
            if (entry.getValue().owner != null) {
                memoryTag.putUUID("Owner", entry.getValue().owner);
            }
            memories.add(memoryTag);
        }
        tag.put("SpawnMemories", memories);

        ListTag players = new ListTag();
        for (Map.Entry<UUID, PlayerNightState> entry : playerNights.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.putUUID("Player", entry.getKey());
            PlayerNightState state = entry.getValue();
            playerTag.putLong("Night", state.night);
            playerTag.putInt("Limit", state.limit);
            playerTag.putInt("Spawned", state.spawned);
            playerTag.putInt("Attempts", state.attempts);
            playerTag.putInt("Killed", state.killed);
            playerTag.putInt("Replacements", state.replacements);
            playerTag.putBoolean("RememberedSitesProcessed", state.rememberedSitesProcessed);
            players.add(playerTag);
        }
        tag.put("PlayerNights", players);
        tag.putLong("NightSequence", nightSequence);
        tag.putBoolean("DayStateInitialized", dayStateInitialized);
        tag.putBoolean("WasOverworldDay", wasOverworldDay);
        return tag;
    }

    private static GoldSkulltulaSpawnData load(CompoundTag tag) {
        GoldSkulltulaSpawnData data = new GoldSkulltulaSpawnData();
        ListTag memories = tag.getList("SpawnMemories", Tag.TAG_COMPOUND);
        for (int i = 0; i < memories.size(); i++) {
            CompoundTag memoryTag = memories.getCompound(i);
            Direction attachment = memoryTag.contains("Attachment", Tag.TAG_BYTE)
                    ? Direction.from3DDataValue(memoryTag.getByte("Attachment")) : null;
            UUID owner = memoryTag.hasUUID("Owner") ? memoryTag.getUUID("Owner") : null;
            data.chunkMemories.put(memoryTag.getLong("Chunk"),
                    new SpawnMemory(memoryTag.getLong("Night"), BlockPos.of(memoryTag.getLong("Pos")),
                            attachment, owner));
        }

        ListTag players = tag.getList("PlayerNights", Tag.TAG_COMPOUND);
        for (int i = 0; i < players.size(); i++) {
            CompoundTag playerTag = players.getCompound(i);
            if (playerTag.hasUUID("Player")) {
                data.playerNights.put(playerTag.getUUID("Player"), new PlayerNightState(
                        playerTag.getLong("Night"), playerTag.getInt("Limit"),
                        playerTag.getInt("Spawned"), playerTag.getInt("Attempts"),
                        playerTag.getInt("Killed"), playerTag.getInt("Replacements"),
                        playerTag.getBoolean("RememberedSitesProcessed")));
            }
        }
        data.nightSequence = tag.contains("NightSequence", Tag.TAG_LONG)
                ? tag.getLong("NightSequence")
                : data.latestRecordedNight();
        data.dayStateInitialized = tag.getBoolean("DayStateInitialized");
        data.wasOverworldDay = tag.getBoolean("WasOverworldDay");
        return data;
    }

    private long latestRecordedNight() {
        long latest = 0L;
        for (SpawnMemory memory : chunkMemories.values()) {
            latest = Math.max(latest, memory.night);
        }
        for (PlayerNightState state : playerNights.values()) {
            latest = Math.max(latest, state.night);
        }
        return latest;
    }

    private record SpawnMemory(long night, BlockPos pos, Direction attachment, UUID owner) {
    }

    public record RememberedSite(BlockPos pos, Direction attachment) {
    }

    public static final class PlayerNightState {
        private final long night;
        private final int limit;
        private int spawned;
        private int attempts;
        private int killed;
        private int replacements;
        private boolean rememberedSitesProcessed;

        private PlayerNightState(long night, int limit, int spawned, int attempts,
                                 int killed, int replacements, boolean rememberedSitesProcessed) {
            this.night = night;
            this.limit = limit;
            this.spawned = spawned;
            this.attempts = attempts;
            this.killed = killed;
            this.replacements = replacements;
            this.rememberedSitesProcessed = rememberedSitesProcessed;
        }

        public boolean canSpawnMore() {
            // 160 searches spans the full night at the handler's five-second interval.
            return (spawned < limit || replacements > 0) && attempts < 160;
        }

        public boolean hasPendingReplacement() {
            return replacements > 0;
        }

        public boolean canRestoreMore() {
            return spawned < limit;
        }

        public int limit() {
            return limit;
        }
    }
}
