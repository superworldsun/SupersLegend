package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class LockedDungeonDoor extends DoorBlock {
	public LockedDungeonDoor(Properties properties) {
		super(properties);
	}

//	@Override
//	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
//		ItemStack heldItem = player.getItemInHand(handIn);
//		boolean holdingKey = heldItem.getItem() == ItemInit.SMALL_KEY.get() || heldItem.getItem() == ItemInit.MAGICAL_KEY.get();
//		if (holdingKey) {
//			SoundEvent sound = SoundInit.DOOR_UNLOCKED.get();
//			worldIn.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
//			// Replace locked door with unlocked door
//			if (!worldIn.isClientSide) {
//				worldIn.destroyBlock(pos, false);
//				if (!player.isCreative()) {
//					heldItem.shrink(1);
//				}
//				return ActionResultType.SUCCESS;
//			}
//		}
//		return ActionResultType.PASS;
//
//	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getItemInHand(handIn);
		boolean holdingKey = heldItem.getItem() == ItemInit.SMALL_KEY.get() || heldItem.getItem() == ItemInit.MAGICAL_KEY.get();
		if (holdingKey) {
			SoundEvent unlockSound = SoundInit.DOOR_UNLOCKED.get();
			worldIn.playSound(player, pos, unlockSound, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!worldIn.isClientSide) {
				BlockState unlockedDoorState = BlockInit.DUNGEON_DOOR.get().defaultBlockState()
						.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF))
						.setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING))
						.setValue(BlockStateProperties.OPEN, false)
						.setValue(BlockStateProperties.POWERED, state.getValue(BlockStateProperties.POWERED))
						.setValue(BlockStateProperties.DOOR_HINGE, state.getValue(BlockStateProperties.DOOR_HINGE));
				worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
					worldIn.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
				} else {
					worldIn.setBlockAndUpdate(pos.below(), Blocks.AIR.defaultBlockState());
				}
				worldIn.setBlockAndUpdate(pos, unlockedDoorState);
				if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
					worldIn.setBlockAndUpdate(pos.above(), unlockedDoorState.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER));
				} else {
					worldIn.setBlockAndUpdate(pos.below(), unlockedDoorState.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
				}
				if (!player.isCreative() && heldItem.getItem() == ItemInit.SMALL_KEY.get()) {
					heldItem.shrink(1);
				}
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}

}
