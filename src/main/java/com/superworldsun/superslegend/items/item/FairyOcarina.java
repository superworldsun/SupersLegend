package com.superworldsun.superslegend.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import java.util.List;

public class FairyOcarina extends Item
{
    public FairyOcarina(Properties properties)
    {
        super(properties);
    }

    //TODO Restrict the songs the fairy ocarina can play and have effects so not identical to Ocarina of Time

    //TODO When the player plays any notes from the ocarina, make it so other players can hear the notes

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> showOcarinaScreen());
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @OnlyIn(value = Dist.CLIENT)
    private void showOcarinaScreen()
    {
        Minecraft client = Minecraft.getInstance();
        //TODO, re add OcarinaScreen
        //client.setScreen(new OcarinaScreen());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("A standard Ocarina").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}