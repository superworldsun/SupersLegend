package com.superworldsun.superslegend.network.message;

import java.util.function.Supplier;

import com.superworldsun.superslegend.client.screen.WaypointCreationScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ShowWaystoneCreationScreenMessage
{
	private BlockPos blockPos;
	
    public ShowWaystoneCreationScreenMessage(BlockPos blockPos)
    {
    	this.blockPos = blockPos;
    }
    
    private ShowWaystoneCreationScreenMessage()
    {    	
    }

    public static ShowWaystoneCreationScreenMessage decode(PacketBuffer buf)
    {
    	ShowWaystoneCreationScreenMessage message = new ShowWaystoneCreationScreenMessage();
    	message.blockPos = buf.readBlockPos();
        return message;
    }

    public void encode(PacketBuffer buf)
    {
    	buf.writeBlockPos(blockPos);
    }

    public static void receive(ShowWaystoneCreationScreenMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
    {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleClientPacket(message, ctx)));
        ctx.setPacketHandled(true);
    }
    
    @OnlyIn(Dist.CLIENT)
	private static void handleClientPacket(ShowWaystoneCreationScreenMessage message, Context ctx)
	{
		Minecraft client = Minecraft.getInstance();
		client.setScreen(new WaypointCreationScreen(message.blockPos));
	}
}
