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


public class IronBoots extends NonEnchantArmor {
    public IronBoots(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.ironboots, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "Sink or Sink"));
		list.add(new StringTextComponent(TextFormatting.DARK_GRAY + "[WIP]"));
	}
    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	if (!world.isRemote){
            boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.iron_boots);
            if(isBootsOn)
            	{
            	if(player.isInWater() && player.onGround && !player.isSprinting()) 
            	{
            		player.addPotionEffect(new EffectInstance(Effect.get(2), 10, 0, false, false));
            		player.addPotionEffect(new EffectInstance(Effect.get(30), 3, 0, false, false));
            		
            	}
            	else if (player.isInWater())
            	{
            		player.addPotionEffect(new EffectInstance(Effect.get(2), 10, 1, false, false));

            	}
            	else
            	{
            		
            		player.addPotionEffect(new EffectInstance(Effect.get(2), 10, 1, false, false));
            		player.removePotionEffect(Effect.get(30));
            	}
                
            	}
    	}

    }
    
    //player.getMotion().getX() != 0 && player.getMotion().getY() != 0
    
    //@Override
   // public boolean hasEffect(ItemStack stack) {
        //return false;
   // }
}