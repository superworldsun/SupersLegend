package com.superworldsun.superslegend.items.masks;

import java.util.List;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class MaskKamarosmask extends NonEnchantArmor {

    public MaskKamarosmask(Properties properties)
    {
        super(ArmourInit.kamarosmask, EquipmentSlotType.HEAD, properties);
    }

    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "You may get the urge to dance"));
	}
}