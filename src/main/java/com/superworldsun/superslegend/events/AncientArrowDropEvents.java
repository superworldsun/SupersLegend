package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.AncientArrowEntity;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class AncientArrowDropEvents
{
	// TODO MAKE A CONFIG TO TURN THIS OFF FOR PLAYERS UPON DEATH.
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
		Entity directEntity = event.getSource().getDirectEntity();
		
		if (directEntity instanceof AncientArrowEntity)
		{
			event.getDrops().clear();
		}
	}
}
