package com.superworldsun.superslegend.items.armors;

import java.util.List;

import com.superworldsun.superslegend.client.model.ModelRocsCape;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
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

public class RocsCapeEffects extends NonEnchantArmor {

	public RocsCapeEffects(EquipmentSlotType slot, Properties properties) {
		super(ArmourInit.rocscape, slot, properties);
	}

	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default)
	{
		return (A) new ModelRocsCape(0);
	}


    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	if (!world.isClientSide){
    		boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.ROCS_CAPE.get();
            if(isChestplateOn)
            	{
            	/*if(player.getItemBySlot(EquipmentSlotType.FEET).getItem().getItem() == ItemInit.HOVER_BOOTS.get() )
            	{
            		player.removeEffect(Effect.byId(28));
            	}*/
            	if(player.isOnGround())
		        {
            		player.addEffect(new EffectInstance(Effect.byId(28), 24, 0, false, false));
					player.addEffect(new EffectInstance(Effect.byId(8), 1, 3, false, false));
		        }
                
            	}
    	}

    }
        

		    @Override
			public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
			{
				super.appendHoverText(stack, world, list, flag);				
				list.add(new StringTextComponent(TextFormatting.WHITE + "Wearing this will grant better ground mobility"));
			}   
} 