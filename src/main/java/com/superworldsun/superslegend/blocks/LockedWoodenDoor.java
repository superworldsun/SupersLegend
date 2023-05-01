package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class LockedWoodenDoor extends DoorBlock {
	public LockedWoodenDoor(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getItemInHand(handIn);
		if (heldItem.getItem() == ItemInit.SMALL_KEY.get() || heldItem.getItem() == ItemInit.MAGICAL_KEY.get()) {
			SoundEvent sound = SoundInit.DOOR_UNLOCKED.get();
			worldIn.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
			// Replace locked door with unlocked door
			if (!worldIn.isClientSide) {
				worldIn.destroyBlock(pos, false);
				if (!player.isCreative() && heldItem.getItem() == ItemInit.SMALL_KEY.get()) {
					heldItem.shrink(1);
				}
				return ActionResultType.SUCCESS;
			}
		}
			return ActionResultType.PASS;
	}

}
