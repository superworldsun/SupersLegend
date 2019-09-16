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


public class MaskMajorasmask extends ArmorItem {
    public MaskMajorasmask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.majorasmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
 	{
 		super.addInformation(stack, world, list, flag);				
 		list.add(new StringTextComponent(TextFormatting.RED + "This Mask gives off a strong evil aura"));
 	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_majorasmask);
                if(isHelmeton)
            	{
            		player.addPotionEffect(new EffectInstance(Effect.get(20), 120, 0, false, true));
                    player.addPotionEffect(new EffectInstance(Effect.get(5), 10, 1, false, false));
                    player.addPotionEffect(new EffectInstance(Effect.get(11), 10, 1, false, false));
                    player.addPotionEffect(new EffectInstance(Effect.get(1), 10, 1, false, false));
                    player.addPotionEffect(new EffectInstance(Effect.get(31), 10, 0, false, false));
            	}
            	else
            	{
                    player.removePotionEffect(Effect.get(20));
                    
            	}
            }
    }
}
