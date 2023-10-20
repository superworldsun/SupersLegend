package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BugNet extends Item {

    public BugNet(Properties pProperties) {
        super(pProperties);
    }

    //TODO make it so when you capture mobs, their data is saved (health, anger, etc), maybe look at Supplementaries for an example.
    // https://github.com/MehVahdJukaar/Supplementaries/blob/master/common/src/main/java/net/mehvahdjukaar/supplementaries/common/items/AbstractMobContainerItem.java#L46

    //TODO, This dosent seem to do anything, i would like this to work on right click.
    public InteractionResult doInteract(ItemStack stack, Player player, Entity entity, InteractionHand hand) {
        if (hand == null) {
            return InteractionResult.PASS;
        }
        player.swing(hand);
        if(entity instanceof Bee)
        {
            {
                for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack.getItem() == Items.GLASS_BOTTLE) {
                        itemStack.shrink(1);

                        entity.remove(Entity.RemovalReason.DISCARDED);
                        player.addItem(new ItemStack(ItemInit.BOTTLED_BEE.get()));
                        break;
                    }
                }
            }
        }
        if(entity instanceof Silverfish)
        {
            {
                for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack.getItem() == Items.GLASS_BOTTLE) {
                        itemStack.shrink(1);

                        entity.remove(Entity.RemovalReason.DISCARDED);
                        player.addItem(new ItemStack(ItemInit.BOTTLED_SILVERFISH.get()));
                        break;
                    }
                }
            }
        }
        if(entity instanceof Endermite)
        {
            {
                for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack.getItem() == Items.GLASS_BOTTLE) {
                        itemStack.shrink(1);

                        entity.remove(Entity.RemovalReason.DISCARDED);
                        player.addItem(new ItemStack(ItemInit.BOTTLED_ENDERMITE.get()));
                        break;
                    }
                }
            }
        }
        player.getCooldowns().addCooldown(this, 18);
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("Bottles small critters on right click").withStyle(ChatFormatting.WHITE));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
