package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.sound.DekuFlowerGlideSound;
import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.DekuFlowerInputMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class DekuFlowerInputEvents {
    private static boolean glideSoundPlaying;

    private DekuFlowerInputEvents() {
    }

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        Input input = event.getInput();
        if (DekuFlowerFlightEvents.isBuried(player)) {
            input.leftImpulse = 0.0F;
            input.forwardImpulse = 0.0F;
            input.jumping = false;
            return;
        }

        if (DekuFlowerFlightEvents.isFlying(player)) {
            NetworkDispatcher.network_channel.sendToServer(new DekuFlowerInputMessage(
                    input.leftImpulse,
                    input.forwardImpulse,
                    player.getYRot(),
                    input.shiftKeyDown
            ));
            input.jumping = false;
        }
    }

    @SubscribeEvent
    public static void onInteractionInput(InputEvent.InteractionKeyMappingTriggered event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && DekuFlowerFlightEvents.isFlowerAbilityActive(player)
                && (event.isAttack() || event.isUseItem())) {
            event.setCanceled(true);
            event.setSwingHand(false);
        }
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && DekuFlowerFlightEvents.isFlowerAbilityActive(player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        boolean gliding = player != null && DekuFlowerFlightEvents.isGliding(player);
        if (gliding && !glideSoundPlaying) {
            minecraft.getSoundManager().play(new DekuFlowerGlideSound(player));
            glideSoundPlaying = true;
        } else if (!gliding) {
            glideSoundPlaying = false;
        }
    }
}
