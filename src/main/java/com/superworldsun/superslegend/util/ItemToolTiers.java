package com.superworldsun.superslegend.util;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum ItemToolTiers implements IItemTier {
    // Harvest-level, durability, efficiency, attackdamage, enchantability, repair material
    LANZANITE(4, 3031, 9.0F, 5.0F, 15, () -> {
        return Ingredient.of(ItemInit.LANZANITE.get());
    });

    //Private values to store
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    //This sets and/or gets the values we specific for the material
    private ItemToolTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
                          Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    //Int and float methods, self-explanatory
    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}