package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.trading.rupee.RupeeTradeNetworking;
import com.superworldsun.superslegend.trading.rupee.RupeeTradeRegistry;
import com.superworldsun.superslegend.trading.rupee.RupeeTradeService;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Diverts only an otherwise-valid vanilla villager trade opening to the
 * Emerald/Rupee chooser. Important mob interactions and all of Villager's
 * normal eligibility checks have already run when this injection is reached.
 */
@Mixin(Villager.class)
public abstract class MixinVillagerRupeeTrading {
    @Inject(
            method = "mobInteract",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/npc/Villager;startTrading(Lnet/minecraft/world/entity/player/Player;)V"
            ),
            cancellable = true,
            require = 1
    )
    private void superslegend$openTradeChoice(Player player, InteractionHand hand,
                                               CallbackInfoReturnable<InteractionResult> callback) {
        Villager villager = (Villager) (Object) this;
        if (player instanceof ServerPlayer serverPlayer
                && !RupeeTradeRegistry.getTrades(villager).isEmpty()) {
            RupeeTradeService.removeLegacyOffers(villager);
            RupeeTradeNetworking.openChoice(serverPlayer, villager, hand);
            callback.setReturnValue(InteractionResult.CONSUME);
        }
    }
}
