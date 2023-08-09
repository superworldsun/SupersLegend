package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class GoronSet extends Item
{
    public GoronSet(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

            ItemStack stack = player.getItemInHand(hand);

            addOrDrop(player, ItemInit.GORON_CAP);
            addOrDrop(player, ItemInit.GORON_TUNIC);
            addOrDrop(player, ItemInit.GORON_LEGGINGS);

            if (!player.getAbilities().instabuild)
            {
                stack.shrink(1);
            }

            return InteractionResultHolder.success(stack);
        }

        private void addOrDrop (Player player, RegistryObject< Item > itemSupplier)
        {
            if (!player.addItem(new ItemStack(itemSupplier.get()))) {
                player.spawnAtLocation(itemSupplier.get());
            }
        }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Goron set").withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}