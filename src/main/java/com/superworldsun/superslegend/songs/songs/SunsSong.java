package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

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
	public void onSongPlayed(PlayerEntity player, World level) {
		if (level.dimension() == World.OVERWORLD) {
			was_played = true;
			played_at_day = level.isDay();
		}
	}

	@SubscribeEvent
	public static void onWorldTick(ServerTickEvent event) {
		if (was_played) {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			ServerWorld serverWorld = server.getLevel(World.OVERWORLD);
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

	private static SUpdateTimePacket createUpdateTimePacket(ServerWorld world) {
		return new SUpdateTimePacket(world.getGameTime(), world.getDayTime(), world.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT));
	}
}
