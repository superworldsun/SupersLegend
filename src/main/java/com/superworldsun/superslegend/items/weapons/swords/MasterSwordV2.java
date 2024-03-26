package com.superworldsun.superslegend.items.weapons.swords;

import com.superworldsun.superslegend.entities.projectiles.magic.MasterSwordBeamEntity;
import com.superworldsun.superslegend.items.customclass.ItemCustomSword;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class MasterSwordV2 extends ItemCustomSword {
    public MasterSwordV2(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isCrouching() && !player.getCooldowns().isOnCooldown(this)) {
            player.swing(hand);
            player.getCooldowns().addCooldown(this, 20);
            if (player.getHealth() >= player.getMaxHealth() || player.isCreative()) {
                if (!level.isClientSide) {
                    MasterSwordBeamEntity swordBeam = new MasterSwordBeamEntity(EntityTypeInit.MASTERSWORD_SWORD_BEAM.get(), level, player);
                    level.addFreshEntity(swordBeam);
                }
            }
            else
            {

            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.literal("A Tempered Blade of Evil's Bane").withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.literal("[Hold Shift for Info]").withStyle(ChatFormatting.DARK_GRAY));
        }
        else if(Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Sneak+Right-Click to Fire a Beam of energy when at full HP").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal("The Beam does 2x more damage on undead").withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}