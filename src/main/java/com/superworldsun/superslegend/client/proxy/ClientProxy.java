package com.superworldsun.superslegend.client.proxy;

import com.superworldsun.superslegend.client.screen.GossipStoneScreen;
import com.superworldsun.superslegend.proxy.IProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class ClientProxy implements IProxy
{
	@Override
	public void showGossipStoneScreen(BlockPos pos)
	{
		Minecraft client = Minecraft.getInstance();
		client.setScreen(new GossipStoneScreen(pos));
	}
}
