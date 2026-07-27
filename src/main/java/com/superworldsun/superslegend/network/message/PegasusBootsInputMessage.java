package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.items.armors.PegasusBootsArmor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PegasusBootsInputMessage {
    private final boolean forwardOnly;

    public PegasusBootsInputMessage(boolean forwardOnly) {
        this.forwardOnly = forwardOnly;
    }

    public static PegasusBootsInputMessage decode(FriendlyByteBuf buffer) {
        return new PegasusBootsInputMessage(buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(forwardOnly);
    }

    public static void receive(PegasusBootsInputMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();

        if (player != null) {
            context.enqueueWork(() -> PegasusBootsArmor.setForwardOnlyInput(player, message.forwardOnly));
        }
        context.setPacketHandled(true);
    }
}
