package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignBlock.class)
public abstract class MixinSignBlock {
    @Inject(method = "openTextEdit", at = @At("HEAD"), cancellable = true)
    private void superslegend$requireBookForHylianSign(
            Player player,
            SignBlockEntity sign,
            boolean front,
            CallbackInfo callbackInfo
    ) {
        if (HylianTextUtil.isHoldingBookOfMudora(player)
                || !HylianTextUtil.containsHylianText(sign.getText(front))) {
            return;
        }

        if (!player.level().isClientSide) {
            player.displayClientMessage(
                    Component.translatable("superslegend.message.hylian_sign_requires_book"),
                    true
            );
        }
        callbackInfo.cancel();
    }
}
