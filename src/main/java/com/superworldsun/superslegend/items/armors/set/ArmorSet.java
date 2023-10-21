package com.superworldsun.superslegend.items.armors.set;

import com.superworldsun.superslegend.SupersLegendMain;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public class ArmorSet implements ArmorMaterial {
	private int[] durabilityForType = {0, 0, 0, 0};
	private final int[] defenseForType;
	private int enchantability = 0;
	private SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_LEATHER;
	private final Supplier<Ingredient> repairMaterial;
	private final String name;
	private float toughness;
	private float knockbackResistance;

	public ArmorSet(String name, int helmetDefence, int chestplateDefence, int leggingsDefence, int bootsDefence, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.defenseForType = new int[]{helmetDefence, chestplateDefence, leggingsDefence, bootsDefence};
		this.repairMaterial = repairMaterial;
	}

	public void setEquipSound(SoundEvent equipSound) {
		this.equipSound = equipSound;
	}

	public void setEnchantability(int enchantability) {
		this.enchantability = enchantability;
	}

	public void setKnockbackResistance(float knockbackResistance) {
		this.knockbackResistance = knockbackResistance;
	}

	public void setToughness(float toughness) {
		this.toughness = toughness;
	}

	public void setDurabilityForType(int helmetDurability, int chestplateDurability, int leggingsDurability, int bootsDurability) {
		this.durabilityForType = new int[]{helmetDurability, chestplateDurability, leggingsDurability, bootsDurability};
	}

	@Override
	public int getDurabilityForType(ArmorItem.Type type) {
		return this.durabilityForType[type.ordinal()];
	}

	@Override
	public int getDefenseForType(ArmorItem.Type type) {
		return this.defenseForType[type.ordinal()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public @NotNull SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public @NotNull Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}

	@Override
	public @NotNull String getName() {
		return SupersLegendMain.MOD_ID + ":" + this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
