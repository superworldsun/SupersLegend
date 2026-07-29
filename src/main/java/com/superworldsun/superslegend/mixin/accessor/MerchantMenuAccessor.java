package com.superworldsun.superslegend.mixin.accessor;

import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Exposes the server-side merchant bound to an active vanilla trade menu. */
@Mixin(MerchantMenu.class)
public interface MerchantMenuAccessor {
    @Accessor("trader")
    Merchant superslegend$getTrader();
}
