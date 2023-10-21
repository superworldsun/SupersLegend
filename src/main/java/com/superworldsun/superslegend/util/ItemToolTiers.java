package com.superworldsun.superslegend.util;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ItemToolTiers implements Tier {
    // Harvest-level, durability, efficiency, attackdamage, enchantability, repair material

    //WEAPONS
    KOKIRI_SWORD     (0, 0, -2F, 2F, 0, () -> { return Ingredient.EMPTY; }),
    RAZOR_SWORD      (0, 100, -2F, 3F, 0, () -> { return Ingredient.EMPTY; }),
    GILDED_SWORD     (0, 0, -2F, 4F, 0, () -> { return Ingredient.EMPTY; }),
    GREAT_FAIRYS_SWORD(0, 0, -2F, 4F, 0, () -> { return Ingredient.EMPTY; }),

    GIANTS_KNIFE     (0, 8, -2F, 14F, 0, () -> { return Ingredient.EMPTY; }),
    BROKEN_GIANTS_KNIFE(0, 0, -2F, 3F, 0, () -> { return Ingredient.EMPTY; }),
    BIGGORONS_SWORD  (0, 0, -2F, 14F, 0, () -> { return Ingredient.EMPTY; }),
    GUARDIAN_SWORD   (0, 0, -2F, 5F, 0, () -> { return Ingredient.EMPTY; }),
    ANCIENT_BATTLE_AXE   (0, 0, -2F, 5F, 0, () -> { return Ingredient.EMPTY; }),
    GUARDIAN_SPEAR   (0, 0, -2F, 5F, 0, () -> { return Ingredient.EMPTY; }),
    MAGIC_HAMMER     (0, 0, -2F, 4F, 0, () -> { return Ingredient.EMPTY; }),
    MEGATON_HAMMER   (0, 0, -2F, 8F, 0, () -> { return Ingredient.EMPTY; }),
    SKULL_HAMMER     (0, 0, -2F, 11F, 0, () -> { return Ingredient.EMPTY; }),

    //MASTER SWORDS
    GODDESS_SWORD     (0, 0, -2F, 3F, 0, () -> { return Ingredient.EMPTY; }),
    GODDESS_LONGSWORD     (0, 0, -2F, 3F, 0, () -> { return Ingredient.EMPTY; }),
    GODDESS_WHITE_SWORD     (0, 0, -2F, 4F, 0, () -> { return Ingredient.EMPTY; }),

    MASTER_SWORD     (0, 0, -2F, 5F, 0, () -> { return Ingredient.EMPTY; }),
    MASTER_SWORD_V2  (0, 0, -2F, 8F, 0, () -> { return Ingredient.EMPTY; }),
    TRUE_MASTER_SWORD(0, 0, -2F, 11F, 0, () -> { return Ingredient.EMPTY; });

    //Private values to store
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;

    //This sets and/or gets the values we specific for the material
	ItemToolTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
				  Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
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
        return null;
    }
}