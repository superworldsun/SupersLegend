package com.superworldsun.superslegend.capability.warppads;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.mana.ManaCapability;

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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class WarpPadsCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
	private static final ResourceLocation CAPABILITY_ID = new ResourceLocation(SupersLegendMain.MOD_ID, "warppads");
	@CapabilityInject(ManaCapability.class)
	public static final Capability<WarpPadsCapability> CAPABILITY = null;
	private final WarpPadsCapability capabilityInstance = CAPABILITY.getDefaultInstance();

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof PlayerEntity)) {
			return;
		}
		event.addCapability(CAPABILITY_ID, new WarpPadsCapabilityProvider());
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (capability == CAPABILITY) {
			return LazyOptional.of(() -> capabilityInstance).cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		return (CompoundNBT) CAPABILITY.writeNBT(capabilityInstance, null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		CAPABILITY.readNBT(capabilityInstance, null, nbt);
	}
}
