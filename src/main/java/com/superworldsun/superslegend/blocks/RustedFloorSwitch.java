package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.List;

public class RustedFloorSwitch extends AbstractPressurePlateBlock
{
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private final SensitivityMod sensitivity;

	public RustedFloorSwitch(SensitivityMod sensitivity, Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
		this.sensitivity = sensitivity;
	}

	protected int getSignalForState(BlockState p_176576_1_) {
		return p_176576_1_.getValue(POWERED) ? 15 : 0;
	}

	protected BlockState setSignalForState(BlockState blockState, int integer) {
		return blockState.setValue(POWERED, Boolean.valueOf(integer > 0));
	}

	//How long it stays down, 30 seconds
	protected int getPressedTime() {
		return 40;
	}

	protected void playOnSound(IWorld world, BlockPos pos) {
		world.playSound((PlayerEntity)null, pos, SoundInit.FLOOR_SWITCH.get(), SoundCategory.BLOCKS, 0.3F, 0.6F);

	}

	protected void playOffSound(IWorld world, BlockPos pos) {
		world.playSound((PlayerEntity)null, pos, SoundInit.FLOOR_SWITCH.get(), SoundCategory.BLOCKS, 0.3F, 0.4F);

	}

	//TODO ALLOW THE PLAYER TO HIT BUTTON BUT AND NOT TRIGGER IT FROM WALKING ON IT.
	@Override
	public void attack(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity)
	{

		if (playerEntity.isHolding(ItemInit.MEGATON_HAMMER.get()) || playerEntity.isHolding(ItemInit.SKULL_HAMMER.get())) {
			BlockPos currentPos = playerEntity.blockPosition();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.SHIELD_BLOCK, SoundCategory.PLAYERS, 1f, 1f);
				int i = this.getSignalForState(state);
				if (i == 0) {
					this.checkPressed(world, pos, state, i);
				}
		}

		super.attack(state, world, pos, playerEntity);
	}

	protected void checkPressed(World p_180666_1_, BlockPos p_180666_2_, BlockState p_180666_3_, int p_180666_4_) {
		int i = this.getSignalStrength(p_180666_1_, p_180666_2_);
		boolean flag = p_180666_4_ > 0;
		boolean flag1 = i > 0;
		if (p_180666_4_ != i) {
			BlockState blockstate = this.setSignalForState(p_180666_3_, i);
			p_180666_1_.setBlock(p_180666_2_, blockstate, 2);
			this.updateNeighbours(p_180666_1_, p_180666_2_);
			p_180666_1_.setBlocksDirty(p_180666_2_, p_180666_3_, blockstate);
		}

		if (!flag1 && flag) {
			this.playOffSound(p_180666_1_, p_180666_2_);
		} else if (flag1 && !flag) {
			this.playOnSound(p_180666_1_, p_180666_2_);
		}

		if (flag1) {
			p_180666_1_.getBlockTicks().scheduleTick(new BlockPos(p_180666_2_), this, this.getPressedTime());
		}

	}

	protected int getSignalStrength(World world, BlockPos pos) {
		AxisAlignedBB axisalignedbb = TOUCH_AABB.move(pos);
		List<? extends Entity> list;
		switch(this.sensitivity) {
			case ENDERDRAGON:
				list = world.getEntitiesOfClass(EnderDragonEntity.class, axisalignedbb);
				break;
			default:
				return 0;
		}

		if (!list.isEmpty()) {
			for(Entity entity : list) {
				if (!entity.isIgnoringBlockTriggers()) {
					return 0;
				}
			}
		}

		return 15;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
		state.add(POWERED);
	}

	public static enum SensitivityMod {
		ENDERDRAGON;
	}
}