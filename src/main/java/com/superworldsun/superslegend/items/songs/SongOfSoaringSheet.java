package com.superworldsun.superslegend.items.songs;

import java.util.List;

import com.superworldsun.superslegend.registries.OcarinaSongInit;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SongOfSoaringSheet extends SongSheet
{
	public SongOfSoaringSheet()
	{
		super(OcarinaSongInit.SONG_OF_SOARING);
	}
	
	@Override
	protected void addSongDescription(List<ITextComponent> list)
	{
		list.add(new StringTextComponent(TextFormatting.GRAY + "Playing this will allow you to fly"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "to any activated owl statues"));
	}
}
