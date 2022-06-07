package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.client.screen.WaypointsScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ShowWaystonesScreenMessage
{
    public ShowWaystonesScreenMessage()
    {
    }

    public static ShowWaystonesScreenMessage decode(PacketBuffer buf)
    {
        return new ShowWaystonesScreenMessage();
    }

    public void encode(PacketBuffer buf)
    {
    }

    public static void receive(ShowWaystonesScreenMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
    {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleClientPacket(message, ctx)));
        ctx.setPacketHandled(true);
    }
    
    @OnlyIn(Dist.CLIENT)
	private static void handleClientPacket(ShowWaystonesScreenMessage message, Context ctx)
	{
		Minecraft client = Minecraft.getInstance();
		client.setScreen(new WaypointsScreen());
	}
}
