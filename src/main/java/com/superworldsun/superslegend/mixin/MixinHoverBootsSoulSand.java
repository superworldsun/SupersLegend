package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.interfaces.IHoveringEntity;
import com.superworldsun.superslegend.items.armors.HoverBootsArmor;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinHoverBootsSoulSand {
    // Ice friction lowers acceleration while retaining momentum. This compensates for that change
    // so sustained walking matches vanilla soul-sand speed while keeping the boots' slipperiness.
    private static final float SOUL_SAND_SPEED_WITH_ICE_TRACTION = 0.92F;
    // Outside an active hover, this reproduces vanilla honey speed with the boots' ice traction.
    private static final float HONEY_SPEED_WITH_ICE_TRACTION = 0.92F;
    // Ice friction makes sustained movement roughly half a percent slower than ordinary ground.
    // This restores normal walking speed while retaining the Hover Boots' slippery traction.
    private static final float NORMAL_SPEED_WITH_ICE_TRACTION = 1.005F;

    @Inject(method = "getBlockSpeedFactor", at = @At("HEAD"), cancellable = true)
    private void superslegend$ignoreSoulSandSpeedFactor(CallbackInfoReturnable<Float> callbackInfo) {
        Entity entity = (Entity) (Object) this;
        if (!(entity instanceof Player player)
                || !player.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.HOVER_BOOTS.get())) {
            return;
        }

        IHoveringEntity hoveringPlayer = (IHoveringEntity) player;
        BlockPos feet = player.blockPosition();
        BlockPos belowFeet = feet.below();

        boolean supportedByHoney = player.level().getBlockState(feet).is(Blocks.HONEY_BLOCK)
                || player.level().getBlockState(belowFeet).is(Blocks.HONEY_BLOCK);
        if (supportedByHoney) {
            // A honey hover represents floating above the sticky surface, so honey must not slow
            // it. Include the entry tick before PlayerTickEvent.END sets the synced hover flags;
            // otherwise that one slow tick appears as a visible horizontal jitter.
            callbackInfo.setReturnValue(isActiveOrPendingHoneyHover(player, hoveringPlayer)
                    ? NORMAL_SPEED_WITH_ICE_TRACTION
                    : HONEY_SPEED_WITH_ICE_TRACTION);
            return;
        }

        boolean supportedBySlime = player.level().getBlockState(feet).is(Blocks.SLIME_BLOCK)
                || player.level().getBlockState(belowFeet).is(Blocks.SLIME_BLOCK);
        if (supportedBySlime) {
            callbackInfo.setReturnValue(NORMAL_SPEED_WITH_ICE_TRACTION);
            return;
        }

        BlockPos soulSandPosition = player.level().getBlockState(feet).is(Blocks.SOUL_SAND)
                ? feet
                : belowFeet;
        if (player.level().getBlockState(soulSandPosition).is(Blocks.SOUL_SAND)) {
            callbackInfo.setReturnValue(hoveringPlayer.isHovering()
                    || hasPendingSoulSandHover(player, hoveringPlayer, soulSandPosition)
                    ? 1.0F
                    : SOUL_SAND_SPEED_WITH_ICE_TRACTION);
        }
    }

    private static boolean isActiveOrPendingHoneyHover(Player player,
                                                        IHoveringEntity hoveringPlayer) {
        if (hoveringPlayer.isHovering() && hoveringPlayer.isBounceHovering()) {
            return true;
        }

        return player.onGround()
                && hoveringPlayer.wasWearingHoverBoots()
                && !hoveringPlayer.wasOnStickyHoverBlock()
                && !hoveringPlayer.jumpedFromBlock()
                && !hoveringPlayer.isHovering()
                && !hoveringPlayer.isHazardHovering()
                && !hoveringPlayer.isBounceHovering()
                && hoveringPlayer.getHoverTime() < HoverBootsArmor.MAX_HOVER_TICKS;
    }

    /**
     * Uses the full-block ledge height before the platform flag becomes visible. This prevents
     * the first soul-sand movement tick from using the post-hover slowdown and then snapping to
     * full hover speed one tick later.
     */
    private static boolean hasPendingSoulSandHover(Player player, IHoveringEntity hoveringPlayer,
                                                   BlockPos soulSandPosition) {
        if (hoveringPlayer.jumpedFromBlock()
                || hoveringPlayer.getHoverTime() >= HoverBootsArmor.MAX_HOVER_TICKS) {
            return false;
        }

        var state = player.level().getBlockState(soulSandPosition);
        var collision = state.getCollisionShape(player.level(), soulSandPosition);
        if (collision.isEmpty()) {
            return false;
        }

        double soulSandTop = soulSandPosition.getY() + collision.max(Direction.Axis.Y);
        return hoveringPlayer.getHoverHeight() > soulSandTop + 0.01D;
    }
}
