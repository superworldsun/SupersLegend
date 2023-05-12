package com.superworldsun.superslegend.events;


import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class CameraRenderEvents {


	//TODO wip, trying to make it so when the player is in third person their FOV changes in really close.
	// Currently using iron helmet for testing, this will be used for Gnat Hat
	/*@SubscribeEvent
	public static void onFOVUpdate(FOVUpdateEvent event) {
		PlayerEntity player = event.getEntity();
		ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);
		boolean isThirdPerson = player.getCameraEntity() != player;

		if (isThirdPerson && helmet.getItem() == Items.IRON_HELMET) {
			event.setNewfov(event.getFov() - 5.0F);
		} else {
			event.setNewfov(event.getFov());
		}
	}*/

}
