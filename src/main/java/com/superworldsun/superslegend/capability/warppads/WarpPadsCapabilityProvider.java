package com.superworldsun.superslegend.capability.warppads;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class WarpPadsCapabilityProvider implements ICapabilitySerializable<ListNBT> {
	private static final ResourceLocation CAPABILITY_ID = new ResourceLocation(SupersLegendMain.MOD_ID, "warppads");
	@CapabilityInject(WarpPadsCapability.class)
	public static final Capability<WarpPadsCapability> CAPABILITY = null;
	private final WarpPadsCapability capabilityInstance = CAPABILITY.getDefaultInstance();

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof PlayerEntity)) {
			return;
		}
		event.addCapability(CAPABILITY_ID, new WarpPadsCapabilityProvider());
	}

	@SubscribeEvent
	public static void persistThroughDeath(PlayerEvent.Clone event) {
		INBT originalNBT = CAPABILITY.writeNBT(getWarpPadsCapability(event.getOriginal()), null);
		CAPABILITY.readNBT(getWarpPadsCapability(event.getPlayer()), null, originalNBT);
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (capability == CAPABILITY) {
			return LazyOptional.of(() -> capabilityInstance).cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public ListNBT serializeNBT() {
		return (ListNBT) CAPABILITY.writeNBT(capabilityInstance, null);
	}

	@Override
	public void deserializeNBT(ListNBT nbt) {
		CAPABILITY.readNBT(capabilityInstance, null, nbt);
	}

	private static WarpPadsCapability getWarpPadsCapability(PlayerEntity player) {
		return player.getCapability(WarpPadsCapabilityProvider.CAPABILITY).orElse(new WarpPadsCapability());
	}
}
