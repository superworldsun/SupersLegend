package com.superworldsun.superslegend.client.keys;

import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.items.ammobags.BombBagItem;
import com.superworldsun.superslegend.network.message.DropBombMessage;
import com.superworldsun.superslegend.network.message.MaskAbilityMessage;
import com.superworldsun.superslegend.network.message.ToggleCrawlingMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Predicate;

@EventBusSubscriber(bus = Bus.MOD, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class KeyBindings {
    private static final String KEYS_CATEGORY = "key.categories." + SupersLegendMain.MOD_ID;
    public static final KeyMapping KEY_USE_MASK = new KeyMapping("key.mask_ability", GLFW.GLFW_KEY_B, KEYS_CATEGORY);
    public static final KeyMapping KEY_DROP_BOMB = new KeyMapping("key.drop_bomb", GLFW.GLFW_KEY_N, KEYS_CATEGORY);
    public static final KeyMapping KEY_CRAWL = new KeyMapping("key.crawl", GLFW.GLFW_KEY_V, KEYS_CATEGORY);
    // Dosent work as intended and is incomplete
    // public static final KeyBinding SELECT_INVENTORY = new KeyBinding("key.select_inventory", GLFW.GLFW_KEY_C, "key.categories." + SupersLegendMain.MOD_ID);

    @SubscribeEvent
    public static void onClientSetup(RegisterKeyMappingsEvent event) {
        event.register(KEY_USE_MASK);
        event.register(KEY_DROP_BOMB);
        event.register(KEY_CRAWL);
        // ClientRegistry.registerKeyBinding(SELECT_INVENTORY);
    }

    @EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
    private static class KeyboardInputEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.player == null || minecraft.screen != null) {
                return;
            }

            if (event.getKey() == KEY_USE_MASK.getKey().getValue()) {
                useMaskKeyPressed(minecraft, event.getAction());
            }
            else if (event.getKey() == KEY_DROP_BOMB.getKey().getValue()) {
                dropBombKeyPressed(minecraft, event.getAction());
            }
            else if (event.getKey() == KEY_CRAWL.getKey().getValue()) {
                crawlKeyPressed(minecraft, event.getAction());
            }
            /*
             * else if (SELECT_INVENTORY.isDown()) { NetworkDispatcher.networkChannel.sendToServer(new SelectInteractionMessage(0, true)); }
             */
        }

        private static void crawlKeyPressed(Minecraft minecraft, int keyAction) {
            if (keyAction == GLFW.GLFW_PRESS) {
                if (minecraft.player.getForcedPose() != Pose.SWIMMING)
                    minecraft.player.setForcedPose(Pose.SWIMMING);
                else
                    minecraft.player.setForcedPose(null);
                NetworkDispatcher.network_channel.sendToServer(new ToggleCrawlingMessage());
            }
        }

        private static void useMaskKeyPressed(Minecraft minecraft, int keyAction) {
            Predicate<ItemStack> isMaskWithAbility = stack -> stack.getItem() instanceof IMaskAbility;
            CuriosApi.getCuriosHelper().findEquippedCurio(isMaskWithAbility, minecraft.player).ifPresent(i -> {
                ItemStack maskStack = i.getRight();
                IMaskAbility mask = (IMaskAbility) maskStack.getItem();

                if (keyAction == GLFW.GLFW_PRESS) {
                    mask.startUsingAbility(minecraft.player);
                    NetworkDispatcher.network_channel.sendToServer(new MaskAbilityMessage(true));
                } else if (keyAction == GLFW.GLFW_RELEASE) {
                    mask.stopUsingAbility(minecraft.player);
                    NetworkDispatcher.network_channel.sendToServer(new MaskAbilityMessage(false));
                }
            });
        }

        private static void dropBombKeyPressed(Minecraft minecraft, int keyAction) {
            Predicate<ItemStack> isBombBag = stack -> stack.getItem() instanceof BombBagItem;
            CuriosApi.getCuriosHelper().findEquippedCurio(isBombBag, minecraft.player).ifPresent(i -> {
                if (keyAction == GLFW.GLFW_PRESS) {
                    NetworkDispatcher.network_channel.sendToServer(new DropBombMessage());
                }
            });
        }
    }
}
