package com.superworldsun.superslegend.util;
/*
import com.noobanidus.dwmh.types.DimBlockPos;
import com.noobanidus.dwmh.world.DataHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityTracking {
  public static Map<UUID, Runnable> clearMap = new HashMap<>();

  private static WorldServer getWorld(int dim) {
    return DimensionManager.getWorld(dim);
  }

  private static WorldServer getWorld() {
    return getWorld(DimensionType.OVERWORLD.getId());
  }

  public static EntityData getData() {
    WorldServer world = getWorld();
    return DataHelper.getTrackingData(world);
  }

  public static void storeEntity(Entity entity, DimBlockPos location) {
    EntityData data = getData();
    data.lastKnownLocation.put(entity.getUniqueID(), location);
    save(data);
  }

  public static void setOwnerForEntity(EntityPlayer player, Entity entity) {
    EntityData data = getData();
    UUID playerId = player.getUniqueID();
    UUID entityId = entity.getUniqueID();
    data.entityToOwner.put(entityId, playerId);
    data.trackedEntities.add(entityId);
    save(data);
  }

  public static void unsetOwnerForEntity(Entity entity) {
    unsetOwnerForEntity(entity.getUniqueID());
  }

  public static void unsetOwnerForEntity(UUID entityId) {
    EntityData data = getData();
    data.entityToOwner.remove(entityId);
    data.trackedEntities.remove(entityId);
    save(data);
  }

  public static void save(EntityData data) {
    data.markDirty();
    WorldServer world = getWorld();
    world.getMapStorage().saveAllData();
  }

  @Nullable
  public static UUID getOwnerForEntity(Entity entity) {
    EntityData data = getData();
    return data.entityToOwner.getOrDefault(entity.getUniqueID(), null);
  }

  @Nullable
  public static Entity fetchEntity(UUID uuid) {
    EntityData data = getData();
    Entity entity = findEntity(uuid);
    if (entity != null) {
      return entity;
    }

    DimBlockPos pos = data.lastKnownLocation.get(uuid);
    if (pos != null) {
      return loadEntity(uuid, pos);
    }

    return null;
  }

  @Nullable
  public static Entity loadEntity(UUID uuid, DimBlockPos dimpos) {
    WorldServer dim = DimensionManager.getWorld(dimpos.getDim());
    if (dim == null) {
      return null;
    }
    ChunkPos pos = new ChunkPos(dimpos.getPos());
    // TODO: Chunk loading
/*    dim.forceChunk(pos.x, pos.z, true);
    clearMap.put(uuid, () -> dim.forceChunk(pos.x, pos.z, false));
    return dim.getEntityFromUuid(uuid);
  }



  @Nullable
  public static Entity findEntity(UUID uuid) {
    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
    for (WorldServer world : server.worlds) {
      Entity potential = world.getEntityFromUuid(uuid);
      if (potential != null) {
        return potential;
      }
    }

    return null;
  }

  public static void clearEntity(UUID entity) {
    Runnable runner = clearMap.remove(entity);
    if (runner != null) {
      runner.run();
    }
  }
}
*/