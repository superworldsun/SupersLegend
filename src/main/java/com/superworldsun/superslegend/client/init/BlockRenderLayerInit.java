package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockRenderLayerInit {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlockInit.GRATE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEKU_FLOWER_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get(), RenderType.cutout());
    }
}
