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
        float eyeHeightScale = resizer != null
                ? resizer.getEyeHeightScale(event.player, event.player.getPose())
                : 1.0F;
        float hitboxHeightScale = resizer != null
                ? resizer.getHitboxHeightScale(event.player, event.player.getPose())
                : 1.0F;
        applyScale(ScaleTypes.HITBOX_WIDTH, event.player, scale);
        applyScale(
                ScaleTypes.HITBOX_HEIGHT,
                event.player,
                hitboxHeightScale,
                resizer != null && resizer.hasInstantHitboxHeightChanges()
        );
        applyScale(
                ScaleTypes.EYE_HEIGHT,
                event.player,
                eyeHeightScale,
                resizer != null && resizer.hasInstantEyeHeightChanges()
        );
    }

    private static void applyScale(ScaleType scaleType, Player player, float scale) {
        applyScale(scaleType, player, scale, false);
    }

    private static void applyScale(ScaleType scaleType, Player player, float scale, boolean immediate) {
        ScaleData scaleData = scaleType.getScaleData(player);

        if (immediate) {
            scaleData.setScaleTickDelay(0);
            scaleData.setScale(scale);
            scaleData.setTargetScale(scale);
            return;
        }

        if (scaleData.getTargetScale() != scale) {
            scaleData.setTargetScale(scale);
        }
    }
}
