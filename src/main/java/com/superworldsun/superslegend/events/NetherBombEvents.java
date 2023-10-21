package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class NetherBombEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();

        // Check if player is holding bomb item in either hand and is in Nether dimension
        if (!level.isClientSide && (
                player.getMainHandItem().getItem() == ItemInit.BOMB.get() || player.getOffhandItem().getItem() == ItemInit.BOMB.get() ||
                        player.getMainHandItem().getItem() == ItemInit.WATER_BOMB.get() || player.getOffhandItem().getItem() == ItemInit.WATER_BOMB.get())) {
            if (level.dimension().location().equals(Level.NETHER.location())) {
                // Remove bomb item from player's hand
                if (player.getMainHandItem().getItem() == ItemInit.BOMB.get() ||
                        player.getMainHandItem().getItem() == ItemInit.WATER_BOMB.get() ||
                        player.getMainHandItem().getItem() == ItemInit.BOMB_ARROW.get())
                {
                    player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                }
                else
                {
                    player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                }
                // Explode player
                level.explode(null, player.getX(), player.getY(), player.getZ(), 3.0F, Level.ExplosionInteraction.BLOCK);
            }
        }
    }
}
