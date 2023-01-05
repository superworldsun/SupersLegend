package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.screen.*;
import com.superworldsun.superslegend.registries.ContainerInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ScreenContainerInit {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ScreenManager.register(ContainerInit.BAG.get(), createScreenFactory("bag"));
		ScreenManager.register(ContainerInit.RING_BOX.get(), createScreenFactory("ring_box"));
		ScreenManager.register(ContainerInit.RING_BOX_BIG.get(), createScreenFactory("big_ring_box"));
		ScreenManager.register(ContainerInit.RING_BOX_BIGGEST.get(), createScreenFactory("biggest_ring_box"));
		ScreenManager.register(ContainerInit.LETTER.get(), createScreenFactory("letter"));
		ScreenManager.register(ContainerInit.POSTBOX.get(), createScreenFactory("postbox"));
		ScreenManager.register(ContainerInit.SELECT_CONTAINER.get(), SelectScreen::new);
		ScreenManager.register(ContainerInit.COOKING_POT.get(), CookingPotScreen::new);
	}

	private static <C extends Container> IScreenFactory<C, SimpleContainerScreen<C>> createScreenFactory(String texture) {
		return (container, inventory, title) -> new SimpleContainerScreen<C>(container, inventory, title, texture);
	}
}