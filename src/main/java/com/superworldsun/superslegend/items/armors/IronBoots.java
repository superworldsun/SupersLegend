package com.superworldsun.superslegend.items.armors;

import java.util.UUID;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.IJumpingEntity;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class IronBoots extends NonEnchantArmor
{
	private static final UUID IRON_BOOTS_MODIFIER_ID = UUID.fromString("0fd3562e-c58f-4c6c-b912-d9d6c36bb5ca");
	
	public IronBoots(Properties builder)
	{
		super(ArmourInit.ironboots, EquipmentSlotType.FEET, builder);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.DARK_BLUE + "Sink or Sink"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Allows underwater ground movement"));
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		// Prevent applying changes 2 times per tick
		if (event.phase == Phase.START)
		{
			return;
		}
		
		// Only if we have boots
		if (event.player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.IRON_BOOTS.get())
		{
			if (event.player.isInWater())
			{
				IJumpingEntity jumpingPlayer = (IJumpingEntity) event.player;
				
				if (!jumpingPlayer.isJumping())
				{
					Vector3d motion = event.player.getDeltaMovement();
					event.player.setDeltaMovement(motion.x, -0.3, motion.z);
				}
				
				if (event.player.isOnGround())
				{
					// +100%
					addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), IRON_BOOTS_MODIFIER_ID, 1.0F, Operation.MULTIPLY_TOTAL);
				}
				else
				{
					removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), IRON_BOOTS_MODIFIER_ID);
				}
			}
			else
			{
				// -30%
				addOrReplaceModifier(event.player, Attributes.MOVEMENT_SPEED, IRON_BOOTS_MODIFIER_ID, -0.3F, Operation.MULTIPLY_TOTAL);
				removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), IRON_BOOTS_MODIFIER_ID);
			}
		}
		else
		{
			removeModifier(event.player, Attributes.MOVEMENT_SPEED, IRON_BOOTS_MODIFIER_ID);
			removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), IRON_BOOTS_MODIFIER_ID);
		}
	}
	
	private static void removeModifier(PlayerEntity player, Attribute attribute, UUID id)
	{
		ModifiableAttributeInstance attributeInstance = player.getAttribute(attribute);
		AttributeModifier modifier = attributeInstance.getModifier(id);
		
		if (modifier != null)
		{
			attributeInstance.removeModifier(modifier);
		}
	}
	
	private static void addOrReplaceModifier(PlayerEntity player, Attribute attribute, UUID id, float amount, Operation operation)
	{
		ModifiableAttributeInstance attributeInstance = player.getAttribute(attribute);
		AttributeModifier modifier = attributeInstance.getModifier(id);
		
		if (modifier != null && modifier.getAmount() != amount)
		{
			attributeInstance.removeModifier(modifier);
			modifier = new AttributeModifier(id, id.toString(), amount, operation);
		}
		else if (modifier == null)
		{
			modifier = new AttributeModifier(id, id.toString(), amount, operation);
		}
		
		if (modifier != null && !attributeInstance.hasModifier(modifier))
		{
			attributeInstance.addPermanentModifier(modifier);
		}
	}
}