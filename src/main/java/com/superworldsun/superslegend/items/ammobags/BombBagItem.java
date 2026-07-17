package com.superworldsun.superslegend.items.ammobags;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.List;

public class BombBagItem extends AmmoContainerItem {
    public BombBagItem(int capacity) {
        super(capacity);
    }

    //TODO When the players have water bombs in their bags i would like it consume 2x the capacity
    @Override
    public boolean canHoldItem(ItemStack itemStack) {
        return itemStack.getItem() == ItemInit.BOMB.get() || itemStack.getItem() == ItemInit.WATER_BOMB.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        Pair<ItemStack, Integer> contents = getContents(stack);

        if (contents != null) {
            tooltip.add(Component.literal(contents.getRight().toString()).withStyle(ChatFormatting.AQUA));
            tooltip.add(Component.literal(contents.getLeft().getHoverName().getString()).withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.literal("Right click to get bombs.").withStyle(ChatFormatting.YELLOW));
        }
    }
}
