package com.superworldsun.superslegend.items.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class RedPotion extends Item {
    public RedPotion(Properties pProperties) {
        super(pProperties);
    }

    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        Player player = entity instanceof Player ? (Player) entity : null;

        if (player instanceof ServerPlayerEntity)
        {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
        }

        if (player != null)
        {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 230, 0, false, false));
            player.awardStat(Stats.ITEM_USED.get(this));

            if (!player.abilities.instabuild)
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
    }*/
}
