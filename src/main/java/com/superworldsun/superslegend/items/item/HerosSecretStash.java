package com.superworldsun.superslegend.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class HerosSecretStash extends Item {
    public HerosSecretStash(Properties pProperties) {
        super(pProperties);
    }
    private static final Component CONTAINER_TITLE = Component.translatable("container.enderchest");

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand)
    {

        PlayerEnderChestContainer enderChest = player.getEnderChestInventory();
        PlayerEnderChestContainer playerenderchestcontainer = player.getEnderChestInventory();

        if (enderChest != null)
        {
            if (!level.isClientSide)
            {
                BlockPos currentPos = player.blockPosition();
                level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENDER_CHEST_OPEN, SoundSource.PLAYERS, 1f, 1f);

                player.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
                    return ChestMenu.threeRows(p_53124_, p_53125_, playerenderchestcontainer);
                }, CONTAINER_TITLE));
            }
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("A safe place for things on the go").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Right+Click to open up your ender chest").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
