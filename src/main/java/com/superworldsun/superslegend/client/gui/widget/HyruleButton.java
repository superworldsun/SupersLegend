package com.superworldsun.superslegend.client.gui.widget;

import com.superworldsun.superslegend.client.gui.HyruleGuiTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

/** A narrated vanilla button with Hyrule-style rendering. */
public class HyruleButton extends Button {
    private final Style style;
    private boolean selected;

    public HyruleButton(int x, int y, int width, int height, Component message,
                        OnPress onPress, Style style) {
        super(Button.builder(message, onPress).bounds(x, y, width, height));
        this.style = style;
    }

    public HyruleButton setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int x = getX();
        int y = getY();
        int border;
        int top;
        int bottom;
        int textColor;

        if (!active) {
            border = selected ? HyruleGuiTheme.GOLD_DARK : 0xFF4A3A2A;
            top = selected ? HyruleGuiTheme.GREEN_TOP : 0xFF3A3128;
            bottom = selected ? HyruleGuiTheme.GREEN_BOTTOM : 0xFF211D19;
            textColor = selected ? HyruleGuiTheme.TEXT_GOLD : 0xFF8F877B;
        } else if (selected || style == Style.GREEN) {
            border = isHoveredOrFocused() ? 0xFFFFE17A : HyruleGuiTheme.GOLD_BRIGHT;
            top = isHoveredOrFocused() ? 0xFF558C31 : HyruleGuiTheme.GREEN_TOP;
            bottom = isHoveredOrFocused() ? 0xFF315B20 : HyruleGuiTheme.GREEN_BOTTOM;
            textColor = HyruleGuiTheme.TEXT_LIGHT;
        } else {
            border = isHoveredOrFocused() ? 0xFFFFD66A : HyruleGuiTheme.GOLD_DARK;
            top = isHoveredOrFocused() ? 0xFF81512A : 0xFF65401F;
            bottom = isHoveredOrFocused() ? 0xFF4C2B17 : 0xFF382013;
            textColor = HyruleGuiTheme.TEXT_LIGHT;
        }

        graphics.fill(x + 2, y + 3, x + width + 2, y + height + 3, 0x66000000);
        graphics.fill(x, y, x + width, y + height, HyruleGuiTheme.BROWN_OUTER);
        HyruleGuiTheme.drawBorder(graphics, x + 1, y + 1, width - 2, height - 2, border);
        graphics.fillGradient(x + 3, y + 3, x + width - 3, y + height - 3, top, bottom);
        graphics.fill(x + 4, y + 3, x + width - 4, y + 4, 0x55FFFFFF);

        Font font = Minecraft.getInstance().font;
        String fullText = getMessage().getString();
        int availableWidth = Math.max(0, width - 8);
        String visibleText = font.plainSubstrByWidth(fullText, availableWidth);
        if (visibleText.length() < fullText.length() && availableWidth > font.width("...")) {
            visibleText = font.plainSubstrByWidth(fullText, availableWidth - font.width("...")) + "...";
        }
        graphics.drawCenteredString(font, visibleText, x + width / 2,
                y + (height - 8) / 2, textColor);
    }

    public enum Style {
        BROWN,
        GREEN,
        TAB
    }
}
