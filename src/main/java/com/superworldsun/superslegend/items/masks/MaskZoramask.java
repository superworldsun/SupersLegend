package com.superworldsun.superslegend.items.masks;

import java.util.List;

import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.mana.ManaProvider;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class MaskZoramask extends NonEnchantArmor {

    public MaskZoramask(Properties properties)
    {
        super(ArmourInit.ZORA_MASK, EquipmentSlotType.HEAD, properties);
    }

    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "The face of a Zora"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "You can swim with the grace of a Zora"));
	}

    float manaCost = 0.03F;

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        boolean hasMana = ManaProvider.get(player).getMana() >= manaCost || player.abilities.instabuild;
        boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.MASK_ZORAMASK.get();

        if(isHelmetOn)
        {
            if(player.isSwimming() && hasMana && player.isAlive())
            {
                ManaProvider.get(player).spendMana(manaCost);
                player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 4, 0, true, true, true));
            }
        }
    }
        
    
    
}