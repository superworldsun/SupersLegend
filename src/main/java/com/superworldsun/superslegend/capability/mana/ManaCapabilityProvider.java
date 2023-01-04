package com.superworldsun.superslegend.capability.mana;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SyncManaMessage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.PacketDistributor;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ManaCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
	private static final ResourceLocation MANA_ID = new ResourceLocation(SupersLegendMain.MOD_ID, "mana");
	@CapabilityInject(ManaCapability.class)
	public static final Capability<ManaCapability> MANA_CAPABILITY = null;
	private final ManaCapability capabilityInstance = MANA_CAPABILITY.getDefaultInstance();

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (!canHaveMana(event.getObject())) {
			return;
		}

		event.addCapability(MANA_ID, new ManaCapabilityProvider());
	}

	@SubscribeEvent
	public static void playerJoinWorld(EntityJoinWorldEvent event) {
		if (!canHaveMana(event.getEntity())) {
			return;
		}

		if (!event.getEntity().level.isClientSide) {
			ManaCapabilityProvider.syncWithSelf((ServerPlayerEntity) event.getEntity());
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (capability == MANA_CAPABILITY) {
			return LazyOptional.of(() -> capabilityInstance).cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		return (CompoundNBT) MANA_CAPABILITY.writeNBT(capabilityInstance, null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		MANA_CAPABILITY.readNBT(capabilityInstance, null, nbt);
	}

	private static boolean canHaveMana(Entity entity) {
		return entity instanceof PlayerEntity;
	}

	private static void syncWithSelf(ServerPlayerEntity player) {
		NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> player), new SyncManaMessage(player));
	}
}
