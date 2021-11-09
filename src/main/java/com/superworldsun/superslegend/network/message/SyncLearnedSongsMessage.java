package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.songs.LearnedSongsProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SyncLearnedSongsMessage
{
	public CompoundNBT nbt;
	
	public SyncLearnedSongsMessage(PlayerEntity player)
	{
		nbt = (CompoundNBT) LearnedSongsProvider.LEARNED_SONGS_CAPABILITY.writeNBT(LearnedSongsProvider.get(player), null);
	}
	
	private SyncLearnedSongsMessage()
	{
	}
	
	public static SyncLearnedSongsMessage decode(PacketBuffer buf)
	{
		SyncLearnedSongsMessage result = new SyncLearnedSongsMessage();
		result.nbt = buf.readAnySizeNbt();
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeNbt(nbt);
	}
	
	public static void receive(SyncLearnedSongsMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SyncLearnedSongsMessage.handleClientPacket(message, ctx)));
	}
	
	@OnlyIn(value = Dist.CLIENT)
	private static void handleClientPacket(SyncLearnedSongsMessage message, Context ctx)
	{
		Minecraft client = Minecraft.getInstance();
		LearnedSongsProvider.LEARNED_SONGS_CAPABILITY.readNBT(LearnedSongsProvider.get(client.player), null, message.nbt);
	}
}
