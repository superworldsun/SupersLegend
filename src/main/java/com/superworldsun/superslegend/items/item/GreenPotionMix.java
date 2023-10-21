package com.superworldsun.superslegend.items.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
}
