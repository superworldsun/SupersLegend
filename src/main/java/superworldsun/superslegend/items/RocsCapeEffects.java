package superworldsun.superslegend.items;

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
        

		    @Override
			public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
			{
				super.addInformation(stack, world, list, flag);				
				list.add(new StringTextComponent(TextFormatting.WHITE + "Wearing this will slow your decent"));
			}   
} 