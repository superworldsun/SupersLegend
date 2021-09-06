package com.superworldsun.superslegend.items.armors;

import java.util.HashMap;
import java.util.Map;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.PegasusBootsModel;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
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

public class PegasusBoots extends NonEnchantArmor
{
	private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();
	
	public PegasusBoots(EquipmentSlotType slot, Properties properties)
	{
		super(ArmourInit.PEGASUS_BOOTS, slot, properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
	{
		if (!MODELS_CACHE.containsKey(armorSlot))
		{
			MODELS_CACHE.put(armorSlot, new PegasusBootsModel());
		}
		
		return (M) MODELS_CACHE.get(armorSlot);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type)
	{
		return SupersLegendMain.MOD_ID + ":textures/models/armor/pegasus_boots.png";
	}
	
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.YELLOW + "Provides Great Speed"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "Uses Stamina on use"));
	}
	
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{
		if (!world.isClientSide)
		{
			boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.PEGASUS_BOOTS.get();
			
			if (isBootsOn)
			{
				if (player.isInWater())
				{
					player.removeEffect(Effect.byId(1));
				}
				else if (player.isOnGround() && player.isSprinting() && player.getFoodData().getFoodLevel() != 0)
				{
					player.addEffect(new EffectInstance(Effect.byId(1), 9, 1, false, false));
					player.causeFoodExhaustion(0.04f);
				}
			}
		}
	}
}