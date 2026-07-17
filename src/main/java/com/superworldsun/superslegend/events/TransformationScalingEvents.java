package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.IEntityResizer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class TransformationScalingEvents {
    // Only the hitbox and eye height are scaled: the transformation models are already
    // modelled at their actual size, so the render scale has to stay at 1.
    private static final ScaleType[] SCALED_TYPES = { ScaleTypes.HITBOX_WIDTH, ScaleTypes.HITBOX_HEIGHT, ScaleTypes.EYE_HEIGHT };

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        // Pehkui synchronizes scales set on the server to all clients
        if (event.player.level().isClientSide()) {
            return;
        }

        IEntityResizer resizer = IEntityResizer.get(event.player);
        float scale = resizer != null ? resizer.getScale(event.player) : 1.0F;
        applyScale(event.player, scale);
    }

    private static void applyScale(Player player, float scale) {
        for (ScaleType scaleType : SCALED_TYPES) {
            ScaleData scaleData = scaleType.getScaleData(player);

            if (scaleData.getTargetScale() != scale) {
                scaleData.setTargetScale(scale);
            }
        }
    }
}
