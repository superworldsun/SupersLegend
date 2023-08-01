package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.events.MaxHealthEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.UUID;

public class VoidContainer extends Item {

    public VoidContainer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        AttributeModifier heartsModifier = player.getAttribute(Attributes.MAX_HEALTH).getModifier(HeartContainer.HEARTS_MODIFIER_ID);
        AttributeModifier baseModifier = player.getAttribute(Attributes.MAX_HEALTH).getModifier(MaxHealthEvents.BASE_HEALTH_MODIFIER_ID);
        double playerBaseHealth = player.getAttribute(Attributes.MAX_HEALTH).getBaseValue();

        // Can't use if already have 40 or more maximum health (base + hearts, bonuses from other mods don't count)
        if (heartsModifier != null && playerBaseHealth + baseModifier.getAmount() + heartsModifier.getAmount() >= 40)
        {
            return InteractionResultHolder.fail(itemstack);
        }
        else
        {
            if (heartsModifier == null)
            {
                heartsModifier = new AttributeModifier(HeartContainer.HEARTS_MODIFIER_ID, "Hearts", -2.0D, AttributeModifier.Operation.ADDITION);
            }
            else
            {
                player.getAttribute(Attributes.MAX_HEALTH).removeModifier(heartsModifier);
                heartsModifier = new AttributeModifier(HeartContainer.HEARTS_MODIFIER_ID, "Hearts", heartsModifier.getAmount() - 2.0D, AttributeModifier.Operation.ADDITION);
            }

            BlockPos currentPos = player.blockPosition();
            //level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.HEART.get(), SoundCategory.PLAYERS, 1f, 1f);
            itemstack.shrink(1);
            // Add 2 health (1 heart)
            player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(heartsModifier);
            // Also restore 2 health
            if (player.getHealth() > player.getMaxHealth())
            {
                player.setHealth(player.getMaxHealth());
            }
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Decreases Maximum Health").withStyle(ChatFormatting.BLACK));
        tooltip.add(Component.literal("Right-click to use").withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
