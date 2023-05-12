package com.superworldsun.superslegend.capability.warppads;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;

import com.superworldsun.superslegend.blocks.WarpPadBlock;

import net.minecraft.util.math.BlockPos;

/**
 * 
 * @author Daripher
 *
 */
public class WarpPadsCapability {
	private final Map<WarpPadBlock, BlockPos> warpPositions = new HashMap<>();

	/**
	 * Only for reading, to add warp position use
	 * {@link WarpPadsCapability#saveWarpPosition(WarpPadBlock, BlockPos)}
	 * 
	 * @return Returns copy of warp positions map saved by player
	 * 
	 * @see WarpPadsCapability#getWarpPosition(WarpPadBlock)
	 */
	@Nonnull
	Map<WarpPadBlock, BlockPos> getWarpPositions() {
		return new HashMap<>(warpPositions);
	}

	/**
	 * 
	 * @param warpPadBlock Warp pad block whose associated position is to be returned
	 * 
	 * @return Returns optional position to which the specified warp pad block is associated with.
	 *         Returns empty optional if the capability contains no saved position for the warp pad.
	 * 
	 * @see WarpPadsCapability#getWarpPositions()
	 */
	@Nonnull
	Optional<BlockPos> getWarpPosition(WarpPadBlock warpPadBlock) {
		return Optional.ofNullable(warpPositions.get(warpPadBlock));
	}

	/**
	 * Associates the specified position with the specified warp pad block. If the capability previously
	 * contained a position for the block, the old position is replaced by the specified position.
	 * 
	 * @param warpPadBlock Warp pad block with which the specified position is to be associated.
	 * @param position     Block position to be associated with the specified warp pad.
	 * 
	 * @return Optional value of block position previously associated with provided warp pad block.
	 * 
	 * @see WarpPadsCapability#saveWarpPosition(Pair)
	 */
	@Nonnull
	Optional<BlockPos> saveWarpPosition(WarpPadBlock warpPadBlock, BlockPos position) {
		return Optional.ofNullable(warpPositions.put(warpPadBlock, position));
	}

	/**
	 * Associates the specified position with the specified warp pad block. If the capability previously
	 * contained a position for the block, the old position is replaced by the specified position.
	 * 
	 * @param warpPosition Pair of ward pad block and its position to be associated with each other.
	 * 
	 * @return Optional value of block position previously associated with provided warp pad block.
	 * 
	 * @see WarpPadsCapability#saveWarpPosition(WarpPadBlock, BlockPos)
	 */
	@Nonnull
	Optional<BlockPos> saveWarpPosition(Pair<WarpPadBlock, BlockPos> warpPosition) {
		return saveWarpPosition(warpPosition.getLeft(), warpPosition.getRight());
	}
}
