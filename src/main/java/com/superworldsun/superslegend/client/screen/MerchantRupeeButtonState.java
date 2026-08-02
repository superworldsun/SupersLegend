package com.superworldsun.superslegend.client.screen;

import com.superworldsun.superslegend.client.gui.widget.HyruleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/** Applies the server's Rupee-trade availability result to the matching merchant screen. */
@OnlyIn(Dist.CLIENT)
public final class MerchantRupeeButtonState {
    private static int trackedContainerId = -1;
    private static HyruleButton trackedButton;

    private MerchantRupeeButtonState() {
    }

    public static void track(int containerId, HyruleButton button) {
        trackedContainerId = containerId;
        trackedButton = button;
    }

    public static void apply(int containerId, boolean available) {
        if (containerId != trackedContainerId || trackedButton == null
                || !(Minecraft.getInstance().screen instanceof MerchantScreen screen)
                || screen.getMenu().containerId != containerId) {
            return;
        }

        trackedButton.active = available;
        trackedButton.setTooltip(Tooltip.create(Component.literal(available
                ? "Pay from your equipped rupee wallet"
                : "This trader has no Rupee trades")));
    }
}
