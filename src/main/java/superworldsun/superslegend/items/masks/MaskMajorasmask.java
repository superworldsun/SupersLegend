package superworldsun.superslegend.items.masks;

import net.minecraft.client.renderer.entity.model.BipedModel;
//import net.minecraft.client.renderer.arrows.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
//import net.minecraft.arrows.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;
//import superworldsun.superslegend.models.armor.ModelMajorasMask;
//import superworldsun.superslegend.models.armor.ModelMajorasMask;
//import ModelPostmansHat;


public class MaskMajorasmask extends NonEnchantArmor {
    public MaskMajorasmask(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.majorasmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    /*@SuppressWarnings("unchecked")
    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
    	ModelMajorasMask model = new ModelMajorasMask();

        model.isChild = _default.isChild;
        model.isSneak = _default.isSneak;
        model.isSitting = _default.isSitting;

        return (A) model;
    }*/
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
 	{
 		super.addInformation(stack, world, list, flag);				
 		list.add(new StringTextComponent(TextFormatting.RED + "This Mask gives off a strong evil aura"));
 	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_majorasmask);
                if(isHelmeton)
            	{
            		player.addPotionEffect(new EffectInstance(Effect.get(20), 120, 0, false, true));
                    player.addPotionEffect(new EffectInstance(Effect.get(5), 10, 1, false, false));
                    player.addPotionEffect(new EffectInstance(Effect.get(11), 10, 1, false, false));
                    player.addPotionEffect(new EffectInstance(Effect.get(1), 10, 1, false, false));
                    player.addPotionEffect(new EffectInstance(Effect.get(31), 10, 0, false, false));
            	}
            	else
            	{
                    player.removePotionEffect(Effect.get(20));
                    
            	}
            }
    }
}
