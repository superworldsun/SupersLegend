package superworldsun.superslegend.items.masks;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;


import net.minecraft.item.Item.Properties;

public class MaskGreatfairymask extends NonEnchantArmor {
    public MaskGreatfairymask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.greatfairymask, slot, new Properties().tab(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "The face of a fairy"));
	}
}