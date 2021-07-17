package com.superworldsun.superslegend.registries;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.masks.MaskMoonmask;
import com.superworldsun.superslegend.util.CustomArmorMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ArmorInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            SupersLegendMain.MOD_ID);


    public static final RegistryObject<Item> MASK_MOONMASK = ITEMS.register("mask_moonmask", () -> new MaskMoonmask(CustomArmorMaterial.MASK, EquipmentSlotType.HEAD, 4));


    //armor													boots, legs, chest, helm
    //KOKIRI		("kokiri", 0, new int[] 		{1, 3, 5, 1}, 0, null, "item.armor.equip_leather", 0.0f);

    /*private static final int[] max_damage_array = new int[]{13, 15, 16, 11};
    private String name, equipSound;
    private int durability, enchantability;
    private Item repairItem;
    private int[] damageReductionAmounts;
    private float toughness;

    private ArmorInit(String name, int durability, int[] damageReductionAmounts, int enchantability, Item repairItem, String equipSound, float toughness)
    {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
        this.damageReductionAmounts = damageReductionAmounts;
        this.toughness = toughness;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slot)
    {
        return this.damageReductionAmounts[slot.getIndex()];
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slot)
    {
        return max_damage_array[slot.getIndex()] * this.durability;
    }

    @Override
    public int getEnchantmentValue()
    {
        return this.enchantability;
    }

    @Override
    public String getName()
    {
        return SupersLegendMain.MOD_ID + ":" + this.name;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return Ingredient.of(this.repairItem);
    }

    @Override
    public SoundEvent getEquipSound()
    {
        return new SoundEvent(new ResourceLocation(equipSound));
    }

    @Override
    public float getToughness()
    {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }*/

}
