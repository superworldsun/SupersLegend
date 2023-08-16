package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ZoraArmorArmor extends NonEnchantArmor {
    private static final UUID ZORA_ARMOR_MODIFIER_ID = UUID.fromString("9b86d513-457e-4231-8d90-bdd270ec6748");
    public ZoraArmorArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide)
        {
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.ZORA_ARMOR_CAP.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.ZORA_ARMOR_TUNIC.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.ZORA_ARMOR_LEGGINGS.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.ZORA_ARMOR_BOOTS.get();
            if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
            {
                if(player.isInWater())
                {
                    player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 0, false, false, false));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }

        // Only if we have flippers
        boolean isHelmetOn = event.player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.ZORA_ARMOR_CAP.get();
        boolean isChestplateOn = event.player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.ZORA_ARMOR_TUNIC.get();
        boolean isLeggingsOn = event.player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.ZORA_ARMOR_LEGGINGS.get();
        boolean isBootsOn = event.player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.ZORA_ARMOR_BOOTS.get();
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

    private static void removeModifier(Player player, Attribute attribute, UUID id)
    {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null)
        {
            attributeInstance.removeModifier(modifier);
        }
    }

    private static void addOrReplaceModifier(Player player, Attribute attribute, UUID id, float amount, AttributeModifier.Operation operation)
    {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
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
