package com.superworldsun.superslegend.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class PlayerUtil {
    /**
     *
     * @param player The Player
     * @param checkHeight How many blocks under the player's foot must be clear (Not Solid)
     * @return false, if all blocks are not solid. true, if one of the blocks is solid
     */
    public static boolean solidGroundCheck(Player player, int checkHeight) {
        BlockPos currentPos = player.blockPosition();

        for (int i = 0; i < checkHeight; i++) {
            if (player.getCommandSenderWorld().getBlockState(currentPos).isSolid()) {
                return true;
            }
            currentPos = currentPos.below();
        }
        return false;
    }
}