package superworldsun.superslegend.items.armors;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


import net.minecraft.item.Item.Properties;

public class ArmorDarkEffects extends NonEnchantArmor
{
    public ArmorDarkEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.dark, slot, new Properties().tab(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
        
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLACK + "Armor of darkness"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Grants a boost of speed during night"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isClientSide){
        		boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.dark_cap);
                boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.dark_tunic);
                boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().equals(ItemList.dark_leggings);
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemList.dark_boots);
                if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn&&!world.isDay())
                	{
                		player.addEffect(new EffectInstance(Effect.byId(1), 10, 1, false, false, false));
                	}
                	
                }
    }
}