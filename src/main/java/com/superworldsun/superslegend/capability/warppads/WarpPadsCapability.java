package com.superworldsun.superslegend.capability.warppads;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.superworldsun.superslegend.blocks.WarpPadBlock;

import net.minecraft.util.math.BlockPos;

public class WarpPadsCapability {
	private final Map<WarpPadBlock, BlockPos> warpPositions = new HashMap<>();

	/**
	 * Only for reading, to add warp position use {@link WarpPadsCapability#saveWarpPosition(WarpPadBlock, BlockPos)}
	 * 
	 * @return Copy of warp positions saved by player
	 */
	Map<WarpPadBlock, BlockPos> getWarpPositions() {
		return new HashMap<>(warpPositions);
	}

	Optional<BlockPos> getWarpPosition(WarpPadBlock warpPadBlock) {
		return Optional.ofNullable(warpPositions.get(warpPadBlock));
	}

	void saveWarpPosition(WarpPadBlock warpPadBlock, BlockPos position) {
		warpPositions.put(warpPadBlock, position);
	}

	void saveWarpPosition(Pair<WarpPadBlock, BlockPos> warpPosition) {
		saveWarpPosition(warpPosition.getLeft(), warpPosition.getRight());
	}
}
