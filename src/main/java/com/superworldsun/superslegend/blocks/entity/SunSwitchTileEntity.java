package com.superworldsun.superslegend.blocks.entity;

import com.superworldsun.superslegend.blocks.SunSwitchBlock;
import com.superworldsun.superslegend.light.ILightReceiver;
import com.superworldsun.superslegend.registries.BlockInit;
import com.superworldsun.superslegend.registries.TileEntityInit;

import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;

public class SunSwitchTileEntity extends TileEntity implements ITickableTileEntity, ILightReceiver
{
	private boolean isLit;
	
	public SunSwitchTileEntity()
	{
		super(TileEntityInit.SUN_SWITCH.get());
	}

	//TODO had light shine on a sun block and broke the block it was sitting on and it crashed

	//TODO crashed when i tried breaking the sun block itself with light on it

	@Override
	public void tick()
	{
		if (level != null && !level.isClientSide)
		{
			if (getBlockState().getBlock() instanceof SunSwitchBlock)
			{
				updateSignalStrength();
			}
		}
	}
	
	private void updateSignalStrength()
	{
		int power = 0;
		
		if (isLit)
		{
			power = 15;
		}
		else if (level.dimensionType().hasSkyLight())
		{
			int lightBrightness = level.getBrightness(LightType.SKY, worldPosition) - level.getSkyDarken();
			
			if (lightBrightness > 0)
			{
				float sunAngle = level.getSunAngle(1F);
				float horizonAngle = (float) (sunAngle < Math.PI ? 0F : Math.PI * 2F);
				sunAngle = sunAngle + (horizonAngle - sunAngle) * 0.2F;
				lightBrightness = Math.round((float) lightBrightness * MathHelper.cos(sunAngle));
			}
			
			power = MathHelper.clamp(lightBrightness, 0, 1);
		}
		
		if (getBlockState().getValue(BlockStateProperties.POWER) != power)
		{
			level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockStateProperties.POWER, power).setValue(BlockStateProperties.POWERED, power > 0));
		}
	}
	
	@Override
	public void receiveLight()
	{
		isLit = true;
		level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockStateProperties.POWER, 15).setValue(BlockStateProperties.POWERED, true));
	}
	
	@Override
	public void stopReceivingLight()
	{
		isLit = false;
		level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockStateProperties.POWER, 0).setValue(BlockStateProperties.POWERED, false));
	}
	
	@Override
	public boolean isLit()
	{
		return isLit;
	}
	
	public static TileEntityType<SunSwitchTileEntity> createType()
	{
		return Builder.of(SunSwitchTileEntity::new, BlockInit.SUN_SWITCH.get()).build(null);
	}
}
