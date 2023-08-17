package com.superworldsun.superslegend.items.weapons.bow;

import com.superworldsun.superslegend.items.customclass.ItemCustomBow;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BitBow extends ItemCustomBow
{

    public BitBow(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
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
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Uses Green Rupee as ammo").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}