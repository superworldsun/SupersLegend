package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.renderer.entity.model.BipedModel;
//import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.lists.ArmourMaterialList;
//import superworldsun.superslegend.models.armor.ModelPostmansHat;
import superworldsun.superslegend.models.armor.ModelPostmansHat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class MaskPostmanshat extends NonEnchantArmor {
    public MaskPostmanshat(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.postmanshat, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    

    @SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
	{
		return (A) new ModelPostmansHat<LivingEntity>();
	}

     
    
    @Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "Worn by those who are"));
		list.add(new StringTextComponent(TextFormatting.RED + "never late for a delivery"));
	}
}