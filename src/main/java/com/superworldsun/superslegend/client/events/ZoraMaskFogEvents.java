package com.superworldsun.superslegend.client.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

/** Provides Zora Mask wearers with clear natural-water visibility. */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ZoraMaskFogEvents {
    private ZoraMaskFogEvents() {
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        if (event.getType() != FogType.WATER) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (!isWearingZoraMask(minecraft)) {
            return;
        }
        event.setFarPlaneDistance(event.getFarPlaneDistance() * 2.5F);
        // Forge only uses the changed distances when this event is cancelled.
        event.setCanceled(true);
    }

    private static boolean isWearingZoraMask(Minecraft minecraft) {
        LocalPlayer player = minecraft.player;
        return player != null && CuriosApi.getCuriosHelper()
                .findEquippedCurio(ItemInit.MASK_ZORAMASK.get(), player).isPresent();
    }
}
