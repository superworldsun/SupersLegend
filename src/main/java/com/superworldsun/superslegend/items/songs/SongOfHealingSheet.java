package com.superworldsun.superslegend.items.songs;

import java.util.List;

import com.superworldsun.superslegend.registries.OcarinaSongInit;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SongOfHealingSheet extends SongSheetItem
{
	public SongOfHealingSheet()
	{
		super(OcarinaSongInit.SONG_OF_HEALING);
	}
	
	@Override
	protected void addSongDescription(List<ITextComponent> list)
	{
		list.add(new StringTextComponent(TextFormatting.GRAY + "Playing this will cure the weak"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "and destroy curses"));
	}
}
