package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.client.HylianFontMetrics;
import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mixin(BookEditScreen.class)
public abstract class MixinBookEditScreen {
    @Shadow
    @Final
    private Player owner;

    @Shadow
    @Final
    private List<String> pages;

    @Unique
    private List<String> superslegend$originalPages = List.of();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void superslegend$prepareHylianPages(
            Player player,
            ItemStack book,
            InteractionHand hand,
            CallbackInfo callbackInfo
    ) {
        superslegend$originalPages = new ArrayList<>(pages);
        pages.replaceAll(HylianTextUtil::removeHylianTextMarker);
    }

    @Inject(method = "saveChanges", at = @At("HEAD"))
    private void superslegend$saveHylianPages(boolean signing, CallbackInfo callbackInfo) {
        boolean writingHylian = HylianTextUtil.isHoldingBookOfMudora(owner);

        for (int page = 0; page < pages.size(); page++) {
            String currentText = HylianTextUtil.removeHylianTextMarker(pages.get(page));
            String originalText = page < superslegend$originalPages.size()
                    ? superslegend$originalPages.get(page)
                    : "";
            String originalPlainText = HylianTextUtil.removeHylianTextMarker(originalText);

            if (currentText.equals(originalPlainText)) {
                pages.set(page, originalText);
            } else if (writingHylian && !currentText.isEmpty()) {
                pages.set(page, HylianTextUtil.markHylianText(currentText));
            } else {
                pages.set(page, currentText);
            }
        }
    }

    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/font/TextFieldHelper;<init>(Ljava/util/function/Supplier;Ljava/util/function/Consumer;Ljava/util/function/Supplier;Ljava/util/function/Consumer;Ljava/util/function/Predicate;)V",
                    ordinal = 0
            ),
            index = 4
    )
    private Predicate<String> superslegend$measureHylianBookText(Predicate<String> vanillaValidator) {
        return text -> HylianTextUtil.isHoldingBookOfMudora(Minecraft.getInstance().player)
                ? text.length() < 1024
                && HylianFontMetrics.wordWrapHeight(Minecraft.getInstance().font, text, 114) <= 128
                : vanillaValidator.test(text);
    }
}
