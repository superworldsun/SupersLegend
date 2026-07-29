package com.superworldsun.superslegend.events;

/**
 * Legacy placeholder.
 *
 * <p>Rupee offers used to be appended to Forge's vanilla villager trade pools
 * here. That made them randomly compete with Emerald offers and forced wallet
 * values into ItemStack counts. They now live in the independent
 * {@code trading.rupee} registry and menu, so this class must not subscribe to
 * VillagerTradesEvent or WandererTradesEvent.</p>
 */
public final class VillagerTradeEvents {
    private VillagerTradeEvents() {
    }
}
