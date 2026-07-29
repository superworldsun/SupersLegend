package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.client.gui.widget.HyruleButton;
import com.superworldsun.superslegend.client.screen.TradeCursorMemory;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SwitchToRupeeTradeMessage;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Adds a direct route from vanilla emerald trading to the wallet-backed shop. */
@Mixin(MerchantScreen.class)
public abstract class MixinMerchantScreen extends AbstractContainerScreen<MerchantMenu> {
    private static final int SUPERSLEGEND$BUTTON_WIDTH = 72;
    private static final int SUPERSLEGEND$BUTTON_HEIGHT = 20;
    private static final int SUPERSLEGEND$BUTTON_GAP = 2;

    protected MixinMerchantScreen(MerchantMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void superslegend$addRupeeTradeButton(CallbackInfo callbackInfo) {
        int buttonX = leftPos + 5;
        int buttonY;

        // Keep the tab attached to the vanilla window while preferring unused
        // space above it. The fallbacks keep it reachable at unusual GUI scales.
        if (topPos >= SUPERSLEGEND$BUTTON_HEIGHT + SUPERSLEGEND$BUTTON_GAP) {
            buttonY = topPos - SUPERSLEGEND$BUTTON_HEIGHT - SUPERSLEGEND$BUTTON_GAP;
        } else if (height - (topPos + imageHeight)
                >= SUPERSLEGEND$BUTTON_HEIGHT + SUPERSLEGEND$BUTTON_GAP) {
            buttonY = topPos + imageHeight + SUPERSLEGEND$BUTTON_GAP;
        } else if (width - (leftPos + imageWidth)
                >= SUPERSLEGEND$BUTTON_WIDTH + SUPERSLEGEND$BUTTON_GAP) {
            buttonX = leftPos + imageWidth + SUPERSLEGEND$BUTTON_GAP;
            buttonY = topPos;
        } else {
            buttonX = Math.max(2, leftPos - SUPERSLEGEND$BUTTON_WIDTH - SUPERSLEGEND$BUTTON_GAP);
            buttonY = topPos;
        }

        HyruleButton rupeesButton = new HyruleButton(
                buttonX,
                buttonY,
                SUPERSLEGEND$BUTTON_WIDTH,
                SUPERSLEGEND$BUTTON_HEIGHT,
                Component.literal("Rupees"),
                button -> superslegend$switchToRupeeTrading(),
                HyruleButton.Style.GREEN
        );
        rupeesButton.setTooltip(Tooltip.create(
                Component.literal("Pay from your equipped rupee wallet")));
        addRenderableWidget(rupeesButton);
        TradeCursorMemory.restoreIfPending();
    }

    @Unique
    private void superslegend$switchToRupeeTrading() {
        TradeCursorMemory.capture();
        NetworkDispatcher.network_channel.sendToServer(
                new SwitchToRupeeTradeMessage(menu.containerId));
    }
}
