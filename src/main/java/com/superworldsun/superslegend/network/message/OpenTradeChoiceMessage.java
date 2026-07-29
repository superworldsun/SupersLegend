package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.client.screen.TradeChoiceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Opens the client-only Emerald/Rupee currency chooser. */
public record OpenTradeChoiceMessage(int traderEntityId, Component traderName) {
    public static OpenTradeChoiceMessage decode(FriendlyByteBuf buffer) {
        return new OpenTradeChoiceMessage(buffer.readVarInt(), buffer.readComponent());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(traderEntityId);
        buffer.writeComponent(traderName);
    }

    public static void receive(OpenTradeChoiceMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> openClientScreen(message)));
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void openClientScreen(OpenTradeChoiceMessage message) {
        Minecraft.getInstance().setScreen(
                new TradeChoiceScreen(message.traderEntityId, message.traderName));
    }
}
