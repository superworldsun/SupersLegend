package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public final class HylianTextEvents {
    private HylianTextEvents() {
    }

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        if (HylianTextUtil.isHoldingBookOfMudora(Minecraft.getInstance().player)) {
            event.setMessage(HylianTextUtil.markHylianChatMessage(event.getMessage()));
        }
    }

    @SubscribeEvent
    public static void onClientChatReceived(ClientChatReceivedEvent.Player event) {
        String signedMessage = event.getPlayerChatMessage().signedContent();
        if (!HylianTextUtil.isMarkedHylianChatMessage(signedMessage)) {
            return;
        }

        Component hylianMessage = HylianTextUtil.markAsHylian(
                HylianTextUtil.removeHylianChatMarker(signedMessage)
        );
        event.setMessage(event.getBoundChatType().decorate(hylianMessage));
    }
}
