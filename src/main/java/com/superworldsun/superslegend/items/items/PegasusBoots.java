package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;

public class PegasusBoots extends NonEnchantArmor {

	public PegasusBoots(EquipmentSlotType slot, Properties properties) {
		super(ArmourInit.pegasusboots, slot, properties);
	}

	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Provides Great Speed"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}

    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isClientSide){
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.PEGASUS_BOOTS);
                if(isBootsOn)
                	{
                	if(player.isInWater()) 
                	{
                		player.removeEffect(Effect.byId(1));
                	}
                	else if(player.isOnGround() &&player.isSprinting() && player.getFoodData().getFoodLevel()!= 0)
                	{
                		player.addEffect(new EffectInstance(Effect.byId(1), 9, 1, false, false));
                		player.causeFoodExhaustion(0.04f);
                	}
                }
        }
    }
}