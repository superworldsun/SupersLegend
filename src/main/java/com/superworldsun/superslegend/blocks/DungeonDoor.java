package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class DungeonDoor extends DoorBlock {

	public DungeonDoor(Properties properties) {
		super(properties);
	}

	/*public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ActionResultType result = super.use(state, world, pos, player, hand, hit);
		if (result == ActionResultType.SUCCESS) {
			if (state.getValue(DoorBlock.OPEN)) {
				world.playSound(player, pos, SoundInit.DOOR_UNLOCKED.get(), SoundCategory.BLOCKS, 2.0F, 1.0F);
			} else {
				world.playSound(player, pos, SoundInit.DOOR_UNLOCKED.get(), SoundCategory.BLOCKS, 1.0F, 0.8F);
			}
		}
		return result;
	}*/
}