package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IResizableEntity;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GnatHat extends ArmorItem
{
	public GnatHat(Properties properties)
	{
		super(ArmourInit.gnathat, EquipmentSlotType.HEAD, properties);
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		// Prevent applying changes 2 times per tick
		if (event.phase == Phase.END)
		{
			return;
		}
		
		IResizableEntity resizablePlayer = (IResizableEntity) event.player;
		float shrinkingSpeed = 0.05F;
		boolean hasGnatsHat = event.player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.GNAT_HAT.get();
		
		// Only if player wearing this hat
		if (hasGnatsHat)
		{
			// Minimum size is 20%
			if (event.player.getScale() > 0.2F)
			{
				resizablePlayer.setScale(event.player.getScale() - shrinkingSpeed);
				
				// Double checking, yes it is necessary
				if (event.player.getScale() < 0.2F)
				{
					resizablePlayer.setScale(0.2F);
				}
			}
		}
		// If not wearing a mask and size was changed, encrease it back
		else if (event.player.getScale() < 1.0F)
		{
			resizablePlayer.setScale(event.player.getScale() + shrinkingSpeed);
			
			// Double checking, yes it is necessary
			if (event.player.getScale() > 1.0F)
			{
				resizablePlayer.setScale(1.0F);
			}
		}
	}
}
