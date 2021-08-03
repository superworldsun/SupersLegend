package com.superworldsun.superslegend.items.masks;

import java.util.List;

import com.superworldsun.superslegend.client.model.ModelBunnyhoodMask;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MaskBunnyhood extends NonEnchantArmor
{

	public MaskBunnyhood(Properties properties)
	{
		super(ArmourInit.bunnyhood, EquipmentSlotType.HEAD, properties);
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
        if (!world.isClientSide){
                boolean isHelmeton = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.MASK_BUNNYHOOD);
                
                if(isHelmeton) {
                	if(!player.isInWater())
                	{
						player.addEffect(new EffectInstance(Effect.byId(1), 10, 0, false, false));
                	}
                }
        }
    }
    
    @Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.YELLOW + "I am Speed"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Grants a boost of speed"));
	}
}