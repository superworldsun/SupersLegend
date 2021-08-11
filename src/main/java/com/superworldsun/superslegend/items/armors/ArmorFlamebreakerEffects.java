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

public class ArmorFlamebreakerEffects extends NonEnchantArmor
{


    public ArmorFlamebreakerEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.flamebreaker, slot, properties);
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
        if (!world.isClientSide)
        {
        		boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().getItem() == ItemInit.FLAMEBREAKER_HELMET.get();
                boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getItem() == ItemInit.FLAMEBREAKER_TUNIC.get();
                boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().getItem() == ItemInit.FLAMEBREAKER_LEGGINGS.get();
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().getItem() == ItemInit.FLAMEBREAKER_BOOTS.get();
                if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
                	{
                		player.addEffect(new EffectInstance(Effect.byId(12), 10, 0, false, false, false));
                		player.clearFire();
                	}
                	
        }
    }
}