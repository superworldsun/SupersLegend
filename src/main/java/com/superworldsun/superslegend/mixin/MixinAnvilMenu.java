package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu extends ItemCombinerMenu {
    protected MixinAnvilMenu(
            MenuType<?> menuType,
            int containerId,
            Inventory playerInventory,
            ContainerLevelAccess access
    ) {
        super(menuType, containerId, playerInventory, access);
    }

    @Redirect(
            method = {"createResult", "setItemName"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;literal(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"
            )
    )
    private MutableComponent superslegend$createWrittenItemName(String text) {
        return HylianTextUtil.isHoldingBookOfMudora(player)
                ? HylianTextUtil.markAsHylian(text)
                : Component.literal(text);
    }
}
