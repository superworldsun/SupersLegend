package com.superworldsun.superslegend.client.keys;

import com.superworldsun.superslegend.network.message.SelectInteractionMessage;
import org.lwjgl.glfw.GLFW;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.MaskAbilityMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class KeyBindings
{
	public static final KeyBinding MASK_ABILITY = new KeyBinding("key.mask_ability", GLFW.GLFW_KEY_B, "key.categories." + SupersLegendMain.MOD_ID);
	public static final KeyBinding SELECT_INVENTORY = new KeyBinding("key.select_inventory", GLFW.GLFW_KEY_C, "key.categories." + SupersLegendMain.MOD_ID);

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		ClientRegistry.registerKeyBinding(MASK_ABILITY);
		ClientRegistry.registerKeyBinding(SELECT_INVENTORY);
	}
	
	@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
	private static class Events
	{
		@SubscribeEvent
		public static void onKeyInput(InputEvent.KeyInputEvent event)
		{
			if (event.getKey() == MASK_ABILITY.getKey().getValue())
			{
				Minecraft client = Minecraft.getInstance();
				Item helmetItem = client.player.getItemBySlot(EquipmentSlotType.HEAD).getItem();
				
				if (helmetItem instanceof IMaskAbility)
				{
					if (event.getAction() == GLFW.GLFW_PRESS)
					{
						((IMaskAbility) helmetItem).startUsingAbility(client.player);
						NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage(true));
					}
					else if (event.getAction() == GLFW.GLFW_RELEASE)
					{
						((IMaskAbility) helmetItem).stopUsingAbility(client.player);
						NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage(false));
					}
				}
			} else if(SELECT_INVENTORY.isDown())
			{
				NetworkDispatcher.networkChannel.sendToServer(new SelectInteractionMessage(0, true));
			}
		}
	}
}
