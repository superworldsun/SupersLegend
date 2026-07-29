package com.superworldsun.superslegend.trading.rupee;

import com.superworldsun.superslegend.mixin.accessor.VillagerStartTradingInvoker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;

/**
 * Reopens the target's untouched vanilla Emerald trade flow after the server
 * has validated a choice-screen response.
 */
public final class VanillaTradeOpener {
    private VanillaTradeOpener() {
    }

    /**
     * @return {@code true} when this helper supports the supplied vanilla trader
     */
    public static boolean open(ServerPlayer player, AbstractVillager trader) {
        if (trader instanceof Villager villager) {
            ((VillagerStartTradingInvoker) villager).superslegend$invokeStartTrading(player);
            return true;
        }

        if (trader instanceof WanderingTrader wanderingTrader) {
            wanderingTrader.setTradingPlayer(player);
            wanderingTrader.openTradingScreen(player, wanderingTrader.getDisplayName(), 1);
            return true;
        }

        return false;
    }
}
