package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

/** Preserves the cursor while the server replaces one trading screen with another. */
public final class TradeCursorMemory {
    private static final long RESTORE_WINDOW_MS = 5_000L;

    private static double cursorX;
    private static double cursorY;
    private static long expiresAt;

    private TradeCursorMemory() {
    }

    public static void capture() {
        Minecraft minecraft = Minecraft.getInstance();
        cursorX = minecraft.mouseHandler.xpos();
        cursorY = minecraft.mouseHandler.ypos();
        expiresAt = Util.getMillis() + RESTORE_WINDOW_MS;
    }

    public static void restoreIfPending() {
        if (expiresAt == 0L || Util.getMillis() > expiresAt) {
            expiresAt = 0L;
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        double restoredX = Mth.clamp(cursorX, 0.0D, minecraft.getWindow().getScreenWidth() - 1.0D);
        double restoredY = Mth.clamp(cursorY, 0.0D, minecraft.getWindow().getScreenHeight() - 1.0D);
        expiresAt = 0L;
        InputConstants.grabOrReleaseMouse(
                minecraft.getWindow().getWindow(), 212993, restoredX, restoredY);
    }
}
