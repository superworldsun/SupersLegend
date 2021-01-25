package superworldsun.superslegend.items.masks;

import java.util.List;

//import net.minecraft.client.renderer.arrows.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
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
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;
//import superworldsun.superslegend.models.armor.ModelBunnyHood;


public class MaskBunnyhoodEffects extends NonEnchantArmor {
    public MaskBunnyhoodEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.bunnyhood, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

/*    @SuppressWarnings("unchecked")
	@Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
    	ModelBunnyHood model = new ModelBunnyHood(toughness);

        model.isChild = _default.isChild;
        model.isSneak = _default.isSneak;
        model.isSitting = _default.isSitting;
        model.rightArmPose = _default.rightArmPose;
        model.leftArmPose = _default.leftArmPose;

        return (A) model;
}*/

    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_bunnyhood);
                
                if(isHelmeton) {
                	if(player.isInWater()) 
                	{
                		player.removePotionEffect(Effect.get(1));
                	}
                	else
                	{
                		player.addPotionEffect(new EffectInstance(Effect.get(1), 10, 0, false, false));
                	}
                    
                	}
        		}
    		}
    
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.YELLOW + "I am Speed"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants a boost of speed"));
	}
}