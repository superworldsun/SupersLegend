package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.trading.rupee.RupeeTradeRegistry;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Assigns each wandering trader its persistent random Rupee offers when it enters the server world. */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public final class WanderingTraderRupeeTradeEvents {
    private WanderingTraderRupeeTradeEvents() {
    }

    @SubscribeEvent
    public static void assignRupeeOffers(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof WanderingTrader trader) {
            RupeeTradeRegistry.getTrades(trader);
        }
    }
}
