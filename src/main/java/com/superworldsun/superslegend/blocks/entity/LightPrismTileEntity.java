package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.blocks.LightPrismBlock;
import com.superworldsun.superslegend.light.AbstractLightEmitter;
import com.superworldsun.superslegend.light.BlockLightEmitter;
import com.superworldsun.superslegend.light.ILightReceiver;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class LightPrismTileEntity extends SyncableTileEntity implements ITickableTileEntity, ILightReceiver {
	private static final double ROTATION_SPEED = 0.07;
	public final AbstractLightEmitter lightEmitter = new BlockLightEmitter(this::getLevel, this::getLightDirection, this::getBlockPos);
	private float rotation;
	public float targetRotation;
	private boolean isLit;

	public LightPrismTileEntity() {
		super(TileEntityInit.LIGHT_PRISM.get());
	}

	@Override
	public void tick() {
		if (rotation < targetRotation) {
			rotation += ROTATION_SPEED;

			if (rotation > targetRotation) {
				rotation = targetRotation;
			}

			setChanged();
		}

		if (rotation > Math.PI * 2) {
			rotation -= Math.PI * 2;
			targetRotation -= Math.PI * 2;
			setChanged();
		}

		lightEmitter.tick();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public void receiveLight() {
		isLit = true;
		level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(LightPrismBlock.LIT, true));
	}

	@Override
	public void stopReceivingLight() {
		isLit = false;
		boolean wasLightingSomething = lightEmitter.litObject instanceof ILightReceiver && ((ILightReceiver) lightEmitter).isLit();

		if (wasLightingSomething) {
			ILightReceiver previouslyLitObject = (ILightReceiver) lightEmitter.litObject;
			previouslyLitObject.stopReceivingLight();
		}

		boolean canSetBlockLitValue = level.getBlockState(worldPosition).getOptionalValue(LightPrismBlock.LIT).isPresent();

		if (canSetBlockLitValue) {
			level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(LightPrismBlock.LIT, false));
		}
	}

	@Override
	public boolean isLit() {
		return isLit;
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.putFloat("targetRotation", targetRotation);
		compound.putFloat("rotation", rotation);
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		targetRotation = compound.getFloat("targetRotation");
		rotation = compound.getFloat("rotation");
		super.load(state, compound);
	}

	private Vector3d getLightDirection() {
		return isLit ? new Vector3d(0, 0, 1).yRot(rotation) : Vector3d.ZERO;
	}

	public static TileEntityType<LightPrismTileEntity> createType() {
		return Builder.of(LightPrismTileEntity::new, BlockInit.LIGHT_PRISM.get()).build(null);
	}
}
