package com.superworldsun.superslegend.client.screen;

import com.superworldsun.superslegend.client.gui.HyruleGuiTheme;
import com.superworldsun.superslegend.client.gui.widget.HyruleButton;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SelectTradeModeMessage;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/** Initial currency choice shown before opening either trading system. */
@OnlyIn(Dist.CLIENT)
public class TradeChoiceScreen extends Screen {
    private static final int MAX_PANEL_WIDTH = 360;
    private static final int PANEL_HEIGHT = 172;

    private final int traderEntityId;
    private final Component traderName;
    private int panelLeft;
    private int panelTop;
    private int panelWidth;
    private int emeraldButtonX;
    private int rupeeButtonX;
    private int modeButtonWidth;
    private int modeButtonY;
    private boolean selectionSent;

    public TradeChoiceScreen(int traderEntityId, Component traderName) {
        super(Component.literal("Choose Trade Currency"));
        this.traderEntityId = traderEntityId;
        this.traderName = traderName.copy();
    }

    @Override
    protected void init() {
        panelWidth = Math.max(280, Math.min(MAX_PANEL_WIDTH, width - 20));
        panelLeft = (width - panelWidth) / 2;
        panelTop = Math.max(8, (height - PANEL_HEIGHT) / 2);

        int portraitWidth = panelWidth >= 330 ? 92 : 72;
        int contentLeft = panelLeft + 18;
        int contentWidth = panelWidth - portraitWidth - 42;
        int gap = 6;
        modeButtonWidth = Math.max(70, (contentWidth - gap) / 2);
        modeButtonY = panelTop + 111;
        emeraldButtonX = contentLeft;
        rupeeButtonX = contentLeft + modeButtonWidth + gap;

        HyruleButton emeraldButton = new HyruleButton(emeraldButtonX, modeButtonY, modeButtonWidth, 28,
                Component.literal("Emeralds"), button -> choose(SelectTradeModeMessage.Mode.EMERALDS),
                HyruleButton.Style.BROWN);
        emeraldButton.setTooltip(Tooltip.create(Component.literal("Open vanilla villager offers")));
        addRenderableWidget(emeraldButton);
        HyruleButton rupeeButton = new HyruleButton(rupeeButtonX, modeButtonY, modeButtonWidth, 28,
                Component.literal("Rupees"), button -> choose(SelectTradeModeMessage.Mode.RUPEES),
                HyruleButton.Style.GREEN);
        rupeeButton.setTooltip(Tooltip.create(Component.literal("Pay from your equipped rupee wallet")));
        addRenderableWidget(rupeeButton);
        addRenderableWidget(new HyruleButton(panelLeft + panelWidth - 31, panelTop + 10, 20, 18,
                Component.literal("X"), button -> onClose(), HyruleButton.Style.BROWN));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        HyruleGuiTheme.drawScreenShade(graphics, width, height);
        HyruleGuiTheme.drawFrame(graphics, panelLeft, panelTop, panelWidth, PANEL_HEIGHT);

        int portraitWidth = panelWidth >= 330 ? 92 : 72;
        int portraitLeft = panelLeft + panelWidth - portraitWidth - 17;
        int portraitTop = panelTop + 39;
        HyruleGuiTheme.drawDarkPanel(graphics, portraitLeft, portraitTop, portraitWidth, 113);

        String visibleTitle = fitText(traderTitle().getString(), panelWidth - 80);
        graphics.drawString(font, visibleTitle,
                panelLeft + (panelWidth - font.width(visibleTitle)) / 2,
                panelTop + 15, 0xFF3A2818, false);
        HyruleGuiTheme.drawRule(graphics, panelLeft + 16, panelTop + 32, panelWidth - 32);

        int contentLeft = panelLeft + 18;
        graphics.drawString(font, Component.literal("How would you like to trade?"),
                contentLeft, panelTop + 51, 0xFF594323, false);
        graphics.drawString(font, Component.literal("Choose vanilla offers or pay"),
                contentLeft, panelTop + 70, 0xFF6F5632, false);
        graphics.drawString(font, Component.literal("directly from your rupee wallet."),
                contentLeft, panelTop + 80, 0xFF6F5632, false);

        renderTrader(graphics, portraitLeft, portraitTop, portraitWidth, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);

        if (modeButtonWidth >= 100) {
            ItemStack emerald = new ItemStack(Items.EMERALD);
            ItemStack rupee = new ItemStack(ItemInit.RUPEE.get());
            graphics.renderItem(emerald, emeraldButtonX + 7, modeButtonY + 6);
            graphics.renderItem(rupee, rupeeButtonX + 7, modeButtonY + 6);
        }
    }

    private void renderTrader(GuiGraphics graphics, int portraitLeft, int portraitTop, int portraitWidth,
                              int mouseX, int mouseY) {
        if (minecraft == null || minecraft.level == null) {
            return;
        }
        Entity entity = minecraft.level.getEntity(traderEntityId);
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        int centerX = portraitLeft + portraitWidth / 2;
        int baseY = portraitTop + 101;
        int scale = portraitWidth >= 90 ? 42 : 34;
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, centerX, baseY, scale,
                centerX - mouseX, portraitTop + 38 - mouseY, livingEntity);
    }

    private void choose(SelectTradeModeMessage.Mode mode) {
        if (selectionSent) {
            return;
        }
        selectionSent = true;
        TradeCursorMemory.capture();
        NetworkDispatcher.network_channel.sendToServer(new SelectTradeModeMessage(traderEntityId, mode));
        onClose();
    }

    private Component traderTitle() {
        if (minecraft != null && minecraft.level != null) {
            Entity entity = minecraft.level.getEntity(traderEntityId);
            if (entity instanceof Villager villager) {
                int level = villager.getVillagerData().getLevel();
                if (level > 0 && level <= 5) {
                    return traderName.copy().append(" - ")
                            .append(Component.translatable("merchant.level." + level));
                }
            }
        }
        return traderName;
    }

    private String fitText(String text, int maximumWidth) {
        if (font.width(text) <= maximumWidth) {
            return text;
        }
        int ellipsisWidth = font.width("...");
        if (maximumWidth <= ellipsisWidth) {
            return font.plainSubstrByWidth(text, maximumWidth);
        }
        return font.plainSubstrByWidth(text, maximumWidth - ellipsisWidth) + "...";
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
