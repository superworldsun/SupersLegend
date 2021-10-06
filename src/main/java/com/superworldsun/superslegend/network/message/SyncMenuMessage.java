package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.network.ClientPacketHandlerClass;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncMenuMessage
{
    public final int menu;

    public SyncMenuMessage(int menu)
    {
        this.menu = menu;
    }

    public static SyncMenuMessage decode(PacketBuffer buf)
    {
        return new SyncMenuMessage(buf.readInt());
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeInt(menu);
    }

    public static void handle(SyncMenuMessage message, Supplier<NetworkEvent.Context> ctxSupplier)
    {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handleMenu(message, ctx)));
        ctx.setPacketHandled(true);

    }
}
