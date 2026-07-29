package com.superworldsun.superslegend.mixin.client;

import com.superworldsun.superslegend.client.events.HookshotPlayerPoseEvents;
import com.superworldsun.superslegend.events.DekuFlowerFlightEvents;
import com.superworldsun.superslegend.items.hookshot.HookshotItem;
import com.superworldsun.superslegend.items.hookshot.LongshotItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraftHookshotInput {
    @Shadow
    @Final
    public Options options;

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void superslegend$suppressActiveHookshotDropAnimation(CallbackInfo callbackInfo) {
        Player player = ((Minecraft) (Object) this).player;
        if (player == null) {
            return;
        }

        boolean flowerAbilityActive = DekuFlowerFlightEvents.isFlowerAbilityActive(player);
        boolean mainHandHookshotActive =
                HookshotPlayerPoseEvents.findActiveHookHand(player) == InteractionHand.MAIN_HAND
                        && isHookshot(player.getMainHandItem());
        if (!flowerAbilityActive && !mainHandHookshotActive) {
            return;
        }

        while (options.keyDrop.consumeClick()) {
            // Consume the click before vanilla predicts a throw and swings the hand.
        }
    }

    private static boolean isHookshot(ItemStack stack) {
        return stack.getItem() instanceof HookshotItem || stack.getItem() instanceof LongshotItem;
    }
}
