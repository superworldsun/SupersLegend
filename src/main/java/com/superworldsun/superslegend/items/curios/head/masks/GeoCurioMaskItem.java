package com.superworldsun.superslegend.items.curios.head.masks;

import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/** Shared GeckoLib support for masks rendered in the Curios head slot. */
public class GeoCurioMaskItem extends Item implements ICurioItem, GeoItem {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final String modelName;

    public GeoCurioMaskItem(Properties properties, String modelName) {
        super(properties);
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // These masks currently have static models. Controllers can be added per mask later.
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
