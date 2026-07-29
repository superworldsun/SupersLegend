package com.superworldsun.superslegend.mixin.accessor;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** Provides access to Villager's discount-aware vanilla trade opener. */
@Mixin(Villager.class)
public interface VillagerStartTradingInvoker {
    @Invoker("startTrading")
    void superslegend$invokeStartTrading(Player player);
}
