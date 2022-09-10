package com.superworldsun.superslegend.songs.songs;

import com.mojang.brigadier.context.CommandContext;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;
import java.util.function.BiConsumer;

import static net.minecraft.world.GameRules.RULE_RANDOMTICKING;

public class InvertedSongOfTime extends OcarinaSong
{
	public InvertedSongOfTime()
	{
		super("dardar");
	}

	@Override
	public SoundEvent getPlayingSound()
	{
		return SoundInit.INVERTED_SONG_OF_TIME.get();
	}

	@Override
	public void onSongPlayed(PlayerEntity player, World level)
	{
		ServerWorld serverWorld = (ServerWorld) level;
		MinecraftServer minecraftServer = serverWorld.getServer();

		if(serverWorld.getServer().getGameRules().getRule(RULE_RANDOMTICKING).get() != 1) {
			player.sendMessage(new TranslationTextComponent("text.ocarina.inverted", player.getName()), UUID.randomUUID());

			GameRules.IntegerValue integerValue = new GameRules.IntegerValue(GameRules.IntegerValue.create(1, (p_223561_0_, p_223561_1_) -> {
			}), 1);
			serverWorld.getServer().getGameRules().getRule(RULE_RANDOMTICKING).setFrom(integerValue, minecraftServer);

		} else {
			player.sendMessage(new TranslationTextComponent("text.ocarina.inverted_second", player.getName()), UUID.randomUUID());

			GameRules.IntegerValue integerValue = new GameRules.IntegerValue(GameRules.IntegerValue.create(20, (p_223561_0_, p_223561_1_) -> {
			}), 20);
			serverWorld.getServer().getGameRules().getRule(RULE_RANDOMTICKING).setFrom(integerValue, minecraftServer);
		}
	}
}
