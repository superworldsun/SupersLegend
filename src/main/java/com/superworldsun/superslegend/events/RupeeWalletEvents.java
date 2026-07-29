package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.RupeeEntity;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.RupeeValue;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public final class RupeeWalletEvents {
    private static final String STARTER_WALLET_GRANTED = "SupersLegendStarterWalletGranted";

    private RupeeWalletEvents() {
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        CompoundTag persisted = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        if (persisted.getBoolean(STARTER_WALLET_GRANTED)) {
            return;
        }

        ItemStack starterWallet = new ItemStack(ItemInit.WALLET.get());
        boolean placedInWalletSlot = CuriosApi.getCuriosHelper().getCuriosHandler(player)
                .resolve()
                .flatMap(handler -> handler.getStacksHandler("wallet"))
                .filter(handler -> handler.getSlots() > 0 && handler.getStacks().getStackInSlot(0).isEmpty())
                .map(handler -> {
                    handler.getStacks().setStackInSlot(0, starterWallet);
                    return true;
                })
                .orElse(false);

        if (!placedInWalletSlot && !player.getInventory().add(starterWallet)) {
            player.spawnAtLocation(starterWallet);
        }

        persisted.putBoolean(STARTER_WALLET_GRANTED, true);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, persisted);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRupeePickup(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        ItemEntity entity = event.getItem();
        ItemStack sourceStack = entity.getItem();
        RupeeValue sourceRupee = RupeeValue.from(sourceStack);

        if (sourceRupee == null || RupeeWalletUtil.findEquippedWallet(player).isEmpty()) {
            return;
        }

        event.setCanceled(true);

        int originalCount = sourceStack.getCount();
        int totalValue = RupeeValue.totalValue(sourceStack);
        int walletAccepted = RupeeWalletUtil.deposit(player, totalValue);
        int valueRemaining = totalValue - walletAccepted;
        int inventoryAccepted = 0;
        List<ItemStack> inventoryOverflow = new ArrayList<>();

        for (ItemStack denomination : RupeeValue.splitIntoStacks(valueRemaining)) {
            RupeeValue denominationType = RupeeValue.from(denomination);
            int before = denomination.getCount();
            player.getInventory().add(denomination);
            int inserted = before - denomination.getCount();
            inventoryAccepted += inserted * denominationType.value();
            if (!denomination.isEmpty()) {
                inventoryOverflow.add(denomination.copy());
            }
        }

        if (walletAccepted == 0 && inventoryAccepted == 0) {
            return;
        }

        player.take(entity, originalCount);
        entity.discard();

        for (ItemStack overflow : inventoryOverflow) {
            ItemEntity overflowEntity = new ItemEntity(player.level(), player.getX(), player.getY() + 0.25D,
                    player.getZ(), overflow);
            overflowEntity.setDefaultPickUpDelay();
            player.level().addFreshEntity(overflowEntity);
        }
    }
}
