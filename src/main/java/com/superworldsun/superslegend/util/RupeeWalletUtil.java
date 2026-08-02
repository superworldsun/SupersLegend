package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.items.wallet.RupeeWalletItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public final class RupeeWalletUtil {
    private RupeeWalletUtil() {
    }

    public static Optional<SlotResult> findEquippedWallet(Player player) {
        return CuriosApi.getCuriosHelper().findFirstCurio(player,
                stack -> stack.getItem() instanceof RupeeWalletItem);
    }

    public static int getStoredRupees(Player player) {
        return findEquippedWallet(player)
                .map(result -> ((RupeeWalletItem) result.stack().getItem()).getStoredRupees(result.stack()))
                .orElse(0);
    }

    public static int getWalletCapacity(Player player) {
        return findEquippedWallet(player)
                .map(result -> ((RupeeWalletItem) result.stack().getItem()).getCapacity())
                .orElse(0);
    }

    public static int getAvailableSpace(Player player) {
        return Math.max(0, getWalletCapacity(player) - getStoredRupees(player));
    }

    /** A zero-cost action is affordable even when no wallet is equipped. */
    public static boolean canAfford(Player player, int requestedAmount) {
        return requestedAmount >= 0 && (requestedAmount == 0 || getStoredRupees(player) >= requestedAmount);
    }

    /**
     * Deposits as much of {@code requestedAmount} as the equipped wallet can hold.
     *
     * @return the exact rupee value accepted by the wallet
     */
    public static int deposit(Player player, int requestedAmount) {
        if (requestedAmount <= 0) {
            return 0;
        }

        Optional<SlotResult> walletResult = findEquippedWallet(player);
        if (walletResult.isEmpty()) {
            return 0;
        }

        SlotResult result = walletResult.get();
        RupeeWalletItem walletItem = (RupeeWalletItem) result.stack().getItem();
        int stored = walletItem.getStoredRupees(result.stack());
        int accepted = Math.min(requestedAmount, walletItem.getCapacity() - stored);
        if (accepted <= 0) {
            return 0;
        }

        ItemStack updatedWallet = result.stack().copy();
        walletItem.setStoredRupees(updatedWallet, stored + accepted);
        CuriosApi.getCuriosHelper().setEquippedCurio(player, result.slotContext().identifier(),
                result.slotContext().index(), updatedWallet);
        return accepted;
    }

    /** Deposits the entire amount or leaves the wallet unchanged. */
    public static boolean tryDepositExact(Player player, int requestedAmount) {
        if (requestedAmount < 0) {
            return false;
        }
        return requestedAmount == 0 || getAvailableSpace(player) >= requestedAmount
                && deposit(player, requestedAmount) == requestedAmount;
    }

    /**
     * Removes up to {@code requestedAmount} rupees from the wallet currently
     * equipped in the player's Curios wallet slot.
     *
     * @return the exact number of rupees removed, or {@code 0} when no wallet
     * is equipped or its balance is empty
     */
    public static int spend(Player player, int requestedAmount) {
        if (requestedAmount <= 0) {
            return 0;
        }

        Optional<SlotResult> walletResult = findEquippedWallet(player);
        if (walletResult.isEmpty()) {
            return 0;
        }

        SlotResult result = walletResult.get();
        RupeeWalletItem walletItem = (RupeeWalletItem) result.stack().getItem();
        int stored = walletItem.getStoredRupees(result.stack());
        int spent = Math.min(requestedAmount, stored);
        if (spent <= 0) {
            return 0;
        }

        ItemStack updatedWallet = result.stack().copy();
        walletItem.setStoredRupees(updatedWallet, stored - spent);
        CuriosApi.getCuriosHelper().setEquippedCurio(player, result.slotContext().identifier(),
                result.slotContext().index(), updatedWallet);
        return spent;
    }

    /**
     * Removes exactly {@code requestedAmount}, or changes nothing when the
     * equipped wallet cannot cover the full amount. This is the safe primitive
     * for server-authoritative purchases; {@link #spend(Player, int)} remains
     * available for effects which intentionally consume a partial balance.
     */
    public static boolean trySpendExact(Player player, int requestedAmount) {
        if (requestedAmount < 0) {
            return false;
        }
        if (requestedAmount == 0) {
            return true;
        }

        Optional<SlotResult> walletResult = findEquippedWallet(player);
        if (walletResult.isEmpty()) {
            return false;
        }

        SlotResult result = walletResult.get();
        RupeeWalletItem walletItem = (RupeeWalletItem) result.stack().getItem();
        int stored = walletItem.getStoredRupees(result.stack());
        if (stored < requestedAmount) {
            return false;
        }

        ItemStack updatedWallet = result.stack().copy();
        walletItem.setStoredRupees(updatedWallet, stored - requestedAmount);
        CuriosApi.getCuriosHelper().setEquippedCurio(player, result.slotContext().identifier(),
                result.slotContext().index(), updatedWallet);
        return true;
    }
}
