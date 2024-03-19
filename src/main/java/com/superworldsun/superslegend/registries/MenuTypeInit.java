package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.menu.LetterMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SupersLegendMain.MOD_ID);

    public static final RegistryObject<MenuType<LetterMenu>> LETTER = MENU_TYPES.register("letter",
            () -> IForgeMenuType.create(LetterMenu::new));

    public static void register(IEventBus eventBus) {
        eventBus.register(MENU_TYPES);
    }
}
