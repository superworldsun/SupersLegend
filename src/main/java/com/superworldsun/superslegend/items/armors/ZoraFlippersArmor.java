package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ZoraFlippersArmor extends NonEnchantArmor implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final UUID ZORA_FLIPPERS_MODIFIER_ID = UUID.fromString("0fcc5d27-433f-4e9f-ac0e-0488a54b01ac");

    public ZoraFlippersArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }


    private PlayState predicate(AnimationState animationState){
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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
        if (event.player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.ZORA_FLIPPERS.get())
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
