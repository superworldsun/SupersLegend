package com.superworldsun.superslegend.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

/**
 * Items (armor or curios) that change the size of the player while equipped.
 * The scale is applied to the hitbox and eye height via Pehkui, the visual
 * size comes from the transformation models themselves.
 */
public interface IEntityResizer {
    float getScale(Player player);

    default float getEyeHeightScale(Player player, Pose pose) {
        return getScale(player);
    }

    default float getHitboxHeightScale(Player player, Pose pose) {
        return getScale(player);
    }

    default boolean hasInstantEyeHeightChanges() {
        return false;
    }

    default boolean hasInstantHitboxHeightChanges() {
        return false;
    }

    /**
     * Returns the resizer from the player's equipped armor or curios, or null if there is none.
     */
    static IEntityResizer get(Player player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof IEntityResizer resizer) {
                return resizer;
            }
        }

        Optional<IItemHandlerModifiable> curios = CuriosApi.getCuriosHelper().getEquippedCurios(player).resolve();

        if (curios.isPresent()) {
            for (int i = 0; i < curios.get().getSlots(); i++) {
                ItemStack stack = curios.get().getStackInSlot(i);

                if (!stack.isEmpty() && stack.getItem() instanceof IEntityResizer resizer) {
                    return resizer;
                }
            }
        }

        return null;
    }
}
