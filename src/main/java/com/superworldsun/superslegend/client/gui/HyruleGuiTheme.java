package com.superworldsun.superslegend.client.gui;

import net.minecraft.client.gui.GuiGraphics;

/**
 * Small, texture-independent drawing toolkit shared by the Hyrule-style menus.
 * Keeping the chrome separate from screen logic also makes it straightforward to
 * replace these fills with an art atlas later without changing layout code.
 */
public final class HyruleGuiTheme {
    public static final int TEXT_LIGHT = 0xFFF5E7C1;
    public static final int TEXT_MUTED = 0xFFC9B789;
    public static final int TEXT_GOLD = 0xFFFFD85A;
    public static final int TEXT_GREEN = 0xFF9DDB67;
    public static final int TEXT_RED = 0xFFFF6B62;

    public static final int GOLD_BRIGHT = 0xFFE5B94D;
    public static final int GOLD_DARK = 0xFF7B4B16;
    public static final int BROWN_OUTER = 0xFF26160C;
    public static final int BROWN_MID = 0xFF5A351A;
    public static final int BROWN_LIGHT = 0xFF85552A;
    public static final int PANEL_TOP = 0xF22E1E12;
    public static final int PANEL_BOTTOM = 0xF219100A;
    public static final int PARCHMENT_TOP = 0xFFE7CC8E;
    public static final int PARCHMENT_BOTTOM = 0xFFCBA96B;
    public static final int GREEN_TOP = 0xFF416D25;
    public static final int GREEN_BOTTOM = 0xFF244719;

    private HyruleGuiTheme() {
    }

    public static void drawScreenShade(GuiGraphics graphics, int width, int height) {
        graphics.fillGradient(0, 0, width, height, 0xB8070A0C, 0xD00B0907);
    }

    /** Draws the parchment-and-wood outer frame used by both trading screens. */
    public static void drawFrame(GuiGraphics graphics, int x, int y, int width, int height) {
        graphics.fill(x + 4, y + 5, x + width + 4, y + height + 5, 0x99000000);
        graphics.fill(x, y, x + width, y + height, BROWN_OUTER);
        drawBorder(graphics, x + 1, y + 1, width - 2, height - 2, GOLD_DARK);
        graphics.fillGradient(x + 3, y + 3, x + width - 3, y + height - 3,
                BROWN_LIGHT, BROWN_MID);
        drawBorder(graphics, x + 5, y + 5, width - 10, height - 10, GOLD_BRIGHT);
        graphics.fillGradient(x + 7, y + 7, x + width - 7, y + height - 7,
                PARCHMENT_TOP, PARCHMENT_BOTTOM);

        drawCornerStud(graphics, x + 4, y + 4);
        drawCornerStud(graphics, x + width - 8, y + 4);
        drawCornerStud(graphics, x + 4, y + height - 8);
        drawCornerStud(graphics, x + width - 8, y + height - 8);
    }

    /** Draws a dark inset panel with a warm metallic rim. */
    public static void drawDarkPanel(GuiGraphics graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, BROWN_OUTER);
        drawBorder(graphics, x + 1, y + 1, width - 2, height - 2, GOLD_DARK);
        graphics.fillGradient(x + 3, y + 3, x + width - 3, y + height - 3,
                PANEL_TOP, PANEL_BOTTOM);
        graphics.fill(x + 4, y + 4, x + width - 4, y + 5, 0x665E4124);
    }

    public static void drawParchmentPanel(GuiGraphics graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, BROWN_OUTER);
        drawBorder(graphics, x + 1, y + 1, width - 2, height - 2, GOLD_DARK);
        graphics.fillGradient(x + 3, y + 3, x + width - 3, y + height - 3,
                PARCHMENT_TOP, PARCHMENT_BOTTOM);
    }

    public static void drawRule(GuiGraphics graphics, int x, int y, int width) {
        graphics.fill(x, y, x + width, y + 1, GOLD_DARK);
        graphics.fill(x + 1, y + 1, x + width - 1, y + 2, 0x667C5829);
    }

    public static void drawBorder(GuiGraphics graphics, int x, int y, int width, int height, int color) {
        if (width <= 0 || height <= 0) {
            return;
        }
        graphics.fill(x, y, x + width, y + 1, color);
        graphics.fill(x, y + height - 1, x + width, y + height, color);
        graphics.fill(x, y, x + 1, y + height, color);
        graphics.fill(x + width - 1, y, x + width, y + height, color);
    }

    public static boolean contains(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    private static void drawCornerStud(GuiGraphics graphics, int x, int y) {
        graphics.fill(x, y, x + 4, y + 4, BROWN_OUTER);
        graphics.fill(x + 1, y + 1, x + 3, y + 3, GOLD_BRIGHT);
    }
}
