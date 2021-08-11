package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;

public class ArmorFlippersEffects extends NonEnchantArmor
{


    public ArmorFlippersEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.flippers, slot, properties);
    }

    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.AQUA + "Provides the ability to swim like a Zora"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isClientSide){
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.ZORA_FLIPPERS);
                if(isBootsOn)
                	{
                	if(player.isInWater()&&player.isSprinting()) 
                	{
                	    //todo add potion back
                		//player.addEffect(new EffectInstance(PotionList.zoras_grace_effect, 8, 0, false, false));
                	}
                	if(!player.isSprinting()) 
                	{
                		//player.removeEffect(PotionList.zoras_grace_effect);
                	}
                }
        }
    }
}