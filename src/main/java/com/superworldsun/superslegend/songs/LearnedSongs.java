package com.superworldsun.superslegend.songs;

import java.util.HashSet;
import java.util.Set;

public class LearnedSongs implements ILearnedSongs
{
	private Set<OcarinaSong> learnedSongs = new HashSet<>();
	private OcarinaSong currentSong;
	
	@Override
	public Set<OcarinaSong> getLearnedSongs()
	{
		return learnedSongs;
	}
	
	@Override
	public OcarinaSong getCurrentSong()
	{
		return currentSong;
	}
	
	@Override
	public void setCurrentSong(OcarinaSong song)
	{
		currentSong = song;
	}
}
