package com.superworldsun.superslegend.world;

import com.superworldsun.superslegend.entities.statue.ElegyStatueEntity;
import com.superworldsun.superslegend.entities.statue.ElegyStatueVariant;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import top.theillusivec4.curios.api.CuriosApi;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import com.superworldsun.superslegend.registries.BlockInit;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/** Owns variant selection and the one-active-statue-per-variant contract. */
public final class ElegyStatueManager {
    private static final String DATA_KEY = "SupersLegendElegyStatues";
    private static final String LEGACY_DATA_KEY = "SupersLegendElegyStatue";
    private static final String ENTITY_ID = "EntityId";
    private static final String DIMENSION = "Dimension";
    private static final String POSITION = "Position";

    private ElegyStatueManager() {
    }

    /**
     * Returns the statue form allowed by the player's current transformation.
     * Future Goron and Zora variants can be selected here when their assets
     * are added.
     */
    public static Optional<ElegyStatueVariant> selectVariant(Player player) {
        boolean wearingDekuMask = CuriosApi.getCuriosHelper()
                .findFirstCurio(player, ItemInit.MASK_DEKUMASK.get())
                .isPresent();
        if (wearingDekuMask) {
            return Optional.of(ElegyStatueVariant.DEKU);
        }

        Set<Item> unsupportedForms = Set.of(
                ItemInit.MASK_GIANTSMASK.get(),
                ItemInit.GNAT_HAT.get(),
                ItemInit.MASK_GORONMASK.get(),
                ItemInit.MASK_ZORAMASK.get(),
                ItemInit.MASK_FIERCEDEITYSMASK.get()
        );
        boolean wearingUnsupportedForm = CuriosApi.getCuriosHelper()
                .findFirstCurio(player, stack -> unsupportedForms.contains(stack.getItem()))
                .isPresent();
        return wearingUnsupportedForm ? Optional.empty() : Optional.of(ElegyStatueVariant.PLAYER);
    }

    public static void summon(ServerPlayer player, ElegyStatueVariant variant) {
        removePrevious(player, variant);

        ServerLevel level = player.serverLevel();
        BlockPos supportPos = player.getOnPos();
        BlockState supportState = level.getBlockState(supportPos);
        VoxelShape collision = supportState.getCollisionShape(level, supportPos);
        double y = collision.isEmpty()
                ? player.getY()
                : supportPos.getY() + collision.max(Direction.Axis.Y);
        double x = supportPos.getX() + 0.5D;
        double z = supportPos.getZ() + 0.5D;

        ElegyStatueEntity statue = new ElegyStatueEntity(level, player, variant,
                x, y, z, player.getYRot());
        if (level.addFreshEntity(statue)) {
            setActiveRecord(player, statue);
            BlockState underStatue = level.getBlockState(statue.getOnPos());
            if (underStatue.getBlock() instanceof BasePressurePlateBlock
                    || underStatue.is(BlockInit.BLUE_FLOOR_SWITCH.get())
                    || underStatue.is(BlockInit.YELLOW_FLOOR_SWITCH.get())
                    || underStatue.is(BlockInit.RED_FLOOR_SWITCH.get())) {
                ModAdvancementHelper.award(player, "weight_of_emptiness", "placed_on_switch");
            }
        }
    }

    private static void removePrevious(ServerPlayer player, ElegyStatueVariant variant) {
        CompoundTag record = getActiveRecord(player, variant);
        if (!record.hasUUID(ENTITY_ID) || !record.contains(DIMENSION) || !record.contains(POSITION)) {
            clearRecord(player, variant);
            return;
        }

        ResourceLocation dimensionId = ResourceLocation.tryParse(record.getString(DIMENSION));
        if (dimensionId != null) {
            MinecraftServer server = player.getServer();
            ServerLevel oldLevel = server == null ? null : server.getLevel(
                    ResourceKey.create(Registries.DIMENSION, dimensionId));
            if (oldLevel != null) {
                BlockPos oldPos = BlockPos.of(record.getLong(POSITION));
                // Load the one containing chunk so replacement also works when the
                // prior statue is in another dimension or outside ticking range.
                oldLevel.getChunk(oldPos.getX() >> 4, oldPos.getZ() >> 4);
                Entity oldEntity = oldLevel.getEntity(record.getUUID(ENTITY_ID));
                if (oldEntity instanceof ElegyStatueEntity statue) {
                    statue.despawnWithSmoke();
                }
            }
        }
        clearRecord(player, variant);
    }

    public static boolean isCurrent(ElegyStatueEntity statue) {
        Optional<UUID> ownerId = statue.getOwnerId();
        if (ownerId.isEmpty() || statue.getServer() == null) {
            return false;
        }
        ServerPlayer owner = statue.getServer().getPlayerList().getPlayer(ownerId.get());
        if (owner == null) {
            return true;
        }
        CompoundTag record = getActiveRecord(owner, statue.getStatueVariant());
        return record.hasUUID(ENTITY_ID) && statue.getUUID().equals(record.getUUID(ENTITY_ID));
    }

    public static void clearIfCurrent(ElegyStatueEntity statue) {
        Optional<UUID> ownerId = statue.getOwnerId();
        if (ownerId.isEmpty() || statue.getServer() == null) {
            return;
        }
        ServerPlayer owner = statue.getServer().getPlayerList().getPlayer(ownerId.get());
        if (owner == null) {
            return;
        }
        ElegyStatueVariant variant = statue.getStatueVariant();
        CompoundTag record = getActiveRecord(owner, variant);
        if (record.hasUUID(ENTITY_ID) && statue.getUUID().equals(record.getUUID(ENTITY_ID))) {
            clearRecord(owner, variant);
        }
    }

    private static void setActiveRecord(ServerPlayer player, ElegyStatueEntity statue) {
        CompoundTag persisted = getPersistentData(player);
        CompoundTag record = new CompoundTag();
        record.putUUID(ENTITY_ID, statue.getUUID());
        record.putString(DIMENSION, statue.level().dimension().location().toString());
        record.putLong(POSITION, statue.blockPosition().asLong());
        CompoundTag records = persisted.getCompound(DATA_KEY);
        records.put(recordKey(statue.getStatueVariant()), record);
        persisted.put(DATA_KEY, records);
        if (statue.getStatueVariant() == ElegyStatueVariant.PLAYER) {
            persisted.remove(LEGACY_DATA_KEY);
        }
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, persisted);
    }

    private static void clearRecord(ServerPlayer player, ElegyStatueVariant variant) {
        CompoundTag persisted = getPersistentData(player);
        CompoundTag records = persisted.getCompound(DATA_KEY);
        records.remove(recordKey(variant));
        if (records.isEmpty()) {
            persisted.remove(DATA_KEY);
        } else {
            persisted.put(DATA_KEY, records);
        }
        if (variant == ElegyStatueVariant.PLAYER) {
            persisted.remove(LEGACY_DATA_KEY);
        }
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, persisted);
    }

    private static CompoundTag getActiveRecord(Player player, ElegyStatueVariant variant) {
        CompoundTag persisted = getPersistentData(player);
        CompoundTag records = persisted.getCompound(DATA_KEY);
        String key = recordKey(variant);
        if (records.contains(key)) {
            return records.getCompound(key);
        }
        // Player statues made before per-variant tracking used a single record.
        if (variant == ElegyStatueVariant.PLAYER) {
            return persisted.getCompound(LEGACY_DATA_KEY);
        }
        return new CompoundTag();
    }

    private static String recordKey(ElegyStatueVariant variant) {
        return variant.name().toLowerCase(java.util.Locale.ROOT);
    }

    private static CompoundTag getPersistentData(Player player) {
        return player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
    }
}
