package com.superworldsun.superslegend.client.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BowItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

/** Applies the Hawkeye Mask's bow zoom without allowing an invalid camera FOV. */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class HawkeyeMaskFovEvents {
    private static final double BOW_ZOOM_MULTIPLIER = 0.25D;
    private static final double MINIMUM_FOV_DEGREES = 5.0D;

    private HawkeyeMaskFovEvents() {
    }

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null
                || !player.isUsingItem()
                || !(player.getUseItem().getItem() instanceof BowItem)
                || CuriosApi.getCuriosHelper()
                .findEquippedCurio(ItemInit.MASK_HAWKEYEMASK.get(), player).isEmpty()) {
            return;
        }

        // The 1.16.5 implementation subtracted 3 from a multiplier near 1, producing
        // a negative projection FOV. A positive bounded FOV keeps frustum/chunk
        // culling valid even while the player holds the camera completely still.
        event.setFOV(Math.max(MINIMUM_FOV_DEGREES, event.getFOV() * BOW_ZOOM_MULTIPLIER));
    }
}
