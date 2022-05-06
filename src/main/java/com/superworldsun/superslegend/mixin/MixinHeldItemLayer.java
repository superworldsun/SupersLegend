package com.superworldsun.superslegend.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.interfaces.IHandRenderer;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;

@Mixin(HeldItemLayer.class)
public abstract class MixinHeldItemLayer<T extends LivingEntity, M extends EntityModel<T> & IHasArm> extends LayerRenderer<T, M>
{
	// This constructor is fake and never used
	protected MixinHeldItemLayer()
	{
		super(null);
	}
	
	@Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
	private void injectRenderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack,
			IRenderTypeBuffer renderTypeBuffer, int light, CallbackInfo ci)
	{
		if (getParentModel() instanceof IHandRenderer)
		{
			if (!itemStack.isEmpty())
			{
				matrixStack.pushPose();
				getParentModel().translateToHand(handSide, matrixStack);
				((IHandRenderer) getParentModel()).renderThirdPersonItem(livingEntity, itemStack, transformType, handSide, matrixStack, renderTypeBuffer, light);
				matrixStack.popPose();
			}
			
			ci.cancel();
		}
	}
}