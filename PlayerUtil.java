package com.superworldsun.superslegend.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil {
    /**
     *
     * @param player The Player
     * @param checkHeight How many blocks under the player's foot must be clear (Not Solid)
     * @return false, if all blocks are not solid. true, if one of the blocks is solid
     */
    public static boolean solidGroundCheck(PlayerEntity player, int checkHeight) {
        BlockPos currentPos = player.blockPosition();

            for (int i = 0; i < checkHeight; i++) {
                if (player.getCommandSenderWorld().getBlockState(currentPos).getMaterial().isSolid()) {
                    return true;
                }
                currentPos = currentPos.below();
            }
        return false;
    }
}