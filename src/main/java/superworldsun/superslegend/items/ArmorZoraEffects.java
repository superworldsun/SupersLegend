package superworldsun.superslegend.items;

import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;


public class ArmorZoraEffects extends ArmorItem {
    public ArmorZoraEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.zora, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
        
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(SupersLegend.modid + ".zora_tunic.description"));

    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isChestplateOn = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.zora_tunic);
                boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.iron_boots);
                if(isChestplateOn) player.addPotionEffect(new EffectInstance(Effect.get(13), 10, 0));
                if(isBootsOn)
                	{
                	if(player.isInWater()) 
                	{
                		player.removePotionEffect(Effect.get(2));
                	}
                	else
                	{
                		player.addPotionEffect(new EffectInstance(Effect.get(2), 10, 1));
                	}
                }
        }
    }
}