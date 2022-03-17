package com.superworldsun.superslegend.client.sound;

import com.superworldsun.superslegend.interfaces.IMaskAbility;
import com.superworldsun.superslegend.registries.ItemInit;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import org.apache.commons.lang3.tuple.ImmutableTriple;
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
		ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_BREMANMASK.get(), player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		if (!stack0.isEmpty()) {
			if (stack0.getItem() != ItemInit.MASK_BREMANMASK.get() || !((IMaskAbility) stack0.getItem()).isPlayerUsingAbility(player)) {
				stop();
				return;
			}
		}
		
		x = player.getX();
		y = player.getY();
		z = player.getZ();
	}
}
