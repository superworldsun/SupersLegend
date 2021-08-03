package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
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

public class ArmorDarkEffects extends NonEnchantArmor
{


    public ArmorDarkEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.dark, slot, properties);
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
        		boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.DARK_CAP);
                boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(ItemInit.DARK_TUNIC);
                boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().equals(ItemInit.DARK_LEGGINGS);
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.DARK_BOOTS);
                if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn&&!world.isDay())
                	{
                		player.addEffect(new EffectInstance(Effect.byId(1), 10, 1, false, false, false));
                	}
                	
                }
    }
}