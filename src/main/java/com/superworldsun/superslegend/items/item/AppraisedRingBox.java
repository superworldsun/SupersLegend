package com.superworldsun.superslegend.items.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.tags.ITag;

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
}
