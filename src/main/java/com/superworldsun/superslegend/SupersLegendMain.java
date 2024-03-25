package com.superworldsun.superslegend;

import com.mojang.logging.LogUtils;
import com.superworldsun.superslegend.client.init.ItemModelPropertiesInit;
import com.superworldsun.superslegend.client.render.entites.BombRenderer;
import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.world.biome.BiomeModifiers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SupersLegendMain.MOD_ID)
public class SupersLegendMain
{
    public static final String MOD_ID = "superslegend";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SupersLegendMain()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CreativeModTabsInit.resiter(modEventBus);

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

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ItemInit.RUPEE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ItemModelPropertiesInit.addCustomItemProperties();

            //EntityRenderers.register(EntityTypeInit.BOMB.get(), ThrownItemRenderer::new);
            EntityRenderers.register(EntityTypeInit.BOMB.get(), ctx -> new BombRenderer(ctx));
        }
    }
}
