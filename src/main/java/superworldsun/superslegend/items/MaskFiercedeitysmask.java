package superworldsun.superslegend.items;

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
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class MaskFiercedeitysmask extends NonEnchantArmor {
    public MaskFiercedeitysmask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.fiercedeitysmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
 	{
 		super.addInformation(stack, world, list, flag);				
 		list.add(new StringTextComponent(TextFormatting.RED + "Contains a dark, godlike power.."));
 		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants Strength and removes some negative effects"));
 		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses some stamina"));
 	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_fiercedeitysmask);
                if(isHelmeton && player.getFoodStats().getFoodLevel()!= 0)
            	{
                	player.addExhaustion(0.0175f);
            		player.addPotionEffect(new EffectInstance(Effect.get(5), 10, 1, false, false));
            		player.addPotionEffect(new EffectInstance(Effect.get(8), 10, 0, false, false));
            		player.addPotionEffect(new EffectInstance(Effect.get(26), 10, 0, false, false));
            		player.removePotionEffect(Effect.get(2));
            		player.removePotionEffect(Effect.get(9));
            		player.removePotionEffect(Effect.get(18));
            		player.removePotionEffect(Effect.get(27));
            		player.removePotionEffect(Effect.get(31));
            	}
            }
    }
}
