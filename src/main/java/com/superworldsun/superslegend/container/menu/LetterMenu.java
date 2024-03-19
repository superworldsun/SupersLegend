package com.superworldsun.superslegend.container.menu;

import com.superworldsun.superslegend.registries.MenuTypeInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class LetterMenu extends ItemContainerMenu {
    public LetterMenu(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
        super(MenuTypeInit.LETTER.get(), windowId, playerInventory, buf, 1, 1);
    }
}
