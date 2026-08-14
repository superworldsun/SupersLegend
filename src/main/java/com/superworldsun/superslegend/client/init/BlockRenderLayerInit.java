package com.superworldsun.superslegend.client.init;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.FluidInit;
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
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CHAIN_LINK_FENCE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SPIKES_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DUNGEON_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.LOCKED_DUNGEON_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BOSS_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.LOCKED_BOSS_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.LOCKED_WOODEN_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.WOODEN_BOSS_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BUSH_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.GRATE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DEKU_FLOWER_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.YELLOW_DEKU_FLOWER_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.GRASS_PATCH_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.ODD_MUSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.MAGIC_MUSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SHADOW_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.FALSE_SHADOW_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.HIDDEN_SHADOW_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.TOMBSTONE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SPIKED_PEG_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.STONE_PATH_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.STONE_TILE_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.BLUE_FLOOR_SWITCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.YELLOW_FLOOR_SWITCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.RED_FLOOR_SWITCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.RUSTED_FLOOR_SWITCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DINS_FLAME.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.FARORES_FLAME.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.NAYRUS_FLAME.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.DINS_SACRED_PEDESTAL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.FARORES_SACRED_PEDESTAL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.NAYRUS_SACRED_PEDESTAL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.SUN_SWITCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.POISON_SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.POISON_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.MUD_SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.MUD_FLOWING.get(), RenderType.translucent());
    }
}
