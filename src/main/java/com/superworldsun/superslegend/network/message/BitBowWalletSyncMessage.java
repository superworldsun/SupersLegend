package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.client.hud.RupeeWalletHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Makes only Bit Bow ammunition spending appear immediately on the wallet HUD. */
public record BitBowWalletSyncMessage(int balance) {
    public static BitBowWalletSyncMessage decode(FriendlyByteBuf buffer) {
        return new BitBowWalletSyncMessage(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(balance);
    }

    public static void receive(BitBowWalletSyncMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> RupeeWalletHud.requestInstantBalance(message.balance)));
        context.setPacketHandled(true);
    }
}
