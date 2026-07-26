package com.superworldsun.superslegend.songs.epona;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Persistent, server-wide index of the one horse marked by each player. */
public final class EponasHorseSavedData extends SavedData {
    private static final String DATA_ID = "eponas_horses";
    private final Map<UUID, MarkedHorse> horsesByOwner = new HashMap<>();

    public static EponasHorseSavedData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(
                EponasHorseSavedData::load, EponasHorseSavedData::new, DATA_ID);
    }

    private static EponasHorseSavedData load(CompoundTag tag) {
        EponasHorseSavedData data = new EponasHorseSavedData();
        ListTag horses = tag.getList("Horses", Tag.TAG_COMPOUND);
        for (Tag entry : horses) {
            CompoundTag horseTag = (CompoundTag) entry;
            try {
                UUID ownerId = horseTag.getUUID("Owner");
                UUID horseId = horseTag.getUUID("Horse");
                ResourceLocation dimensionId = new ResourceLocation(horseTag.getString("Dimension"));
                BlockPos position = new BlockPos(horseTag.getInt("X"), horseTag.getInt("Y"), horseTag.getInt("Z"));
                String customName = horseTag.getString("CustomName");
                data.horsesByOwner.put(ownerId,
                        new MarkedHorse(horseId, ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION,
                                dimensionId), position, customName));
            } catch (RuntimeException ignored) {
                // Ignore malformed legacy/corrupt entries instead of preventing the world from loading.
            }
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag horses = new ListTag();
        horsesByOwner.forEach((ownerId, horse) -> {
            CompoundTag horseTag = new CompoundTag();
            horseTag.putUUID("Owner", ownerId);
            horseTag.putUUID("Horse", horse.horseId());
            horseTag.putString("Dimension", horse.dimension().location().toString());
            horseTag.putInt("X", horse.position().getX());
            horseTag.putInt("Y", horse.position().getY());
            horseTag.putInt("Z", horse.position().getZ());
            horseTag.putString("CustomName", horse.customName());
            horses.add(horseTag);
        });
        tag.put("Horses", horses);
        return tag;
    }

    @Nullable
    public MarkedHorse getHorse(UUID ownerId) {
        return horsesByOwner.get(ownerId);
    }

    public boolean matches(UUID ownerId, UUID horseId) {
        MarkedHorse marked = horsesByOwner.get(ownerId);
        return marked != null && marked.horseId().equals(horseId);
    }

    public void setHorse(UUID ownerId, MarkedHorse horse) {
        horsesByOwner.put(ownerId, horse);
        setDirty();
    }

    public void updateHorse(UUID ownerId, MarkedHorse horse) {
        MarkedHorse current = horsesByOwner.get(ownerId);
        if (!horse.equals(current)) {
            horsesByOwner.put(ownerId, horse);
            setDirty();
        }
    }

    public void removeHorse(UUID ownerId, UUID horseId) {
        if (matches(ownerId, horseId)) {
            horsesByOwner.remove(ownerId);
            setDirty();
        }
    }

    public record MarkedHorse(UUID horseId, ResourceKey<Level> dimension, BlockPos position, String customName) {
    }
}
