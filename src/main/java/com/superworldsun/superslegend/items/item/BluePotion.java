package com.superworldsun.superslegend.items.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BluePotion extends Item {
    public BluePotion(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack)
    {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack)
    {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        //TODO, Undo when Magic is re added
        /*if (ManaHelper.isFullMana(player) && !player.isHurt())
        {
            return InteractionResultHolder.fail(stack);
        }*/

        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity)
    {
        Player player = entity instanceof Player ? (Player) entity : null;

        if (player instanceof Player)
        {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
        }

        if (player != null)
        {
            //TODO, Undo when Magic is re added
            //ManaHelper.restoreManaToFull(player);
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 4, false, false));
            player.awardStat(Stats.ITEM_USED.get(this));

            if (!player.getAbilities().instabuild)
            {
                stack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild)
        {
            if (stack.isEmpty())
            {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (player != null)
            {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }
}
