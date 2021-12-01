package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.SupersLegendRegistries;
import com.superworldsun.superslegend.songs.OcarinaSong;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlaySongMessage
{
	private OcarinaSong song;
	
	public PlaySongMessage(OcarinaSong song)
	{
		this.song = song;
	}
	
	private PlaySongMessage()
	{
	}
	
	public static PlaySongMessage decode(PacketBuffer buf)
	{
		PlaySongMessage result = new PlaySongMessage();
		result.song = SupersLegendRegistries.OCARINA_SONGS.getValue(new ResourceLocation(buf.readUtf()));
		return result;
	}
	
	public void encode(PacketBuffer buf)
	{
		buf.writeUtf(song.getRegistryName().toString());
	}
	
	public static void receive(PlaySongMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
	{
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);
		message.song.onSongPlayed(player, player.level);
	}
}
