package com.superworldsun.superslegend.songs;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SyncTimeSongModeMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class TimeSongSavedData extends SavedData {
    public static final String ITEM_REAL_LIFETIME_TAG = "SupersLegendRealItemLifetime";
    public static final String ITEM_INVERTED_EXTENDED_TAG = "SupersLegendInvertedExtendedItem";
    private static final String DATA_ID = SupersLegendMain.MOD_ID + "_time_song";
    private static final String MODE_TAG = "Mode";
    private static final String REMAINING_TICKS_TAG = "RemainingTicks";
    private static final String ACCUMULATED_TIME_TAG = "AccumulatedTime";
    private static final String ORIGINAL_RANDOM_TICK_SPEED_TAG = "OriginalRandomTickSpeed";
    private static final String HAS_ORIGINAL_RANDOM_TICK_SPEED_TAG = "HasOriginalRandomTickSpeed";
    private static final int EFFECT_DURATION_TICKS = 24000 * 3;

    private Mode mode = Mode.NORMAL;
    private int remainingTicks;
    private double accumulatedTime;
    private int originalRandomTickSpeed;
    private boolean hasOriginalRandomTickSpeed;

    public static TimeSongSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                TimeSongSavedData::load,
                TimeSongSavedData::new,
                DATA_ID
        );
    }

    private static TimeSongSavedData load(CompoundTag tag) {
        TimeSongSavedData data = new TimeSongSavedData();
        data.mode = Mode.fromName(tag.getString(MODE_TAG));
        data.remainingTicks = Math.max(0, tag.getInt(REMAINING_TICKS_TAG));
        data.accumulatedTime = tag.getDouble(ACCUMULATED_TIME_TAG);
        data.originalRandomTickSpeed = tag.getInt(ORIGINAL_RANDOM_TICK_SPEED_TAG);
        data.hasOriginalRandomTickSpeed = tag.getBoolean(HAS_ORIGINAL_RANDOM_TICK_SPEED_TAG);
        if (data.remainingTicks == 0) {
            data.mode = Mode.NORMAL;
            data.accumulatedTime = 0.0D;
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putString(MODE_TAG, mode.name());
        tag.putInt(REMAINING_TICKS_TAG, remainingTicks);
        tag.putDouble(ACCUMULATED_TIME_TAG, accumulatedTime);
        tag.putInt(ORIGINAL_RANDOM_TICK_SPEED_TAG, originalRandomTickSpeed);
        tag.putBoolean(HAS_ORIGINAL_RANDOM_TICK_SPEED_TAG, hasOriginalRandomTickSpeed);
        return tag;
    }

    /**
     * Activates the requested effect if time is normal. If either time song is already active,
     * playing either song cancels the current effect instead.
     *
     * @return true when the requested effect was activated; false when an active effect was canceled
     */
    public boolean activateOrCancel(Mode requestedMode, ServerLevel level) {
        if (mode != Mode.NORMAL) {
            clearEffect(level);
            return false;
        }

        setMode(requestedMode, level);
        return true;
    }

    public void setMode(Mode requestedMode, ServerLevel level) {
        if (requestedMode == Mode.NORMAL) {
            clearEffect(level);
            return;
        }

        restoreLegacyRandomTickSpeed(level);
        mode = requestedMode;
        remainingTicks = EFFECT_DURATION_TICKS;
        accumulatedTime = 0.0D;
        setDirty();
    }

    public static boolean isModeEnabled(Mode requestedMode) {
        return switch (requestedMode) {
            case DOUBLE -> Config.isSongOfDoubleTimeEnabled();
            case INVERTED -> Config.isInvertedSongOfTimeEnabled();
            case NORMAL -> true;
        };
    }

    public static Mode getMode(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return get(serverLevel.getServer().overworld()).mode;
        }
        return Mode.NORMAL;
    }

    public static boolean shouldRunInvertedTick(Level level) {
        return Math.floorMod(level.getGameTime(), 3L) == 0L;
    }

    public static int scaleScheduledBlockDelay(LevelAccessor accessor, Block block, int delay) {
        if (!(accessor instanceof ServerLevel level) || delay <= 0) {
            return delay;
        }

        boolean affected = block instanceof FireBlock
                ? Config.timeSongsAffectFireSpread()
                : Config.timeSongsAffectRedstone() && isRedstoneComponent(block);
        if (!affected) {
            return delay;
        }

        Mode activeMode = getMode(level);
        if (activeMode == Mode.DOUBLE) {
            return Math.max(1, (delay + 1) / 2);
        }
        if (activeMode == Mode.INVERTED) {
            return delay > Integer.MAX_VALUE / 3 ? Integer.MAX_VALUE : delay * 3;
        }
        return delay;
    }

    private static boolean isRedstoneComponent(Block block) {
        return block instanceof DiodeBlock
                || block instanceof ObserverBlock
                || block instanceof RedstoneTorchBlock
                || block instanceof RedStoneWireBlock
                || block instanceof ButtonBlock
                || block instanceof LeverBlock
                || block instanceof BasePressurePlateBlock
                || block instanceof PistonBaseBlock
                || block instanceof TripWireBlock
                || block instanceof TripWireHookBlock
                || block instanceof TargetBlock
                || block instanceof DispenserBlock
                || block instanceof DetectorRailBlock
                || block instanceof PoweredRailBlock
                || block instanceof LightningRodBlock
                || block instanceof SculkSensorBlock;
    }

    public void syncToAllPlayers() {
        NetworkDispatcher.network_channel.send(PacketDistributor.ALL.noArg(), createSyncMessage());
    }

    private void syncToPlayer(ServerPlayer player) {
        NetworkDispatcher.network_channel.send(PacketDistributor.PLAYER.with(() -> player), createSyncMessage());
    }

    private SyncTimeSongModeMessage createSyncMessage() {
        return new SyncTimeSongModeMessage(mode, accumulatedTime, Config.timeSongsAffectDayNightCycle());
    }

    private void tick(ServerLevel level) {
        restoreLegacyRandomTickSpeed(level);
        if (mode == Mode.NORMAL) {
            return;
        }

        if (!isModeEnabled(mode)) {
            clearEffect(level);
            syncToAllPlayers();
            return;
        }

        if (Config.timeSongsAffectDayNightCycle()
                && level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            long currentTime = level.getDayTime();
            if (mode == Mode.DOUBLE) {
                // Vanilla already advanced one tick, so one additional tick produces double time.
                level.setDayTime(currentTime + 1L);
            } else if (mode == Mode.INVERTED) {
                // Replace vanilla's one-tick advance with one tick every three server ticks.
                accumulatedTime += 1.0D / 3.0D;
                long desiredAdvance = (long) accumulatedTime;
                accumulatedTime -= desiredAdvance;
                level.setDayTime(currentTime - 1L + desiredAdvance);
            }
        }

        remainingTicks--;
        if (remainingTicks <= 0) {
            clearEffect(level);
            syncToAllPlayers();
        } else {
            setDirty();
        }
    }

    private void clearEffect(ServerLevel level) {
        restoreLegacyRandomTickSpeed(level);
        mode = Mode.NORMAL;
        remainingTicks = 0;
        accumulatedTime = 0.0D;
        hasOriginalRandomTickSpeed = false;
        setDirty();
    }

    private void restoreLegacyRandomTickSpeed(ServerLevel level) {
        if (hasOriginalRandomTickSpeed) {
            level.getGameRules().getRule(GameRules.RULE_RANDOMTICKING)
                    .set(originalRandomTickSpeed, level.getServer());
            hasOriginalRandomTickSpeed = false;
            setDirty();
        }
    }

    private void adjustWeatherBeforeTick(ServerLevel level) {
        if (mode == Mode.NORMAL || !Config.timeSongsAffectWeather()) {
            return;
        }

        int adjustment = mode == Mode.DOUBLE ? -1 : shouldRunInvertedTick(level) ? 0 : 1;
        if (adjustment == 0) {
            return;
        }

        ServerLevelData weather = (ServerLevelData) level.getLevelData();
        weather.setClearWeatherTime(adjustPositiveTimer(weather.getClearWeatherTime(), adjustment));
        weather.setRainTime(adjustPositiveTimer(weather.getRainTime(), adjustment));
        weather.setThunderTime(adjustPositiveTimer(weather.getThunderTime(), adjustment));
    }

    private static int adjustPositiveTimer(int timer, int adjustment) {
        return timer > 0 ? Math.max(0, timer + adjustment) : timer;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        ServerLevel overworld = event.getServer().getLevel(Level.OVERWORLD);
        if (overworld == null) {
            return;
        }

        TimeSongSavedData data = get(overworld);
        if (event.phase == TickEvent.Phase.START) {
            data.adjustWeatherBeforeTick(overworld);
        } else {
            data.tick(overworld);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            get(player.server.overworld()).syncToPlayer(player);
        }
    }

    public enum Mode {
        NORMAL,
        DOUBLE,
        INVERTED;

        private static Mode fromName(String name) {
            try {
                return valueOf(name);
            } catch (IllegalArgumentException exception) {
                return NORMAL;
            }
        }

        public static Mode fromId(int id) {
            Mode[] modes = values();
            return id >= 0 && id < modes.length ? modes[id] : NORMAL;
        }
    }
}
