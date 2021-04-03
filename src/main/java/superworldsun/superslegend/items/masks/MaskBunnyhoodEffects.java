package superworldsun.superslegend.items.masks;

import java.util.List;

//import net.minecraft.client.renderer.arrows.model.BipedModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.arrows.LivingEntity;
import net.minecraft.entity.LivingEntity;
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
import superworldsun.superslegend.models.masks.ModelAllnightmask;
import superworldsun.superslegend.models.masks.ModelBunnyhoodMask;
//import superworldsun.superslegend.models.armor.ModelBunnyHood;


public class MaskBunnyhoodEffects extends NonEnchantArmor {
    public MaskBunnyhoodEffects(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.bunnyhood, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
	{
		return (A) new ModelBunnyhoodMask(0);
	}

    
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isRemote){
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_bunnyhood);
                
                if(isHelmeton) {
                	if(!player.isInWater())
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