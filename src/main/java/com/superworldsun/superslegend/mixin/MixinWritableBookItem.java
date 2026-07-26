package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WritableBookItem.class)
public abstract class MixinWritableBookItem {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void superslegend$requireBookForHylianPages(
            Level level,
            Player player,
            InteractionHand hand,
            CallbackInfoReturnable<InteractionResultHolder<ItemStack>> callbackInfo
    ) {
        ItemStack book = player.getItemInHand(hand);
        if (!HylianTextUtil.containsHylianBookText(book)
                || HylianTextUtil.isHoldingBookOfMudora(player)) {
            return;
        }

        if (!level.isClientSide) {
            player.displayClientMessage(
                    Component.translatable("superslegend.message.hylian_book_requires_mudora"),
                    true
            );
        }
        callbackInfo.setReturnValue(InteractionResultHolder.fail(book));
    }
}
