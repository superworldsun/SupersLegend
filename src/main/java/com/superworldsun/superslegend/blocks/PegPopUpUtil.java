package com.superworldsun.superslegend.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

final class PegPopUpUtil {
    private static final double RESTORED_PEG_TOP = 1.5D;

    private PegPopUpUtil() {
    }

    static void liftPlayers(Level level, BlockPos pos, boolean spiked) {
        if (level.isClientSide) {
            return;
        }

        AABB standingArea = new AABB(pos.getX(), pos.getY() - 0.05D, pos.getZ(),
                pos.getX() + 1.0D, pos.getY() + 0.55D, pos.getZ() + 1.0D);
        double topY = pos.getY() + RESTORED_PEG_TOP;

        for (Player player : level.getEntitiesOfClass(Player.class, standingArea,
                candidate -> candidate.isAlive() && candidate.getBoundingBox().minY < pos.getY() + 0.55D)) {
            if (spiked) {
                SpikedPegBlock.protectFromPopUp(player, level.getGameTime());
            }

            Vec3 movement = player.getDeltaMovement();
            player.teleportTo(player.getX(), topY, player.getZ());
            player.setDeltaMovement(movement.x, Math.max(0.0D, movement.y), movement.z);
            player.fallDistance = 0.0F;
            player.hurtMarked = true;
        }
    }
}
