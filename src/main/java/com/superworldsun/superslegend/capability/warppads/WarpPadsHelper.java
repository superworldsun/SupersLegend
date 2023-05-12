package com.superworldsun.superslegend.capability.warppads;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.blocks.WarpPadBlock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;

public class WarpPadsHelper {
	private static LazyOptional<WarpPadsCapability> getCapability(PlayerEntity player) {
		return player.getCapability(WarpPadsCapabilityProvider.CAPABILITY);
	}

	@Nullable
	public static BlockPos getWarpPosition(PlayerEntity player, WarpPadBlock warpPad) {
		return getCapability(player).map(capability -> capability.getWarpPosition(warpPad)).orElse(null);
	}

	public static void saveWarpPosition(PlayerEntity player, WarpPadBlock warpPad, BlockPos pos) {
		getCapability(player).ifPresent(capability -> capability.saveWarpPosition(warpPad, pos));
	}
}
