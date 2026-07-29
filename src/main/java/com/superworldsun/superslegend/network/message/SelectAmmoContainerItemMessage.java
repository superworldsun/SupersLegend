package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.items.ammobags.AmmoContainerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectAmmoContainerItemMessage {
    private final int containerId;
    private final int menuSlotIndex;
    private final int direction;

    public SelectAmmoContainerItemMessage(int containerId, int menuSlotIndex, int direction) {
        this.containerId = containerId;
        this.menuSlotIndex = menuSlotIndex;
        this.direction = Integer.signum(direction);
    }

    public static SelectAmmoContainerItemMessage decode(FriendlyByteBuf buffer) {
        return new SelectAmmoContainerItemMessage(buffer.readVarInt(), buffer.readVarInt(), buffer.readByte());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(containerId);
        buffer.writeVarInt(menuSlotIndex);
        buffer.writeByte(direction);
    }

    public static void receive(SelectAmmoContainerItemMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();

        if (player != null) {
            context.enqueueWork(() -> handleOnServer(player, message));
        }
        context.setPacketHandled(true);
    }

    private static void handleOnServer(ServerPlayer player, SelectAmmoContainerItemMessage message) {
        AbstractContainerMenu menu = player.containerMenu;
        if (message.direction == 0 || menu.containerId != message.containerId
                || message.menuSlotIndex < 0 || message.menuSlotIndex >= menu.slots.size()) {
            return;
        }

        ItemStack containerStack = menu.getSlot(message.menuSlotIndex).getItem();
        if (containerStack.getItem() instanceof AmmoContainerItem containerItem
                && containerItem.cycleSelectedItem(containerStack, message.direction)) {
            menu.broadcastChanges();
        }
    }
}
