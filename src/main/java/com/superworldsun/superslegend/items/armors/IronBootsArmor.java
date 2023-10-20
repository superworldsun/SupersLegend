package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class IronBootsArmor extends NonEnchantArmor {
    private static final UUID IRON_BOOTS_MODIFIER_ID = UUID.fromString("0fd3562e-c58f-4c6c-b912-d9d6c36bb5ca");
    public IronBootsArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        // Prevent applying changes 2 times per tick
        if (event.phase == TickEvent.Phase.START)
        {
            return;
        }

        // Only if we have boots
        if (event.player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.IRON_BOOTS.get())
        {
            if (event.player.isInWater())
            {
                //IJumpingEntity jumpingPlayer = (IJumpingEntity) event.player;
                //if (!jumpingPlayer.isJumping())
                //TODO ,(event.player.isInWater()) is temporary fix untill IJumpingEntity is fixed, currently causing crash
                if (event.player.isInWater())
                {
                    Vec3 motion = event.player.getDeltaMovement();
                    event.player.setDeltaMovement(motion.x, -0.3, motion.z);
                }

                if (event.player.onGround())
                {
                    // +100%
                    addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), IRON_BOOTS_MODIFIER_ID, 1.0F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                }
                else
                {
                    removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), IRON_BOOTS_MODIFIER_ID);
                }
            }
            else
            {
                // -30%
                addOrReplaceModifier(event.player, Attributes.MOVEMENT_SPEED, IRON_BOOTS_MODIFIER_ID, -0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL);
                removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), IRON_BOOTS_MODIFIER_ID);
            }
        }
        else
        {
            removeModifier(event.player, Attributes.MOVEMENT_SPEED, IRON_BOOTS_MODIFIER_ID);
            removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), IRON_BOOTS_MODIFIER_ID);
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
