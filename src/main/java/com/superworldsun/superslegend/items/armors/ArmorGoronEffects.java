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

public class ArmorGoronEffects extends NonEnchantArmor
{


    public ArmorGoronEffects(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "This Armor set is Hot!"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
    	if (!world.isClientSide){
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(ItemInit.GORON_TUNIC);
            if(isChestplateOn) player.addEffect(new EffectInstance(Effect.byId(12), 10, 0, false, false));
            
            player.clearFire();
            }
    }
}