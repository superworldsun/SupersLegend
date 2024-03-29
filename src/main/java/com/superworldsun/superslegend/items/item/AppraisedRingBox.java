package com.superworldsun.superslegend.items.item;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.registries.TagInit;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient.TagValue;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class AppraisedRingBox extends Item {
    public AppraisedRingBox(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Collection<ItemStack> drops = new TagValue(TagInit.APPRAISAL_LIST).getItems();
        ItemStack randomItem = drops.stream().skip(player.getRandom().nextInt(drops.size())).findFirst().get();
        addOrDrop(player, randomItem);
        player.playSound(SoundEvents.ITEM_PICKUP, 1.0f, 1.0f);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.success(stack);
    }

    private void addOrDrop(Player player, ItemStack stack) {
        if (!player.addItem(stack)) {
            player.spawnAtLocation(stack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Contains an Appraised Ring").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            tooltip.add(Component.literal("Right-click to receive a random appraised ring")
                    .withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
            tooltip.add(Component.literal("an Appraised Ring Box can be traded from Tool Smiths and Wandering Traders")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
