package com.superworldsun.superslegend.items.weapons.bow;

import com.superworldsun.superslegend.items.customclass.ItemCustomBow;
import com.superworldsun.superslegend.registries.*;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import net.minecraftforge.api.distmarker.*;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BitBow extends ItemCustomBow {

    public BitBow(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && !player.isCreative() && player.getInventory().contains(new ItemStack(ItemInit.RUPEE.get())))
        {
            player.getCooldowns().addCooldown(this, 15);
            player.playSound(SoundInit.BITBOW_ARROW.get(), 3.0f, 1.0f);

            ArrowItem itemarrow = (ArrowItem) Items.ARROW;
            AbstractArrow entityarrow = itemarrow.createArrow(level, new ItemStack(Items.ARROW), player);
            float arrowVelocity = 3.0F;
            entityarrow.shootFromRotation(player, player.xRotO, player.yRotO, 0.0F, arrowVelocity, 1.0F);
            entityarrow.setBaseDamage(1);
            level.addFreshEntity(entityarrow);
            entityarrow.pickup = AbstractArrow.Pickup.DISALLOWED;

            for (int i = 0; i < player.getInventory().getContainerSize(); ++i)
            {
                ItemStack stackslot = player.getInventory().getItem(i);
                if (stackslot.getItem() == ItemInit.RUPEE.get())
                {
                    stackslot.shrink(1);
                    break;
                }
            }
        }
        else if (!level.isClientSide && player.isCreative()) {
            player.getCooldowns().addCooldown(this, 15);
            player.playSound(SoundInit.BITBOW_ARROW.get(), 3.0f, 1.0f);

            ArrowItem itemarrow = (ArrowItem)Items.ARROW;
            AbstractArrow entityarrow = itemarrow.createArrow(level, new ItemStack(Items.ARROW), player);
            float arrowVelocity = 3.0F;
            entityarrow.shootFromRotation(player, player.xRotO, player.yRotO, 0.0F, arrowVelocity, 1.0F);
            entityarrow.setBaseDamage(1);
            level.addFreshEntity(entityarrow);
            entityarrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("A relic with pixel perfect accuracy").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Uses Green Rupee as ammo").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Bow fires instantly at a set strength and has a short cool down between shots").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}