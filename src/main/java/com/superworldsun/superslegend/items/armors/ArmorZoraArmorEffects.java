package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
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
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ArmorZoraArmorEffects extends NonEnchantArmor
{
	private static final UUID ZORA_ARMOR_MODIFIER_ID = UUID.fromString("9b86d513-457e-4231-8d90-bdd270ec6748");

	public ArmorZoraArmorEffects(EquipmentSlotType slot, Properties properties) {
		super(ArmourInit.ZORAARMOR, slot, properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.BLUE + "Armor of the Zoras"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Wearing full set grants water breathing"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "and the ability to swim like a Zora"));
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!world.isClientSide)
        {
        		boolean isHelmetOn = player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.ZORA_ARMOR_CAP.get();
                boolean isChestplateOn = player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.ZORA_ARMOR_TUNIC.get();
                boolean isLeggingsOn = player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ItemInit.ZORA_ARMOR_LEGGINGS.get();
                boolean isBootsOn = player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.ZORA_ARMOR_BOOTS.get();
                if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
                	{
                	if(player.isInWater()) 
                	{
                		player.addEffect(new EffectInstance(Effect.byId(13), 10, 0, false, false, false));
                		//Todo add potion back
                		//player.addEffect(new EffectInstance(PotionList.zoras_grace_effect, 10, 0, false, false, false));
                	}
                }
        }
    }

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		// Prevent applying changes 2 times per tick
		if (event.phase == TickEvent.Phase.START)
		{
			return;
		}

		// Only if we have flippers
		boolean isHelmetOn = event.player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == ItemInit.ZORA_ARMOR_CAP.get();
		boolean isChestplateOn = event.player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == ItemInit.ZORA_ARMOR_TUNIC.get();
		boolean isLeggingsOn = event.player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == ItemInit.ZORA_ARMOR_LEGGINGS.get();
		boolean isBootsOn = event.player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.ZORA_ARMOR_BOOTS.get();
		if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
		{
			if (event.player.isInWater())
			{

				if(event.player.isInWater()&&event.player.isSprinting())
				{
					// +50%
					addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID, 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
				}
				else
				{
					removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID);
				}
			}
		}
		else
		{
			removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID);
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

	private static void addOrReplaceModifier(PlayerEntity player, Attribute attribute, UUID id, float amount, AttributeModifier.Operation operation)
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