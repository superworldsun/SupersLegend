package com.superworldsun.superslegend.util;
/*
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.*;

public class EntityData extends WorldSavedData {
  public static final String id = "DudeWheresMyHorse-EntityTracking";

  public Set<UUID> trackedEntities = new HashSet<>();
  public Map<UUID, UUID> entityToOwner = new HashMap<>();
  public Map<UUID, DimBlockPos> lastKnownLocation = new HashMap<>();

  public EntityData() {
    super(id);
  }

  public EntityData(String name) {
    super(name);
  }

  @Override
  public void load(CompoundNBT nbt) {
    entityToOwner.clear();
    trackedEntities.clear();
    lastKnownLocation.clear();
    NBTTagList owners = nbt.getTagList("owners", Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < owners.tagCount(); i++) {
      NBTTagCompound thisEntry = owners.getCompoundTagAt(i);
      UUID owner = thisEntry.getUniqueId("owner");
      UUID entity = thisEntry.getUniqueId("entity");
      entityToOwner.put(entity, owner);
    }
    NBTTagList tracked = nbt.getTagList("tracked", Constants.NBT.TAG_COMPOUND);
    for (int i = 0; i < tracked.tagCount(); i++) {
      NBTTagCompound thisEntry = tracked.getCompoundTagAt(i);
      UUID entity = thisEntry.getUniqueId("entity");
      trackedEntities.add(entity);
    }
    if (nbt.hasKey("lastknown")) {
      NBTTagList lastKnown = nbt.getTagList("lastknown", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < lastKnown.tagCount(); i++) {
        NBTTagCompound thisEntry = lastKnown.getCompoundTagAt(i);
        BlockPos pos = BlockPos.fromLong(thisEntry.getLong("pos"));
        int dimId = thisEntry.getInteger("dim");
        UUID entity = thisEntry.getUniqueId("entity");
        lastKnownLocation.put(entity, new DimBlockPos(pos, dimId));
      }
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT compound) {
    NBTTagList owners = new NBTTagList();
    for (Map.Entry<UUID, UUID> entry : entityToOwner.entrySet()) {
      NBTTagCompound thisEntry = new NBTTagCompound();
      thisEntry.setUniqueId("entity", entry.getKey());
      thisEntry.setUniqueId("owner", entry.getValue());
      owners.appendTag(thisEntry);
    }
    NBTTagList tracked = new NBTTagList();
    for (UUID entity : trackedEntities) {
      NBTTagCompound thisEntry = new NBTTagCompound();
      thisEntry.setUniqueId("entity", entity);
      tracked.appendTag(thisEntry);
    }
    NBTTagList lastknown = new NBTTagList();
    for (Map.Entry<UUID, DimBlockPos> entry : lastKnownLocation.entrySet()) {
      NBTTagCompound thisEntry = new NBTTagCompound();
      thisEntry.setUniqueId("entity", entry.getKey());
      thisEntry.setLong("pos", entry.getValue().getPos().toLong());
      thisEntry.setInteger("dim", entry.getValue().getDim());
      lastknown.appendTag(thisEntry);
    }
    NBTTagCompound result = new NBTTagCompound();
    result.setTag("owners", owners);
    result.setTag("tracked", tracked);
    result.setTag("lastknown", lastknown);
    return result;
  }

}
*/