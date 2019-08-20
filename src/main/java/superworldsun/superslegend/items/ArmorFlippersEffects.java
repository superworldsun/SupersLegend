package superworldsun.superslegend.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class ArmorFlippersEffects extends ArmorItem {
    public ArmorFlippersEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.flippers, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.zoras_flippers);
                if(isBootsOn)
                	{
                	if(player.isInWater()) 
                	{
                		player.addPotionEffect(new EffectInstance(Effect.get(1), 10, 1));
                	}
                }
        }
    }
}