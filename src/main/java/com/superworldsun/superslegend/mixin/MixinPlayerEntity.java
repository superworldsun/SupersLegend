package com.superworldsun.superslegend.mixin;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.superworldsun.superslegend.interfaces.IEntityResizer;
import com.superworldsun.superslegend.interfaces.IHoveringEntity;
import com.superworldsun.superslegend.interfaces.IJumpingEntity;
import com.superworldsun.superslegend.interfaces.IResizableEntity;
import com.superworldsun.superslegend.items.ammobags.AmmoContainerItem;
import com.superworldsun.superslegend.items.shields.MirrorShield;
import com.superworldsun.superslegend.light.AbstractLightEmitter;
import com.superworldsun.superslegend.light.EntityLightEmitter;
import com.superworldsun.superslegend.light.ILightEmitterContainer;
import com.superworldsun.superslegend.light.ILightReceiver;
import com.superworldsun.superslegend.registries.EffectInit;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeHooks;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity implements IResizableEntity, IHoveringEntity, IJumpingEntity, ILightReceiver, ILightEmitterContainer
{
	private float targetScale = 1.0F;
	private float scale = 1.0F;
	private float targetRenderScale = 1.0F;
	private float renderScale = 1.0F;
	private float prevRenderScale = 1.0F;
	
	private int hoverTime;
	private int hoverHeight;
	private boolean jumpedFromGround;
	
	private boolean isLit;
	private EntityLightEmitter lightEmitter = new EntityLightEmitter(() -> level, this::getLightRayVector, this::getLightRayPosition, this);
	
	// This constructor is fake and never used
	protected MixinPlayerEntity()
	{
		super(null, null);
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
		targetScale = 1.0F;
		targetRenderScale = 1.0F;
		
		getArmorSlots().forEach(stack ->
		{
			if (stack.getItem() instanceof IEntityResizer)
			{
				targetScale *= ((IEntityResizer) stack.getItem()).getScale((PlayerEntity) getEntity());
				targetRenderScale *= ((IEntityResizer) stack.getItem()).getRenderScale((PlayerEntity) getEntity());
			}
		});
		
		CuriosApi.getCuriosHelper().getEquippedCurios((PlayerEntity) getEntity()).ifPresent(curios ->
		{
			for (int i = 0; i < curios.getSlots(); i++)
			{
				ItemStack curioStack = curios.getStackInSlot(i);
				
				if (!curioStack.isEmpty() && curioStack.getItem() instanceof IEntityResizer)
				{
					targetScale *= ((IEntityResizer) curioStack.getItem()).getScale((PlayerEntity) getEntity());
					targetRenderScale *= ((IEntityResizer) curioStack.getItem()).getRenderScale((PlayerEntity) getEntity());
				}
			}
		});
		
		prevRenderScale = renderScale;
		
		if (targetRenderScale > renderScale)
		{
			renderScale = Math.min(targetRenderScale, renderScale + 0.05F);
		}
		
		if (targetRenderScale < renderScale)
		{
			renderScale = Math.max(targetRenderScale, renderScale - 0.05F);
		}
		
		if (targetScale > scale)
		{
			setScale(Math.min(targetScale, scale + 0.05F));
		}
		
		if (targetScale < scale)
		{
			setScale(Math.max(targetScale, scale - 0.05F));
		}
		
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
		
		lightEmitter.tick();
	}
	
	@Inject(method = "getProjectile", at = @At(value = "HEAD"), cancellable = true)
	private void injectGetProjectile(ItemStack weaponStack, CallbackInfoReturnable<ItemStack> callbackInfo)
	{
		if (!(weaponStack.getItem() instanceof ShootableItem))
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) (Object) this;
		ShootableItem shootableItem = (ShootableItem) weaponStack.getItem();
		
		CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curios ->
		{
			for (int i = 0; i < curios.getSlots(); i++)
			{
				ItemStack curioStack = curios.getStackInSlot(i);
				
				if (!curioStack.isEmpty() && curioStack.getItem() instanceof AmmoContainerItem)
				{
					AmmoContainerItem quiverItem = (AmmoContainerItem) curioStack.getItem();
					Pair<ItemStack, Integer> quiverContents = quiverItem.getContents(curioStack);
					
					if (quiverContents == null)
					{
						continue;
					}
					
					int arrowsCount = quiverContents.getRight();
					
					if (arrowsCount == 0)
					{
						continue;
					}
					
					ItemStack arrowsStack = quiverContents.getLeft();
					
					// if our weapon can shoot items inside of the quiver
					if (shootableItem.getSupportedHeldProjectiles().test(arrowsStack))
					{
						if (player.level.isClientSide())
						{
							callbackInfo.setReturnValue(ItemStack.EMPTY);
						}
						else
						{
							callbackInfo.setReturnValue(arrowsStack);
						}
						
						return;
					}
				}
			}
		});
	}
	
	@Override
	public void doubleJump()
	{
		PlayerEntity player = (PlayerEntity) (Object) this;
		double jumpStrength = 0.5;
		
		if (player.hasEffect(Effects.JUMP))
		{
			jumpStrength += 0.1 * (player.getEffect(Effects.JUMP).getAmplifier() + 1);
		}
		
		Vector3d movementVector = player.getDeltaMovement();
		Vector3d jumpVector = new Vector3d(0, jumpStrength - movementVector.y, 0);
		player.setDeltaMovement(movementVector.add(jumpVector));
		player.hasImpulse = true;
		player.awardStat(Stats.JUMP);
		ForgeHooks.onLivingJump(player);
		
		if (player.isSprinting())
		{
			player.causeFoodExhaustion(0.2F);
		}
		else
		{
			player.causeFoodExhaustion(0.05F);
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
	protected void pushEntities()
	{
		if (!hasEffect(EffectInit.CLOAKED.get()))
		{
			super.pushEntities();
		}
	}
	
	@Override
	public boolean isPushable()
	{
		return !hasEffect(EffectInit.CLOAKED.get()) && super.isPushable();
	}
	
	@Override
	public float getScaleForRender(float partialTick)
	{
		return prevRenderScale + (renderScale - prevRenderScale) * partialTick;
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
		return hoverTime++;
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
	public boolean jumpedFromBlock()
	{
		return jumpedFromGround;
	}
	
	@Override
	public void setJumpedFromBlock(boolean state)
	{
		jumpedFromGround = state;
	}
	
	@Override
	public boolean isSwimming()
	{
		return canSwim() && !abilities.flying && !this.isSpectator() && super.isSwimming();
	}
	
	@Override
	public boolean isJumping()
	{
		return jumping;
	}
	
	@Override
	public void receiveLight()
	{
		isLit = true;
	}
	
	@Override
	public void stopReceivingLight()
	{
		isLit = false;
		
		if (lightEmitter.litObject instanceof ILightReceiver && ((ILightReceiver) lightEmitter.litObject).isLit())
		{
			((ILightReceiver) lightEmitter.litObject).stopReceivingLight();
		}
	}
	
	@Override
	public boolean isLit()
	{
		return isLit;
	}
	
	@Override
	public AbstractLightEmitter getLightEmitter()
	{
		return lightEmitter;
	}
	
	private Vector3d getLightRayVector()
	{
		if (isLit && isBlocking() && getItemInHand(getUsedItemHand()).getItem() instanceof MirrorShield)
		{
			return getLookAngle();
		}
		
		return Vector3d.ZERO;
	}
	
	private Vector3d getLightRayPosition()
	{
		return position().add(0, getEyeHeight() * 0.8, 0);
	}
	
	private void setScale(float scale)
	{
		this.scale = scale;
		updateEyeHeight();
		refreshDimensions();
	}

	private boolean canSwim()
	{
		if (getItemBySlot(EquipmentSlotType.FEET).getItem() == ItemInit.IRON_BOOTS.get())
			return false;

		if (CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_GORONMASK.get(), (PlayerEntity) (Object) this).isPresent())
			return false;

		return true;
	}
	
	private void updateEyeHeight()
	{
		eyeHeight = getDimensions(getPose()).height * 0.85F;
	}
	
	/* Everything below is taken from original class */
	
	@Shadow
	@Final
	private static Map<Pose, EntitySize> POSES;
	
	@Shadow
	@Final
	private static EntitySize STANDING_DIMENSIONS;
	
	@Shadow
	@Final
	public PlayerAbilities abilities;
	
	@Shadow
	public ItemStack getItemBySlot(EquipmentSlotType slot)
	{
		return null;
	}
}
