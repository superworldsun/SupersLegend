package com.superworldsun.superslegend.util.datagen;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();

        if (event.includeServer())
        {
            SupersLegendMain.LOGGER.debug("Starting Server Data Generators");
            TagsList.BlockTagsDataGen blockTagsProvider = new TagsList.BlockTagsDataGen(event.getGenerator(), event.getExistingFileHelper());
            generator.addProvider(new TagsList.BlockTagsDataGen(generator, event.getExistingFileHelper()));
            generator.addProvider(new TagsList.ItemTagsDataGen(generator, blockTagsProvider, event.getExistingFileHelper()));
        }
        if (event.includeClient())
        {
            SupersLegendMain.LOGGER.debug("Starting Client Data Generators");
        }
    }
}
