package com.superworldsun.superslegend.items.items;

import com.superworldsun.superslegend.items.custom.ItemCustomSword;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class GuardianSword extends ItemCustomSword
{
	public GuardianSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
	{
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.AQUA + "A sword made from old technology"));
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack equipped = player.getMainHandItem();
			{
				if(stack == equipped)
					{
						if(player.hasItemInSlot(EquipmentSlotType.MAINHAND))
						{
							if(!player.hasItemInSlot(EquipmentSlotType.OFFHAND))
							{
								boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(ItemInit.ANCIENT_HELMET);
								boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(ItemInit.ANCIENT_CUIRASS);
								boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem().equals(ItemInit.ANCIENT_GREAVES);
								boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.ANCIENT_BOOTS);
								if (isHelmetOn & isChestplateOn & isLeggingsOn & isBootsOn) {
									player.addEffect(new EffectInstance(Effect.byId(5), 5, 0, false, false, false));
								}
							}
						}
					}
			}
		}
	}
}
