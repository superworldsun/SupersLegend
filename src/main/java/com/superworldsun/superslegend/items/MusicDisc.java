package com.superworldsun.superslegend.items;

import java.util.function.Supplier;

import com.superworldsun.superslegend.registries.ItemGroupInit;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.SoundEvent;

public class MusicDisc extends MusicDiscItem
{
	public MusicDisc(Supplier<SoundEvent> soundSupplier)
	{
		super(1, soundSupplier, new Properties().stacksTo(1).tab(ItemGroupInit.RESOURCES).rarity(Rarity.RARE));
	}	
}
