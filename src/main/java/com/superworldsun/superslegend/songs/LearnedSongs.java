package com.superworldsun.superslegend.songs;

import java.util.HashSet;
import java.util.Set;

public class LearnedSongs implements ILearnedSongs
{
	private Set<OcarinaSong> learnedSongs = new HashSet<>();
	
	@Override
	public Set<OcarinaSong> getLearnedSongs()
	{
		return learnedSongs;
	}
}
