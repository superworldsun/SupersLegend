package com.superworldsun.superslegend.items.songs;

import java.util.List;

import com.superworldsun.superslegend.registries.OcarinaSongInit;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SunsSongSheet extends SongSheetItem
{
	public SunsSongSheet()
	{
		super(OcarinaSongInit.SUNS_SONG);
	}
	
	@Override
	protected void addSongDescription(List<ITextComponent> list)
	{
		list.add(new StringTextComponent(TextFormatting.GRAY + "Playing this will accelerate the passage"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "of time to Day and Night"));
	}
}
