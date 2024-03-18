package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class LockedDungeonDoor extends DoorBlock {
    public LockedDungeonDoor(Properties pProperties, BlockSetType pType) {
        super(pProperties, pType);
    }

    //TODO, make it so the door cant be opened with redstone
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockstate, @NotNull Level worldIn, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult blocktrace) {
        ItemStack heldItem = player.getItemInHand(hand);
        boolean holdingKey = heldItem.getItem() == ItemInit.SMALL_KEY.get() || heldItem.getItem() == ItemInit.MAGICAL_KEY.get();
        if (holdingKey) {
            SoundEvent unlockSound = SoundInit.DOOR_UNLOCKED.get();
            worldIn.playSound(player, pos, unlockSound, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!worldIn.isClientSide) {
                BlockState unlockedDoorState = BlockInit.DUNGEON_DOOR.get().defaultBlockState()
                        .setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, blockstate.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF))
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, blockstate.getValue(BlockStateProperties.HORIZONTAL_FACING))
                        .setValue(BlockStateProperties.OPEN, false)
                        .setValue(BlockStateProperties.POWERED, blockstate.getValue(BlockStateProperties.POWERED))
                        .setValue(BlockStateProperties.DOOR_HINGE, blockstate.getValue(BlockStateProperties.DOOR_HINGE));
                worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                if (blockstate.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                    worldIn.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
                } else {
                    worldIn.setBlockAndUpdate(pos.below(), Blocks.AIR.defaultBlockState());
                }
                worldIn.setBlockAndUpdate(pos, unlockedDoorState);
                if (blockstate.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
                    worldIn.setBlockAndUpdate(pos.above(), unlockedDoorState.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER));
                } else {
                    worldIn.setBlockAndUpdate(pos.below(), unlockedDoorState.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
                }
                if (!player.isCreative() && heldItem.getItem() == ItemInit.SMALL_KEY.get()) {
                    heldItem.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
