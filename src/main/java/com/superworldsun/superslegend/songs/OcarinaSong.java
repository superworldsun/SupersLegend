package com.superworldsun.superslegend.songs;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class OcarinaSong implements IForgeRegistryEntry<OcarinaSong>
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
}
