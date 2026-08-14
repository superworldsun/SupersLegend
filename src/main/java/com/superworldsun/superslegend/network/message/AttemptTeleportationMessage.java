package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.capability.waypoint.Waypoint;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.capability.waypoint.WaypointsProvider;
import com.superworldsun.superslegend.capability.waypoint.WaypointsServerData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Set;
import java.util.function.Supplier;

public class AttemptTeleportationMessage {
    private final BlockPos pos;

    public AttemptTeleportationMessage(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(AttemptTeleportationMessage message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
    }

    public static AttemptTeleportationMessage decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        return new AttemptTeleportationMessage(pos);
    }

    public static void handle(AttemptTeleportationMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                player.getCapability(WaypointsProvider.WAYPOINTS_CAPABILITY).ifPresent(waypoints -> {
                    Waypoint savedWaypoint = waypoints.getWaypoint(message.pos);
                    if (savedWaypoint == null) {
                        WaypointsProvider.sync(player);
                        player.displayClientMessage(Component.literal("Waypoint not found in player data")
                                .withStyle(ChatFormatting.RED), true);
                        return;
                    }

                    ResourceLocation dimensionId = ResourceLocation.tryParse(savedWaypoint.getDimension());
                    ServerLevel waypointLevel = dimensionId == null ? null : player.getServer().getLevel(
                            ResourceKey.create(Registries.DIMENSION, dimensionId));
                    Waypoint serverWaypoint = waypointLevel == null ? null
                            : WaypointsServerData.get(waypointLevel).getWaypoint(message.pos);

                    if (serverWaypoint == null) {
                        waypoints.removeWaypoint(message.pos);
                        WaypointsProvider.sync(player);
                        player.displayClientMessage(Component.literal("Waypoint not found on server")
                                .withStyle(ChatFormatting.RED), true);
                        return;
                    }

                    if (waypointLevel != player.serverLevel()) {
                        player.displayClientMessage(Component.literal("Cannot teleport between dimensions")
                                .withStyle(ChatFormatting.RED), true);
                        return;
                    }

                    float yaw = serverWaypoint.getFacing().getOpposite().toYRot();
                    player.teleportTo(
                            waypointLevel,
                            serverWaypoint.getTeleportPos().x,
                            serverWaypoint.getTeleportPos().y,
                            serverWaypoint.getTeleportPos().z,
                            Set.of(),
                            yaw,
                            player.getXRot()
                    );
                    ModAdvancementHelper.recordOwlStatueTeleport(player,
                            waypointLevel.dimension().location(), message.pos);

                    player.displayClientMessage(Component.literal("Teleported to waypoint")
                            .withStyle(ChatFormatting.DARK_GREEN)
                            .append(String.format(" x: %d", (int) serverWaypoint.getTeleportPos().x))
                            .append(String.format(" y: %d", (int) serverWaypoint.getTeleportPos().y))
                            .append(String.format(" z: %d", (int) serverWaypoint.getTeleportPos().z)), true);
                });
            }
        });

        context.setPacketHandled(true);
    }
}
