package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import net.minecraft.entity.Pose;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ToggleCrawlingMessage {
	public ToggleCrawlingMessage() {
	}

	public static ToggleCrawlingMessage decode(PacketBuffer buf) {
		return new ToggleCrawlingMessage();
	}

	public void encode(PacketBuffer buf) {
	}

	public static void receive(ToggleCrawlingMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ServerPlayerEntity player = ctx.getSender();
		ctx.setPacketHandled(true);
		if (player.getForcedPose() != Pose.SWIMMING)
			player.setForcedPose(Pose.SWIMMING);
		else
			player.setForcedPose(null);
	}
}
