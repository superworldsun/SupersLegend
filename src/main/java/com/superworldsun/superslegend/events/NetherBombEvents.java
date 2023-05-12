package com.superworldsun.superslegend.events;


import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class NetherBombEvents {


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.level;

        // Check if player is holding bomb item in either hand and is in Nether dimension
        if (!world.isClientSide && (
                player.getMainHandItem().getItem() == ItemInit.BOMB.get() || player.getOffhandItem().getItem() == ItemInit.BOMB.get() ||
                player.getMainHandItem().getItem() == ItemInit.WATER_BOMB.get() || player.getOffhandItem().getItem() == ItemInit.WATER_BOMB.get())) {
            if (world.dimension().location().equals(World.NETHER.location())) {
                // Remove bomb item from player's hand
                if (player.getMainHandItem().getItem() == ItemInit.BOMB.get() ||
                        player.getMainHandItem().getItem() == ItemInit.WATER_BOMB.get() ||
                        player.getMainHandItem().getItem() == ItemInit.BOMB_ARROW.get())
                {
                    player.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                }
                else
                {
                    player.setItemInHand(Hand.OFF_HAND, ItemStack.EMPTY);
                }
                // Explode player
                world.explode(null, player.getX(), player.getY(), player.getZ(), 3.0F, Explosion.Mode.DESTROY);
            }
        }
    }

}
