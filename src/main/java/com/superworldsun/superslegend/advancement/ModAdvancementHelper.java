package com.superworldsun.superslegend.advancement;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.block.Block;
import com.superworldsun.superslegend.entities.projectiles.boomerang.AbstractBoomerangEntity;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModAdvancementHelper {
    private static final String WARP_PAD_DESTINATIONS_TAG = "SupersLegendWarpPadDestinations";
    private static final String OWL_STATUE_DESTINATIONS_TAG = "SupersLegendOwlStatueDestinations";

    private ModAdvancementHelper() {
    }

    public static void award(ServerPlayer player, String advancementPath, String criterion) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }

        Advancement advancement = server.getAdvancements().getAdvancement(
                new ResourceLocation(SupersLegendMain.MOD_ID, advancementPath));
        if (advancement != null) {
            player.getAdvancements().award(advancement, criterion);
        }
    }

    public static void recordWarpPadTeleport(ServerPlayer player, Block warpPad) {
        ResourceLocation warpPadId = ForgeRegistries.BLOCKS.getKey(warpPad);
        if (warpPadId == null) {
            return;
        }

        int uniqueDestinations = rememberUniqueDestination(player, WARP_PAD_DESTINATIONS_TAG,
                warpPadId.toString());
        award(player, "first_warp_pad_trip", "warp_pad_teleport");
        if (uniqueDestinations >= 6) {
            award(player, "warp_pad_traveler", "all_warp_pads");
        }
    }

    public static void recordOwlStatueTeleport(ServerPlayer player, ResourceLocation dimension, BlockPos statuePos) {
        String destinationId = dimension + "|" + statuePos.asLong();
        int uniqueDestinations = rememberUniqueDestination(player, OWL_STATUE_DESTINATIONS_TAG, destinationId);
        award(player, "first_owl_statue_trip", "owl_teleport");
        if (uniqueDestinations >= 4) {
            award(player, "soaring_to_safety", "four_owl_statues");
        }
    }

    private static int rememberUniqueDestination(ServerPlayer player, String tagName, String destinationId) {
        CompoundTag persisted = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        ListTag destinations = persisted.getList(tagName, StringTag.TAG_STRING);
        for (int index = 0; index < destinations.size(); index++) {
            if (destinationId.equals(destinations.getString(index))) {
                return destinations.size();
            }
        }

        destinations.add(StringTag.valueOf(destinationId));
        persisted.put(tagName, destinations);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, persisted);
        return destinations.size();
    }

    /** Counts towers lit by the same projectile/action entity. */
    public static void recordTorchTowerLit(Entity action) {
        int count = action.getPersistentData().getInt("SupersLegendTorchTowersLit") + 1;
        action.getPersistentData().putInt("SupersLegendTorchTowersLit", count);
        Entity owner = action instanceof Projectile projectile ? projectile.getOwner()
                : action instanceof AbstractBoomerangEntity boomerang ? boomerang.getOwner()
                : action;
        if (count >= 2 && owner instanceof ServerPlayer player) {
            award(player, "two_for_one_flame", "lit_two_towers");
        }
    }
}
