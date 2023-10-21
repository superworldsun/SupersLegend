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

public class BluePotionMix extends Item {
    public BluePotionMix(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if(!level.isClientSide &&  !player.isCreative() && player.getFoodData().needsFood() && player.isHurt())
        {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 1f, 1f);

            player.setHealth(player.getHealth() + 1.0F);
            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 1);
            stack.shrink(1);
            player.addItem(new ItemStack(Items.GLASS_BOTTLE));
        }
        if(!level.isClientSide && !player.isCreative() && !player.getFoodData().needsFood() && player.isHurt())
        {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 1f, 1f);

            player.setHealth(player.getHealth() + 1.0F);
            stack.shrink(1);
            player.addItem(new ItemStack(Items.GLASS_BOTTLE));
        }
        if(!level.isClientSide && !player.isCreative() && player.getFoodData().needsFood() && !player.isHurt())
        {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 1f, 1f);

            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 1);
            stack.shrink(1);
            player.addItem(new ItemStack(Items.GLASS_BOTTLE));
        }



        if(player.isCreative() && player.getFoodData().needsFood())
        {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 1f, 1f);

            player.setHealth(player.getHealth() + 1.0F);
            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 1);
        }
        if(player.isCreative() && !player.getFoodData().needsFood())
        {
            BlockPos currentPos = player.blockPosition();
            level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 1f, 1f);

            player.setHealth(player.getHealth() + 1.0F);
        }

        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, player.getItemInHand(hand));
    }
}
