package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.client.HylianFontMetrics;
import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(AbstractSignEditScreen.class)
public abstract class MixinAbstractSignEditScreen {
    @Shadow
    @Final
    private SignBlockEntity sign;

    @ModifyArg(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/font/TextFieldHelper;<init>(Ljava/util/function/Supplier;Ljava/util/function/Consumer;Ljava/util/function/Supplier;Ljava/util/function/Consumer;Ljava/util/function/Predicate;)V"
            ),
            index = 4
    )
    private Predicate<String> superslegend$measureHylianSignText(Predicate<String> vanillaValidator) {
        return text -> HylianTextUtil.isHoldingBookOfMudora(Minecraft.getInstance().player)
                ? HylianFontMetrics.width(Minecraft.getInstance().font, text)
                <= sign.getMaxTextLineWidth()
                : vanillaValidator.test(text);
    }
}
