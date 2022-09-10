package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.KokiriArmorModel;
import com.superworldsun.superslegend.client.model.armor.KokiriBootsModel;
import com.superworldsun.superslegend.client.model.armor.ZorasFlippersModel;
import com.superworldsun.superslegend.interfaces.IJumpingEntity;
import com.superworldsun.superslegend.items.custom.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ArmourInit;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ArmorFlippersEffects extends NonEnchantArmor
{
    private static final Map<EquipmentSlotType, BipedModel<?>> MODELS_CACHE = new HashMap<>();
    private static final UUID ZORA_FLIPPERS_MODIFIER_ID = UUID.fromString("0fcc5d27-433f-4e9f-ac0e-0488a54b01ac");

    public ArmorFlippersEffects(EquipmentSlotType slot, Properties properties) {
        super(ArmourInit.FLIPPERS, slot, properties);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.AQUA + "Provides the ability to swim like a Zora"));
	}

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    @Override
    public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default)
    {
        if (!MODELS_CACHE.containsKey(armorSlot))
        {
            if (armorSlot == EquipmentSlotType.FEET)
            {
                MODELS_CACHE.put(armorSlot, new ZorasFlippersModel());
            }
        }

        return (M) MODELS_CACHE.get(armorSlot);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type)
    {
        if (armorSlot == EquipmentSlotType.FEET)
            return SupersLegendMain.MOD_ID + ":textures/models/armor/zoras_flippers.png";
        else
            return SupersLegendMain.MOD_ID + ":textures/models/armor/zoras_flippers.png";
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
        if (event.player.getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.ZORA_FLIPPERS.get())
        {
            if (event.player.isInWater())
            {

                if(event.player.isInWater()&&event.player.isSprinting())
                {
                    // +50%
                    addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_FLIPPERS_MODIFIER_ID, 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                }
                else
                {
                    removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_FLIPPERS_MODIFIER_ID);
                }
            }
        }
        else
        {
            removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_FLIPPERS_MODIFIER_ID);
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