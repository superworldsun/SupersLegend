package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.FormattedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({
        BookViewScreen.WritableBookAccess.class,
        BookViewScreen.WrittenBookAccess.class
})
public abstract class MixinBookAccess {
    @Inject(method = "getPageRaw", at = @At("RETURN"), cancellable = true)
    private void superslegend$renderHylianBookPage(
            int page,
            CallbackInfoReturnable<FormattedText> callbackInfo
    ) {
        FormattedText text = callbackInfo.getReturnValue();
        if (text == null || !HylianTextUtil.isMarkedHylianText(text.getString())) {
            return;
        }

        callbackInfo.setReturnValue(HylianTextUtil.markAsHylian(
                HylianTextUtil.removeHylianTextMarker(text.getString())
        ));
    }
}
