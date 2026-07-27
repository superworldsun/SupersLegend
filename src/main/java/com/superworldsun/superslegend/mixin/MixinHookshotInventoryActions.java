package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.capability.hookshot.HookModel;
import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import com.superworldsun.superslegend.items.hookshot.HookshotItem;
import com.superworldsun.superslegend.items.hookshot.LongshotItem;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinHookshotInventoryActions {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "handlePlayerAction", at = @At("HEAD"), cancellable = true)
    private void superslegend$lockActiveHookshot(ServerboundPlayerActionPacket packet,
                                                 CallbackInfo callbackInfo) {
        if (!HookModel.get(player).getHasHook()) {
            return;
        }

        ServerboundPlayerActionPacket.Action action = packet.getAction();
        boolean droppingAnyItem = action == ServerboundPlayerActionPacket.Action.DROP_ITEM
                || action == ServerboundPlayerActionPacket.Action.DROP_ALL_ITEMS;
        if (droppingAnyItem && DekuFlowerFlightEvents.isFlowerAbilityActive(player)) {
            player.containerMenu.sendAllDataToRemote();
            callbackInfo.cancel();
            return;
        }

        boolean swappingHands = action == ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND;
        boolean droppingItem = droppingAnyItem && isHookshot(player.getMainHandItem());

        if (swappingHands || droppingItem) {
            player.containerMenu.sendAllDataToRemote();
            callbackInfo.cancel();
        }
    }

    private static boolean isHookshot(ItemStack stack) {
        return stack.getItem() instanceof HookshotItem || stack.getItem() instanceof LongshotItem;
    }
}
