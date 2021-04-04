package superworldsun.superslegend.items.armors;

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
import superworldsun.superslegend.lists.PotionList;


public class ArmorZoraArmorEffects extends NonEnchantArmor
{
    public ArmorZoraArmorEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.zoraarmor, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
        
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "Armor of the Zoras"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Wearing full set grants water breathing"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "and the ability to swim like a Zora"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
        		boolean isHelmetOn = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.zora_armor_cap);
                boolean isChestplateOn = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.zora_armor_tunic);
                boolean isLeggingsOn = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem().equals(ItemList.zora_armor_leggings);
                boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.zora_armor_flippers);
                if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
                	{
                	if(player.isInWater()) 
                	{
                		player.addPotionEffect(new EffectInstance(Effect.get(13), 10, 0, false, false, false));
                		player.addPotionEffect(new EffectInstance(PotionList.zoras_grace_effect, 10, 0, false, false, false));
                	}
                	if(player.isInWater() && player.isSprinting()) 
                	{
                		player.addPotionEffect(new EffectInstance(Effect.get(13), 10, 0, false, false, false));
                		player.addPotionEffect(new EffectInstance(PotionList.zoras_grace_effect, 10, 0, false, false, false));
                	}
                	if(!player.isSprinting()) 
                	{
                		player.removePotionEffect(PotionList.zoras_grace_effect);
                	}
                	
                }
        }
    }
}