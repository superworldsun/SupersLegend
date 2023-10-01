package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class ZoraArmor extends NonEnchantArmor implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public ZoraArmor(ArmorMaterial material, Type type, Properties properties) {
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

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide){
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.ZORA_TUNIC.get();
            if(isChestplateOn)
            {
                if(player.isEyeInFluidType(ForgeMod.WATER_TYPE.get()))
                {
                    player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 0, false, false, false));
                }
            }
        }
    }
}
