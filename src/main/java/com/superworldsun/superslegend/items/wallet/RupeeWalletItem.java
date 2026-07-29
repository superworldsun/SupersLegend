package com.superworldsun.superslegend.items.wallet;

import com.superworldsun.superslegend.client.screen.RupeeWalletScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
import java.util.List;

public class RupeeWalletItem extends Item implements ICurioItem {
    private static final String RUPEE_COUNT_TAG = "Rupees";

    private final int capacity;

    public RupeeWalletItem(int capacity) {
        super(new Item.Properties().stacksTo(1));
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStoredRupees(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        return Math.min(capacity, Math.max(0, stack.getOrCreateTag().getInt(RUPEE_COUNT_TAG)));
    }

    public void setStoredRupees(ItemStack stack, int amount) {
        stack.getOrCreateTag().putInt(RUPEE_COUNT_TAG, Math.min(capacity, Math.max(0, amount)));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
                                                            @NotNull InteractionHand hand) {
        if (level.isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> openWalletScreen(hand));
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    private static void openWalletScreen(InteractionHand hand) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> minecraft.setScreen(new RupeeWalletScreen(hand)));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return "wallet".equals(slotContext.identifier());
    }

    @Override
    public boolean canSync(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public net.minecraft.nbt.CompoundTag writeSyncData(SlotContext slotContext, ItemStack stack) {
        return stack.getOrCreateTag().copy();
    }

    @Override
    public void readSyncData(SlotContext slotContext, net.minecraft.nbt.CompoundTag tag, ItemStack stack) {
        stack.setTag(tag.copy());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.superslegend.wallet.amount",
                        getStoredRupees(stack), capacity)
                .withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
