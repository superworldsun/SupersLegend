package com.superworldsun.superslegend.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.interfaces.IHandRenderer;
import com.superworldsun.superslegend.interfaces.IPlayerModelChanger;
import com.superworldsun.superslegend.interfaces.IResizableEntity;
import com.superworldsun.superslegend.light.ILightEmitterContainer;
import com.superworldsun.superslegend.light.LightRayRenderer;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
	private PlayerModel<AbstractClientPlayerEntity> baseModel;
	private LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> armorLayer;
	private LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> capeLayer;
	
	// This constructor is fake and never used
	protected MixinPlayerRenderer()
	{
		super(null, null, 0);
	}
	
	@Overwrite
	private void renderHand(MatrixStack matrix, IRenderTypeBuffer buffer, int color, AbstractClientPlayerEntity player, ModelRenderer hand, ModelRenderer handOverlay)
	{
		chooseCurrentModel(player);
		PlayerModel<AbstractClientPlayerEntity> model = getModel();
		setModelProperties(player);
		model.attackTime = 0.0F;
		model.crouching = false;
		model.swimAmount = 0.0F;
		model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		ResourceLocation texture = getTextureLocation(player);
		
		if (model instanceof IHandRenderer)
		{
			((IHandRenderer) model).renderFirstPersonHand(matrix, buffer, color, player, hand, handOverlay, texture);
		}
		else
		{
			hand.xRot = 0.0F;
			hand.render(matrix, buffer.getBuffer(RenderType.entitySolid(texture)), color, OverlayTexture.NO_OVERLAY);
			handOverlay.xRot = 0.0F;
			handOverlay.render(matrix, buffer.getBuffer(RenderType.entityTranslucent(texture)), color, OverlayTexture.NO_OVERLAY);
		}
	}
	
	@Inject(method = "render", at = @At("HEAD"))
	public void injectRender(AbstractClientPlayerEntity player, float rotationYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer renderBuffer, int light, CallbackInfo ci)
	{
		matrix.pushPose();
		matrix.translate(-player.getX(), -player.getY(), -player.getZ());
		LightRayRenderer.render(((ILightEmitterContainer) player).getLightEmitter(), partialTicks, matrix, renderBuffer, light);
		matrix.popPose();
		chooseCurrentModel(player);
	}
	
	@Inject(method = "getTextureLocation", at = @At("HEAD"), cancellable = true)
	private void injectGetTextureLocation(AbstractClientPlayerEntity player, CallbackInfoReturnable<ResourceLocation> ci)
	{
		player.getArmorSlots().forEach(stack ->
		{
			if (stack.getItem() instanceof IPlayerModelChanger)
			{
				ci.setReturnValue(((IPlayerModelChanger) stack.getItem()).getPlayerTexture(player));
			}
		});
		
		CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curios ->
		{
			for (int i = 0; i < curios.getSlots(); i++)
			{
				ItemStack curioStack = curios.getStackInSlot(i);
				
				if (!curioStack.isEmpty() && curioStack.getItem() instanceof IPlayerModelChanger)
				{
					ci.setReturnValue(((IPlayerModelChanger) curioStack.getItem()).getPlayerTexture(player));
				}
			}
		});
	}
	
	@Overwrite
	protected void scale(AbstractClientPlayerEntity player, MatrixStack matrix, float partialTicks)
	{
		float playerScale = ((IResizableEntity) player).getScaleForRender(partialTicks);
		float scale = 0.9375F * playerScale;
		matrix.scale(scale, scale, scale);
		shadowRadius = 0.5F * playerScale;
	}
	
	private void chooseCurrentModel(AbstractClientPlayerEntity player)
	{
		if (baseModel == null)
		{
			baseModel = model;
			armorLayer = layers.get(0);
			capeLayer = layers.get(4);
		}
		
		model = baseModel;
		
		player.getArmorSlots().forEach(stack ->
		{
			if (stack.getItem() instanceof IPlayerModelChanger)
			{
				model = ((IPlayerModelChanger) stack.getItem()).getPlayerModel(player);
			}
		});
		
		CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curios ->
		{
			for (int i = 0; i < curios.getSlots(); i++)
			{
				ItemStack curioStack = curios.getStackInSlot(i);
				
				if (!curioStack.isEmpty() && curioStack.getItem() instanceof IPlayerModelChanger)
				{
					model = ((IPlayerModelChanger) curioStack.getItem()).getPlayerModel(player);
				}
			}
		});
		
		if (model != baseModel)
		{
			if (layers.contains(armorLayer))
			{
				layers.remove(armorLayer);
				layers.remove(capeLayer);
			}
		}
		else
		{
			if (!layers.contains(armorLayer))
			{
				layers.add(armorLayer);
				layers.add(capeLayer);
			}
		}
	}
	
	/* Everything below is taken from original class */
	
	@Shadow
	private void setModelProperties(AbstractClientPlayerEntity player)
	{
	}
}
