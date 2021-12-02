package com.superworldsun.superslegend.songs;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class OcarinaSong implements IForgeRegistryEntry<OcarinaSong>
{
	private ResourceLocation registryName;
	private final String pattern;
	
	public OcarinaSong(String pattern)
	{
		this.pattern = pattern;
	}
	
	public String getPattern()
	{
		return pattern;
	}
	
	public ITextComponent getLocalizedName()
	{
		return new TranslationTextComponent("song." + getRegistryName().toString());
	}
	
	@Override
	public OcarinaSong setRegistryName(ResourceLocation registryName)
	{
		this.registryName = registryName;
		return this;
	}
	
	@Override
	public ResourceLocation getRegistryName()
	{
		return registryName;
	}
	
	@Override
	public Class<OcarinaSong> getRegistryType()
	{
		return OcarinaSong.class;
	}
	
	public SoundEvent getPlayingSound()
	{
		return SoundEvents.MUSIC_DISC_PIGSTEP;
	}
	
	public abstract void onSongPlayed(PlayerEntity player, World level);
}
