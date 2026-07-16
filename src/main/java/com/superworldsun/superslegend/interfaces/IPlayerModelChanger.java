package com.superworldsun.superslegend.interfaces;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

public interface IPlayerModelChanger {
    @OnlyIn(value = Dist.CLIENT)
    PlayerModel<AbstractClientPlayer> getPlayerModel(AbstractClientPlayer player);

    @OnlyIn(value = Dist.CLIENT)
    ResourceLocation getPlayerTexture(AbstractClientPlayer player);

    /**
     * Returns the model changer from the player's equipped armor or curios, or null if there is none.
     */
    static IPlayerModelChanger get(Player player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof IPlayerModelChanger changer) {
                return changer;
            }
        }

        Optional<IItemHandlerModifiable> curios = CuriosApi.getCuriosHelper().getEquippedCurios(player).resolve();

        if (curios.isPresent()) {
            for (int i = 0; i < curios.get().getSlots(); i++) {
                ItemStack stack = curios.get().getStackInSlot(i);

                if (!stack.isEmpty() && stack.getItem() instanceof IPlayerModelChanger changer) {
                    return changer;
                }
            }
        }

        return null;
    }
}
