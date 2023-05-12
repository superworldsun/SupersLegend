package com.superworldsun.superslegend.capability.mana;

import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SyncManaMessage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class ManaHelper {
	private static LazyOptional<ManaCapability> getCapability(PlayerEntity player) {
		return player.getCapability(ManaCapabilityProvider.CAPABILITY);
	}

	public static float getMana(PlayerEntity player) {
		return getCapability(player).map(ManaCapability::getMana).orElse(0F);
	}

	public static boolean isFullMana(PlayerEntity player) {
		return getCapability(player).map(ManaCapability::isFullMana).orElse(false);
	}

	public static boolean hasMana(PlayerEntity player, float amount) {
		if (player.isCreative()) {
			return true;
		}

		return getCapability(player).map(capability -> capability.hasMana(amount)).orElse(false);
	}

	public static void restoreMana(PlayerEntity player, float amount) {
		getCapability(player).ifPresent(capability -> {
			capability.restoreMana(amount);
			sycnIfNeeded(player);
		});
	}

	public static void spendMana(PlayerEntity player, float amount) {
		if (player.isCreative()) {
			return;
		}

		getCapability(player).ifPresent(capability -> {
			capability.spendMana(amount);
			sycnIfNeeded(player);
		});
	}

	public static void restoreManaToFull(PlayerEntity player) {
		restoreMana(player, getMaxMana(player));
	}

	public static float getMaxMana(PlayerEntity player) {
		return getCapability(player).map(ManaCapability::getMaxMana).orElse(0F);
	}

	private static void sycnIfNeeded(PlayerEntity player) {
		if (!player.level.isClientSide) {
			syncManaCapabilityWithSelf((ServerPlayerEntity) player);
		}
	}

	private static void syncManaCapabilityWithSelf(ServerPlayerEntity player) {
		NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> player), new SyncManaMessage(player));
	}
}
