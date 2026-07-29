package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.item.FairyOcarina;
import com.superworldsun.superslegend.items.item.OcarinaOfTime;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class OcarinaPoseEvents {
    private static final Map<Player, InteractionHand> PLAYING_HANDS =
            Collections.synchronizedMap(new WeakHashMap<>());

    private OcarinaPoseEvents() {
    }

    public static void setPlaying(Player player, InteractionHand hand, boolean playing) {
        if (!playing) {
            PLAYING_HANDS.remove(player);
            if (isOcarina(player.getUseItem())) {
                player.stopUsingItem();
            }
            return;
        }

        ItemStack stack = player.getItemInHand(hand);
        if (isOcarina(stack)) {
            PLAYING_HANDS.put(player, hand);
            player.startUsingItem(hand);
        }
    }

    @SubscribeEvent
    public static void maintainPlayingPose(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) {
            return;
        }

        Player player = event.player;
        InteractionHand hand = PLAYING_HANDS.get(player);
        if (hand == null) {
            return;
        }

        if (!player.isAlive() || !isOcarina(player.getItemInHand(hand))) {
            setPlaying(player, hand, false);
            return;
        }

        if (!player.isUsingItem() || player.getUsedItemHand() != hand || !isOcarina(player.getUseItem())) {
            player.startUsingItem(hand);
        }
    }

    private static boolean isOcarina(ItemStack stack) {
        return stack.getItem() instanceof OcarinaOfTime || stack.getItem() instanceof FairyOcarina;
    }
}
