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
	private int playerId;
	private float magic;

	private SyncMagicMessage() {
	}

	public SyncMagicMessage(Player player) {
		playerId = player.getId();
		magic = MagicProvider.getMagic(player);
	}

	public static SyncMagicMessage decode(FriendlyByteBuf buf) {
		SyncMagicMessage result = new SyncMagicMessage();
		result.playerId = buf.readVarInt();
		result.magic = buf.readFloat();
		return result;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeVarInt(playerId);
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
		if (minecraft.level != null && minecraft.level.getEntity(message.playerId) instanceof Player player) {
			MagicProvider.setMagicFromSync(player, message.magic);
		}
	}
}
