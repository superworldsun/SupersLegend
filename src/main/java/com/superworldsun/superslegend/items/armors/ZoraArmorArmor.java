package com.superworldsun.superslegend.items.armors;

import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.Streams;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.armor.GeoArmorRendererExtension;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class ZoraArmorArmor extends NonEnchantArmor implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final UUID ZORA_ARMOR_MODIFIER_ID = UUID.fromString("9b86d513-457e-4231-8d90-bdd270ec6748");

    public ZoraArmorArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        GeoArmorRendererExtension<ZoraArmorArmor> extension = new GeoArmorRendererExtension<>("zora_armor_armor");
        consumer.accept(extension);
    }

    private PlayState predicate(AnimationState<ZoraArmorArmor> animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide) {
            if (isWearingFullSet(player)) {
                if (player.isInWater()) {
                    player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 0, false, false, false));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        if (isWearingFullSet(event.player)) {
            if (event.player.isInWater()) {

                if (event.player.isInWater() && event.player.isSprinting()) {
                    // +50%
                    addOrReplaceModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID, 0.5F,
                            AttributeModifier.Operation.MULTIPLY_TOTAL);
                } else {
                    removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID);
                }
            }
        } else {
            removeModifier(event.player, ForgeMod.SWIM_SPEED.get(), ZORA_ARMOR_MODIFIER_ID);
        }
    }

    private static boolean isWearingFullSet(Player player) {
        return Streams.stream(player.getArmorSlots())
                .map(ItemStack::getItem)
                .allMatch(ZoraArmorArmor.class::isInstance);
    }

    private static void removeModifier(Player player, Attribute attribute, UUID id) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null) {
            attributeInstance.removeModifier(modifier);
        }
    }

    private static void addOrReplaceModifier(Player player, Attribute attribute, UUID id, float amount,
            Operation operation) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        AttributeModifier modifier = attributeInstance.getModifier(id);

        if (modifier != null && modifier.getAmount() != amount) {
            attributeInstance.removeModifier(modifier);
            modifier = new AttributeModifier(id, id.toString(), amount, operation);
        } else if (modifier == null) {
            modifier = new AttributeModifier(id, id.toString(), amount, operation);
        }

        if (modifier != null && !attributeInstance.hasModifier(modifier)) {
            attributeInstance.addPermanentModifier(modifier);
        }
    }
}
