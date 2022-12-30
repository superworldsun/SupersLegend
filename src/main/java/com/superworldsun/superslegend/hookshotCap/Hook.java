package com.superworldsun.superslegend.hookshotCap;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookProvider;
import com.superworldsun.superslegend.network.message.SyncHookShot;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class Hook
{
	@SubscribeEvent
	public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof PlayerEntity)
		{
			HookModel skillModel = new HookModel();
			HookProvider provider = new HookProvider(skillModel);
			event.addCapability(new ResourceLocation("zelda_hs", "cap_hook"), provider);
			event.addListener(provider::invalidate);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e)
	{
		SyncHookShot.send(e.getPlayer());
	}
	
	@SubscribeEvent
	public static void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent e)
	{
		SyncHookShot.send(e.getPlayer());
	}
}
