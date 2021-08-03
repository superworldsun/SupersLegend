package com.superworldsun.superslegend.items.masks;

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

public class MaskMajorasmask extends NonEnchantArmor {

    public MaskMajorasmask(Properties properties)
    {
        super(ArmourInit.majorasmask, EquipmentSlotType.HEAD, properties);
    }
    
    /*@SuppressWarnings("unchecked")
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
    	ModelMajorasMask model = new ModelMajorasMask();

        model.isChild = _default.isChild;
        model.isSneak = _default.isSneak;
        model.isSitting = _default.isSitting;

        return (A) model;
    }*/
    
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
 	{
 		super.appendHoverText(stack, world, list, flag);				
 		list.add(new StringTextComponent(TextFormatting.RED + "This Mask gives off a strong evil aura"));
 	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isClientSide){
                boolean isHelmeton = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.MASK_MAJORASMASK);
                if(isHelmeton)
            	{
            		player.addEffect(new EffectInstance(Effect.byId(20), 120, 0, false, true));
                    player.addEffect(new EffectInstance(Effect.byId(5), 10, 1, false, false));
                    player.addEffect(new EffectInstance(Effect.byId(11), 10, 1, false, false));
                    player.addEffect(new EffectInstance(Effect.byId(1), 10, 1, false, false));
                    player.addEffect(new EffectInstance(Effect.byId(31), 10, 0, false, false));
            	}
            	else
            	{
                    player.removeEffect(Effect.byId(20));
                    
            	}
            }
    }
}
