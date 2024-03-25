package com.superworldsun.superslegend.items.weapons.swords;

import com.superworldsun.superslegend.entities.projectiles.magic.MasterSwordBeamEntity;
import com.superworldsun.superslegend.items.customclass.ItemCustomSword;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MasterSword extends ItemCustomSword {
    public MasterSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
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
            } else {
                player.displayClientMessage(Component.literal(ChatFormatting.RED + "Try again with full health!"), true);
            }
        }

        return InteractionResultHolder.pass(itemstack);
    }
}