package com.superworldsun.superslegend.items.songs;

import java.util.List;

import com.superworldsun.superslegend.registries.OcarinaSongInit;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SongOfDoubleTimeSheet extends SongSheetItem
{
	public SongOfDoubleTimeSheet()
	{
		super(OcarinaSongInit.SONG_OF_DOUBLE_TIME);
	}
	
	@Override
	protected void addSongDescription(List<ITextComponent> list)
	{
		list.add(new StringTextComponent(TextFormatting.GRAY + "Playing this will accelerate the passage of time."));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Play a second time to return it to normal"));
		list.add(new StringTextComponent(TextFormatting.RED + "Only works in single player"));
	}
}
