package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;


import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class InvertedSongOfTime extends OcarinaSong
{
	private static final long TICKS_PER_DAY = 24000L;
	private static double accumulatedTime = 0.0;
	public InvertedSongOfTime()
	{
		super("dardar", 0x3170CF);
	}

	// TODO: this is not being saved when the game is closed
	static int timer;

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.INVERTED_SONG_OF_TIME.get();
	}

	@Override
	public void onSongPlayed(Player player, Level level)
	{
		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}

		GameRules gameRules = serverLevel.getGameRules();
		if (gameRules.getInt(GameRules.RULE_RANDOMTICKING) != 1) {
			player.sendSystemMessage(Component.translatable("text.ocarina.inverted", player.getName()));
			gameRules.getRule(GameRules.RULE_RANDOMTICKING).set(1, serverLevel.getServer());
			timer = 24000 * 3;
		} else {
			player.sendSystemMessage(Component.translatable("text.ocarina.inverted_second", player.getName()));
			gameRules.getRule(GameRules.RULE_RANDOMTICKING).set(3, serverLevel.getServer());
			timer = 0;
		}
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.ServerTickEvent event) {
		timer = timer - 1;
		ServerLevel serverLevel = event.getServer().getLevel(Level.OVERWORLD);
		if (serverLevel == null) return;
		int tickSpeed = serverLevel.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);

		if (tickSpeed == 1) {
			if (timer <= 0) {
				serverLevel.getGameRules().getRule(GameRules.RULE_RANDOMTICKING).set(3, event.getServer());
			}
			if (event.phase == TickEvent.Phase.END) {
				accumulatedTime += 1.0 / 3.0;
				long currentTime = serverLevel.getDayTime();
				if (accumulatedTime >= 1.0) {
					serverLevel.setDayTime((currentTime + (long)accumulatedTime) % TICKS_PER_DAY);
					accumulatedTime %= 1.0;
				} else {
					serverLevel.setDayTime(currentTime);
				}
			} else {
				accumulatedTime = 0.0;			}
			}
	}
}
