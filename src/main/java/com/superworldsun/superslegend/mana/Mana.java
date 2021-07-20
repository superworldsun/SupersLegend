package com.superworldsun.superslegend.mana;

import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SyncManaMessage;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class Mana implements IMana
{
	private final float maxMana = 20.0F;
	private float mana = maxMana;
	
	@Override
	public float getMana()
	{
		return mana;
	}
	
	@Override
	public float getMaxMana()
	{
		return maxMana;
	}
	
	@Override
	public void spendMana(float amount)
	{
		// Can't go below 0
		mana = Math.max(0, mana - amount);
	}
	
	@Override
	public void setMana(float amount)
	{
		// Only between 0 and maximum
		mana = Math.max(0, Math.min(maxMana, amount));
	}
	
	@Override
	public void restoreMana(float amount)
	{
		// Can't go above maximum
		mana = Math.min(maxMana, mana + amount);
	}
	
	@Override
	public void sync(ServerPlayerEntity player)
	{
		NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> player), new SyncManaMessage(player));
	}
}
