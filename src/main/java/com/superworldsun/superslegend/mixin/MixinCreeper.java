package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.interfaces.IShockChargeableCreeper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Creeper.class)
public abstract class MixinCreeper implements IShockChargeableCreeper {
    @Shadow @Final private static EntityDataAccessor<Boolean> DATA_IS_POWERED;

    @Unique
    @Override
    public void superslegend$setShockCharged() {
        Creeper creeper = (Creeper) (Object) this;
        creeper.getEntityData().set(DATA_IS_POWERED, true);
    }
}
