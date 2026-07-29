package com.superworldsun.superslegend;

import com.mojang.logging.LogUtils;
import com.superworldsun.superslegend.client.init.ItemModelPropertiesInit;
import com.superworldsun.superslegend.registries.*;
import com.superworldsun.superslegend.songs.LearnedSongs;
import com.superworldsun.superslegend.worldgen.biome.BiomeModifiers;
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
        ModGameRules.register();

        CreativeModTabsInit.register(modEventBus);

        FluidInit.FLUID_TYPES.register(modEventBus);
        FluidInit.FLUIDS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        EffectInit.MOB_EFFECT.register(modEventBus);
        SoundInit.SOUNDS.register(modEventBus);
        EntityTypeInit.ENTITY_TYPES.register(modEventBus);
        BlockEntityInit.BLOCK_ENTITIES.register(modEventBus);
        BiomeModifiers.register(modEventBus);
        MenuTypeInit.MENU_TYPES.register(modEventBus);
        RecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
        OcarinaSongInit.OCARINA_SONGS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        AttributeInit.ATTRIBUTES.register(modEventBus);
        ContainerInit.CONTAINERS.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(LearnedSongs.Provider.class);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
//        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
//            event.accept(ItemInit.RUPEE);
//        }
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
        }
    }

}
