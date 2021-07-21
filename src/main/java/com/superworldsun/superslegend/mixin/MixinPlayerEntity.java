package com.superworldsun.superslegend.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.IHoveringEntity;
import com.superworldsun.superslegend.util.IResizableEntity;
import com.superworldsun.superslegend.util.ISwimmingEntity;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity extends LivingEntity implements IResizableEntity, IHoveringEntity, ISwimmingEntity
{
	private float scale = 1.0F;
	private float prevScale = 1.0F;
	private int hoverTime;
	private int hoverHeight;
	
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
	public float getScale()
	{
		return scale;
	}
	
	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		refreshDimensions();
	}
	
	@Override
	public AxisAlignedBB getBoundingBoxForCulling()
	{
		return getBoundingBoxForPose(getPose());
	}
	
	@Override
	public void setScale(float scale)
	{
		this.scale = scale;
		updateEyeHeight();
		refreshDimensions();
	}
	
	@Override
	public float getScaleForRender(float partialTick)
	{
		return prevScale + (scale - prevScale) * partialTick;
	}
	
	@Override
	public int getHoverTime()
	{
		return hoverTime;
	}
	
	@Override
	public void setHoverTime(int amount)
	{
		hoverTime = amount;
	}
	
	@Override
	public int increaseHoverTime()
	{
		return ++hoverTime;
	}
	
	@Override
	public void setHoverHeight(int height)
	{
		hoverHeight = height;
	}
	
	@Override
	public int getHoverHeight()
	{
		return hoverHeight;
	}
	
	@Override
	public boolean isSwimming()
	{
		return canSwim() && !abilities.flying && !this.isSpectator() && super.isSwimming();
	}
	
	@Override
	public boolean canSwim()
	{
		return getItemBySlot(EquipmentSlotType.FEET).getItem() != ItemInit.IRON_BOOTS.get();
	}
	
	private void updateEyeHeight()
	{
		eyeHeight = getDimensions(getPose()).height * 0.85F;
	}
	
	/* Everything below is taken from original class */
	
	@Shadow
	private static Map<Pose, EntitySize> POSES;
	
	@Shadow
	private static EntitySize STANDING_DIMENSIONS;
	
	@Shadow
	public PlayerAbilities abilities;
	
	@Shadow
	public Iterable<ItemStack> getArmorSlots()
	{
		return null;
	}
	
	@Shadow
	public ItemStack getItemBySlot(EquipmentSlotType slot)
	{
		return null;
	}
	
	@Shadow
	public void setItemSlot(EquipmentSlotType slot, ItemStack stack)
	{		
	}
	
	@Shadow
	public HandSide getMainArm()
	{
		return null;
	}
}
