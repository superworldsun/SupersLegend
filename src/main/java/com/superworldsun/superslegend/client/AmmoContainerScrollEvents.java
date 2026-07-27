package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.ammobags.AmmoContainerItem;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SelectAmmoContainerItemMessage;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public final class AmmoContainerScrollEvents {
    private AmmoContainerScrollEvents() {
    }

    @SubscribeEvent
    public static void onMouseScrolled(ScreenEvent.MouseScrolled.Pre event) {
        if (event.getScrollDelta() == 0.0D
                || !(event.getScreen() instanceof AbstractContainerScreen<?> screen)
                || !screen.getMenu().getCarried().isEmpty()) {
            return;
        }

        Slot hoveredSlot = screen.getSlotUnderMouse();
        if (hoveredSlot == null || !hoveredSlot.hasItem()) {
            return;
        }

        ItemStack hoveredStack = hoveredSlot.getItem();
        if (!(hoveredStack.getItem() instanceof AmmoContainerItem containerItem)) {
            return;
        }

        int direction = event.getScrollDelta() > 0.0D ? 1 : -1;
        if (!containerItem.cycleSelectedItem(hoveredStack, direction)) {
            return;
        }

        int menuSlotIndex = screen.getMenu().slots.indexOf(hoveredSlot);
        if (menuSlotIndex < 0) {
            return;
        }

        NetworkDispatcher.network_channel.sendToServer(new SelectAmmoContainerItemMessage(
                screen.getMenu().containerId, menuSlotIndex, direction));
        event.setCanceled(true);
    }
}
