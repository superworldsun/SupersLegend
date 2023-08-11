package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.capability.magic.MagicProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

public class SyncMagicMessage {
	private float magic;

	private SyncMagicMessage() {
	}

	public SyncMagicMessage(Player player) {
		magic = MagicProvider.getMagic(player);
	}

	public static SyncMagicMessage decode(FriendlyByteBuf buf) {
		SyncMagicMessage result = new SyncMagicMessage();
		result.magic = buf.readFloat();
		return result;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeFloat(magic);
	}

	public static void receive(SyncMagicMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
		Context ctx = ctxSupplier.get();
		ctx.setPacketHandled(true);
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handlePacket(message, ctx)));
	}

	@OnlyIn(value = Dist.CLIENT)
	private static void handlePacket(SyncMagicMessage message, NetworkEvent.Context ctx) {
		Minecraft minecraft = Minecraft.getInstance();
		MagicProvider.setMagic(minecraft.player, message.magic);
	}
}
