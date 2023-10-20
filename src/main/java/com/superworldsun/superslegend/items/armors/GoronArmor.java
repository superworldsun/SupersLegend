package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.api.IncomingDamageModifier;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class GoronArmor extends NonEnchantArmor implements IncomingDamageModifier, GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public GoronArmor(ArmorMaterial material, Type type, Properties properties) {
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
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.GORON_TUNIC.get();

            if (isChestplateOn)
            {
                player.clearFire();
            }
        }
    }
    //TODO, I want In Fire damage and lava to be exactly /3, and to be immune to Hot Floor damage
    @Override
    public boolean canModifyDamage(DamageSource damage) {
        return damage.is(DamageTypes.IN_FIRE) || damage.is(DamageTypes.LAVA) || damage.is(DamageTypes.HOT_FLOOR);
    }

    @Override
    public float getDamageModifier() {
        return -0.5f;
    }
}
