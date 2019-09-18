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


public class RocsCapeEffects extends ArmorItem {
    public RocsCapeEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.rocscape, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isChestplateOn = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.rocs_cape);
                if(isChestplateOn) player.addPotionEffect(new EffectInstance(Effect.get(28), 10, 0, false, false));
                }
            }
        }