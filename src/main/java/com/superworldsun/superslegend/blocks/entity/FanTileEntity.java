package com.superworldsun.superslegend.blocks.entity;

import java.util.List;

import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;

public class FanTileEntity extends TileEntity implements ITickableTileEntity {
	private static final double AIRFLOW_STRENGTH = 0.3D;
	private static final int AIRFLOW_MAX_DISTANCE = 16;
	public float bladesRotation;

	public FanTileEntity() {
		super(TileEntityInit.FAN.get());
	}

	public FanTileEntity(TileEntityType<? extends FanTileEntity> type) {
		super(type);
	}

	@Override
	public void tick() {
		if (!isPowered()) {
			return;
		}

		getPushedEntities().stream().filter(this::canPush).forEach(this::pushEntity);
	}

	private void pushEntity(Entity entity) {
		entity.move(MoverType.PISTON, getAirflowDirection());
	}

	private boolean canPush(Entity entity) {
		return !(entity instanceof LivingEntity) || ((LivingEntity) entity).getItemBySlot(EquipmentSlotType.FEET).getItem() != ItemInit.IRON_BOOTS.get();
	}

	private List<Entity> getPushedEntities() {
		return level.getEntities(null, getAirflowArea());
	}

	private AxisAlignedBB getAirflowArea() {
		AxisAlignedBB airflowArea = new AxisAlignedBB(worldPosition, worldPosition.offset(1, 1, 1));
		Direction fanDirection = getFanDirection();

		for (int i = 1; i < AIRFLOW_MAX_DISTANCE; i++) {
			BlockPos checkingPos = worldPosition.relative(fanDirection, i);

			if (isAirflowBlockedAt(checkingPos)) {
				break;
			}

			airflowArea = airflowArea.expandTowards(fanDirection.getStepX(), fanDirection.getStepY(), fanDirection.getStepZ());
		}

		return airflowArea;
	}

	private boolean isAirflowBlockedAt(BlockPos blockPos) {
		VoxelShape blockShape = level.getBlockState(blockPos).getCollisionShape(level, blockPos);
		return !level.isEmptyBlock(blockPos) && blockShape != VoxelShapes.empty() && blockShape.max(Axis.Y) >= 0.75D && blockShape.min(Axis.Y) <= 0.75D;
	}

	public Direction getFanDirection() {
		return level.getBlockState(worldPosition).getValue(DirectionalBlock.FACING);
	}

	private Vector3d getAirflowDirection() {
		return new Vector3d(getFanDirection().step()).multiply(AIRFLOW_STRENGTH, AIRFLOW_STRENGTH, AIRFLOW_STRENGTH);
	}

	public boolean isPowered() {
		return true;
	}

	public static TileEntityType<FanTileEntity> createType() {
		return Builder.of(FanTileEntity::new, BlockInit.FAN.get()).build(null);
	}

	public static TileEntityType<SwitchableFanTileEntity> createSwitchableFanType() {
		return Builder.of(SwitchableFanTileEntity::new, BlockInit.SWITCHABLE_FAN.get()).build(null);
	}
}
