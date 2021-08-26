package com.superworldsun.superslegend.blocks.tile;

import com.superworldsun.superslegend.blocks.LightEmitterBlock;
import com.superworldsun.superslegend.light.AbstractLightEmitter;
import com.superworldsun.superslegend.light.BlockLightEmitter;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class LightEmitterTileEntity extends TileEntity implements ITickableTileEntity
{
	public final AbstractLightEmitter lightEmitter = new BlockLightEmitter(this::getLevel, this::getLightDirecion, this::getBlockPos);
	
	public LightEmitterTileEntity(Direction direction)
	{
		super(TileEntityInit.LIGHT_EMITTER.get());
	}
	
	private LightEmitterTileEntity()
	{
		super(TileEntityInit.LIGHT_EMITTER.get());
	}
	
	@Override
	public void tick()
	{
		lightEmitter.tick();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	private Vector3d getLightDirecion()
	{
		return new Vector3d(getBlockState().getValue(LightEmitterBlock.FACING).step());
	}
	
	public static TileEntityType<LightEmitterTileEntity> createType()
	{
		return Builder.of(LightEmitterTileEntity::new, BlockInit.LIGHT_EMITTER.get()).build(null);
	}
}
