package com.superworldsun.superslegend.items.material;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

// Material for all the masks. No defence, no durability.
public class MaskMaterial implements IArmorMaterial
{
	// If you will have more materials, better merge all the instances in one
	// class, that will be handier
	public static final MaskMaterial INSTANCE = new MaskMaterial();
	
	@Override
	public int getDurabilityForSlot(EquipmentSlotType slot)
	{
		// Unbrakeable
		return 0;
	}
	
	@Override
	public int getDefenseForSlot(EquipmentSlotType slot)
	{
		// No defence
		return 0;
	}
	
	@Override
	public int getEnchantmentValue()
	{
		return 0;
	}
	
	@Override
	public SoundEvent getEquipSound()
	{
		return SoundEvents.ARMOR_EQUIP_LEATHER;
	}
	
	@Override
	public Ingredient getRepairIngredient()
	{
		return Ingredient.EMPTY;
	}
	
	@Override
	public String getName()
	{
		return "mask";
	}
	
	@Override
	public float getToughness()
	{
		return 0;
	}
	
	@Override
	public float getKnockbackResistance()
	{
		return 0;
	}
}
