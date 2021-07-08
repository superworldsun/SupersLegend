package superworldsun.superslegend.items.armors;

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
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;
import superworldsun.superslegend.lists.PotionList;
import net.minecraft.util.text.ITextComponent;

import net.minecraft.item.Item.Properties;

public class ArmorFlippersEffects extends NonEnchantArmor
{
    public ArmorFlippersEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.flippers, slot, new Properties().tab(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.AQUA + "Provides the ability to swim like a Zora"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isClientSide){
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemList.zoras_flippers);
                if(isBootsOn)
                	{
                	if(player.isInWater()&&player.isSprinting()) 
                	{
                		player.addEffect(new EffectInstance(PotionList.zoras_grace_effect, 8, 0, false, false));
                	}
                	if(!player.isSprinting()) 
                	{
                		player.removeEffect(PotionList.zoras_grace_effect);
                	}
                }
        }
    }
}