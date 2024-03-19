package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.screen.SimpleContainerScreen;
import com.superworldsun.superslegend.registries.MenuTypeInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScreenContainerInit {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
//            MenuScreens.register(MenuTypeInit.BAG.get(), createScreenFactory("bag"));
//            MenuScreens.register(MenuTypeInit.RING_BOX.get(), createScreenFactory("ring_box"));
//            MenuScreens.register(MenuTypeInit.RING_BOX_BIG.get(), createScreenFactory("big_ring_box"));
//            MenuScreens.register(MenuTypeInit.RING_BOX_BIGGEST.get(), createScreenFactory("biggest_ring_box"));
            MenuScreens.register(MenuTypeInit.LETTER.get(), createScreenFactory("letter"));
//            MenuScreens.register(MenuTypeInit.POSTBOX.get(), createScreenFactory("postbox"));
//            MenuScreens.register(MenuTypeInit.SELECT_CONTAINER.get(), SelectScreen::new);
//            MenuScreens.register(MenuTypeInit.COOKING_POT.get(), CookingPotScreen::new);
        });
    }

    private static <M extends AbstractContainerMenu> MenuScreens.ScreenConstructor<M, SimpleContainerScreen<M>> createScreenFactory(String texture) {
        return (menu, inventory, title) -> new SimpleContainerScreen<>(menu, inventory, title, texture);
    }
}