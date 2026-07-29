package com.superworldsun.superslegend.songs;

import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public abstract class OcarinaSong {
    private final String songPattern;
    private final int iconColor;
    private ResourceLocation registryName;

    public OcarinaSong(String songPattern, int iconColor) {
        this.songPattern = songPattern;
        this.iconColor = iconColor;
    }

    public String getSongPattern() {
        return songPattern;
    }

    public Component getLocalizedName() {
        return Component.translatable("song.superslegend." + getRegistryName().getPath());
    }

    public abstract SoundEvent getPlayingSound();

    public boolean requiresOcarinaOfTime() {
        return false;
    }

    public int getSongIconColor() {
        return iconColor;
    }

    public abstract void onSongPlayed(Player player, Level level);

    /**
     * Client view information is only relevant to songs with camera-aware effects.
     * Existing songs continue through the original two-argument method.
     */
    public void onSongPlayed(Player player, Level level, boolean rearFacingCamera) {
        onSongPlayed(player, level);
    }

    public void setRegistryName(ResourceLocation name) {
        if (this.registryName != null) {
            throw new IllegalStateException("Registry name is already set.");
        }
        this.registryName = name;
    }

    public ResourceLocation getRegistryName() {
        return this.registryName;
    }
}