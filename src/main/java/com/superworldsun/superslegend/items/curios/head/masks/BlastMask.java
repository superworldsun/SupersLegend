package com.superworldsun.superslegend.items.curios.head.masks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class BlastMask extends Item implements ICurioItem {
    public BlastMask(Properties pProperties) {
        super(pProperties);
    }


    /*public void startUsingAbility(Player player)
    {
        // Do nothing if on cooldown
        if (player.getCooldowns().isOnCooldown(ItemInit.MASK_BLASTMASK.get()))
        {
            return;
        }

        Vec3 explosionPos = player.getEyePosition(1.0F).add(player.getLookAngle().multiply(0.5D, 0.5D, 0.5D));
        player.level.explode(player, explosionPos.x, explosionPos.y, explosionPos.z, 2.0F, Mode.BREAK);
        // 200 ticks are 10 seconds
        player.getCooldowns().addCooldown(ItemInit.MASK_BLASTMASK.get(), 200);

        if (!player.isBlocking())
        {
            player.hurt(DamageSource.explosion(player), 2.0F);
        }
        else if (player.isBlocking())
        {
            int shieldDamage = 1;
            player.getUseItem().hurtAndBreak(shieldDamage, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
            player.playSound(SoundEvents.SHIELD_BLOCK, 1F, 1F);
        }
    }*/

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("Bomb Blastic").withStyle(ChatFormatting.GRAY));
        //tooltip.add(Component.literal("Press '" + keybind + "' to Explode")).withStyle(ChatFormatting.GREEN));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
