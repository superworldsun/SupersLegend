package com.superworldsun.superslegend.songs.songs;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.UUID;

import static net.minecraft.world.GameRules.RULE_RANDOMTICKING;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class SongOfDoubleTime extends OcarinaSong
{
	public SongOfDoubleTime()
	{
		super("aaaaaaad");
	}

	static int worldTime;

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.SONG_OF_DOUBLE_TIME.get();
	}

	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		ServerWorld serverWorld = (ServerWorld) level;
		MinecraftServer minecraftServer = serverWorld.getServer();

		if(serverWorld.getServer().getGameRules().getRule(RULE_RANDOMTICKING).get() != 40) {
			player.sendMessage(new TranslationTextComponent("text.ocarina.doubled", player.getName()), UUID.randomUUID());

			GameRules.IntegerValue integerValue = new GameRules.IntegerValue(GameRules.IntegerValue.create(40, (p_223561_0_, p_223561_1_) -> {
			}), 40);
			serverWorld.getServer().getGameRules().getRule(RULE_RANDOMTICKING).setFrom(integerValue, minecraftServer);
			worldTime = 24000 * 3;
		} else {

			for(int i = 0; i < minecraftServer.getPlayerList().getPlayers().size(); i++){
				minecraftServer.getPlayerList().getPlayers().get(i).sendMessage(new TranslationTextComponent("text.ocarina.doubled_second", player.getName()), UUID.randomUUID());
			}
			GameRules.IntegerValue integerValue = new GameRules.IntegerValue(GameRules.IntegerValue.create(20, (p_223561_0_, p_223561_1_) -> {
			}), 20);
			serverWorld.getServer().getGameRules().getRule(RULE_RANDOMTICKING).setFrom(integerValue, minecraftServer);
			worldTime = 0;
		}
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.ServerTickEvent event) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		worldTime = worldTime - 1;
		if (worldTime <= 0 && server.getGameRules().getRule(RULE_RANDOMTICKING).get() == 40) {

			GameRules.IntegerValue integerValue = new GameRules.IntegerValue(GameRules.IntegerValue.create(20, (p_223561_0_, p_223561_1_) -> {
			}), 20);
			server.getGameRules().getRule(RULE_RANDOMTICKING).setFrom(integerValue, server);

		}
	}

}
