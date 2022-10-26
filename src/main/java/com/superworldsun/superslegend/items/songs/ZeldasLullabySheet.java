package com.superworldsun.superslegend.items.songs;

import java.util.List;

import com.superworldsun.superslegend.registries.OcarinaSongInit;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ZeldasLullabySheet extends SongSheetItem
{
	public ZeldasLullabySheet()
	{
		super(OcarinaSongInit.ZELDAS_LULLABY);
	}

	@Override
	protected void addSongDescription(List<ITextComponent> list)
	{
		list.add(new StringTextComponent(TextFormatting.GRAY + "Playing this will activate nearby Royal Tiles"));
	}
}
