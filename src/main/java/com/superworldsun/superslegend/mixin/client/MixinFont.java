package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.client.HylianFontMetrics;
import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Font.class)
public abstract class MixinFont {
    @ModifyVariable(
            method = "getFontSet",
            at = @At("HEAD"),
            argsOnly = true
    )
    private ResourceLocation superslegend$decodeHylianFont(ResourceLocation fontLocation) {
        if (HylianTextUtil.HYLIAN_FONT.equals(fontLocation)
                && !HylianFontMetrics.isMeasuringHylian()
                && HylianTextUtil.isHoldingBookOfMudora(Minecraft.getInstance().player)) {
            return Style.DEFAULT_FONT;
        }
        return fontLocation;
    }
}
