package superworldsun.superslegend.items.masks;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


import net.minecraft.item.Item.Properties;

public class MaskDekumask extends NonEnchantArmor {
    public MaskDekumask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.dekumask, slot, new Properties().tab(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_GREEN + "The face of a Deku"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Your wooden skin is unlikely to be poisoned"));
	}
    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isClientSide){
                boolean isHelmeton = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_dekumask);
                if(isHelmeton) 
                	
                	player.removeEffect(Effect.byId(19));
                }
            }
        }