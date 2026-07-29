package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ClientTimeSongState {
    private static TimeSongSavedData.Mode mode = TimeSongSavedData.Mode.NORMAL;
    private static double accumulatedTime;
    private static boolean affectsDayNightCycle = true;

    private ClientTimeSongState() {
    }

    public static void setMode(TimeSongSavedData.Mode newMode, double serverAccumulatedTime,
                               boolean serverAffectsDayNightCycle) {
        mode = newMode;
        accumulatedTime = serverAccumulatedTime;
        affectsDayNightCycle = serverAffectsDayNightCycle;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || mode == TimeSongSavedData.Mode.NORMAL
                || !affectsDayNightCycle) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (minecraft.isPaused() || level == null || level.dimension() != Level.OVERWORLD
                || !level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            return;
        }

        long currentTime = level.getDayTime();
        if (mode == TimeSongSavedData.Mode.DOUBLE) {
            level.setDayTime(currentTime + 1L);
        } else if (mode == TimeSongSavedData.Mode.INVERTED) {
            accumulatedTime += 1.0D / 3.0D;
            long desiredAdvance = (long) accumulatedTime;
            accumulatedTime -= desiredAdvance;
            level.setDayTime(currentTime - 1L + desiredAdvance);
        }
    }
}
