package superworldsun.superslegend.items;

import java.util.List;

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
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class MaskGoronmask extends NonEnchantArmor {
    public MaskGoronmask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.goronmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_RED + "The face of a Goron"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "Your skin is like stone and cannot stay withered"));
	}
    
    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_goronmask);
                if(isHelmeton) 
                	
                	if(player.getActivePotionEffect(Effects.WITHER) != null)
                	{
                	
                	player.removePotionEffect(Effect.get(20));
                	
                	}
                	else	
                	{
                		player.removePotionEffect(Effect.get(20));
                	}
                
                }
            }
        
    
    
}