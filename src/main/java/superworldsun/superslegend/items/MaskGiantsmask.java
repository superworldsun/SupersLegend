package superworldsun.superslegend.items;


import net.minecraft.client.util.ITooltipFlag;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ArmourMaterialList;



public class MaskGiantsmask extends ArmorItem {
    public MaskGiantsmask(String name, EquipmentSlotType slot) 

    {
        super(ArmourMaterialList.giantsmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

    /*public void applyCustomModifiers(ItemStack stack, PlayerEntity player) {
		
    	
		// TODO
    	PlayerEntity.setSize(player, player.getWidth() * 3.0F, player.getHeight() * 3.0F);
		if (player.world.isRemote) {
			player.stepHeight += 1.0F;
		}
		 
	}
	public void removeModifiers(ItemStack stack, PlayerEntity player) {

			if (player.world.isRemote) {
				player.stepHeight -= 1.0F;
			}
		}*/
	
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "Takes a big spirit to wear this mask"));
	}
}
