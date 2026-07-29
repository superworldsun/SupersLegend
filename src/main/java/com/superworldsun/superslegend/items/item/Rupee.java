package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.entities.RupeeEntity;
import com.superworldsun.superslegend.items.wallet.RupeeWalletItem;
import com.superworldsun.superslegend.util.RupeeValue;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/** Shared wallet-deposit behavior for every rupee denomination. */
public class Rupee extends Item {
    private final RupeeValue denomination;
    private final ChatFormatting color;
    private final boolean hasBreakdownRecipe;
    private final boolean isMaximumValue;

    public Rupee(Properties properties) {
        this(properties, RupeeValue.GREEN, ChatFormatting.GREEN, false, false);
    }

    protected Rupee(Properties properties, RupeeValue denomination, ChatFormatting color,
                     boolean hasBreakdownRecipe, boolean isMaximumValue) {
        super(properties);
        this.denomination = denomination;
        this.color = color;
        this.hasBreakdownRecipe = hasBreakdownRecipe;
        this.isMaximumValue = isMaximumValue;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player,
                                                            @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        var walletResult = RupeeWalletUtil.findEquippedWallet(player);
        if (walletResult.isEmpty()) {
            return InteractionResultHolder.pass(heldStack);
        }

        ItemStack walletStack = walletResult.get().stack();
        RupeeWalletItem wallet = (RupeeWalletItem) walletStack.getItem();
        if (wallet.getStoredRupees(walletStack) >= wallet.getCapacity()) {
            return InteractionResultHolder.pass(heldStack);
        }

        if (level.isClientSide) {
            return InteractionResultHolder.success(heldStack);
        }

        int originalValue = denomination.value() * heldStack.getCount();
        int acceptedValue = RupeeWalletUtil.deposit(player, originalValue);
        if (acceptedValue <= 0) {
            return InteractionResultHolder.pass(heldStack);
        }

        // Consume the original stack, then return any value that did not fit as
        // the smallest practical collection of rupee denominations.
        heldStack.setCount(0);
        returnRemainder(player, originalValue - acceptedValue);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), denomination.pickupSound(),
                SoundSource.PLAYERS, 0.8F, 1.0F);
        return InteractionResultHolder.success(heldStack);
    }

    private static void returnRemainder(Player player, int remainingValue) {
        for (ItemStack change : RupeeValue.splitIntoStacks(remainingValue)) {
            player.getInventory().add(change);
            if (!change.isEmpty()) {
                ItemEntity overflow = new RupeeEntity(player.level(), player.getX(), player.getY() + 0.25D,
                        player.getZ(), change.copy());
                overflow.setDefaultPickUpDelay();
                player.level().addFreshEntity(overflow);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (!Screen.hasShiftDown()) {
            String suffix = denomination.value() == 1 ? " Rupee" : " Rupees";
            tooltip.add(Component.literal(denomination.value() + suffix).withStyle(color));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            tooltip.add(Component.literal("Right-Clicking a Rupee will place it into an equipped wallet")
                    .withStyle(color, ChatFormatting.ITALIC));
            if (isMaximumValue) {
                tooltip.add(Component.literal("The maximum value of a rupee")
                        .withStyle(color, ChatFormatting.ITALIC));
            }
            tooltip.add(Component.literal("Rupees can be used for ammo, fuel, or trading")
                    .withStyle(color, ChatFormatting.ITALIC));
            if (hasBreakdownRecipe) {
                tooltip.add(Component.literal("You can also put this in a crafting table to break it down")
                        .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
            }
            tooltip.add(Component.literal("Rupees can be found by slaying monsters")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
