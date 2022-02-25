package com.superworldsun.superslegend.mixin;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.*;
import com.superworldsun.superslegend.items.items.Lantern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.superworldsun.superslegend.light.AbstractLightEmitter;
import com.superworldsun.superslegend.light.EntityLightEmitter;
import com.superworldsun.superslegend.light.ILightEmitterContainer;
import com.superworldsun.superslegend.light.ILightReceiver;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity
		implements IResizableEntity, IHoveringEntity, IJumpingEntity, ILightReceiver, ILightEmitterContainer
{
	@Shadow public abstract Iterable<ItemStack> getHandSlots();

	private float targetScale = 1.0F;
	private float scale = 1.0F;
	private float targetRenderScale = 1.0F;
	private float renderScale = 1.0F;
	private float prevRenderScale = 1.0F;

	private int hoverTime;
	private int hoverHeight;
	private boolean jumpedFromGround;

	private boolean isLit;
	private EntityLightEmitter lightEmitter = new EntityLightEmitter(this::getCommandSenderWorld, this::getLookAngle, this::position, this);
	
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
	private void injectTick(CallbackInfo ci) {
		if (Thread.currentThread().getThreadGroup() != SidedThreadGroups.SERVER) {
			Minecraft mcinstance;
			mcinstance = Minecraft.getInstance();
			AtomicBoolean shouldremove = new AtomicBoolean(true);

			try {
				if (Thread.currentThread().getThreadGroup() != SidedThreadGroups.SERVER) {
					getHandSlots().forEach(stack ->{
						if (stack.getItem() instanceof Lantern) {
							if (stack.getDamageValue() != stack.getMaxDamage()) {
								if (!getEntity().isInWater()) {
									shouldremove.set(false);
									if (!SupersLegendMain.affectedblocks.containsValue(getEntity().blockPosition())) {

										if (SupersLegendMain.affectedblocks.containsKey(getUUID())) {
											SupersLegendMain.affectedblocks.forEach((uuid, blockPos) -> {
												getEntity().level.getLightEngine().checkBlock(blockPos);
											});
											SupersLegendMain.affectedblocks.replace(getUUID(), getEntity().blockPosition());
										} else {
											SupersLegendMain.affectedblocks.put(getUUID(), getEntity().blockPosition());
										}

										getEntity().level.getLightEngine().onBlockEmissionIncrease(getEntity().blockPosition(), 10);
									}
								}
							} else {

							}
						}
					});
					if (shouldremove.get()) {
						if (SupersLegendMain.affectedblocks.containsKey(getUUID())) {
							SupersLegendMain.affectedblocks.forEach((uuid, blockPos) -> {
								getEntity().level.getLightEngine().checkBlock(blockPos);
							});
						}
					}
				}
			} catch (Exception exception) {

			}


		}
		targetScale = 1.0F;
		targetRenderScale = 1.0F;

		getArmorSlots().forEach(stack ->
		{
			if (stack.getItem() instanceof IEntityResizer) {
				targetScale *= ((IEntityResizer) stack.getItem()).getScale((PlayerEntity) getEntity());
				targetRenderScale *= ((IEntityResizer) stack.getItem()).getRenderScale((PlayerEntity) getEntity());
			}
		});
//Check if is Curios.
		ItemInit.getCurios().forEach(stack ->
		{
			if (stack.getItem() instanceof IEntityResizer) {
				ItemStack stack0 = CuriosApi.getCuriosHelper().findEquippedCurio(stack.getItem(), (PlayerEntity) getEntity()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
				if(!stack0.isEmpty()) {
					targetScale *= ((IEntityResizer) stack.getItem()).getScale((PlayerEntity) getEntity());
					targetRenderScale *= ((IEntityResizer) stack.getItem()).getRenderScale((PlayerEntity) getEntity());
				}
				}
		});

		prevRenderScale = renderScale;

		if (targetRenderScale > renderScale) {
			renderScale = Math.min(targetRenderScale, renderScale + 0.05F);
		}

		if (targetRenderScale < renderScale) {
			renderScale = Math.max(targetRenderScale, renderScale - 0.05F);
		}

		if (targetScale > scale) {
			setScale(Math.min(targetScale, scale + 0.05F));
		}

		if (targetScale < scale) {
			setScale(Math.max(targetScale, scale - 0.05F));
		}

		// Increase movement if we are bigger, decrease if we are smaller
		if (scale > 1) {
			float bonusSpeed = scale - 1;
			move(MoverType.SELF, getDeltaMovement().multiply(bonusSpeed, bonusSpeed, bonusSpeed));
		} else if (scale < 1) {
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
	public boolean jumpedFromBlock() { return jumpedFromGround; }

	@Override
	public void setJumpedFromBlock(boolean state) {
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
	
	private void setScale(float scale)
	{
		this.scale = scale;
		updateEyeHeight();
		refreshDimensions();
	}
	
	private boolean canSwim()
	{
		return getItemBySlot(EquipmentSlotType.FEET).getItem() != ItemInit.IRON_BOOTS.get();
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
