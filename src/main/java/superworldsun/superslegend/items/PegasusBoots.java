package superworldsun.superslegend.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;
import net.minecraft.util.text.ITextComponent;

public class PegasusBoots extends NonEnchantArmor {
    public PegasusBoots(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.pegasusboots, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Provides Great Speed"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.pegasus_boots);
                if(isBootsOn)
                	{
                	if(player.isInWater()) 
                	{
                		player.removePotionEffect(Effect.get(1));
                	}
                	else if(player.isOnGround() &&player.isSprinting() && player.getFoodStats().getFoodLevel()!= 0)
                	{
                		player.addPotionEffect(new EffectInstance(Effect.get(1), 9, 1, false, false));
                		player.addExhaustion(0.04f);
                	}
                }
        }
    }
}