package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.client.render.armor.BarbarianArmorRenderer;
import com.superworldsun.superslegend.client.render.armor.ZoraArmorArmorRenderer;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;

public class BarbarianArmor extends NonEnchantArmor implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public BarbarianArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BarbarianArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new BarbarianArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
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
