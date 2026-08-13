package com.superworldsun.superslegend.interfaces;

import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Only use it for helmet items, otherwise it will not work
 *
 * @author Daripher
 *
 */
public interface IMaskAbility
{
    /**
     * Do not use this field directly
     */
    public static final Set<Player> PLAYERS_USING_MASKS = new HashSet<>();

    default void startUsingAbility(Player player)
    {
        PLAYERS_USING_MASKS.add(player);
        if (player instanceof ServerPlayer serverPlayer) {
            ModAdvancementHelper.award(serverPlayer, "a_familiar_face", "used_mask");
        }
    };

    default void stopUsingAbility(Player player)
    {
        PLAYERS_USING_MASKS.remove(player);
    };

    default boolean isPlayerUsingAbility(Player player)
    {
        return PLAYERS_USING_MASKS.contains(player);
    }
}
