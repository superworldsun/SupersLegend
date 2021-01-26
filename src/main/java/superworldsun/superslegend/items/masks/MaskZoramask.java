package superworldsun.superslegend.items.masks;

import java.util.List;

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


public class MaskZoramask extends NonEnchantArmor {
    public MaskZoramask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.zoramask, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "The face of a Zora"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "You can swim with the grace of a Zora"));
	}
    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isHelmetOn = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_zoramask);
                if(isHelmetOn)
                	{
                	if(player.isInWater() && player.isSprinting() && !player.isOnGround() && player.getFoodStats().getFoodLevel()!= 0)
                	{
                		player.addPotionEffect(new EffectInstance(Effect.get(30), 4, 0, false, false, false));
                		player.addExhaustion(0.09f);
                	}
                	
                }
        }
    }
        
    
    
}