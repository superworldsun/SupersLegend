package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.interfaces.IPlayerAnimationState;
import net.minecraft.world.entity.player.Player;

/**
 * Public entry point for custom player animations.
 *
 * <p>The raised-arms state uses synced entity data, so setting it on the
 * server is automatically visible to the player and all tracking clients.</p>
 */
public final class PlayerAnimationUtil {
    private static final int PEGASUS_WEAPON_FORWARD_FLAG = 1 << 8;

    public enum ArmsRaisedSource {
        MANUAL(1),
        CARRYING_MOB(1 << 1),
        DEKU_LEAF(1 << 2);

        private final int flag;

        ArmsRaisedSource(int flag) {
            this.flag = flag;
        }
    }

    private PlayerAnimationUtil() {
    }

    public static boolean hasArmsRaised(Player player) {
        int raisedArmsFlags = ArmsRaisedSource.MANUAL.flag
                | ArmsRaisedSource.CARRYING_MOB.flag
                | ArmsRaisedSource.DEKU_LEAF.flag;
        return (((IPlayerAnimationState) player).superslegend$getAnimationFlags() & raisedArmsFlags) != 0;
    }

    public static void setArmsRaised(Player player, boolean armsRaised) {
        setArmsRaised(player, ArmsRaisedSource.MANUAL, armsRaised);
    }

    /**
     * Enables or clears one reason for raising the arms without disturbing any
     * other feature that currently needs the same pose.
     */
    public static void setArmsRaised(Player player, ArmsRaisedSource source, boolean armsRaised) {
        IPlayerAnimationState state = (IPlayerAnimationState) player;
        int flags = state.superslegend$getAnimationFlags();
        int updatedFlags = armsRaised ? flags | source.flag : flags & ~source.flag;
        if (flags != updatedFlags) {
            state.superslegend$setAnimationFlags(updatedFlags);
        }
    }

    public static boolean hasPegasusWeaponForward(Player player) {
        return (((IPlayerAnimationState) player).superslegend$getAnimationFlags()
                & PEGASUS_WEAPON_FORWARD_FLAG) != 0;
    }

    public static void setPegasusWeaponForward(Player player, boolean weaponForward) {
        IPlayerAnimationState state = (IPlayerAnimationState) player;
        int flags = state.superslegend$getAnimationFlags();
        int updatedFlags = weaponForward
                ? flags | PEGASUS_WEAPON_FORWARD_FLAG
                : flags & ~PEGASUS_WEAPON_FORWARD_FLAG;
        if (flags != updatedFlags) {
            state.superslegend$setAnimationFlags(updatedFlags);
        }
    }
}
