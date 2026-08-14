package com.superworldsun.superslegend.items.weapons.bow;

import com.superworldsun.superslegend.items.customclass.ItemCustomBow;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.BitBowWalletSyncMessage;
import com.superworldsun.superslegend.registries.SoundInit;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BitBow extends ItemCustomBow
{

    public BitBow(Properties properties)
    {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && RupeeWalletUtil.findEquippedWallet(player).isPresent()) {
            boolean paid = player.isCreative() || RupeeWalletUtil.trySpendExact(player, 1);
            if (paid) {
                if (!player.isCreative() && player instanceof ServerPlayer serverPlayer) {
                    NetworkDispatcher.network_channel.send(
                            PacketDistributor.PLAYER.with(() -> serverPlayer),
                            new BitBowWalletSyncMessage(RupeeWalletUtil.getStoredRupees(player)));
                }
                fireArrow(level, player);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, stack);
    }

    private void fireArrow(Level level, Player player) {
        player.getCooldowns().addCooldown(this, 15);
        level.playSound(null, player.blockPosition(), SoundInit.BITBOW_ARROW.get(),
                SoundSource.PLAYERS, 3.0F, 1.0F);

        ArrowItem arrowItem = (ArrowItem) Items.ARROW;
        AbstractArrow arrow = arrowItem.createArrow(level, new ItemStack(Items.ARROW), player);
        arrow.shootFromRotation(player, player.xRotO, player.yRotO, 0.0F, 3.0F, 1.0F);
        arrow.setBaseDamage(1);
        arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        level.addFreshEntity(arrow);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("A relic with pixel perfect accuracy").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Uses 1 Rupee from an equipped Wallet as ammo").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Bow fires instantly at a set strength and has a short cool down between shots").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
