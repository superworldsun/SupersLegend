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

public class MaskGarosmask extends NonEnchantArmor {

    public MaskGarosmask(Properties properties)
    {
        super(ArmourInit.garosmask, EquipmentSlotType.HEAD, properties);
    }

    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.WHITE + "A mask connected to the dead"));
	}
}