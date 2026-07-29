package com.superworldsun.superslegend.registries;

import net.minecraft.world.level.GameRules;

public final class ModGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> TIME_SONG_AFFECTS_MOB_SPEED =
            GameRules.register("timeSongAffectsMobSpeed", GameRules.Category.MOBS,
                    GameRules.BooleanValue.create(true));

    private ModGameRules() {
    }

    public static void register() {
        // Loading this class registers the gamerules above.
    }
}
