package com.superworldsun.superslegend.songs;

import java.util.Set;

public interface ILearnedSongs
{
	Set<OcarinaSong> getLearnedSongs();
	
	OcarinaSong getCurrentSong();
	
	void setCurrentSong(OcarinaSong song);
}
