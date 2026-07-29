package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import com.superworldsun.superslegend.items.curios.head.masks.DekuMask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {
    private static final ResourceLocation DEKU_GUI_ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");

    @Inject(
            method = "renderExperienceBar",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderDekuWaterHopMeter(GuiGraphics guiGraphics, int x, CallbackInfo callbackInfo) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }

        double remainingDistance =
                DekuFlowerFlightEvents.getDisplayedGlideDistanceRemaining(minecraft.player);
        double maximumDistance =
                DekuFlowerFlightEvents.getDisplayedMaximumGlideDistance(minecraft.player);
        if (remainingDistance >= 0.0D && maximumDistance > 0.0D) {
            renderTemporaryMeter(
                    guiGraphics,
                    x,
                    (float) (remainingDistance / maximumDistance),
                    Integer.toString((int) Math.ceil(remainingDistance)),
                    minecraft
            );
            callbackInfo.cancel();
            return;
        }

        int hopsRemaining = DekuMask.getDisplayedWaterHopsRemaining(minecraft.player);
        if (hopsRemaining < 1) {
            return;
        }

        renderTemporaryMeter(
                guiGraphics,
                x,
                hopsRemaining / (float) DekuMask.MAX_WATER_HOPS,
                Integer.toString(hopsRemaining),
                minecraft
        );
        callbackInfo.cancel();
    }

    private static void renderTemporaryMeter(GuiGraphics guiGraphics, int x, float fill,
                                             String text, Minecraft minecraft) {
        int barY = guiGraphics.guiHeight() - 32 + 3;
        int filledWidth = (int) (Math.max(0.0F, Math.min(fill, 1.0F)) * 183.0F);
        guiGraphics.blit(DEKU_GUI_ICONS, x, barY, 0, 84, 182, 5);
        guiGraphics.blit(DEKU_GUI_ICONS, x, barY, 0, 89, filledWidth, 5);

        Font font = minecraft.font;
        int textX = (guiGraphics.guiWidth() - font.width(text)) / 2;
        int textY = guiGraphics.guiHeight() - 31 - 4;
        guiGraphics.drawString(font, text, textX + 1, textY, 0, false);
        guiGraphics.drawString(font, text, textX - 1, textY, 0, false);
        guiGraphics.drawString(font, text, textX, textY + 1, 0, false);
        guiGraphics.drawString(font, text, textX, textY - 1, 0, false);
        guiGraphics.drawString(font, text, textX, textY, 8453920, false);
    }
}
