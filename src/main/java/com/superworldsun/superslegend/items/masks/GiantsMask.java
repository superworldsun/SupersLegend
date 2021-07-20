package com.superworldsun.superslegend.items.masks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.IResizableEntity;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GiantsMask extends ArmorItem
{
	public GiantsMask(Properties properties)
	{
		super(ArmourInit.giantsmask, EquipmentSlotType.HEAD, properties);
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
		float growingSpeed = 0.05F;
		float manaCost = 0.01F;
		boolean hasGiantsMask = event.player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.MASK_GIANTSMASK.get();
		// We can use it in creative even without mana
		boolean hasMana = ManaProvider.get(event.player).getMana() >= manaCost || event.player.abilities.instabuild;
		
		if (hasGiantsMask && hasMana)
		{
			if (!event.player.abilities.instabuild)
			{
				ManaProvider.get(event.player).spendMana(manaCost);
			}
			
			// Maximum size is 400%
			if (resizablePlayer.getScale() < 4.0F)
			{
				resizablePlayer.setScale(resizablePlayer.getScale() + growingSpeed);
				
				// Double checking, yes it is necessary
				if (resizablePlayer.getScale() > 4.0F)
				{
					resizablePlayer.setScale(4.0F);
				}
			}
		}
		// If size was changed, change it back
		else if (resizablePlayer.getScale() > 1.0F)
		{
			resizablePlayer.setScale(resizablePlayer.getScale() - growingSpeed);
			
			// Double checking, yes it is necessary
			if (resizablePlayer.getScale() < 1.0F)
			{
				resizablePlayer.setScale(1.0F);
			}
		}
	}
}
