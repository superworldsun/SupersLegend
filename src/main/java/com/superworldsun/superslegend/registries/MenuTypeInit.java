package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.menus.PostboxMenu;
import com.superworldsun.superslegend.menus.RupeeTradeMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SupersLegendMain.MOD_ID);
    public static final RegistryObject<MenuType<PostboxMenu>> POSTBOX_MENU = MENU_TYPES.register("postbox_menu",
            () -> IForgeMenuType.create(PostboxMenu::new));
    public static final RegistryObject<MenuType<RupeeTradeMenu>> RUPEE_TRADE_MENU = MENU_TYPES.register("rupee_trade_menu",
            () -> IForgeMenuType.create(RupeeTradeMenu::new));

//    public static void register(IEventBus eventBus) {
//        eventBus.register(MENU_TYPES);
//    }
}
