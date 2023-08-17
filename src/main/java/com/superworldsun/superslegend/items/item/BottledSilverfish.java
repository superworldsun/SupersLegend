package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BottledSilverfish extends Item
{
    public BottledSilverfish(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if(!level.isClientSide && !player.isCreative())
        {
            player.playSound(SoundInit.BOTTLE_POP.get(), 1f, 1f);

            if (!player.level().isClientSide)
            {
                Silverfish entity = EntityType.SILVERFISH.create(player.level());
                entity.moveTo(player.getX(), player.getY(), player.getZ(), player.yRotO, 0.0F);
                player.level().addFreshEntity(entity);
                player.getCooldowns().addCooldown(this, 6);
            }
            stack.shrink(1);
            player.addItem(new ItemStack(Items.GLASS_BOTTLE));
        }

        if(!level.isClientSide && player.isCreative())
        {
            player.playSound(SoundInit.BOTTLE_POP.get(), 1f, 1f);

            if (!player.level().isClientSide)
            {
                Silverfish entity = EntityType.SILVERFISH.create(player.level());
                entity.moveTo(player.getX(), player.getY(), player.getZ(), player.yRotO, 0.0F);
                player.level().addFreshEntity(entity);
                player.getCooldowns().addCooldown(this, 6);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }
}