package com.superworldsun.superslegend.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.interfaces.IResizableEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

@Mixin(InventoryScreen.class)
public class MixinInventoryScreen {
	@SuppressWarnings("deprecation")
	@Inject(method = "renderEntityInInventory", at = @At("HEAD"), cancellable = true)
	private static void injectRenderEntityInInventory(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity, CallbackInfo callbackInfo) {
		float f = (float) Math.atan(mouseX / 40F);
		float f1 = (float) Math.atan(mouseY / 40F);
		RenderSystem.pushMatrix();
		RenderSystem.translatef(x, y, 1050.0F);
		RenderSystem.scalef(1.0F, 1.0F, -1.0F);
		MatrixStack matrixstack = new MatrixStack();
		matrixstack.translate(0.0D, 0.0D, 1000.0D);
		matrixstack.scale(size, size, size);

		if (entity instanceof IResizableEntity) {
			float playerScale = ((IResizableEntity) entity).getScaleForRender(1F);
			matrixstack.scale(1 / playerScale, 1 / playerScale, 1 / playerScale);
		}

		Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
		Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
		quaternion.mul(quaternion1);
		matrixstack.mulPose(quaternion);
		float f2 = entity.yBodyRot;
		float f3 = entity.yRot;
		float f4 = entity.xRot;
		float f5 = entity.yHeadRotO;
		float f6 = entity.yHeadRot;
		entity.yBodyRot = 180.0F + f * 20.0F;
		entity.yRot = 180.0F + f * 40.0F;
		entity.xRot = -f1 * 20.0F;
		entity.yHeadRot = entity.yRot;
		entity.yHeadRotO = entity.yRot;
		EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
		quaternion1.conj();
		entityrenderermanager.overrideCameraOrientation(quaternion1);
		entityrenderermanager.setRenderShadow(false);
		IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
		RenderSystem.runAsFancy(() -> {
			entityrenderermanager.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
		});
		irendertypebuffer$impl.endBatch();
		entityrenderermanager.setRenderShadow(true);
		entity.yBodyRot = f2;
		entity.yRot = f3;
		entity.xRot = f4;
		entity.yHeadRotO = f5;
		entity.yHeadRot = f6;
		RenderSystem.popMatrix();
		callbackInfo.cancel();
	}
}
