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

public class GuardianSword extends ItemCustomSword {
    public GuardianSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
