package com.superworldsun.superslegend.items.masks;

import java.util.List;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MaskGoronmask extends NonEnchantArmor {


    public MaskGoronmask(Properties properties)
    {
        super(ArmourInit.goronmask, EquipmentSlotType.HEAD, properties);
    }

    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_RED + "The face of a Goron"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "Your skin is like stone and cannot stay withered"));
	}
    
    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isClientSide){
                boolean isHelmeton = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.MASK_GORONMASK);
                if(isHelmeton) 
                	
                	if(player.getEffect(Effects.WITHER) != null)
                	{
                	
                	player.removeEffect(Effect.byId(20));
                	
                	}
                	else	
                	{
                		player.removeEffect(Effect.byId(20));
                	}
                
                }
            }
        
    
    
}