package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.entities.statue.ElegyStatueEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MineElegyStatueMessage {
    private final int entityId;
    private final boolean mining;

    public MineElegyStatueMessage(int entityId, boolean mining) {
        this.entityId = entityId;
        this.mining = mining;
    }

    public static MineElegyStatueMessage decode(FriendlyByteBuf buffer) {
        return new MineElegyStatueMessage(buffer.readVarInt(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeBoolean(mining);
    }

    public static void receive(MineElegyStatueMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> {
                Entity entity = player.level().getEntity(message.entityId);
                if (entity instanceof ElegyStatueEntity statue) {
                    if (message.mining) {
                        statue.continueMining(player);
                    } else {
                        statue.stopMining(player);
                    }
                }
            });
        }
        context.setPacketHandled(true);
    }
}
