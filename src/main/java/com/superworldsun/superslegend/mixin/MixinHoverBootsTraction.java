package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Gives Hover Boots the same low traction and retained momentum as ice,
 * regardless of the solid block beneath the wearer.
 */
@Mixin(LivingEntity.class)
public abstract class MixinHoverBootsTraction {
    private static final float ICE_FRICTION = 0.98F;

    @ModifyVariable(
            method = "travel",
            at = @At("STORE"),
            index = 8
    )
    private float superslegend$useIceFrictionWithHoverBoots(float originalFriction) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player player
                && player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.HOVER_BOOTS.get())) {
            return ICE_FRICTION;
        }
        return originalFriction;
    }
}
