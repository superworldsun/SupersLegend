package com.superworldsun.superslegend.capability.warppads;

import java.util.Optional;

import com.superworldsun.superslegend.blocks.WarpPadBlock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;

public class WarpPadsHelper {
	private static LazyOptional<WarpPadsCapability> getCapability(PlayerEntity player) {
		return player.getCapability(WarpPadsCapabilityProvider.CAPABILITY);
	}

	public static Optional<BlockPos> getWarpPosition(PlayerEntity player, WarpPadBlock warpPad) {
		return getCapability(player).map(capability -> capability.getWarpPosition(warpPad)).orElse(Optional.empty());
	}

	public static void saveWarpPosition(PlayerEntity player, WarpPadBlock warpPad, BlockPos pos) {
		if (player.level.isClientSide) {
			return;
		}
		getCapability(player).ifPresent(capability -> {
			Optional<BlockPos> previousPos = capability.saveWarpPosition(warpPad, pos);
			if (previousPos.isPresent() && !previousPos.get().equals(pos)) {
				player.sendMessage(new TranslationTextComponent("superslegend.message.warp_saved"), Util.NIL_UUID);
			}
		});
	}
}
