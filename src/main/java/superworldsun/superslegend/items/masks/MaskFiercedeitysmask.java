package superworldsun.superslegend.items.masks;

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
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


import net.minecraft.item.Item.Properties;

public class MaskFiercedeitysmask extends NonEnchantArmor {
    public MaskFiercedeitysmask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.fiercedeitysmask, slot, new Properties().tab(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
 	{
 		super.appendHoverText(stack, world, list, flag);				
 		list.add(new StringTextComponent(TextFormatting.RED + "Contains a dark, godlike power.."));
 		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants Strength and removes some negative effects"));
 		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses some stamina"));
 	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isClientSide){
                boolean isHelmeton = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_fiercedeitysmask);
                if(isHelmeton && player.getFoodData().getFoodLevel()!= 0)
            	{
                	player.causeFoodExhaustion(0.0175f);
            		player.addEffect(new EffectInstance(Effect.byId(5), 10, 1, false, false));
            		player.addEffect(new EffectInstance(Effect.byId(8), 10, 0, false, false));
            		player.addEffect(new EffectInstance(Effect.byId(26), 10, 0, false, false));
            		player.removeEffect(Effect.byId(2));
            		player.removeEffect(Effect.byId(9));
            		player.removeEffect(Effect.byId(18));
            		player.removeEffect(Effect.byId(27));
            		player.removeEffect(Effect.byId(31));
            	}
            }
    }
}
