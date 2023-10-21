package com.superworldsun.superslegend.items.armors;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.render.armor.GeoArmorRendererExtension;
import com.superworldsun.superslegend.items.customclass.NonEnchantArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class FlamebreakerArmor extends NonEnchantArmor implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public FlamebreakerArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        GeoArmorRendererExtension<FlamebreakerArmor> extension = new GeoArmorRendererExtension<>("flamebreaker_armor");
        consumer.accept(extension);
    }

    private PlayState predicate(AnimationState<FlamebreakerArmor> animationState) {
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
        if (!level.isClientSide)
        {
            boolean isHelmetOn = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemInit.FLAMEBREAKER_HELMET.get();
            boolean isChestplateOn = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemInit.FLAMEBREAKER_TUNIC.get();
            boolean isLeggingsOn = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemInit.FLAMEBREAKER_LEGGINGS.get();
            boolean isBootsOn = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemInit.FLAMEBREAKER_BOOTS.get();
            if(isHelmetOn&isChestplateOn&isLeggingsOn&isBootsOn)
            {
                player.clearFire();
            }
        }
    }
}
