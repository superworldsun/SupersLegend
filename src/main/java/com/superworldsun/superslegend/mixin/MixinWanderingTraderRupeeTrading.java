package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.trading.rupee.RupeeTradeNetworking;
import com.superworldsun.superslegend.trading.rupee.RupeeTradeRegistry;
import com.superworldsun.superslegend.trading.rupee.RupeeTradeService;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Diverts only an otherwise-valid wandering-trader menu opening. Injecting
 * before setTradingPlayer avoids reserving the trader while the chooser is
 * still open.
 */
@Mixin(WanderingTrader.class)
public abstract class MixinWanderingTraderRupeeTrading {
    @Inject(
            method = "mobInteract",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/npc/WanderingTrader;setTradingPlayer(Lnet/minecraft/world/entity/player/Player;)V"
            ),
            cancellable = true,
            require = 1
    )
    private void superslegend$openTradeChoice(Player player, InteractionHand hand,
                                               CallbackInfoReturnable<InteractionResult> callback) {
        WanderingTrader trader = (WanderingTrader) (Object) this;
        if (player instanceof ServerPlayer serverPlayer
                && !RupeeTradeRegistry.getTrades(trader).isEmpty()) {
            RupeeTradeService.removeLegacyOffers(trader);
            RupeeTradeNetworking.openChoice(serverPlayer, trader, hand);
            callback.setReturnValue(InteractionResult.CONSUME);
        }
    }
}
