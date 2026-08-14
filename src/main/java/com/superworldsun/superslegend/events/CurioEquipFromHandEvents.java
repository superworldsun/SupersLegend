package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.wallet.RupeeWalletItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.Map;
import java.util.Optional;

/**
 * Gives every Curio the same right-click-from-hand behavior as wearable armor.
 */
@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public final class CurioEquipFromHandEvents {
    private CurioEquipFromHandEvents() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack heldStack = event.getItemStack();
        // Wallet right-click is reserved for its deposit/withdrawal screen.
        // Wallets can still be equipped normally through the Curios inventory.
        if (heldStack.getItem() instanceof RupeeWalletItem) {
            return;
        }
        ICuriosHelper helper = CuriosApi.getCuriosHelper();
        Optional<ICurio> heldCurio = helper.getCurio(heldStack).resolve();
        Optional<ICuriosItemHandler> curiosHandler = helper.getCuriosHandler(player).resolve();

        if (heldCurio.isEmpty() || curiosHandler.isEmpty()) {
            return;
        }

        EquipTarget target = findBottomMostValidSlot(player, heldStack, heldCurio.get(),
                curiosHandler.get(), helper);
        if (target == null || !canReplaceEquippedStack(target)) {
            return;
        }

        ItemStack equippedStack = target.stacks().getStackInSlot(target.context().index());
        target.stacks().setStackInSlot(target.context().index(), heldStack.copy());
        heldCurio.get().onEquipFromUse(target.context());

        if (equippedStack.isEmpty()) {
            if (!player.getAbilities().instabuild) {
                heldStack.shrink(heldStack.getCount());
            }
        } else {
            player.setItemInHand(event.getHand(), equippedStack.copy());
        }

        event.setCancellationResult(InteractionResult.sidedSuccess(player.level().isClientSide));
        event.setCanceled(true);
    }

    private static EquipTarget findBottomMostValidSlot(Player player, ItemStack heldStack,
                                                        ICurio heldCurio,
                                                        ICuriosItemHandler curiosHandler,
                                                        ICuriosHelper helper) {
        for (Map.Entry<String, ICurioStacksHandler> entry : curiosHandler.getCurios().entrySet()) {
            ICurioStacksHandler slotHandler = entry.getValue();
            IDynamicStackHandler stacks = slotHandler.getStacks();

            // Curios displays increasing indices from top to bottom, so search in reverse.
            for (int index = stacks.getSlots() - 1; index >= 0; index--) {
                SlotContext context = createContext(entry.getKey(), player, index, slotHandler);
                CurioEquipEvent equipEvent = new CurioEquipEvent(heldStack, context);
                MinecraftForge.EVENT_BUS.post(equipEvent);

                if (equipEvent.getResult() == Event.Result.DENY) {
                    continue;
                }

                boolean allowed = equipEvent.getResult() == Event.Result.ALLOW
                        || (helper.isStackValid(context, heldStack) && heldCurio.canEquip(context));
                if (allowed) {
                    return new EquipTarget(stacks, context);
                }
            }
        }
        return null;
    }

    private static boolean canReplaceEquippedStack(EquipTarget target) {
        ItemStack equippedStack = target.stacks().getStackInSlot(target.context().index());
        if (equippedStack.isEmpty()) {
            return true;
        }

        CurioUnequipEvent unequipEvent = new CurioUnequipEvent(equippedStack, target.context());
        MinecraftForge.EVENT_BUS.post(unequipEvent);
        if (unequipEvent.getResult() == Event.Result.DENY) {
            return false;
        }
        if (unequipEvent.getResult() == Event.Result.ALLOW) {
            return true;
        }

        return CuriosApi.getCuriosHelper().getCurio(equippedStack)
                .map(curio -> curio.canUnequip(target.context()))
                .orElse(true);
    }

    private static SlotContext createContext(String identifier, Player player, int index,
                                             ICurioStacksHandler slotHandler) {
        NonNullList<Boolean> renders = slotHandler.getRenders();
        boolean render = index < renders.size() && renders.get(index);
        return new SlotContext(identifier, player, index, false, render);
    }

    private record EquipTarget(IDynamicStackHandler stacks, SlotContext context) {
    }
}
