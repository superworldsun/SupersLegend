package com.superworldsun.superslegend.network.message;

import com.superworldsun.superslegend.items.wallet.RupeeWalletItem;
import com.superworldsun.superslegend.util.RupeeValue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Arrays;
import java.util.function.Supplier;

public class WithdrawRupeesMessage {
    private static final int MAX_TRANSACTION_ITEMS = 10000;

    private final InteractionHand hand;
    private final int[] counts;
    private final boolean deposit;

    public WithdrawRupeesMessage(InteractionHand hand, int[] counts, boolean deposit) {
        this.hand = hand;
        this.counts = Arrays.copyOf(counts, RupeeValue.values().length);
        this.deposit = deposit;
    }

    public static WithdrawRupeesMessage decode(FriendlyByteBuf buffer) {
        InteractionHand hand = buffer.readEnum(InteractionHand.class);
        int[] counts = new int[RupeeValue.values().length];
        for (int index = 0; index < counts.length; index++) {
            counts[index] = buffer.readVarInt();
        }
        return new WithdrawRupeesMessage(hand, counts, buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(hand);
        for (int count : counts) {
            buffer.writeVarInt(count);
        }
        buffer.writeBoolean(deposit);
    }

    public static void receive(WithdrawRupeesMessage message,
                               Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player != null) {
            context.enqueueWork(() -> transact(player, message));
        }
        context.setPacketHandled(true);
    }

    private static void transact(ServerPlayer player, WithdrawRupeesMessage message) {
        ItemStack walletStack = player.getItemInHand(message.hand);
        if (!(walletStack.getItem() instanceof RupeeWalletItem walletItem)) {
            return;
        }

        long requestedValue = 0;
        int requestedItems = 0;
        for (RupeeValue denomination : RupeeValue.values()) {
            int count = message.counts[denomination.ordinal()];
            if (count < 0 || count > MAX_TRANSACTION_ITEMS) {
                return;
            }
            requestedItems += count;
            requestedValue += (long) count * denomination.value();
        }

        if (requestedItems <= 0 || requestedItems > MAX_TRANSACTION_ITEMS
                || requestedValue <= 0 || requestedValue > Integer.MAX_VALUE) {
            return;
        }

        if (message.deposit) {
            deposit(player, walletStack, walletItem, message.counts, (int) requestedValue);
        } else {
            withdraw(player, walletStack, walletItem, message.counts, (int) requestedValue);
        }
    }

    private static void withdraw(ServerPlayer player, ItemStack walletStack, RupeeWalletItem walletItem,
                                 int[] counts, int requestedValue) {
        int stored = walletItem.getStoredRupees(walletStack);
        if (requestedValue > stored) {
            return;
        }

        if (!canFitInInventory(player.getInventory(), counts)) {
            player.displayClientMessage(
                    Component.translatable("screen.superslegend.rupee_wallet.inventory_full"), true);
            return;
        }

        walletItem.setStoredRupees(walletStack, stored - requestedValue);
        for (RupeeValue denomination : RupeeValue.values()) {
            giveRupees(player, denomination, counts[denomination.ordinal()]);
        }
        syncInventory(player);
    }

    private static void deposit(ServerPlayer player, ItemStack walletStack, RupeeWalletItem walletItem,
                                int[] counts, int requestedValue) {
        int stored = walletItem.getStoredRupees(walletStack);
        int walletSpace = walletItem.getCapacity() - stored;
        if (walletSpace <= 0 || !hasRupees(player.getInventory(), counts)) {
            return;
        }

        removeRupees(player.getInventory(), counts);
        int depositedValue = Math.min(requestedValue, walletSpace);
        int change = requestedValue - depositedValue;
        walletItem.setStoredRupees(walletStack, stored + depositedValue);
        giveRupeeValue(player, change);
        syncInventory(player);
    }

    private static boolean hasRupees(Inventory inventory, int[] counts) {
        for (RupeeValue denomination : RupeeValue.values()) {
            int found = 0;
            for (ItemStack stack : inventory.items) {
                if (stack.is(denomination.item())) {
                    found += stack.getCount();
                }
            }
            if (found < counts[denomination.ordinal()]) {
                return false;
            }
        }
        return true;
    }

    private static void removeRupees(Inventory inventory, int[] counts) {
        for (RupeeValue denomination : RupeeValue.values()) {
            int remaining = counts[denomination.ordinal()];
            for (ItemStack stack : inventory.items) {
                if (remaining <= 0) {
                    break;
                }
                if (stack.is(denomination.item())) {
                    int removed = Math.min(remaining, stack.getCount());
                    stack.shrink(removed);
                    remaining -= removed;
                }
            }
        }
    }

    private static void syncInventory(ServerPlayer player) {
        player.getInventory().setChanged();
        player.containerMenu.broadcastChanges();
    }

    private static boolean canFitInInventory(Inventory inventory, int[] counts) {
        int emptySlots = 0;
        for (ItemStack stack : inventory.items) {
            if (stack.isEmpty()) {
                emptySlots++;
            }
        }

        for (RupeeValue denomination : RupeeValue.values()) {
            int remaining = counts[denomination.ordinal()];
            for (ItemStack stack : inventory.items) {
                if (stack.is(denomination.item())) {
                    remaining -= Math.max(0, stack.getMaxStackSize() - stack.getCount());
                }
            }
            if (remaining <= 0) {
                continue;
            }

            int maxStackSize = new ItemStack(denomination.item()).getMaxStackSize();
            int requiredSlots = (remaining + maxStackSize - 1) / maxStackSize;
            if (requiredSlots > emptySlots) {
                return false;
            }
            emptySlots -= requiredSlots;
        }
        return true;
    }

    private static void giveRupees(ServerPlayer player, RupeeValue denomination, int count) {
        int maxStackSize = new ItemStack(denomination.item()).getMaxStackSize();
        int remaining = count;
        while (remaining > 0) {
            ItemStack stack = new ItemStack(denomination.item(), Math.min(maxStackSize, remaining));
            player.getInventory().add(stack);
            if (!stack.isEmpty()) {
                player.drop(stack, false);
            }
            remaining -= Math.min(maxStackSize, remaining);
        }
    }

    private static void giveRupeeValue(ServerPlayer player, int value) {
        for (ItemStack stack : RupeeValue.splitIntoStacks(value)) {
            player.getInventory().add(stack);
            if (!stack.isEmpty()) {
                player.drop(stack, false);
            }
        }
    }
}
