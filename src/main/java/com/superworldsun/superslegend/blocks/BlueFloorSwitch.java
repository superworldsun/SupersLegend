package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.List;

public class BlueFloorSwitch extends AbstractPressurePlateBlock
{
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private final SensitivityMod sensitivity;

	public BlueFloorSwitch(SensitivityMod p_i48348_1_, Properties p_i48348_2_) {
		super(p_i48348_2_);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
		this.sensitivity = p_i48348_1_;
	}

	protected int getSignalForState(BlockState p_176576_1_) {
		return p_176576_1_.getValue(POWERED) ? 15 : 0;
	}

	protected BlockState setSignalForState(BlockState p_176575_1_, int p_176575_2_) {
		return p_176575_1_.setValue(POWERED, Boolean.valueOf(p_176575_2_ > 0));
	}

	//How long it stays down, .5 seconds
	protected int getPressedTime() {
		return 5;
	}

	protected void playOnSound(IWorld world, BlockPos pos) {
		world.playSound((PlayerEntity)null, pos, SoundInit.FLOOR_SWITCH.get(), SoundCategory.BLOCKS, 0.3F, 0.6F);

	}

	protected void playOffSound(IWorld world, BlockPos pos) {
		world.playSound((PlayerEntity)null, pos, SoundInit.FLOOR_SWITCH.get(), SoundCategory.BLOCKS, 0.3F, 0.4F);

	}

	protected int getSignalStrength(World world, BlockPos pos) {
		AxisAlignedBB axisalignedbb = TOUCH_AABB.move(pos);
		List<? extends Entity> list;
		switch(this.sensitivity) {
			case EVERYTHING:
				list = world.getEntities((Entity)null, axisalignedbb);
				break;
			case MOBS:
				list = world.getEntitiesOfClass(LivingEntity.class, axisalignedbb);
				break;
			case PLAYER:
				list = world.getEntitiesOfClass(PlayerEntity.class, axisalignedbb);
				break;
			default:
				return 0;
		}

		if (!list.isEmpty()) {
			for(Entity entity : list) {
				if (!entity.isIgnoringBlockTriggers()) {
					return 15;
				}
			}
		}

		return 0;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(POWERED);
	}

	public static enum SensitivityMod {
		EVERYTHING,
		MOBS,
		PLAYER;
	}
}