package com.superworldsun.superslegend.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.superworldsun.superslegend.util.IExtendedPlayer;
import com.superworldsun.superslegend.util.IResizableEntity;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity implements IResizableEntity, IExtendedPlayer
{
	private float scale = 1.0F;
	private float prevScale = 1.0F;
	private int hoverTime;
	
	// This constructor is fake and never used
	protected MixinPlayerEntity(EntityType<? extends LivingEntity> type, World world)
	{
		super(type, world);
	}
	
	@Overwrite
	public EntitySize getDimensions(Pose pose)
	{
		return POSES.getOrDefault(pose, STANDING_DIMENSIONS).scale(getScale());
	}
	
	@Overwrite
	public float getStandingEyeHeight(Pose pose, EntitySize size)
	{
		switch (pose)
		{
			case SWIMMING:
			case FALL_FLYING:
			case SPIN_ATTACK:
				return 0.4F * getScale();
			case CROUCHING:
				return 1.27F * getScale();
			default:
				return 1.62F * getScale();
		}
	}
	
	@Inject(method = "tick", at = @At("HEAD"))
	private void injectTick(CallbackInfo ci)
	{
		prevScale = scale;
		
		// Increase movement if we are bigger, decrease if we are smaller
		if (scale > 1)
		{
			float bonusSpeed = scale - 1;
			move(MoverType.SELF, getDeltaMovement().multiply(bonusSpeed, bonusSpeed, bonusSpeed));
		}
		else if (scale < 1)
		{
			float bonusSpeed = (scale - 1) / 2;
			setBoundingBox(getBoundingBox().move(getDeltaMovement().multiply(bonusSpeed, bonusSpeed, bonusSpeed)));
		}
	}
	
	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		refreshDimensions();
	}
	
	public AxisAlignedBB getBoundingBoxForCulling()
	{
		return getBoundingBoxForPose(getPose());
	}
	
	public float getScale()
	{
		return scale;
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
		updateEyeHeight();
		refreshDimensions();
	}
	
	public float getScaleForRender(float partialTick)
	{
		return prevScale + (scale - prevScale) * partialTick;
	}
	
	private void updateEyeHeight()
	{
		eyeHeight = getDimensions(getPose()).height * 0.85F;
	}
	
	public int getHoverTime()
	{
		return hoverTime;
	}
	
	public void setHoverTime(int amount)
	{
		hoverTime = amount;
	}
	
	public int increaseHoverTime()
	{
		return ++hoverTime;
	}
	
	// Everything below is taken from original class
	@Shadow
	private static Map<Pose, EntitySize> POSES;
	@Shadow
	private static EntitySize STANDING_DIMENSIONS;
}
