package com.superworldsun.superslegend.entities;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.advancement.ModAdvancementHelper;
import com.superworldsun.superslegend.registries.EntityTypeInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** The stationary, collectible token left by a defeated Gold Skulltula. */
public class GoldSkulltulaTokenEntity extends ItemEntity {
    private static final String TOKENS_COLLECTED_TAG =
            SupersLegendMain.MOD_ID + ":GoldSkulltulaTokensCollected";
    private static final String PICKUP_RECORDED_TAG =
            SupersLegendMain.MOD_ID + ":PickupRecorded";
    private boolean remoteToolPickup;

    public GoldSkulltulaTokenEntity(EntityType<? extends GoldSkulltulaTokenEntity> entityType, Level level) {
        super(entityType, level);
        setNoGravity(true);
    }

    public GoldSkulltulaTokenEntity(Level level, double x, double y, double z, ItemStack stack) {
        this(EntityTypeInit.GOLD_SKULLTULA_TOKEN.get(), level);
        setPos(x, y, z);
        setItem(stack);
        setDeltaMovement(0.0D, 0.0D, 0.0D);
        setNoGravity(true);
        lifespan = stack.getEntityLifespan(level);
    }

    @Override
    public void tick() {
        setNoGravity(true);
        super.tick();

        // Keep the reward suspended where the Skulltula died. It remains a normal
        // ItemEntity subclass so players, hookshots and boomerangs can collect it.
        if (!isRemoved() && !noPhysics) {
            setDeltaMovement(0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void playerTouch(Player player) {
        int countBeforePickup = getItem().getCount();
        super.playerTouch(player);
        int countAfterPickup = isRemoved() ? 0 : getItem().getCount();

        if (!level().isClientSide && countAfterPickup < countBeforePickup) {
            level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundInit.SMALL_ITEM_FANFARE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            if (player instanceof ServerPlayer serverPlayer
                    && !getPersistentData().getBoolean(PICKUP_RECORDED_TAG)) {
                getPersistentData().putBoolean(PICKUP_RECORDED_TAG, true);
                recordCollectedToken(serverPlayer);
                if (remoteToolPickup) {
                    ModAdvancementHelper.award(serverPlayer, "token_from_afar", "retrieved_token");
                }
            }
        }
    }

    public boolean collectWithRemoteTool(Player player) {
        int countBeforePickup = getItem().getCount();
        setNoPickUpDelay();
        remoteToolPickup = true;
        try {
            playerTouch(player);
        } finally {
            remoteToolPickup = false;
        }
        return !isAlive() || getItem().getCount() < countBeforePickup;
    }

    public void markRemoteToolPickup() {
        remoteToolPickup = true;
    }

    private static void recordCollectedToken(ServerPlayer player) {
        CompoundTag root = player.getPersistentData();
        CompoundTag data = root.getCompound(Player.PERSISTED_NBT_TAG);
        int total = data.getInt(TOKENS_COLLECTED_TAG) + 1;
        data.putInt(TOKENS_COLLECTED_TAG, total);
        root.put(Player.PERSISTED_NBT_TAG, data);

        player.displayClientMessage(Component.translatable(
                "superslegend.message.gold_skulltula_tokens_collected", total
        ).withStyle(ChatFormatting.YELLOW), true);

        if (total >= 1) {
            ModAdvancementHelper.award(player, "gold_skulltula_hunter", "tokens");
        }
        if (total >= 50) {
            ModAdvancementHelper.award(player, "gold_skulltula_collector", "tokens");
        }
        if (total >= 100) {
            ModAdvancementHelper.award(player, "gold_skulltula_master", "tokens");
        }
    }
}
