package com.superworldsun.superslegend.items.ammobags;

import com.superworldsun.superslegend.registries.TagInit;
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

public class BulletBagItem extends AmmoContainerItem {
    public BulletBagItem(int capacity) {
        super(capacity);
    }

    @Override
    public boolean canHoldItem(ItemStack itemStack) {
        return itemStack.is(TagInit.PELLETS);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        Pair<ItemStack, Integer> contents = getContents(stack);

        if (contents != null) {
            tooltip.add(Component.literal(contents.getRight().toString()).withStyle(ChatFormatting.AQUA));
            tooltip.add(Component.literal(contents.getLeft().getHoverName().getString()).withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.literal("Right click to get pellets.").withStyle(ChatFormatting.YELLOW));
        }
    }
}
