package com.superworldsun.superslegend.client.model.player;

import com.superworldsun.superslegend.client.model.ModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Client-side cache for the transformation player models, so they are not rebuilt every frame.
 */
@OnlyIn(Dist.CLIENT)
public class PlayerTransformationModels {
    private static GoronPlayerModel goron;
    private static ZoraPlayerModel zora;
    private static DekuPlayerModel deku;

    public static GoronPlayerModel getGoron() {
        if (goron == null) {
            goron = new GoronPlayerModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.GORON_PLAYER));
        }

        return goron;
    }

    public static ZoraPlayerModel getZora() {
        if (zora == null) {
            zora = new ZoraPlayerModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.ZORA_PLAYER));
        }

        return zora;
    }

    public static DekuPlayerModel getDeku() {
        if (deku == null) {
            deku = new DekuPlayerModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.DEKU_PLAYER));
        }

        return deku;
    }
}
