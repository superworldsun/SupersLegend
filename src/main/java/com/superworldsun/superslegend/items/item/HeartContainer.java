package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.events.MaxHealthEvents;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class HeartContainer extends Item {
    public static final UUID HEARTS_MODIFIER_ID = UUID.fromString("3dc4214d-14eb-455c-9700-a2ab1433dfcc");
    public HeartContainer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        AttributeModifier heartsModifier = player.getAttribute(Attributes.MAX_HEALTH).getModifier(HEARTS_MODIFIER_ID);
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
                heartsModifier = new AttributeModifier(HEARTS_MODIFIER_ID, "Hearts", 2.0D, AttributeModifier.Operation.ADDITION);
            }
            else
            {
                player.getAttribute(Attributes.MAX_HEALTH).removeModifier(heartsModifier);
                heartsModifier = new AttributeModifier(HEARTS_MODIFIER_ID, "Hearts", heartsModifier.getAmount() + 2.0D, AttributeModifier.Operation.ADDITION);
            }

            BlockPos currentPos = player.blockPosition();
            //level.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.HEART.get(), SoundCategory.PLAYERS, 1f, 1f);
            itemstack.shrink(1);
            // Add 2 health (1 heart)
            player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(heartsModifier);
            // Also restore 2 health
            player.heal(2.0F);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event)
    {
        // We are copying health modifier only on respawn
        if (!event.isWasDeath())
        {
            return;
        }

        AttributeModifier heartsModifier = event.getOriginal().getAttribute(Attributes.MAX_HEALTH).getModifier(HEARTS_MODIFIER_ID);

        if (heartsModifier != null)
        {
            event.getEntity().getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(heartsModifier);
            // Also update current health
            event.getEntity().setHealth(event.getEntity().getMaxHealth());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Increases Maximum Health").withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal("Right-click to use").withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
