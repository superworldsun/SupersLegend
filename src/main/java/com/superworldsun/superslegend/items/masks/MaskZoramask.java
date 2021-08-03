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
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class MaskZoramask extends NonEnchantArmor {

    public MaskZoramask(Properties properties)
    {
        super(ArmourInit.zoramask, EquipmentSlotType.HEAD, properties);
    }

    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "The face of a Zora"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "You can swim with the grace of a Zora"));
	}
    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isClientSide){
                boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.MASK_ZORAMASK);
                if(isHelmetOn)
                	{
                	if(player.isInWater() && player.isSprinting() && !player.isOnGround() && player.getFoodData().getFoodLevel()!= 0)
                	{
                		player.addEffect(new EffectInstance(Effect.byId(30), 4, 0, false, false, false));
                		player.causeFoodExhaustion(0.09f);
                	}
                	
                }
        }
    }
        
    
    
}