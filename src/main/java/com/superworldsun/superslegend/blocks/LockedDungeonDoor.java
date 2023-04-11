package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class LockedDungeonDoor extends DoorBlock {
	public LockedDungeonDoor(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getItemInHand(handIn);
		if (heldItem.getItem() == ItemInit.SMALL_KEY.get()) {
			SoundEvent sound = SoundInit.DOOR_UNLOCKED.get();
			worldIn.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
			// Replace locked door with unlocked door
			if (!worldIn.isClientSide) {
				worldIn.destroyBlock(pos, false);
				if (!player.isCreative()) {
					heldItem.shrink(1);
				}
				return ActionResultType.SUCCESS;
			}
		}
		if (heldItem.getItem() == ItemInit.MAGICAL_KEY.get()) {
			SoundEvent sound = SoundInit.DOOR_UNLOCKED.get();
			worldIn.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
			// Replace locked door with unlocked door
			if (!worldIn.isClientSide) {
				worldIn.destroyBlock(pos, false);
				return ActionResultType.SUCCESS;
			}
		}
			return ActionResultType.PASS;

	}

	//TODO Try and make it so when the player right clicks a locked door with a key, the door turns into a unlocked door instead of breaking
	/*@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getItemInHand(handIn);
		if (heldItem.getItem() == ItemInit.SMALL_KEY.get() || heldItem.getItem() == ItemInit.MAGICAL_KEY.get()) {
			SoundEvent sound = SoundInit.DOOR_UNLOCKED.get();
			worldIn.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
			// Replace locked door with unlocked door
			if (!worldIn.isClientSide) {
				BlockState unlockedDoorState = BlockInit.DUNGEON_DOOR.get().defaultBlockState()
						.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF))
						.setValue(BlockStateProperties.FACING, state.getValue(BlockStateProperties.FACING))
						.setValue(BlockStateProperties.OPEN, state.getValue(BlockStateProperties.OPEN))
						.setValue(BlockStateProperties.POWERED, state.getValue(BlockStateProperties.POWERED))
						.setValue(BlockStateProperties.DOOR_HINGE, state.getValue(BlockStateProperties.DOOR_HINGE))
						.setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));
				worldIn.setBlock(pos, unlockedDoorState, 3);
				if (!player.isCreative()) {
					heldItem.shrink(1);
				}
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}*/

}
