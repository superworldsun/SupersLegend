package com.superworldsun.superslegend.client.sound;

import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.registries.ItemInit;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class BremenMaskSound extends TickableSound
{
	private final PlayerEntity player;
	
	public BremenMaskSound(PlayerEntity entity)
	{
		super(SoundInit.BREMEN_MARCH.get(), SoundCategory.PLAYERS);
		this.player = entity;
		this.looping = true;
		this.delay = 0;
		this.volume = 1.0F;
		this.x = entity.getX();
		this.y = entity.getY();
		this.z = entity.getZ();
	}
	
	@Override
	public boolean canPlaySound()
	{
		return !player.isSilent();
	}
	
	@Override
	public boolean canStartSilent()
	{
		return true;
	}
	
	@Override
	public void tick()
	{
		if (!player.isAlive())
		{
			stop();
			return;
		}
		
		Item helmet = player.getItemBySlot(EquipmentSlotType.HEAD).getItem();
		
		if (helmet != ItemInit.MASK_BREMANMASK.get() || !((IMaskAbility) helmet).isPlayerUsingAbility(player))
		{
			stop();
			return;
		}
		
		x = player.getX();
		y = player.getY();
		z = player.getZ();
	}
}
