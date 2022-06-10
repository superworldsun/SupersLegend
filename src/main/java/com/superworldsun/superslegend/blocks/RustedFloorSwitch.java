package com.superworldsun.superslegend.blocks;

import java.util.Random;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class RustedFloorSwitch extends AbstractPressurePlateBlock
{
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	
	public RustedFloorSwitch()
	{
		super(AbstractBlock.Properties.of(Material.METAL).strength(5f).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE));
		registerDefaultState(stateDefinition.any().setValue(POWERED, false));
	}
	
	@Override
	protected int getSignalForState(BlockState blockState)
	{
		return blockState.getValue(POWERED) ? 15 : 0;
	}
	
	@Override
	protected BlockState setSignalForState(BlockState blockState, int signal)
	{
		return blockState.setValue(POWERED, signal > 0);
	}
	
	@Override
	protected int getPressedTime()
	{
		return 40;
	}
	
	@Override
	protected void playOnSound(IWorld world, BlockPos pos)
	{
		world.playSound(null, pos, SoundInit.FLOOR_SWITCH.get(), SoundCategory.BLOCKS, 0.3F, 0.6F);
	}
	
	@Override
	protected void playOffSound(IWorld world, BlockPos pos)
	{
		world.playSound(null, pos, SoundInit.FLOOR_SWITCH.get(), SoundCategory.BLOCKS, 0.3F, 0.4F);
	}
	
	@Override
	public void attack(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity)
	{
		if (playerEntity.isHolding(ItemInit.MEGATON_HAMMER.get()) || playerEntity.isHolding(ItemInit.SKULL_HAMMER.get()))
		{
			world.playSound(null, playerEntity, SoundEvents.SHIELD_BLOCK, SoundCategory.PLAYERS, 1f, 1f);
			int signal = getSignalForState(blockState);
			
			if (signal == 0)
			{
				world.setBlockAndUpdate(blockPos, setSignalForState(blockState, 15));
				world.getBlockTicks().scheduleTick(blockPos, this, getPressedTime());
				playOnSound(world, blockPos);
			}
		}
	}
	
	@Override
	public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random)
	{
		int signal = this.getSignalForState(blockState);
		
		if (signal > 0)
		{
			serverWorld.setBlockAndUpdate(blockPos, setSignalForState(blockState, 0));
			playOffSound(serverWorld, blockPos);
		}
	}
	
	@Override
	protected int getSignalStrength(World world, BlockPos blockPos)
	{
		return getSignalForState(world.getBlockState(blockPos));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state)
	{
		state.add(POWERED);
	}
}