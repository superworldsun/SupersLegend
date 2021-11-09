package com.superworldsun.superslegend.songs;

import com.superworldsun.superslegend.SupersLegendRegistries;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class LearnedSongsStorage implements IStorage<ILearnedSongs>
{
	@Override
	public CompoundNBT writeNBT(Capability<ILearnedSongs> capability, ILearnedSongs instance, Direction side)
	{
		CompoundNBT nbt = new CompoundNBT();
		
		if (!instance.getLearnedSongs().isEmpty())
		{
			ListNBT learnedSongsList = new ListNBT();
			instance.getLearnedSongs().forEach(song -> learnedSongsList.add(StringNBT.valueOf(song.getRegistryName().toString())));
			nbt.put("LearnedSongs", learnedSongsList);
		}
		
		return nbt;
	}
	
	@Override
	public void readNBT(Capability<ILearnedSongs> capability, ILearnedSongs instance, Direction side, INBT inbt)
	{
		CompoundNBT nbt = (CompoundNBT) inbt;
		instance.getLearnedSongs().clear();
		
		if (nbt.contains("LearnedSongs"))
		{
			ListNBT learnedSongsList = nbt.getList("LearnedSongs", StringNBT.valueOf("").getId());
			learnedSongsList.forEach(stringNbt ->
			{
				ResourceLocation songRegistryName = new ResourceLocation(stringNbt.getAsString());
				instance.getLearnedSongs().add(SupersLegendRegistries.OCARINA_SONGS.getValue(songRegistryName));
			});
		}
	}
}
