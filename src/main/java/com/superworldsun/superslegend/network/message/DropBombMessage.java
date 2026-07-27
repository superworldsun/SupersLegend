package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.items.ammobags.BombBagItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;
import java.util.function.Supplier;

public class DropBombMessage {
    public static DropBombMessage decode(FriendlyByteBuf buffer) {
        return new DropBombMessage();
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void receive(DropBombMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> pullBombFromFirstAvailableBag(player));
        }
        context.setPacketHandled(true);
    }

    private static void pullBombFromFirstAvailableBag(ServerPlayer player) {
        ItemStack bombBagStack = findFirstNonEmptyBombBag(player);
        if (bombBagStack.isEmpty()) {
            return;
        }

        InteractionHand emptyHand;
        if (player.getMainHandItem().isEmpty()) {
            emptyHand = InteractionHand.MAIN_HAND;
        } else if (player.getOffhandItem().isEmpty()) {
            emptyHand = InteractionHand.OFF_HAND;
        } else {
            player.sendSystemMessage(Component.literal("You'll need to free your hands for that"));
            return;
        }

        BombBagItem bombBagItem = (BombBagItem) bombBagStack.getItem();
        bombBagItem.removeStack(bombBagStack).ifPresent(bomb -> player.setItemInHand(emptyHand, bomb));
    }

    private static ItemStack findFirstNonEmptyBombBag(ServerPlayer player) {
        Optional<IItemHandlerModifiable> optionalCurios =
                CuriosApi.getCuriosHelper().getEquippedCurios(player).resolve();
        if (optionalCurios.isEmpty()) {
            return ItemStack.EMPTY;
        }

        IItemHandlerModifiable curios = optionalCurios.get();
        for (int slot = 0; slot < curios.getSlots(); slot++) {
            ItemStack stack = curios.getStackInSlot(slot);
            if (stack.getItem() instanceof BombBagItem bombBagItem
                    && bombBagItem.getTotalStoredItemCount(stack) > 0) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
