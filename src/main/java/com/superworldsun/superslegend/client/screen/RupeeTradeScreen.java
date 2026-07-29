package com.superworldsun.superslegend.client.screen;

import com.superworldsun.superslegend.client.gui.HyruleGuiTheme;
import com.superworldsun.superslegend.client.gui.widget.HyruleButton;
import com.superworldsun.superslegend.client.hud.RupeeWalletHud;
import com.superworldsun.superslegend.menus.RupeeTradeMenu;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.BuyRupeeTradeMessage;
import com.superworldsun.superslegend.network.message.SelectTradeModeMessage;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.trading.rupee.RupeeTrade;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/** Wallet-backed trader shop, intentionally separate from vanilla merchant slots. */
@OnlyIn(Dist.CLIENT)
public class RupeeTradeScreen extends AbstractContainerScreen<RupeeTradeMenu> {
    private static final ResourceLocation VILLAGER_TEXTURE =
            new ResourceLocation("textures/gui/container/villager2.png");
    private static final int MAX_IMAGE_WIDTH = 414;
    private static final int MAX_IMAGE_HEIGHT = 232;
    private static final int MIN_IMAGE_WIDTH = 300;
    private static final int MIN_IMAGE_HEIGHT = 204;
    private static final int MAX_PURCHASE_QUANTITY = 64;
    private static final int MIN_ROW_HEIGHT = 24;
    private static final int MAX_ROWS_PER_PAGE = 5;

    private final List<TradeRowButton> rowButtons = new ArrayList<>();
    private final ItemStack defaultRupeeIcon = new ItemStack(ItemInit.RUPEE.get());

    private int selectedTradeIndex = -1;
    private int page;
    private int quantity = 1;
    private int tradesPerPage = MAX_ROWS_PER_PAGE;

    private int listLeft;
    private int listTop;
    private int listWidth;
    private int detailsLeft;
    private int detailsTop;
    private int detailsWidth;
    private int contentHeight;
    private int rowsTop;
    private int rowHeight;
    private int paginationY;
    private int quantityY;

    private HyruleButton previousPageButton;
    private HyruleButton nextPageButton;
    private HyruleButton minusButton;
    private HyruleButton plusButton;
    private HyruleButton buyButton;

    public RupeeTradeScreen(RupeeTradeMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = MAX_IMAGE_WIDTH;
        imageHeight = MAX_IMAGE_HEIGHT;
    }

    @Override
    protected void init() {
        imageWidth = Math.min(MAX_IMAGE_WIDTH, Math.max(MIN_IMAGE_WIDTH, width - 12));
        imageHeight = Math.min(MAX_IMAGE_HEIGHT, Math.max(MIN_IMAGE_HEIGHT, height - 12));
        super.init();

        int bodyGap = 6;
        listLeft = leftPos + 9;
        listTop = topPos + 48;
        contentHeight = imageHeight - 57;
        listWidth = Math.max(151, (imageWidth - 24) * 53 / 100);
        detailsLeft = listLeft + listWidth + bodyGap;
        detailsTop = listTop;
        detailsWidth = leftPos + imageWidth - 9 - detailsLeft;

        rowsTop = listTop + 25;
        paginationY = listTop + contentHeight - 24;
        int availableRowsHeight = Math.max(MIN_ROW_HEIGHT * 3, paginationY - rowsTop - 2);
        tradesPerPage = Mth.clamp(availableRowsHeight / MIN_ROW_HEIGHT, 3, MAX_ROWS_PER_PAGE);
        rowHeight = Math.max(MIN_ROW_HEIGHT, availableRowsHeight / tradesPerPage);
        quantityY = detailsTop + contentHeight - 57;

        normalizeSelection();
        rowButtons.clear();

        addRenderableWidget(new HyruleButton(leftPos + 13, topPos + 26, 74, 18,
                Component.literal("Emeralds"), button -> switchToEmeralds(), HyruleButton.Style.TAB));
        addRenderableWidget(new HyruleButton(leftPos + imageWidth - 31, topPos + 10, 20, 18,
                Component.literal("X"), button -> onClose(), HyruleButton.Style.BROWN));

        int firstTrade = page * tradesPerPage;
        List<RupeeTrade> trades = trades();
        for (int row = 0; row < tradesPerPage; row++) {
            int tradeIndex = firstTrade + row;
            if (tradeIndex >= trades.size()) {
                break;
            }
            ItemStack result = trades.get(tradeIndex).result();
            Component narration = Component.literal(result.getHoverName().getString()
                    + ", " + trades.get(tradeIndex).rupeeCost() + " rupees");
            TradeRowButton rowButton = new TradeRowButton(listLeft + 6,
                    rowsTop + row * rowHeight, listWidth - 12, rowHeight - 2,
                    narration, tradeIndex);
            rowButtons.add(addRenderableWidget(rowButton));
        }

        previousPageButton = addRenderableWidget(new HyruleButton(listLeft + 7, paginationY + 2, 25, 18,
                Component.literal("<"), button -> changePage(-1), HyruleButton.Style.BROWN));
        nextPageButton = addRenderableWidget(new HyruleButton(listLeft + listWidth - 32, paginationY + 2, 25, 18,
                Component.literal(">"), button -> changePage(1), HyruleButton.Style.BROWN));

        minusButton = addRenderableWidget(new HyruleButton(detailsLeft + 10, quantityY, 24, 20,
                Component.literal("-"), button -> adjustQuantity(-quantityStep()), HyruleButton.Style.BROWN));
        plusButton = addRenderableWidget(new HyruleButton(detailsLeft + detailsWidth - 34, quantityY, 24, 20,
                Component.literal("+"), button -> adjustQuantity(quantityStep()), HyruleButton.Style.BROWN));
        buyButton = addRenderableWidget(new HyruleButton(detailsLeft + 10,
                detailsTop + contentHeight - 29, detailsWidth - 20, 21,
                Component.literal("Trade"), button -> purchase(), HyruleButton.Style.GREEN));

        updateButtonStates();
        TradeCursorMemory.restoreIfPending();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        HyruleGuiTheme.drawScreenShade(graphics, width, height);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderCustomTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        HyruleGuiTheme.drawFrame(graphics, leftPos, topPos, imageWidth, imageHeight);
        HyruleGuiTheme.drawDarkPanel(graphics, listLeft, listTop, listWidth, contentHeight);
        HyruleGuiTheme.drawDarkPanel(graphics, detailsLeft, detailsTop, detailsWidth, contentHeight);

        Component traderTitle = Component.literal(displayedTraderTitle().getString());
        graphics.drawString(font, traderTitle,
                leftPos + (imageWidth - font.width(traderTitle)) / 2,
                topPos + 12, 0xFF3A2818, false);
        HyruleGuiTheme.drawRule(graphics, leftPos + 11, topPos + 23, imageWidth - 22);

        int balanceRight = leftPos + imageWidth - 39;
        int balanceX = Math.max(leftPos + 171, balanceRight - 78);
        graphics.renderItem(walletRupeeIcon(), balanceX, topPos + 27);
        graphics.drawString(font, Integer.toString(menu.getBalance()), balanceX + 19, topPos + 32,
                0xFF24512A, false);
        renderVillagerProgressBar(graphics, balanceX);

        renderTraderPortrait(graphics, leftPos + imageWidth - 49, topPos + 43, mouseX, mouseY);

        graphics.drawCenteredString(font, Component.literal("Trades"),
                listLeft + listWidth / 2, listTop + 9, HyruleGuiTheme.TEXT_LIGHT);
        HyruleGuiTheme.drawRule(graphics, listLeft + 7, listTop + 21, listWidth - 14);
        graphics.drawCenteredString(font,
                Component.literal((page + 1) + " / " + pageCount()),
                listLeft + listWidth / 2, paginationY + 7, HyruleGuiTheme.TEXT_MUTED);

        renderTradeDetails(graphics);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
        // All labels use absolute positions in renderBg so the responsive panels
        // and their widgets share a single coordinate system.
    }

    @Override
    protected void containerTick() {
        normalizeSelection();
        int maxQuantity = maximumQuantity();
        if (maxQuantity > 0) {
            quantity = Mth.clamp(quantity, 1, maxQuantity);
        } else {
            quantity = 1;
        }
        updateButtonStates();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        if (scrollDelta != 0.0D
                && HyruleGuiTheme.contains(mouseX, mouseY, listLeft, listTop, listWidth, contentHeight)
                && pageCount() > 1) {
            changePage(scrollDelta > 0.0D ? -1 : 1);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollDelta);
    }

    private void renderTradeDetails(GuiGraphics graphics) {
        graphics.drawCenteredString(font, Component.literal("Trade Details"),
                detailsLeft + detailsWidth / 2, detailsTop + 9, HyruleGuiTheme.TEXT_LIGHT);
        HyruleGuiTheme.drawRule(graphics, detailsLeft + 7, detailsTop + 21, detailsWidth - 14);

        RupeeTrade trade = selectedTrade();
        if (trade == null) {
            graphics.drawCenteredString(font, Component.literal("No rupee trades"),
                    detailsLeft + detailsWidth / 2, detailsTop + 52, HyruleGuiTheme.TEXT_MUTED);
            return;
        }

        ItemStack result = trade.result();
        graphics.drawString(font, Component.literal("You will receive:"),
                detailsLeft + 11, detailsTop + 28, HyruleGuiTheme.TEXT_MUTED, false);
        int resultX = detailsLeft + 12;
        int resultY = resultItemY();
        graphics.fill(resultX - 3, resultY - 3, resultX + 21, resultY + 21, 0xAA0B0806);
        HyruleGuiTheme.drawBorder(graphics, resultX - 3, resultY - 3, 24, 24,
                isTradeUnlocked(trade) ? HyruleGuiTheme.GOLD_BRIGHT : 0xFF6C4A38);
        graphics.renderItem(result, resultX + 1, resultY + 1);
        int totalResultCount = safeProduct(result.getCount(), quantity);
        graphics.renderItemDecorations(font, result, resultX + 1, resultY + 1,
                totalResultCount > 1 ? Integer.toString(totalResultCount) : null);

        int resultTextX = resultX + 27;
        int resultTextWidth = Math.max(25, detailsWidth - 51);
        String resultName = fit(result.getHoverName().getString(), resultTextWidth);
        graphics.drawString(font, resultName, resultTextX, resultY,
                isTradeUnlocked(trade) ? HyruleGuiTheme.TEXT_GREEN : HyruleGuiTheme.TEXT_MUTED, false);

        int remainingStock = remainingStock();
        Component stockText = remainingStock > 0
                ? Component.literal("Stock: " + remainingStock + " / " + trade.maxUses())
                : Component.literal("Out of stock");
        graphics.drawString(font, stockText, resultTextX, resultY + 11,
                remainingStock > 0 ? HyruleGuiTheme.TEXT_MUTED : HyruleGuiTheme.TEXT_RED, false);

        List<ItemStack> ingredients = trade.ingredients();
        if (!isCompactDetails()) {
            if (!isTradeUnlocked(trade)) {
                graphics.drawString(font, Component.literal("Unlocks at level " + trade.unlockLevel()),
                        detailsLeft + 11, detailsTop + 68, HyruleGuiTheme.TEXT_RED, false);
            } else {
                graphics.drawString(font,
                        Component.literal(ingredients.isEmpty() ? "Wallet purchase" : "Required items:"),
                        detailsLeft + 11, detailsTop + 68, HyruleGuiTheme.TEXT_LIGHT, false);
            }
            renderIngredientIcons(graphics, ingredients);
        }

        long totalCost = totalRupeeCost(trade);
        int costY = quantityY - 17;
        graphics.renderItem(rupeeIconForValue(totalCost), detailsLeft + 11, costY - 4);
        int costColor = totalCost <= menu.getBalance()
                ? HyruleGuiTheme.TEXT_GOLD : HyruleGuiTheme.TEXT_RED;
        graphics.drawString(font,
                Component.literal(trade.rupeeCost() == 0 ? "No rupee fee" : totalCost + " total"),
                detailsLeft + 31, costY + 1, costColor, false);

        int valueLeft = detailsLeft + 38;
        int valueRight = detailsLeft + detailsWidth - 38;
        graphics.fill(valueLeft, quantityY, valueRight, quantityY + 20, 0xCC0A0705);
        HyruleGuiTheme.drawBorder(graphics, valueLeft, quantityY,
                Math.max(1, valueRight - valueLeft), 20, HyruleGuiTheme.GOLD_DARK);
        graphics.drawCenteredString(font, Integer.toString(quantity),
                (valueLeft + valueRight) / 2, quantityY + 6, HyruleGuiTheme.TEXT_LIGHT);
    }

    private void renderIngredientIcons(GuiGraphics graphics, List<ItemStack> ingredients) {
        int y = ingredientIconsY();
        if (ingredients.isEmpty()) {
            graphics.drawString(font, fit("No item payment required.", detailsWidth - 22),
                    detailsLeft + 11, y + 4, HyruleGuiTheme.TEXT_MUTED, false);
            return;
        }

        int maximumIcons = Math.max(1, (detailsWidth - 22) / 22);
        int shown = Math.min(maximumIcons, ingredients.size());
        for (int index = 0; index < shown; index++) {
            ItemStack ingredient = ingredients.get(index);
            int x = detailsLeft + 11 + index * 22;
            boolean available = hasIngredientForQuantity(ingredient, quantity, ingredients, index);
            graphics.fill(x - 2, y - 2, x + 18, y + 18,
                    available ? 0xAA0A0705 : 0xAA4A1111);
            HyruleGuiTheme.drawBorder(graphics, x - 2, y - 2, 20, 20,
                    available ? HyruleGuiTheme.GOLD_DARK : 0xFFB44432);
            graphics.renderItem(ingredient, x, y);
            int needed = safeProduct(ingredient.getCount(), quantity);
            graphics.renderItemDecorations(font, ingredient, x, y, Integer.toString(needed));
        }

        if (ingredients.size() > shown) {
            graphics.drawString(font, "+" + (ingredients.size() - shown),
                    detailsLeft + detailsWidth - 20, y + 5, HyruleGuiTheme.TEXT_MUTED, false);
        }
    }

    private void renderTraderPortrait(GuiGraphics graphics, int centerX, int baseY, int mouseX, int mouseY) {
        AbstractVillager trader = menu.getTrader();
        if (trader == null || imageWidth < 350) {
            return;
        }
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, centerX, baseY, 20,
                centerX - mouseX, topPos + 20 - mouseY, trader);
    }

    private Component displayedTraderTitle() {
        int level = menu.getTraderLevel();
        if (level > 0 && level <= 5 && menu.showProgressBar()) {
            return title.copy().append(" - ")
                    .append(Component.translatable("merchant.level." + level));
        }
        return title;
    }

    private void renderVillagerProgressBar(GuiGraphics graphics, int balanceX) {
        int level = menu.getTraderLevel();
        if (!menu.showProgressBar() || level <= 0 || !VillagerData.canLevelUp(level)) {
            return;
        }

        int availableLeft = leftPos + 93;
        int availableRight = balanceX - 6;
        int availableWidth = availableRight - availableLeft;
        int barWidth = Math.min(102, availableWidth);
        if (barWidth < 24) {
            return;
        }

        int centeredX = leftPos + (imageWidth - barWidth) / 2;
        int barX = Mth.clamp(centeredX, availableLeft, availableRight - barWidth);

        int barY = topPos + 34;
        graphics.blit(VILLAGER_TEXTURE, barX, barY, 0,
                0.0F, 186.0F, barWidth, 5, 512, 256);
        int minimumXp = VillagerData.getMinXpPerLevel(level);
        int maximumXp = VillagerData.getMaxXpPerLevel(level);
        int traderXp = menu.getTraderXp();
        if (traderXp < minimumXp || maximumXp <= minimumXp) {
            return;
        }

        float pixelsPerXp = (float) barWidth / (float) (maximumXp - minimumXp);
        int filledWidth = Math.min(Mth.floor(pixelsPerXp * (traderXp - minimumXp)), barWidth - 1);
        graphics.blit(VILLAGER_TEXTURE, barX, barY, 0,
                0.0F, 191.0F, filledWidth + 1, 5, 512, 256);
    }

    private ItemStack walletRupeeIcon() {
        if (minecraft == null || minecraft.player == null) {
            return defaultRupeeIcon;
        }
        return RupeeWalletUtil.findEquippedWallet(minecraft.player)
                .map(result -> RupeeWalletHud.getWalletRupeeIcon(result.stack()))
                .orElse(defaultRupeeIcon);
    }

    private ItemStack rupeeIconForValue(long value) {
        if (value >= 300L) {
            return new ItemStack(ItemInit.GOLD_RUPEE.get());
        }
        if (value >= 100L) {
            return new ItemStack(ItemInit.SILVER_RUPEE.get());
        }
        if (value >= 50L) {
            return new ItemStack(ItemInit.PURPLE_RUPEE.get());
        }
        if (value >= 20L) {
            return new ItemStack(ItemInit.RED_RUPEE.get());
        }
        if (value >= 10L) {
            return new ItemStack(ItemInit.YELLOW_RUPEE.get());
        }
        if (value >= 5L) {
            return new ItemStack(ItemInit.BLUE_RUPEE.get());
        }
        return defaultRupeeIcon;
    }

    private void renderCustomTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        if (HyruleGuiTheme.contains(mouseX, mouseY,
                Math.max(leftPos + 171, leftPos + imageWidth - 117), topPos + 27, 16, 16)) {
            graphics.renderTooltip(font, Component.literal("Wallet balance: " + menu.getBalance()), mouseX, mouseY);
            return;
        }

        for (TradeRowButton rowButton : rowButtons) {
            int itemX = rowButton.getX() + 5;
            int itemY = rowButton.getY() + (rowButton.getHeight() - 16) / 2;
            if (HyruleGuiTheme.contains(mouseX, mouseY, itemX, itemY, 16, 16)) {
                RupeeTrade trade = tradeAt(rowButton.tradeIndex);
                if (trade != null) {
                    graphics.renderTooltip(font, trade.result(), mouseX, mouseY);
                }
                return;
            }
        }

        RupeeTrade trade = selectedTrade();
        if (trade == null) {
            return;
        }

        int resultX = detailsLeft + 13;
        int resultY = resultItemY() + 1;
        if (HyruleGuiTheme.contains(mouseX, mouseY, resultX, resultY, 16, 16)) {
            graphics.renderTooltip(font, trade.result(), mouseX, mouseY);
            return;
        }

        List<ItemStack> ingredients = trade.ingredients();
        if (isCompactDetails()) {
            return;
        }
        int maximumIcons = Math.max(1, (detailsWidth - 22) / 22);
        int shown = Math.min(maximumIcons, ingredients.size());
        for (int index = 0; index < shown; index++) {
            int x = detailsLeft + 11 + index * 22;
            int y = ingredientIconsY();
            if (HyruleGuiTheme.contains(mouseX, mouseY, x, y, 16, 16)) {
                graphics.renderTooltip(font, ingredients.get(index), mouseX, mouseY);
                return;
            }
        }
    }

    private void switchToEmeralds() {
        AbstractVillager trader = menu.getTrader();
        if (trader != null) {
            // Do not close this container locally. The server will replace it
            // with the vanilla merchant menu; a local close packet could race it.
            TradeCursorMemory.capture();
            NetworkDispatcher.network_channel.sendToServer(
                    new SelectTradeModeMessage(trader.getId(), SelectTradeModeMessage.Mode.EMERALDS));
        }
    }

    private void purchase() {
        RupeeTrade trade = selectedTrade();
        if (trade == null || !canPurchase()) {
            return;
        }
        NetworkDispatcher.network_channel.sendToServer(
                new BuyRupeeTradeMessage(menu.containerId, trade.id(), quantity));
    }

    private void selectTrade(int tradeIndex) {
        if (tradeIndex < 0 || tradeIndex >= trades().size()) {
            return;
        }
        selectedTradeIndex = tradeIndex;
        quantity = 1;
        updateButtonStates();
    }

    private void changePage(int direction) {
        int targetPage = Mth.clamp(page + direction, 0, Math.max(0, pageCount() - 1));
        if (targetPage == page) {
            return;
        }
        page = targetPage;
        selectedTradeIndex = Math.min(page * tradesPerPage, Math.max(0, trades().size() - 1));
        quantity = 1;
        rebuildWidgets();
    }

    private void adjustQuantity(int amount) {
        int maximum = maximumQuantity();
        if (maximum <= 0) {
            quantity = 1;
        } else {
            quantity = Mth.clamp(quantity + amount, 1, maximum);
        }
        updateButtonStates();
    }

    private int quantityStep() {
        return hasShiftDown() ? 10 : 1;
    }

    private void updateButtonStates() {
        int pageCount = pageCount();
        if (previousPageButton != null) {
            previousPageButton.active = page > 0;
        }
        if (nextPageButton != null) {
            nextPageButton.active = page + 1 < pageCount;
        }

        int maximum = maximumQuantity();
        if (minusButton != null) {
            minusButton.active = selectedTrade() != null && quantity > 1;
        }
        if (plusButton != null) {
            plusButton.active = selectedTrade() != null && maximum > 0 && quantity < maximum;
        }
        if (buyButton != null) {
            boolean purchasable = canPurchase();
            buyButton.active = purchasable;
            buyButton.setMessage(Component.literal(quantity > 1 ? "Trade x" + quantity : "Trade"));
            buyButton.setTooltip(purchasable ? null : Tooltip.create(purchaseFailureMessage()));
        }
    }

    private Component purchaseFailureMessage() {
        RupeeTrade trade = selectedTrade();
        if (trade == null) {
            return Component.literal("No trade selected");
        }
        if (!isTradeUnlocked(trade)) {
            return Component.literal("Requires villager level " + trade.unlockLevel());
        }
        if (remainingStock() < quantity) {
            return Component.literal("This trade is out of stock");
        }
        if (totalRupeeCost(trade) > menu.getBalance()) {
            return Component.literal("Not enough rupees in your equipped wallet");
        }
        if (!hasRequiredIngredients(trade, quantity)) {
            return Component.literal("Missing required items");
        }
        return Component.literal("Trade unavailable");
    }

    private boolean canPurchase() {
        RupeeTrade trade = selectedTrade();
        return trade != null
                && isTradeUnlocked(trade)
                && quantity > 0
                && remainingStock() >= quantity
                && totalRupeeCost(trade) <= menu.getBalance()
                && hasRequiredIngredients(trade, quantity);
    }

    private int maximumQuantity() {
        RupeeTrade trade = selectedTrade();
        if (trade == null || !isTradeUnlocked(trade)) {
            return 0;
        }

        int maximum = Math.min(MAX_PURCHASE_QUANTITY, Math.max(0, remainingStock()));
        if (trade.rupeeCost() > 0) {
            maximum = Math.min(maximum, menu.getBalance() / trade.rupeeCost());
        }
        maximum = Math.min(maximum, maximumIngredientPurchases(trade, maximum));
        return Math.max(0, maximum);
    }

    private int maximumIngredientPurchases(RupeeTrade trade, int limit) {
        if (trade.ingredients().isEmpty()) {
            return limit;
        }
        int supported = 0;
        for (int candidate = 1; candidate <= limit; candidate++) {
            if (!hasRequiredIngredients(trade, candidate)) {
                break;
            }
            supported = candidate;
        }
        return supported;
    }

    private boolean hasRequiredIngredients(RupeeTrade trade, int requestedQuantity) {
        if (minecraft == null || minecraft.player == null || requestedQuantity <= 0) {
            return trade.ingredients().isEmpty();
        }

        List<ItemStack> simulatedInventory = new ArrayList<>();
        for (ItemStack stack : minecraft.player.getInventory().items) {
            simulatedInventory.add(stack.copy());
        }

        for (int purchase = 0; purchase < requestedQuantity; purchase++) {
            for (ItemStack requirement : trade.ingredients()) {
                int remaining = requirement.getCount();
                for (ItemStack available : simulatedInventory) {
                    if (remaining <= 0) {
                        break;
                    }
                    if (matchesIngredient(available, requirement)) {
                        int removed = Math.min(remaining, available.getCount());
                        available.shrink(removed);
                        remaining -= removed;
                    }
                }
                if (remaining > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasIngredientForQuantity(ItemStack ingredient, int requestedQuantity,
                                             List<ItemStack> allIngredients, int ingredientIndex) {
        if (minecraft == null || minecraft.player == null) {
            return false;
        }
        int required = 0;
        for (int index = 0; index <= ingredientIndex && index < allIngredients.size(); index++) {
            ItemStack requirement = allIngredients.get(index);
            if (matchesIngredient(requirement, ingredient)
                    && matchesIngredient(ingredient, requirement)) {
                required += safeProduct(requirement.getCount(), requestedQuantity);
            }
        }
        int available = 0;
        for (ItemStack stack : minecraft.player.getInventory().items) {
            if (matchesIngredient(stack, ingredient)) {
                available += stack.getCount();
            }
        }
        return available >= required;
    }

    private void normalizeSelection() {
        int tradeCount = trades().size();
        if (tradeCount == 0) {
            selectedTradeIndex = -1;
            page = 0;
            quantity = 1;
            return;
        }

        selectedTradeIndex = Mth.clamp(selectedTradeIndex < 0 ? 0 : selectedTradeIndex, 0, tradeCount - 1);
        page = Mth.clamp(page, 0, Math.max(0, pageCount() - 1));
        int firstOnPage = page * tradesPerPage;
        int lastOnPage = Math.min(tradeCount, firstOnPage + tradesPerPage) - 1;
        if (selectedTradeIndex < firstOnPage || selectedTradeIndex > lastOnPage) {
            selectedTradeIndex = firstOnPage;
            quantity = 1;
        }
    }

    private List<RupeeTrade> trades() {
        return menu.getTrades();
    }

    private RupeeTrade selectedTrade() {
        return tradeAt(selectedTradeIndex);
    }

    private RupeeTrade tradeAt(int tradeIndex) {
        List<RupeeTrade> trades = trades();
        return tradeIndex >= 0 && tradeIndex < trades.size() ? trades.get(tradeIndex) : null;
    }

    private int remainingStock() {
        return selectedTradeIndex < 0 ? 0 : Math.max(0, menu.getRemainingStock(selectedTradeIndex));
    }

    private int pageCount() {
        int tradeCount = trades().size();
        return Math.max(1, (tradeCount + tradesPerPage - 1) / tradesPerPage);
    }

    private int traderLevel() {
        return Math.max(1, menu.getTraderLevel());
    }

    private boolean isTradeUnlocked(RupeeTrade trade) {
        return trade.isUnlockedAt(traderLevel());
    }

    private boolean isCompactDetails() {
        return contentHeight < 168;
    }

    private int ingredientIconsY() {
        return detailsTop + 79;
    }

    private int resultItemY() {
        return detailsTop + 40;
    }

    private long totalRupeeCost(RupeeTrade trade) {
        return (long) trade.rupeeCost() * quantity;
    }

    private String fit(String text, int maximumWidth) {
        String visible = font.plainSubstrByWidth(text, maximumWidth);
        if (visible.length() < text.length() && maximumWidth > font.width("...")) {
            visible = font.plainSubstrByWidth(text, maximumWidth - font.width("...")) + "...";
        }
        return visible;
    }

    private static int safeProduct(int left, int right) {
        long product = (long) left * right;
        return product > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) Math.max(0, product);
    }

    private static boolean matchesIngredient(ItemStack candidate, ItemStack required) {
        if (candidate.isEmpty() || !ItemStack.isSameItem(candidate, required)) {
            return false;
        }
        return !required.hasTag() || candidate.hasTag()
                && NbtUtils.compareNbt(required.getTag(), candidate.getTag(), false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private final class TradeRowButton extends Button {
        private final int tradeIndex;

        private TradeRowButton(int x, int y, int width, int height, Component narration, int tradeIndex) {
            super(Button.builder(narration, button -> selectTrade(tradeIndex)).bounds(x, y, width, height));
            this.tradeIndex = tradeIndex;
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            RupeeTrade trade = tradeAt(tradeIndex);
            if (trade == null) {
                return;
            }

            boolean selected = tradeIndex == selectedTradeIndex;
            boolean unlocked = isTradeUnlocked(trade);
            int stock = Math.max(0, menu.getRemainingStock(tradeIndex));
            int border = selected ? HyruleGuiTheme.GOLD_BRIGHT
                    : isHoveredOrFocused() ? 0xFFC89A42 : 0xFF5D4021;
            int top = selected ? HyruleGuiTheme.GREEN_TOP
                    : isHoveredOrFocused() ? 0xFF49301C : 0xFF322115;
            int bottom = selected ? HyruleGuiTheme.GREEN_BOTTOM
                    : isHoveredOrFocused() ? 0xFF2B1B11 : 0xFF1D130D;

            graphics.fill(getX(), getY(), getX() + width, getY() + height, HyruleGuiTheme.BROWN_OUTER);
            HyruleGuiTheme.drawBorder(graphics, getX() + 1, getY() + 1,
                    width - 2, height - 2, border);
            graphics.fillGradient(getX() + 3, getY() + 3,
                    getX() + width - 3, getY() + height - 3, top, bottom);

            ItemStack result = trade.result();
            int itemX = getX() + 5;
            int itemY = getY() + (height - 16) / 2;
            graphics.renderItem(result, itemX, itemY);
            graphics.renderItemDecorations(font, result, itemX, itemY);

            int priceWidth = Math.min(57, Math.max(43, width / 3));
            int nameX = getX() + 26;
            int nameWidth = Math.max(20, width - 31 - priceWidth);
            String name = fit(result.getHoverName().getString(), nameWidth);
            graphics.drawString(font, name, nameX, getY() + 4,
                    unlocked ? HyruleGuiTheme.TEXT_LIGHT : 0xFF8E8272, false);

            Component status = !unlocked
                    ? Component.literal("Level " + trade.unlockLevel())
                    : stock <= 0 ? Component.literal("Out of stock")
                    : Component.literal("Stock " + stock);
            int statusColor = unlocked && stock > 0
                    ? HyruleGuiTheme.TEXT_MUTED : HyruleGuiTheme.TEXT_RED;
            graphics.drawString(font, status, nameX, getY() + height - 10, statusColor, false);

            int priceX = getX() + width - priceWidth;
            graphics.renderItem(rupeeIconForValue(trade.rupeeCost()),
                    priceX, getY() + (height - 16) / 2);
            int costColor = menu.getBalance() >= trade.rupeeCost()
                    ? HyruleGuiTheme.TEXT_GOLD : HyruleGuiTheme.TEXT_RED;
            String price = trade.rupeeCost() == 0 ? "--" : Integer.toString(trade.rupeeCost());
            graphics.drawString(font, price, priceX + 17, getY() + (height - 8) / 2,
                    costColor, false);
        }
    }
}
