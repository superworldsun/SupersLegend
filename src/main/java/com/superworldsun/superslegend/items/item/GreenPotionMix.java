package com.superworldsun.superslegend.items.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class GreenPotionMix extends Item {
    public GreenPotionMix(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if(!level.isClientSide && player.getFoodData().needsFood() && !player.isCreative())
        {

            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 1f, 1f);

            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 1);
            stack.shrink(1);
            player.addItem(new ItemStack(Items.GLASS_BOTTLE));
        }
        else if(player.isCreative() && player.getFoodData().needsFood())
        {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 1f, 1f);

            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 1);
        }

        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        {
            tooltip.add(Component.literal("This could probably be cooked").withStyle(ChatFormatting.DARK_GREEN));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
