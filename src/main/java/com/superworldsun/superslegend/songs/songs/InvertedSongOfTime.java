package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class InvertedSongOfTime extends OcarinaSong {
    public InvertedSongOfTime() {
        super("dardar", 0x3170CF);
    }

    @Override
    public SoundEvent getPlayingSound() {
        return SoundInit.INVERTED_SONG_OF_TIME.get();
    }

    @Override
    public boolean requiresOcarinaOfTime() {
        return true;
    }

    @Override
    public void onSongPlayed(Player player, Level level) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!Config.isInvertedSongOfTimeEnabled()) {
            player.sendSystemMessage(Component.translatable("text.ocarina.inverted_disabled"));
            return;
        }

        TimeSongSavedData timeData = TimeSongSavedData.get(serverLevel.getServer().overworld());
        boolean activated = timeData.activateOrCancel(TimeSongSavedData.Mode.INVERTED,
                serverLevel.getServer().overworld());
        timeData.syncToAllPlayers();
        String message = activated ? "text.ocarina.inverted" : "text.ocarina.inverted_second";
        serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                Component.translatable(message, player.getName()), false);
    }
}
