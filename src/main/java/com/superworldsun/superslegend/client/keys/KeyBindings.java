package com.superworldsun.superslegend.client.keys;

import java.util.function.Predicate;

import com.superworldsun.superslegend.network.message.ToggleCrawlingMessage;
import org.lwjgl.glfw.GLFW;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.items.ammobags.BombBagItem;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.DropBombMessage;
import com.superworldsun.superslegend.network.message.MaskAbilityMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Pose;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(bus = Bus.MOD, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class KeyBindings {
	private static final String KEYS_CATEGORY = "key.categories." + SupersLegendMain.MOD_ID;
	public static final KeyBinding KEY_USE_MASK = new KeyBinding("key.mask_ability", GLFW.GLFW_KEY_B, KEYS_CATEGORY);
	public static final KeyBinding KEY_DROP_BOMB = new KeyBinding("key.drop_bomb", GLFW.GLFW_KEY_N, KEYS_CATEGORY);
	public static final KeyBinding KEY_CRAWL = new KeyBinding("key.crawl", GLFW.GLFW_KEY_H, KEYS_CATEGORY);
	// Dosent work as intended and is incomplete
	// public static final KeyBinding SELECT_INVENTORY = new KeyBinding("key.select_inventory", GLFW.GLFW_KEY_C, "key.categories." + SupersLegendMain.MOD_ID);

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(KEY_USE_MASK);
		ClientRegistry.registerKeyBinding(KEY_DROP_BOMB);
		ClientRegistry.registerKeyBinding(KEY_CRAWL);
		// ClientRegistry.registerKeyBinding(SELECT_INVENTORY);
	}

	@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
	private static class KeyboardInputEvents {
		@SubscribeEvent
		public static void onKeyInput(KeyInputEvent event) {
			Minecraft minecraft = Minecraft.getInstance();

			if (minecraft.player == null || minecraft.screen != null) {
				return;
			}

			if (event.getKey() == KEY_USE_MASK.getKey().getValue()) {
				useMaskKeyPressed(minecraft, event.getAction());
			} else if (event.getKey() == KEY_DROP_BOMB.getKey().getValue()) {
				dropBombKeyPressed(minecraft, event.getAction());
			} else if (event.getKey() == KEY_CRAWL.getKey().getValue()) {
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
				NetworkDispatcher.networkChannel.sendToServer(new ToggleCrawlingMessage());
			}
		}

		private static void useMaskKeyPressed(Minecraft minecraft, int keyAction) {
			Predicate<ItemStack> isMaskWithAbility = stack -> stack.getItem() instanceof IMaskAbility;
			CuriosApi.getCuriosHelper().findEquippedCurio(isMaskWithAbility, minecraft.player).ifPresent(i -> {
				ItemStack maskStack = i.getRight();
				IMaskAbility mask = (IMaskAbility) maskStack.getItem();

				if (keyAction == GLFW.GLFW_PRESS) {
					mask.startUsingAbility(minecraft.player);
					NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage(true));
				} else if (keyAction == GLFW.GLFW_RELEASE) {
					mask.stopUsingAbility(minecraft.player);
					NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage(false));
				}
			});
		}

		private static void dropBombKeyPressed(Minecraft minecraft, int keyAction) {
			Predicate<ItemStack> isBombBag = stack -> stack.getItem() instanceof BombBagItem;
			CuriosApi.getCuriosHelper().findEquippedCurio(isBombBag, minecraft.player).ifPresent(i -> {
				if (keyAction == GLFW.GLFW_PRESS) {
					NetworkDispatcher.networkChannel.sendToServer(new DropBombMessage());
				}
			});
		}
	}
}
