package com.superworldsun.superslegend.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.tags.ITag;

import javax.annotation.Nullable;
import java.util.List;

public class AppraisedRingBox extends Item {
    public AppraisedRingBox(Properties pProperties) {
        super(pProperties);
    }

    //TODO, not sure how to replace INamedTag, no longer in ITag
    /*public static final ITag.INamedTag<Item> itempool =
            ItemTags.bind("superslegend:appraisal_list");

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        addOrDrop(player, itempool.getRandomElement(level.random));

        if (!player.getAbilities().instabuild)
        {
            stack.shrink(1);
            player.playSound(SoundEvents.ITEM_PICKUP, 1.0f, 1.0f);
        }
        return InteractionResultHolder.success(stack);
    }

    private void addOrDrop(Player player, Item itemSupplier)
    {
        if (!player.addItem(new ItemStack(itemSupplier)))
        {
            player.spawnAtLocation(itemSupplier);
        }
    }*/

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Contains an Appraised Ring").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Right-click to receive a random appraised ring").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
            tooltip.add(Component.literal("an Appraised Ring Box can be traded from Tool Smiths and Wandering Traders").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
