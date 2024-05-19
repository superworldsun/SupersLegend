package com.superworldsun.superslegend;

import com.mojang.logging.LogUtils;

import com.superworldsun.superslegend.client.init.ItemModelPropertiesInit;
import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.world.biome.BiomeModifiers;

import net.minecraft.world.item.CreativeModeTabs;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

import software.bernie.geckolib.GeckoLib;

//TODO: make sounds.json use translatable text instead of literal.
@Mod(SupersLegendMain.MOD_ID)
public class SupersLegendMain {

    public static final String MOD_ID = "superslegend";

    private static final Logger LOGGER = LogUtils.getLogger();

    public SupersLegendMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CreativeModTabsInit.register(modEventBus);

        ItemInit.register(modEventBus);
        BlockInit.register(modEventBus);
        EffectInit.register(modEventBus);
        SoundInit.register(modEventBus);
        EntityTypeInit.register(modEventBus);
        BiomeModifiers.register(modEventBus);
        MenuTypeInit.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ItemInit.RUPEE);
            event.accept(ItemInit.BLUE_RUPEE);
            event.accept(ItemInit.RED_RUPEE);
            event.accept(ItemInit.SILVER_RUPEE);
            event.accept(ItemInit.GOLD_RUPEE);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemModelPropertiesInit.addCustomItemProperties();
        }
    }
}