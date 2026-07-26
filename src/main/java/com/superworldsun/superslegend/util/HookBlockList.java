package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.registries.TagInit;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Determines which blocks can hold a hookshot.
 *
 * <p>The data tags are checked on every impact so datapack reloads and blocks
 * supplied by other mods are handled without rebuilding a cached block list.
 * The sound-type fallback covers wooden blocks which were not added to the
 * conventional Minecraft or Forge wood tags.</p>
 */
public final class HookBlockList {
    private HookBlockList() {
    }

    public static boolean isHookable(BlockState state) {
        if (state.is(TagInit.HOOKSHOT_UNHOOKABLE)) {
            return false;
        }

        if (state.is(TagInit.HOOKSHOT_HOOKABLE)) {
            return true;
        }

        SoundType soundType = state.getSoundType();
        return soundType == SoundType.WOOD
                || soundType == SoundType.NETHER_WOOD
                || soundType == SoundType.STEM
                || soundType == SoundType.CHERRY_WOOD
                || soundType == SoundType.BAMBOO_WOOD
                || soundType == SoundType.HANGING_SIGN
                || soundType == SoundType.NETHER_WOOD_HANGING_SIGN
                || soundType == SoundType.BAMBOO_WOOD_HANGING_SIGN
                || soundType == SoundType.CHERRY_WOOD_HANGING_SIGN
                || soundType == SoundType.CHISELED_BOOKSHELF
                || soundType == SoundType.LADDER
                || soundType == SoundType.MANGROVE_ROOTS;
    }
}