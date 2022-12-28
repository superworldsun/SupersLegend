package com.superworldsun.superslegend.songs.songs;

import static net.minecraft.world.GameRules.RULE_RANDOMTICKING;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import com.superworldsun.superslegend.SupersLegendMain;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class SongOfDoubleTime extends OcarinaSong
{
	public SongOfDoubleTime()
	{
		super("rraadd");
	}
	
	// TODO: this is not being saved when the game is closed
	static int timer;
	
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
		
		if (minecraftServer.getGameRules().getRule(RULE_RANDOMTICKING).get() != 6)
		{
			player.sendMessage(new TranslationTextComponent("text.ocarina.doubled", player.getName()), UUID.randomUUID());
			setGameRule(minecraftServer, RULE_RANDOMTICKING, 6);
			timer = 24000 * 3;
		}
		else
		{
			minecraftServer.getPlayerList().getPlayers().forEach(p -> p.sendMessage(new TranslationTextComponent("text.ocarina.doubled_second", p.getName()), UUID.randomUUID()));
			setGameRule(minecraftServer, RULE_RANDOMTICKING, 3);
			timer = 0;
		}
	}
	
	@SubscribeEvent
	public static void onWorldTick(TickEvent.ServerTickEvent event)
	{
		MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
		timer = timer - 1;
		
		if (timer <= 0 && minecraftServer.getGameRules().getRule(RULE_RANDOMTICKING).get() == 6)
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
