package com.superworldsun.superslegend.waypoints;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SyncWaypointsMessage;

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
public class WaypointsProvider implements ICapabilitySerializable<CompoundNBT>
{
	private static final ResourceLocation WAYPOINTS_ID = new ResourceLocation(SupersLegendMain.MOD_ID, "waypoints");
	private IWaypoints instance = WAYPOINTS_CAPABILITY.getDefaultInstance();
	
	@CapabilityInject(IWaypoints.class)
	public static final Capability<IWaypoints> WAYPOINTS_CAPABILITY = null;
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
	{
		// We add waypoints only to players
		if (!(event.getObject() instanceof PlayerEntity))
		{
			return;
		}
		
		event.addCapability(WAYPOINTS_ID, new WaypointsProvider());
	}
	
	@SubscribeEvent
	public static void playerJoinWorld(EntityJoinWorldEvent event)
	{
		// We add waypoints only to players, means we only sync it for players
		if (!(event.getEntity() instanceof PlayerEntity))
		{
			return;
		}
		
		// We only sync from server to client, not other way around!
		if (event.getEntity().level.isClientSide)
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) event.getEntity();
		WaypointsProvider.sync((ServerPlayerEntity) player);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
	{
		if (WAYPOINTS_CAPABILITY == capability)
		{
			return (LazyOptional<T>) LazyOptional.of(() -> instance);
		}
		
		return LazyOptional.empty();
	}
	
	@Override
	public CompoundNBT serializeNBT()
	{
		return (CompoundNBT) WAYPOINTS_CAPABILITY.writeNBT(instance, null);
	}
	
	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		WAYPOINTS_CAPABILITY.readNBT(instance, null, nbt);
	}
	
	public static IWaypoints get(PlayerEntity player)
	{
		return player.getCapability(WAYPOINTS_CAPABILITY).orElse(new Waypoints());
	}
	
	public static void sync(ServerPlayerEntity player)
	{
		NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> player), new SyncWaypointsMessage(player));
	}
}
