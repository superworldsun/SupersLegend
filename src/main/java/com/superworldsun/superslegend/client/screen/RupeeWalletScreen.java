package com.superworldsun.superslegend.client.screen;

import com.superworldsun.superslegend.items.wallet.RupeeWalletItem;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.WithdrawRupeesMessage;
import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RupeeWalletScreen extends Screen {
    private static final int PANEL_WIDTH = 320;
    private static final int PANEL_HEIGHT = 246;
    private static final int ROW_HEIGHT = 20;
    private static final RupeeValue[] DISPLAY_ORDER = {
            RupeeValue.GREEN,
            RupeeValue.BLUE,
            RupeeValue.YELLOW,
            RupeeValue.RED,
            RupeeValue.PURPLE,
            RupeeValue.SILVER,
            RupeeValue.GOLD
    };

    private final InteractionHand hand;
    private final int[] selectedCounts = new int[RupeeValue.values().length];
    private final Button[] subtractButtons = new Button[RupeeValue.values().length];
    private final Button[] addButtons = new Button[RupeeValue.values().length];
    private Button confirmButton;
    private Button maxButton;
    private Button withdrawModeButton;
    private Button depositModeButton;
    private Mode mode = Mode.WITHDRAW;
    private boolean modeInitialized;
    private int panelLeft;
    private int panelTop;

    public RupeeWalletScreen(InteractionHand hand) {
        super(Component.translatable("screen.superslegend.rupee_wallet.title"));
        this.hand = hand;
    }

    @Override
    protected void init() {
        panelLeft = (width - PANEL_WIDTH) / 2;
        panelTop = Math.max(4, (height - PANEL_HEIGHT) / 2);
        if (!modeInitialized) {
            mode = getBalance(getWalletStack()) == 0 ? Mode.DEPOSIT : Mode.WITHDRAW;
            modeInitialized = true;
        }
        int rowsTop = panelTop + 61;

        withdrawModeButton = addRenderableWidget(Button.builder(
                        Component.translatable("screen.superslegend.rupee_wallet.withdraw_mode"),
                        button -> setMode(Mode.WITHDRAW))
                .bounds(panelLeft + 56, panelTop + 21, 102, 18)
                .build());
        depositModeButton = addRenderableWidget(Button.builder(
                        Component.translatable("screen.superslegend.rupee_wallet.deposit_mode"),
                        button -> setMode(Mode.DEPOSIT))
                .bounds(panelLeft + 162, panelTop + 21, 102, 18)
                .build());

        for (int row = 0; row < DISPLAY_ORDER.length; row++) {
            RupeeValue denomination = DISPLAY_ORDER[row];
            int buttonY = rowsTop + row * ROW_HEIGHT;
            subtractButtons[denomination.ordinal()] = addRenderableWidget(
                    Button.builder(Component.literal("-"), button -> adjust(denomination, -stepSize()))
                    .bounds(panelLeft + 100, buttonY, 22, 17)
                    .build());
            addButtons[denomination.ordinal()] = addRenderableWidget(
                    Button.builder(Component.literal("+"), button -> adjust(denomination, stepSize()))
                    .bounds(panelLeft + 174, buttonY, 22, 17)
                    .build());
        }

        confirmButton = addRenderableWidget(Button.builder(
                        Component.empty(), button -> confirmTransaction())
                .bounds(panelLeft + 215, panelTop + 218, 93, 18)
                .build());
        maxButton = addRenderableWidget(Button.builder(Component.empty(), button -> selectMaximum())
                .bounds(panelLeft + 12, panelTop + 218, 86, 18)
                .build());
        addRenderableWidget(Button.builder(Component.translatable("screen.superslegend.rupee_wallet.reset"),
                        button -> resetSelection())
                .bounds(panelLeft + 102, panelTop + 218, 50, 18)
                .build());
        addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), button -> onClose())
                .bounds(panelLeft + 156, panelTop + 218, 55, 18)
                .build());
        updateButtons();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        drawPanel(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void drawPanel(GuiGraphics graphics) {
        graphics.fill(panelLeft, panelTop, panelLeft + PANEL_WIDTH, panelTop + PANEL_HEIGHT, 0xEE202020);
        graphics.fill(panelLeft + 2, panelTop + 2, panelLeft + PANEL_WIDTH - 2, panelTop + PANEL_HEIGHT - 2,
                0xEE454545);
        graphics.fill(panelLeft + 5, panelTop + 5, panelLeft + PANEL_WIDTH - 5, panelTop + PANEL_HEIGHT - 5,
                0xEE252525);

        graphics.drawCenteredString(font, title, width / 2, panelTop + 11, 0xFFFFFF);

        ItemStack wallet = getWalletStack();
        int balance = getBalance(wallet);
        graphics.drawString(font,
                Component.translatable("screen.superslegend.rupee_wallet.balance", balance),
                panelLeft + 12, panelTop + 47, 0xFFFFFF, false);
        graphics.drawCenteredString(font,
                Component.translatable("screen.superslegend.rupee_wallet.inventory", inventoryRupeeValue()),
                panelLeft + PANEL_WIDTH / 2, panelTop + 47, 0xA7E9FF);
        graphics.drawString(font,
                Component.translatable("screen.superslegend.rupee_wallet.selected", selectedValue()),
                panelLeft + 240, panelTop + 47, 0xFFD75A, false);

        int rowsTop = panelTop + 61;
        for (int row = 0; row < DISPLAY_ORDER.length; row++) {
            RupeeValue denomination = DISPLAY_ORDER[row];
            int rowY = rowsTop + row * ROW_HEIGHT;
            int background = row % 2 == 0 ? 0xCC171717 : 0xCC202020;
            graphics.fill(panelLeft + 12, rowY - 1, panelLeft + PANEL_WIDTH - 12, rowY + 18, background);
            graphics.renderItem(new ItemStack(denomination.item()), panelLeft + 20, rowY);
            graphics.drawString(font, Integer.toString(denomination.value()), panelLeft + 47, rowY + 4,
                    0xFFFFFF, false);

            int count = selectedCounts[denomination.ordinal()];
            String countText = Integer.toString(count);
            graphics.drawCenteredString(font, countText, panelLeft + 148, rowY + 4, 0xFFFFFF);
            graphics.drawString(font, "= " + (count * denomination.value()), panelLeft + 211, rowY + 4,
                    0xFFD75A, false);
            graphics.drawString(font,
                    Component.translatable(mode == Mode.WITHDRAW
                            ? "screen.superslegend.rupee_wallet.wallet_available"
                            : "screen.superslegend.rupee_wallet.inventory_available",
                            availableCount(denomination)),
                    panelLeft + 270, rowY + 4, 0x909090, false);
        }

        graphics.drawCenteredString(font, Component.translatable("screen.superslegend.rupee_wallet.controls"),
                panelLeft + PANEL_WIDTH / 2, panelTop + 205, 0xB8B8B8);
    }

    private void adjust(RupeeValue denomination, int change) {
        int index = denomination.ordinal();
        if (change < 0) {
            selectedCounts[index] = Math.max(0, selectedCounts[index] + change);
        } else if (mode == Mode.DEPOSIT) {
            int inventoryAvailable = inventoryCount(denomination) - selectedCounts[index];
            int valueStillNeeded = getWalletSpace() - selectedValue();
            int itemsNeeded = divideRoundUp(Math.max(0, valueStillNeeded), denomination.value());
            int maximumAddition = Math.min(Math.max(0, inventoryAvailable), itemsNeeded);
            selectedCounts[index] += Math.min(change, maximumAddition);
        } else {
            int availableValue = getBalance(getWalletStack()) - selectedValue();
            int maximumAddition = Math.max(0, availableValue / denomination.value());
            selectedCounts[index] += Math.min(change, maximumAddition);
        }
        updateButtons();
    }

    private int stepSize() {
        if (hasControlDown()) {
            return 64;
        }
        return hasShiftDown() ? 10 : 1;
    }

    private void resetSelection() {
        java.util.Arrays.fill(selectedCounts, 0);
        updateButtons();
    }

    private void updateButtons() {
        if (confirmButton != null) {
            int selected = selectedValue();
            confirmButton.active = mode == Mode.WITHDRAW
                    ? selected > 0 && selected <= getBalance(getWalletStack())
                    : selected > 0 && getWalletSpace() > 0;
            confirmButton.setMessage(Component.translatable(mode == Mode.WITHDRAW
                    ? "screen.superslegend.rupee_wallet.withdraw"
                    : "screen.superslegend.rupee_wallet.deposit"));
        }
        if (maxButton != null) {
            maxButton.setMessage(Component.translatable(mode == Mode.WITHDRAW
                    ? "screen.superslegend.rupee_wallet.max_withdraw"
                    : "screen.superslegend.rupee_wallet.max_deposit"));
        }
        if (withdrawModeButton != null && depositModeButton != null) {
            withdrawModeButton.active = mode != Mode.WITHDRAW;
            depositModeButton.active = mode != Mode.DEPOSIT;
        }
        for (RupeeValue denomination : RupeeValue.values()) {
            int index = denomination.ordinal();
            if (subtractButtons[index] != null) {
                subtractButtons[index].active = selectedCounts[index] > 0;
            }
            if (addButtons[index] != null) {
                addButtons[index].active = canAdd(denomination);
            }
        }
    }

    private boolean canAdd(RupeeValue denomination) {
        if (mode == Mode.DEPOSIT) {
            return selectedValue() < getWalletSpace()
                    && selectedCounts[denomination.ordinal()] < inventoryCount(denomination);
        }
        return getBalance(getWalletStack()) - selectedValue() >= denomination.value();
    }

    private void confirmTransaction() {
        if (selectedValue() <= 0) {
            return;
        }

        int change = mode == Mode.DEPOSIT ? Math.max(0, selectedValue() - getWalletSpace()) : 0;
        if (change > 0 && !changeFitsAfterDeposit(change)) {
            showChangeDropWarning();
            return;
        }

        sendTransaction();
    }

    private void showChangeDropWarning() {
        if (minecraft == null) {
            return;
        }
        minecraft.setScreen(new ConfirmScreen(confirmed -> {
            if (confirmed) {
                sendTransaction();
            } else {
                minecraft.setScreen(this);
            }
        }, Component.translatable("screen.superslegend.rupee_wallet.change_warning_title"),
                Component.translatable("screen.superslegend.rupee_wallet.change_warning_message")) {
            @Override
            public boolean isPauseScreen() {
                return false;
            }
        });
    }

    private void sendTransaction() {
        NetworkDispatcher.network_channel.sendToServer(
                new WithdrawRupeesMessage(hand, selectedCounts, mode == Mode.DEPOSIT));
        if (minecraft != null) {
            minecraft.setScreen(null);
        }
    }

    private void setMode(Mode newMode) {
        if (mode != newMode) {
            mode = newMode;
            resetSelection();
        }
    }

    private void selectMaximum() {
        java.util.Arrays.fill(selectedCounts, 0);
        if (mode == Mode.DEPOSIT) {
            selectMaximumDeposit();
        } else {
            selectMaximumWithdrawal();
        }
        updateButtons();
    }

    private void selectMaximumDeposit() {
        int valueStillNeeded = getWalletSpace();
        for (RupeeValue denomination : RupeeValue.values()) {
            if (valueStillNeeded <= 0) {
                break;
            }
            int count = Math.min(inventoryCount(denomination),
                    divideRoundUp(valueStillNeeded, denomination.value()));
            selectedCounts[denomination.ordinal()] = count;
            valueStillNeeded -= count * denomination.value();
        }
    }

    private static int divideRoundUp(int value, int divisor) {
        return value <= 0 ? 0 : (value + divisor - 1) / divisor;
    }

    private void selectMaximumWithdrawal() {
        int remainingValue = getBalance(getWalletStack());
        int emptySlots = emptyInventorySlots();
        for (RupeeValue denomination : RupeeValue.values()) {
            int affordable = remainingValue / denomination.value();
            int fromPartialStacks = Math.min(affordable, partialStackSpace(denomination));
            selectedCounts[denomination.ordinal()] += fromPartialStacks;
            remainingValue -= fromPartialStacks * denomination.value();

            affordable = remainingValue / denomination.value();
            if (affordable <= 0 || emptySlots <= 0) {
                continue;
            }

            int stackSize = new ItemStack(denomination.item()).getMaxStackSize();
            int fromEmptySlots = Math.min(affordable, emptySlots * stackSize);
            selectedCounts[denomination.ordinal()] += fromEmptySlots;
            remainingValue -= fromEmptySlots * denomination.value();
            emptySlots -= (fromEmptySlots + stackSize - 1) / stackSize;
        }
    }

    private ItemStack getWalletStack() {
        if (minecraft == null || minecraft.player == null) {
            return ItemStack.EMPTY;
        }
        return minecraft.player.getItemInHand(hand);
    }

    private static int getBalance(ItemStack stack) {
        return stack.getItem() instanceof RupeeWalletItem wallet ? wallet.getStoredRupees(stack) : 0;
    }

    private int getWalletSpace() {
        ItemStack stack = getWalletStack();
        if (!(stack.getItem() instanceof RupeeWalletItem wallet)) {
            return 0;
        }
        return Math.max(0, wallet.getCapacity() - wallet.getStoredRupees(stack));
    }

    private int inventoryRupeeValue() {
        int total = 0;
        for (RupeeValue denomination : RupeeValue.values()) {
            total += inventoryCount(denomination) * denomination.value();
        }
        return total;
    }

    private int inventoryCount(RupeeValue denomination) {
        if (minecraft == null || minecraft.player == null) {
            return 0;
        }
        int count = 0;
        for (ItemStack stack : minecraft.player.getInventory().items) {
            if (stack.is(denomination.item())) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private boolean changeFitsAfterDeposit(int changeValue) {
        if (minecraft == null || minecraft.player == null) {
            return false;
        }

        List<ItemStack> simulatedInventory = new ArrayList<>();
        for (ItemStack stack : minecraft.player.getInventory().items) {
            simulatedInventory.add(stack.copy());
        }
        removeSelectedRupees(simulatedInventory);

        for (ItemStack changeStack : RupeeValue.splitIntoStacks(changeValue)) {
            int remaining = changeStack.getCount();
            for (ItemStack stack : simulatedInventory) {
                if (remaining <= 0) {
                    break;
                }
                if (!stack.isEmpty() && stack.is(changeStack.getItem())) {
                    int inserted = Math.min(remaining, stack.getMaxStackSize() - stack.getCount());
                    stack.grow(inserted);
                    remaining -= inserted;
                }
            }

            for (int slot = 0; slot < simulatedInventory.size() && remaining > 0; slot++) {
                if (simulatedInventory.get(slot).isEmpty()) {
                    int inserted = Math.min(remaining, changeStack.getMaxStackSize());
                    simulatedInventory.set(slot, new ItemStack(changeStack.getItem(), inserted));
                    remaining -= inserted;
                }
            }
            if (remaining > 0) {
                return false;
            }
        }
        return true;
    }

    private void removeSelectedRupees(List<ItemStack> inventory) {
        for (RupeeValue denomination : RupeeValue.values()) {
            int remaining = selectedCounts[denomination.ordinal()];
            for (ItemStack stack : inventory) {
                if (remaining <= 0) {
                    break;
                }
                if (stack.is(denomination.item())) {
                    int removed = Math.min(remaining, stack.getCount());
                    stack.shrink(removed);
                    remaining -= removed;
                }
            }
        }
    }

    private int availableCount(RupeeValue denomination) {
        if (mode == Mode.DEPOSIT) {
            return Math.max(0, inventoryCount(denomination) - selectedCounts[denomination.ordinal()]);
        }
        int remainingValue = Math.max(0, getBalance(getWalletStack()) - selectedValue());
        return remainingValue / denomination.value();
    }

    private int emptyInventorySlots() {
        if (minecraft == null || minecraft.player == null) {
            return 0;
        }
        int slots = 0;
        for (ItemStack stack : minecraft.player.getInventory().items) {
            if (stack.isEmpty()) {
                slots++;
            }
        }
        return slots;
    }

    private int partialStackSpace(RupeeValue denomination) {
        if (minecraft == null || minecraft.player == null) {
            return 0;
        }
        int space = 0;
        for (ItemStack stack : minecraft.player.getInventory().items) {
            if (stack.is(denomination.item())) {
                space += Math.max(0, stack.getMaxStackSize() - stack.getCount());
            }
        }
        return space;
    }

    private int selectedValue() {
        int total = 0;
        for (RupeeValue denomination : RupeeValue.values()) {
            total += selectedCounts[denomination.ordinal()] * denomination.value();
        }
        return total;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private enum Mode {
        WITHDRAW,
        DEPOSIT
    }
}
