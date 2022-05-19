package com.superworldsun.superslegend.client.sound;

import com.superworldsun.superslegend.songs.LearnedSongsProvider;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;

public class OcarinaSongSound extends TickableSound
{
	private final PlayerEntity player;
	private final OcarinaSong song;
	
	public OcarinaSongSound(PlayerEntity entity, OcarinaSong song)
	{
		super(song.getPlayingSound(), SoundCategory.PLAYERS);
		this.player = entity;
		this.delay = 0;
		this.volume = 1.0F;
		this.x = entity.getX();
		this.y = entity.getY();
		this.z = entity.getZ();
		this.song = song;
	}
	
	@Override
	public boolean canPlaySound()
	{
		return !player.isSilent();
	}
	
	@Override
	public boolean canStartSilent()
	{
		return true;
	}
	
	@Override
	public void tick()
	{
		if (!player.isAlive())
		{
			stop();
			return;
		}
		
		if (LearnedSongsProvider.get(player).getCurrentSong() != song)
		{
			stop();
			return;
		}
		
		x = player.getX();
		y = player.getY();
		z = player.getZ();
	}
}
