package com.superworldsun.superslegend.client.tooltip;

import com.superworldsun.superslegend.items.ammobags.AmmoContainerTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ClientAmmoContainerTooltip implements ClientTooltipComponent {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/bundle.png");
    private final List<ItemStack> contents;
    private final int slots;
    private final int selectedIndex;

    public ClientAmmoContainerTooltip(AmmoContainerTooltip tooltip) {
        this.contents = tooltip.getContents();
        this.slots = tooltip.getSlots();
        this.selectedIndex = tooltip.getSelectedIndex();
    }

    @Override
    public int getHeight() {
        return 26;
    }

    @Override
    public int getWidth(Font font) {
        return slots * 18 + 2;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        for (int slot = 0; slot < slots; slot++) {
            blit(graphics, x + slot * 18 + 1, y + 1, Texture.SLOT);
        }
        drawBorder(graphics, x, y);

        for (int slot = 0; slot < contents.size() && slot < slots; slot++) {
            ItemStack storedStack = contents.get(slot);
            int itemX = x + slot * 18 + 2;
            graphics.renderItem(storedStack, itemX, y + 2, slot);
            graphics.renderItemDecorations(font, storedStack, itemX, y + 2);
            if (slot == selectedIndex) {
                AbstractContainerScreen.renderSlotHighlight(graphics, itemX, y + 2, 0);
            }
        }
    }

    private void drawBorder(GuiGraphics graphics, int x, int y) {
        blit(graphics, x, y, Texture.BORDER_CORNER_TOP);
        blit(graphics, x + slots * 18 + 1, y, Texture.BORDER_CORNER_TOP);

        for (int slot = 0; slot < slots; slot++) {
            blit(graphics, x + slot * 18 + 1, y, Texture.BORDER_HORIZONTAL_TOP);
            blit(graphics, x + slot * 18 + 1, y + 20, Texture.BORDER_HORIZONTAL_BOTTOM);
        }

        blit(graphics, x, y + 1, Texture.BORDER_VERTICAL);
        blit(graphics, x + slots * 18 + 1, y + 1, Texture.BORDER_VERTICAL);
        blit(graphics, x, y + 20, Texture.BORDER_CORNER_BOTTOM);
        blit(graphics, x + slots * 18 + 1, y + 20, Texture.BORDER_CORNER_BOTTOM);
    }

    private void blit(GuiGraphics graphics, int x, int y, Texture texture) {
        graphics.blit(TEXTURE, x, y, 0, texture.x, texture.y, texture.width, texture.height, 128, 128);
    }

    private enum Texture {
        SLOT(0, 0, 18, 20),
        BORDER_VERTICAL(0, 18, 1, 20),
        BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
        BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
        BORDER_CORNER_TOP(0, 20, 1, 1),
        BORDER_CORNER_BOTTOM(0, 60, 1, 1);

        private final int x;
        private final int y;
        private final int width;
        private final int height;

        Texture(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
