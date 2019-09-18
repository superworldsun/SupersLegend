package superworldsun.superslegend.items;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class MaskGiantsmask extends ArmorItem {
    public MaskGiantsmask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.giantsmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "Takes a big spirit to wear this mask"));
	}
}
/*    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_giantsmask);
                if(isHelmeton) PlayerEntity.STANDING_SIZE (2);
                }
            }
        }*/