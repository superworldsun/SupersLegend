package com.superworldsun.superslegend.items.armors;

import java.util.function.Consumer;

import com.superworldsun.superslegend.client.render.armor.GeoArmorRendererExtension;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class BarbarianArmor extends NonEnchantArmor implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public BarbarianArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        GeoArmorRendererExtension<BarbarianArmor> extension = new GeoArmorRendererExtension<>("barbarian_armor");
        consumer.accept(extension);
    }

    private PlayState predicate(AnimationState<BarbarianArmor> animationState) {
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
            int armorPartsEquipped = 0;

            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.BARBARIAN_HELMET.get())
                armorPartsEquipped++;

            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.BARBARIAN_ARMOR.get())
                armorPartsEquipped++;

            if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.BARBARIAN_LEG_WRAPS.get())
                armorPartsEquipped++;

            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.BARBARIAN_BOOTS.get())
                armorPartsEquipped++;

            if (!level.isClientSide) {
                if (armorPartsEquipped > 1 && armorPartsEquipped < 4) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 0, false, false, false));
                }

                if (armorPartsEquipped == 4) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 1, false, false, false));
                }
            }
        }
    }
}
