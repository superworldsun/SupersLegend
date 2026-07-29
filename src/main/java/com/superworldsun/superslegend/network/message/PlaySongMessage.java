package com.superworldsun.superslegend.network.message;


import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.registries.OcarinaSongInit;
import com.superworldsun.superslegend.items.item.FairyOcarina;
import com.superworldsun.superslegend.items.item.OcarinaOfTime;
import com.superworldsun.superslegend.songs.OcarinaSong;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PlaySongMessage {
    private ResourceLocation songId;
    private final boolean rearFacingCamera;
    private final InteractionHand playingHand;

    public PlaySongMessage(OcarinaSong song) {
        this(song, false, InteractionHand.MAIN_HAND);
    }

    public PlaySongMessage(OcarinaSong song, boolean rearFacingCamera) {
        this(song, rearFacingCamera, InteractionHand.MAIN_HAND);
    }

    public PlaySongMessage(OcarinaSong song, boolean rearFacingCamera, InteractionHand playingHand) {
        this.rearFacingCamera = rearFacingCamera;
        this.playingHand = playingHand;
        this.songId = song.getRegistryName();
        if (this.songId == null) {
            // Fallback in case getRegistryName() is not available or returns null
            for (RegistryObject<OcarinaSong> entry : OcarinaSongInit.OCARINA_SONGS.getEntries()) {
                if (entry.get() == song) {
                    this.songId = entry.getId();
                    break;
                }
            }
        }
        if (this.songId == null) {
            throw new IllegalArgumentException("Could not find registry name for song: " + song);
        }
    }

    private PlaySongMessage(ResourceLocation songId, boolean rearFacingCamera, InteractionHand playingHand) {
        this.songId = songId;
        this.rearFacingCamera = rearFacingCamera;
        this.playingHand = playingHand;
    }

    public static PlaySongMessage decode(FriendlyByteBuf buf) {
        return new PlaySongMessage(buf.readResourceLocation(), buf.readBoolean(),
                buf.readEnum(InteractionHand.class));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(songId);
        buf.writeBoolean(rearFacingCamera);
        buf.writeEnum(playingHand);
    }

    public static void handle(PlaySongMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null && isOcarina(player.getItemInHand(message.playingHand))) {
                OcarinaSongInit.OCARINA_SONGS.getEntries().stream()
                        .filter(entry -> entry.getId().equals(message.songId))
                        .findFirst()
                        .ifPresent(songEntry -> {
                            OcarinaSong song = songEntry.get();
                            player.serverLevel().playSound(player, player.getX(), player.getY(), player.getZ(),
                                    song.getPlayingSound(), SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (player.getItemInHand(message.playingHand).getItem() instanceof OcarinaOfTime
                                    || Config.canFairyOcarinaApply(song)) {
                                song.onSongPlayed(player, player.level(), message.rearFacingCamera);
                            }
                        });
            }
        });
        ctx.setPacketHandled(true);
    }

    private static boolean isOcarina(ItemStack stack) {
        return stack.getItem() instanceof OcarinaOfTime || stack.getItem() instanceof FairyOcarina;
    }
}
