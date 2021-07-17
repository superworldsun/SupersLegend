package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
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

public class ArmorFlamebreakerEffects extends NonEnchantArmor
{


    public ArmorFlamebreakerEffects(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Armor of the Gorons"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Wearing full set grants fire restiance"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isClientSide){
        		boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.FLAMEBREAKER_HELMET);
                boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(ItemInit.FLAMEBREAKER_TUNIC);
                boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().equals(ItemInit.FLAMEBREAKER_LEGGINGS);
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.FLAMEBREAKER_BOOTS);
                if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
                	{
                		player.addEffect(new EffectInstance(Effect.byId(12), 10, 0, false, false, false));
                		player.clearFire();
                	}
                	
                }
    }
}