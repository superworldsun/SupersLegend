package com.superworldsun.superslegend.blocks.tile;

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

public class FanTileEntity extends TileEntity implements ITickableTileEntity
{
	public float bladesRotation;
	
	public FanTileEntity()
	{
		super(TileEntityInit.FAN.get());
	}
	
	public FanTileEntity(TileEntityType<? extends FanTileEntity> type)
	{
		super(type);
	}
	
	@Override
	public void tick()
	{
		if (isPowered())
		{
			List<Entity> affectedEntities = level.getEntities(null, getCoveredArea());
			affectedEntities.forEach(entity ->
			{
				// If no iron boots equipped
				if (!(entity instanceof LivingEntity) || ((LivingEntity) entity).getItemBySlot(EquipmentSlotType.FEET).getItem() != ItemInit.IRON_BOOTS.get())
				{
					entity.move(MoverType.PISTON, getAirflowVector());
				}
			});
		}
	}
	
	private AxisAlignedBB getCoveredArea()
	{
		Direction fanDirection = getFanDirection();
		AxisAlignedBB area = new AxisAlignedBB(worldPosition, worldPosition.offset(1, 1, 1));
		
		for (int i = 1; i < 16; i++)
		{
			BlockPos checkingPos = worldPosition.relative(fanDirection, i);
			VoxelShape checkingCollisionShape = level.getBlockState(checkingPos).getCollisionShape(level, checkingPos);
			
			if (!level.isEmptyBlock(checkingPos) && checkingCollisionShape != VoxelShapes.empty() && checkingCollisionShape.max(Axis.Y) >= 0.75D
					&& checkingCollisionShape.min(Axis.Y) <= 0.75D)
			{
				break;
			}
			
			area = area.expandTowards(fanDirection.getStepX(), fanDirection.getStepY(), fanDirection.getStepZ());
		}
		
		return area;
	}
	
	public Direction getFanDirection()
	{
		return level.getBlockState(worldPosition).getValue(DirectionalBlock.FACING);
	}
	
	private Vector3d getAirflowVector()
	{
		return new Vector3d(getFanDirection().step()).multiply(0.3D, 0.3D, 0.3D);
	}
	
	public boolean isPowered()
	{
		return true;
	}
	
	public static TileEntityType<FanTileEntity> createType()
	{
		return Builder.of(FanTileEntity::new, BlockInit.FAN.get()).build(null);
	}
	
	public static TileEntityType<SwitchableFanTileEntity> createSwitchableFanType()
	{
		return Builder.of(SwitchableFanTileEntity::new, BlockInit.SWITCHABLE_FAN.get()).build(null);
	}
}
