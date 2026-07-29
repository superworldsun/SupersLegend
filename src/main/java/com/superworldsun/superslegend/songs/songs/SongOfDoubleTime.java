package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SongOfDoubleTime extends OcarinaSong {
    public SongOfDoubleTime() {
        super("rraadd", 0x023A91);
    }

    @Override
    public SoundEvent getPlayingSound() {
        return SoundInit.SONG_OF_DOUBLE_TIME.get();
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
        if (!Config.isSongOfDoubleTimeEnabled()) {
            player.sendSystemMessage(Component.translatable("text.ocarina.doubled_disabled"));
            return;
        }

        TimeSongSavedData timeData = TimeSongSavedData.get(serverLevel.getServer().overworld());
        boolean activated = timeData.activateOrCancel(TimeSongSavedData.Mode.DOUBLE,
                serverLevel.getServer().overworld());
        timeData.syncToAllPlayers();
        String message = activated ? "text.ocarina.doubled" : "text.ocarina.doubled_second";
        ChatFormatting color = activated ? ChatFormatting.GREEN : ChatFormatting.YELLOW;
        serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                Component.translatable(message, player.getName()).withStyle(color), false);
    }
}
