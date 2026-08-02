package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.client.screen.MerchantRupeeButtonState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Returns whether the current vanilla trader has any wallet-backed offers. */
public record RupeeTradeAvailabilityMessage(int containerId, boolean available) {
    public static RupeeTradeAvailabilityMessage decode(FriendlyByteBuf buffer) {
        return new RupeeTradeAvailabilityMessage(buffer.readVarInt(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(containerId);
        buffer.writeBoolean(available);
    }

    public static void receive(RupeeTradeAvailabilityMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> applyClient(message)));
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void applyClient(RupeeTradeAvailabilityMessage message) {
        MerchantRupeeButtonState.apply(message.containerId, message.available);
    }
}
