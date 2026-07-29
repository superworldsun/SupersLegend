package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.songs.TimeSongSavedData;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinTimeScaledItemEntity {
    @Shadow
    private int age;

    @Inject(method = "tick", at = @At("TAIL"))
    private void superslegend$scaleItemDespawnAge(CallbackInfo callbackInfo) {
        ItemEntity item = (ItemEntity) (Object) this;
        if (item.level().isClientSide || age == Short.MIN_VALUE
                || !Config.timeSongsAffectItemDespawn()) {
            return;
        }

        TimeSongSavedData.Mode mode = TimeSongSavedData.getMode(item.level());
        if (mode == TimeSongSavedData.Mode.DOUBLE) {
            age++;
        } else if (mode == TimeSongSavedData.Mode.INVERTED
                && !TimeSongSavedData.shouldRunInvertedTick(item.level())) {
            age--;
        }
    }
}
