package com.superworldsun.superslegend.client.keys;

import org.lwjgl.glfw.GLFW;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.items.ammobags.BombBagItem;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.DropBombMessage;
import com.superworldsun.superslegend.network.message.MaskAbilityMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(bus = Bus.MOD, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class KeyBindings
{
	public static final KeyBinding MASK_ABILITY = new KeyBinding("key.mask_ability", GLFW.GLFW_KEY_B, "key.categories." + SupersLegendMain.MOD_ID);
	//Dosent work as intended and is incomplete
	//public static final KeyBinding SELECT_INVENTORY = new KeyBinding("key.select_inventory", GLFW.GLFW_KEY_C, "key.categories." + SupersLegendMain.MOD_ID);
	public static final KeyBinding DROP_BOMB = new KeyBinding("key.drop_bomb", GLFW.GLFW_KEY_N, "key.categories." + SupersLegendMain.MOD_ID);
	
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		ClientRegistry.registerKeyBinding(MASK_ABILITY);
		//ClientRegistry.registerKeyBinding(SELECT_INVENTORY);
		ClientRegistry.registerKeyBinding(DROP_BOMB);
	}
	
	@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
	private static class Events
	{
		@SubscribeEvent
		public static void onKeyInput(InputEvent.KeyInputEvent event)
		{
			Minecraft client = Minecraft.getInstance();
			
			if (client.player == null || client.screen != null)
			{
				return;
			}
			
			if (event.getKey() == MASK_ABILITY.getKey().getValue())
			{
				Item helmetItem = client.player.getItemBySlot(EquipmentSlotType.HEAD).getItem();
				
				CuriosApi.getCuriosHelper().getEquippedCurios(client.player).ifPresent(curios ->
				{
					for (int i = 0; i < curios.getSlots(); i++)
					{
						ItemStack curioStack = curios.getStackInSlot(i);
						
						if (!curioStack.isEmpty() && curioStack.getItem() instanceof IMaskAbility)
						{
							IMaskAbility mask = (IMaskAbility) curioStack.getItem();
							
							if (event.getAction() == GLFW.GLFW_PRESS)
							{
								mask.startUsingAbility(client.player);
								NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage(true));
							}
							else if (event.getAction() == GLFW.GLFW_RELEASE)
							{
								mask.stopUsingAbility(client.player);
								NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage(false));
							}
						}
					}
				});
				
				if (helmetItem instanceof IMaskAbility)
				{
					IMaskAbility mask = (IMaskAbility) helmetItem;
					
					if (event.getAction() == GLFW.GLFW_PRESS)
					{
						mask.startUsingAbility(client.player);
						NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage(true));
					}
					else if (event.getAction() == GLFW.GLFW_RELEASE)
					{
						mask.stopUsingAbility(client.player);
						NetworkDispatcher.networkChannel.sendToServer(new MaskAbilityMessage(false));
					}
				}
			}
			/*else if (SELECT_INVENTORY.isDown())
			{
				NetworkDispatcher.networkChannel.sendToServer(new SelectInteractionMessage(0, true));
			}*/
			else if (event.getKey() == DROP_BOMB.getKey().getValue())
			{
				boolean hasBombBag = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof BombBagItem, client.player).isPresent();
				
				if (hasBombBag)
				{
					if (event.getAction() == GLFW.GLFW_PRESS)
					{
						NetworkDispatcher.networkChannel.sendToServer(new DropBombMessage());
					}
				}
			}
		}
	}
}
