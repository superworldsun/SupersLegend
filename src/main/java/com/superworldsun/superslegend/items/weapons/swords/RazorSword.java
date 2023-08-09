package com.superworldsun.superslegend.items.weapons.swords;

import com.superworldsun.superslegend.items.customclass.ItemCustomSword;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.ItemToolTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RazorSword extends ItemCustomSword {
    public RazorSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public boolean hurtEnemy(ItemStack itemStack, LivingEntity attacked, LivingEntity attacker) {
        if(this.getTier() == ItemToolTiers.KOKIRI_SWORD) {
            return false;
        }

        if(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1) {
            super.hurtEnemy(itemStack, attacked, attacker);
        }

        else {
            ItemStack	newItemStack = new ItemStack(ItemInit.KOKIRI_SWORD.get());
            newItemStack.setDamageValue(newItemStack.getMaxDamage());
            attacker.setItemSlot(EquipmentSlot.MAINHAND, newItemStack);
            attacker.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockstate, BlockPos pos, LivingEntity entity) {
        if(this.getTier() == ItemToolTiers.KOKIRI_SWORD) {
            return false;
        }

        if(itemStack.getDamageValue() < itemStack.getMaxDamage() - 2) {
            super.mineBlock(itemStack, level, blockstate, pos, entity);
        }
        else {
            ItemStack	newItemStack = new ItemStack(ItemInit.KOKIRI_SWORD.get());
            newItemStack.setDamageValue(newItemStack.getMaxDamage());
            entity.setItemSlot(EquipmentSlot.MAINHAND, newItemStack);
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }
        return true;
    }
}
