package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SignBlockEntity.class)
public abstract class MixinSignBlockEntity {
    @Unique
    private boolean superslegend$writingHylian;
    @Unique
    private boolean superslegend$textFilteringEnabled;
    @Unique
    private boolean[] superslegend$changedLines = new boolean[0];
    @Unique
    private int superslegend$styleCall;

    @Inject(method = "setMessages", at = @At("HEAD"))
    private void superslegend$beginHylianSignText(
            Player player,
            List<FilteredText> filteredText,
            SignText signText,
            CallbackInfoReturnable<SignText> callbackInfo
    ) {
        superslegend$writingHylian = HylianTextUtil.isHoldingBookOfMudora(player);
        superslegend$textFilteringEnabled = player.isTextFilteringEnabled();
        superslegend$styleCall = 0;
        superslegend$changedLines = new boolean[filteredText.size()];

        for (int line = 0; line < filteredText.size(); line++) {
            String oldText = signText.getMessage(line, false).getString();
            superslegend$changedLines[line] = !oldText.equals(filteredText.get(line).raw());
        }

        if (!superslegend$writingHylian && HylianTextUtil.containsHylianText(signText)) {
            callbackInfo.setReturnValue(signText);
        }
    }

    @Redirect(
            method = "setMessages",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/MutableComponent;setStyle(Lnet/minecraft/network/chat/Style;)Lnet/minecraft/network/chat/MutableComponent;"
            )
    )
    private MutableComponent superslegend$applyHylianSignFont(
            MutableComponent component,
            Style style
    ) {
        int callsPerLine = superslegend$textFilteringEnabled ? 1 : 2;
        int line = superslegend$styleCall++ / callsPerLine;
        if (line >= superslegend$changedLines.length || !superslegend$changedLines[line]) {
            return component.setStyle(style);
        }

        return component.setStyle(style.withFont(
                superslegend$writingHylian
                        ? HylianTextUtil.HYLIAN_FONT
                        : Style.DEFAULT_FONT
        ));
    }

    @Inject(method = "setMessages", at = @At("RETURN"))
    private void superslegend$finishHylianSignText(
            Player player,
            List<FilteredText> filteredText,
            SignText signText,
            CallbackInfoReturnable<SignText> callbackInfo
    ) {
        superslegend$writingHylian = false;
        superslegend$changedLines = new boolean[0];
        superslegend$styleCall = 0;
    }
}
