package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.interfaces.IHoveringEntity;
import com.superworldsun.superslegend.items.armors.HoverBootsArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SlimeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Converts slime's automatic landing rebound into the Hover Boots' one-time small bounce. */
@Mixin(SlimeBlock.class)
public abstract class MixinHoverBootsSlimeBlock {
    @Inject(method = "updateEntityAfterFallOn", at = @At("HEAD"), cancellable = true)
    private void superslegend$controlHoverBootsSlimeBounce(BlockGetter level, Entity entity,
                                                           CallbackInfo callbackInfo) {
        if (!(entity instanceof Player player)
                || !player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.HOVER_BOOTS.get())) {
            return;
        }

        IHoveringEntity hoveringPlayer = (IHoveringEntity) player;

        // The one permitted small bounce and its hover have already completed. Consume every
        // later slime rebound while the player remains here so normal grounded walking resumes.
        if (hoveringPlayer.isBounceHovering()
                && hoveringPlayer.getHoverTime() >= HoverBootsArmor.MAX_HOVER_TICKS) {
            player.setDeltaMovement(player.getDeltaMovement().x, 0.0D,
                    player.getDeltaMovement().z);
            player.setOnGround(true);
            hoveringPlayer.setWasOnStickyHoverBlock(true);
            callbackInfo.cancel();
            return;
        }

        if (!hoveringPlayer.isHovering()
                && !hoveringPlayer.isHazardHovering()
                && !hoveringPlayer.isStickyBouncePending()) {
            HoverBootsArmor.beginSmallSlimeBounce(player, hoveringPlayer);
            callbackInfo.cancel();
        }
    }
}
