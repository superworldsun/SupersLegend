package com.superworldsun.superslegend.cooldowns;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class CooldownsProvider implements ICapabilitySerializable<CompoundNBT>
{
	private static final ResourceLocation MANA_ID = new ResourceLocation(SupersLegendMain.MOD_ID, "cooldowns");
	private ICooldowns instance = COOLDOWNS_CAPABILITY.getDefaultInstance();
	
	@CapabilityInject(ICooldowns.class)
	public static final Capability<ICooldowns> COOLDOWNS_CAPABILITY = null;
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
	{
		// We add cooldowns only to players
		if (!(event.getObject() instanceof PlayerEntity))
		{
			return;
		}
		
		event.addCapability(MANA_ID, new CooldownsProvider());
	}
	
	@SubscribeEvent
	public static void updateCooldowns(PlayerTickEvent event)
	{
		// Prevent changes from being applied 2 times per tick
		if (event.phase == Phase.END)
		{
			return;
		}
		
		ICooldowns cooldowns = CooldownsProvider.get(event.player);
		
		cooldowns.getItemsOnCooldown().forEach(item ->
		{
			cooldowns.setCooldown(item, cooldowns.getCooldown(item) - 1);
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
	{
		if (COOLDOWNS_CAPABILITY == capability)
		{
			return (LazyOptional<T>) LazyOptional.of(() -> instance);
		}
		
		return LazyOptional.empty();
	}
	
	@Override
	public CompoundNBT serializeNBT()
	{
		return (CompoundNBT) COOLDOWNS_CAPABILITY.writeNBT(instance, null);
	}
	
	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		COOLDOWNS_CAPABILITY.readNBT(instance, null, nbt);
	}
	
	@Nullable
	public static ICooldowns get(PlayerEntity player)
	{
		return player.getCapability(COOLDOWNS_CAPABILITY).orElse(new Cooldowns());
	}
}
