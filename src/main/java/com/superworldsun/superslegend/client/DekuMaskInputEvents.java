package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.sound.DekuMagicBubbleChargeSound;
import com.superworldsun.superslegend.client.sound.DekuMagicBubbleFlightSound;
import com.superworldsun.superslegend.entities.projectiles.magic.DekuMagicBubbleEntity;
import com.superworldsun.superslegend.items.curios.head.masks.DekuMask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class DekuMaskInputEvents {
    private static final float CHARGING_FOV_MULTIPLIER = 0.85F;
    private static final float FOV_TRANSITION_SPEED = 0.25F;
    private static float currentFovMultiplier = 1.0F;
    private static final Set<Integer> BUBBLES_WITH_FLIGHT_SOUND = new HashSet<>();

    private DekuMaskInputEvents() {
    }

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        if (!DekuMask.isChargingMagicBubble(player)) {
            return;
        }

        Input input = event.getInput();
        input.leftImpulse = 0.0F;
        input.forwardImpulse = 0.0F;
        input.jumping = false;
        // shiftKeyDown is intentionally untouched so crouching remains available.
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        float targetMultiplier = player != null && DekuMask.isChargingMagicBubble(player)
                ? CHARGING_FOV_MULTIPLIER
                : 1.0F;
        currentFovMultiplier += (targetMultiplier - currentFovMultiplier) * FOV_TRANSITION_SPEED;
        if (Math.abs(targetMultiplier - currentFovMultiplier) < 0.001F) {
            currentFovMultiplier = targetMultiplier;
        }

        updateBubbleFlightSounds(minecraft);
    }

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {
        event.setFOV(event.getFOV() * currentFovMultiplier);
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide
                || !(event.getEntity() instanceof DekuMagicBubbleEntity bubble)) {
            return;
        }

        if (bubble.isCharging()) {
            Minecraft.getInstance().getSoundManager().play(new DekuMagicBubbleChargeSound(bubble));
        }
    }

    private static void updateBubbleFlightSounds(Minecraft minecraft) {
        if (minecraft.level == null) {
            BUBBLES_WITH_FLIGHT_SOUND.clear();
            return;
        }

        BUBBLES_WITH_FLIGHT_SOUND.removeIf(entityId -> {
            if (!(minecraft.level.getEntity(entityId) instanceof DekuMagicBubbleEntity bubble)) {
                return true;
            }
            return !bubble.isAlive();
        });

        for (net.minecraft.world.entity.Entity entity : minecraft.level.entitiesForRendering()) {
            if (entity instanceof DekuMagicBubbleEntity bubble
                    && bubble.isAlive()
                    && !bubble.isCharging()
                    && BUBBLES_WITH_FLIGHT_SOUND.add(bubble.getId())) {
                minecraft.getSoundManager().play(new DekuMagicBubbleFlightSound(bubble));
            }
        }
    }
}
