package com.superworldsun.superslegend.client.sound;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import top.theillusivec4.curios.api.CuriosApi;

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
		
		ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_BREMANMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		if (!maskStack.isEmpty())
		{
			if (maskStack.getItem() != ItemInit.MASK_BREMANMASK.get() || !((IMaskAbility) maskStack.getItem()).isPlayerUsingAbility(player))
			{
				stop();
				return;
			}
		}
		
		x = player.getX();
		y = player.getY();
		z = player.getZ();
	}
}
