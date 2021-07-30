package com.superworldsun.superslegend.client.keys;

import org.lwjgl.glfw.GLFW;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.MaskAbilityMessage;

import net.minecraft.client.settings.KeyBinding;
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
	
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		ClientRegistry.registerKeyBinding(MASK_ABILITY);
	}
	
	@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
	private static class Events
	{
		@SubscribeEvent
		public static void onClientSetup(InputEvent.KeyInputEvent event)
		{
			if (event.getKey() == MASK_ABILITY.getKey().getValue())
			{
				NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage());
			}
		}
	}
}
