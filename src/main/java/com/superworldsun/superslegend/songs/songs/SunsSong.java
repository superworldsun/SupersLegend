package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import net.minecraftforge.server.ServerLifecycleHooks;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class SunsSong extends OcarinaSong {
	private static final int TIME_SPEED_BONUS = 50;
	private static boolean was_played;
	private static boolean played_at_day;

	public SunsSong() {
		super("rdurdu", 0xDEDC19);
	}

	@Override
	public SoundEvent getPlayingSound() {
		return SoundInit.SUNS_SONG.get();
	}

	@Override
	public void onSongPlayed(Player player, Level level) {
		if (level.dimension() == Level.OVERWORLD) {
			if (!level.isDay() && player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
				ModAdvancementHelper.award(serverPlayer, "the_suns_song", "turned_to_day");
			}
			was_played = true;
			played_at_day = level.isDay();
		}
	}

	@SubscribeEvent
	public static void onWorldTick(ServerTickEvent event) {
		if (was_played) {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			ServerLevel serverWorld = server.getLevel(Level.OVERWORLD);
			boolean shouldContinue = serverWorld.isDay() && played_at_day || !serverWorld.isDay() && !played_at_day;

			if (shouldContinue) {
				serverWorld.setDayTime(serverWorld.getDayTime() + TIME_SPEED_BONUS - 1);
			} else {
				was_played = false;
			}

			server.getPlayerList().getPlayers().forEach(player -> {
				player.connection.send(createUpdateTimePacket(serverWorld));
			});
		}
	}

	private static ClientboundSetTimePacket createUpdateTimePacket(ServerLevel world) {
		return new ClientboundSetTimePacket(world.getGameTime(), world.getDayTime(), world.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT));
	}
}
