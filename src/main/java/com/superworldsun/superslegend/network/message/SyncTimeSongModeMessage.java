package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.client.ClientTimeSongState;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncTimeSongModeMessage {
    private final TimeSongSavedData.Mode mode;
    private final double accumulatedTime;
    private final boolean affectsDayNightCycle;

    public SyncTimeSongModeMessage(TimeSongSavedData.Mode mode, double accumulatedTime,
                                   boolean affectsDayNightCycle) {
        this.mode = mode;
        this.accumulatedTime = accumulatedTime;
        this.affectsDayNightCycle = affectsDayNightCycle;
    }

    public static SyncTimeSongModeMessage decode(FriendlyByteBuf buffer) {
        return new SyncTimeSongModeMessage(
                TimeSongSavedData.Mode.fromId(buffer.readVarInt()),
                buffer.readDouble(),
                buffer.readBoolean()
        );
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(mode.ordinal());
        buffer.writeDouble(accumulatedTime);
        buffer.writeBoolean(affectsDayNightCycle);
    }

    public static void receive(SyncTimeSongModeMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> handleClient(message)));
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClient(SyncTimeSongModeMessage message) {
        ClientTimeSongState.setMode(message.mode, message.accumulatedTime, message.affectsDayNightCycle);
    }
}
