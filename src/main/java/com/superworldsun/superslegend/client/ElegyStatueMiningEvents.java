package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.statue.ElegyStatueEntity;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.MineElegyStatueMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT)
public final class ElegyStatueMiningEvents {
    private static int lastMiningEntityId = -1;

    private ElegyStatueMiningEvents() {
    }

    @SubscribeEvent
    public static void onAttackInput(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.isAttack()
                && minecraft.hitResult instanceof EntityHitResult hitResult
                && hitResult.getEntity() instanceof ElegyStatueEntity) {
            // Treat the statue like a block instead of sending a normal entity
            // attack, which would play the punch sound and only swing once.
            event.setCanceled(true);
            event.setSwingHand(true);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        ElegyStatueEntity statue = null;
        if (minecraft.player != null && minecraft.screen == null
                && minecraft.options.keyAttack.isDown()
                && minecraft.hitResult instanceof EntityHitResult hitResult
                && hitResult.getEntity() instanceof ElegyStatueEntity hitStatue) {
            statue = hitStatue;
        }

        int currentEntityId = statue == null ? -1 : statue.getId();
        if (lastMiningEntityId != -1 && lastMiningEntityId != currentEntityId) {
            NetworkDispatcher.network_channel.sendToServer(
                    new MineElegyStatueMessage(lastMiningEntityId, false));
        }
        lastMiningEntityId = currentEntityId;

        if (statue == null) {
            return;
        }

        minecraft.player.swing(InteractionHand.MAIN_HAND);
        NetworkDispatcher.network_channel.sendToServer(new MineElegyStatueMessage(statue.getId(), true));
    }
}
