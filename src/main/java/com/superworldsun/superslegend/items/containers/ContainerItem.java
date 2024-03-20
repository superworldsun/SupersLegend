package com.superworldsun.superslegend.items.containers;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.EmptyByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class ContainerItem extends Item {
    public ContainerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!level.isClientSide) {
            NetworkHooks.openScreen((ServerPlayer) player, createMenuProvider(player, hand), buf -> buf.writeEnum(hand));
            level.playSound(null, player, SoundEvents.ARMOR_EQUIP_ELYTRA, SoundSource.PLAYERS, 1f, 1f);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    protected MenuProvider createMenuProvider(@NotNull Player player, @NotNull InteractionHand hand) {
        return new MenuProvider() {
            @Override
            public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory inventory, @NotNull Player player) {
                FriendlyByteBuf buf = new FriendlyByteBuf(new EmptyByteBuf(ByteBufAllocator.DEFAULT));
                return getMenuType().create(windowId, inventory, buf.writeEnum(hand));
            }

            @Override
            public @NotNull Component getDisplayName() {
                return player.getItemInHand(hand).getHoverName();
            }
        };
    }

    protected abstract MenuType<?> getMenuType();

    public abstract boolean canContainItem(ItemStack stack);
}
