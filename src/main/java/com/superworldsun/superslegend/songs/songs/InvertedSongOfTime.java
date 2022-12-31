package com.superworldsun.superslegend.songs.songs;

import static net.minecraft.world.GameRules.RULE_RANDOMTICKING;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules.IntegerValue;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.GameRules.RuleValue;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class InvertedSongOfTime extends OcarinaSong
{
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
	public void onSongPlayed(PlayerEntity player, World level)
	{
		ServerWorld serverWorld = (ServerWorld) level;
		MinecraftServer minecraftServer = serverWorld.getServer();
		
		if (serverWorld.getServer().getGameRules().getRule(RULE_RANDOMTICKING).get() != 1)
		{
			player.sendMessage(new TranslationTextComponent("text.ocarina.inverted", player.getName()), UUID.randomUUID());
			setGameRule(minecraftServer, RULE_RANDOMTICKING, 1);
			timer = 24000 * 3;
		}
		else
		{
			player.sendMessage(new TranslationTextComponent("text.ocarina.inverted_second", player.getName()), UUID.randomUUID());
			setGameRule(minecraftServer, RULE_RANDOMTICKING, 3);
			timer = 0;
			
		}
	}
	
	@SubscribeEvent
	public static void onWorldTick(TickEvent.ServerTickEvent event)
	{
		MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
		timer = timer - 1;
		
		if (timer <= 0 && minecraftServer.getGameRules().getRule(RULE_RANDOMTICKING).get() == 1)
		{
			setGameRule(minecraftServer, RULE_RANDOMTICKING, 3);
		}
	}
	
	private static void setGameRule(MinecraftServer minecraftServer, RuleKey<IntegerValue> ruleKey, int value)
	{
		IntegerValue gameRule = minecraftServer.getGameRules().getRule(ruleKey);
		ObfuscationReflectionHelper.setPrivateValue(IntegerValue.class, gameRule, value, "field_223566_a");
		Method onChangedMethod = ObfuscationReflectionHelper.findMethod(RuleValue.class, "func_223556_a", MinecraftServer.class);
		
		try
		{
			onChangedMethod.invoke(gameRule, minecraftServer);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
}
