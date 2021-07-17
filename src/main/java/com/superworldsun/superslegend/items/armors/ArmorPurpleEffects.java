package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;

public class ArmorPurpleEffects extends NonEnchantArmor
{


    public ArmorPurpleEffects(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "A rarer outfit not often seen"));
	}
}