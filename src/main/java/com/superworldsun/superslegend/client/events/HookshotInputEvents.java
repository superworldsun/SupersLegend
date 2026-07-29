package com.superworldsun.superslegend.client.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.events.HookshotPullPoseEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class HookshotInputEvents {
    private HookshotInputEvents() {
    }

    @SubscribeEvent
    public static void lockMainHandHookshotHotbar(InputEvent.MouseScrollingEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null
                && HookshotPlayerPoseEvents.findActiveHookHand(player) == InteractionHand.MAIN_HAND) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void preventCrouchingWhileLatchedToBlock(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        if (HookshotPullPoseEvents.hasActiveBlockPull(player)) {
            event.getInput().shiftKeyDown = false;
            player.setShiftKeyDown(false);
        }
    }
}
