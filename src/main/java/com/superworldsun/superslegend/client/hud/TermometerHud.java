package com.superworldsun.superslegend.client.hud;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class TermometerHud
{
	private static final ResourceLocation TERMOMETER_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/termometer.png");
	private static float prev_arrow_rotation;
	private static float arrow_rotation;
	
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		// render termometer right after food
		if (event.getType() == ElementType.FOOD)
		{
			Minecraft minecraft = Minecraft.getInstance();
			ClientPlayerEntity player = minecraft.player;
			
			if (player == null)
			{
				return;
			}
			
			RenderSystem.enableBlend();
			IngameGui gui = minecraft.gui;
			BlockPos playerPos = player.blockPosition();
			Biome currentBiome = player.level.getBiome(playerPos);
			float temperature = currentBiome.getTemperature(playerPos);
			int termometerSizeX = 33;
			int termometerSizeY = 33;
			int termometerX = 2;
			int termometerY = event.getWindow().getGuiScaledHeight() - 2 - termometerSizeY;
			minecraft.getTextureManager().bind(TERMOMETER_TEXTURE);
			renderBackground(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY);
			renderDangerousColdLevel(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY);
			renderDangerousHeatLevel(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY);
			renderOverlay(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY);
			renderArrow(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY, event.getPartialTicks(), temperature);
			// we need to switch texture back to vanilla one
			minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
			RenderSystem.disableBlend();
		}
	}
	
	private static void renderBackground(AbstractGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY)
	{
		gui.blit(matrixStack, termometerX, termometerY, 0, 0, termometerSizeX, termometerSizeY);
	}
	
	private static void renderDangerousColdLevel(AbstractGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY)
	{
		blitCircular(gui, matrixStack, termometerX, termometerY, 33, 0, termometerSizeX, termometerSizeY, false, 0.31F);
	}
	
	private static void renderDangerousHeatLevel(AbstractGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY)
	{
		blitCircular(gui, matrixStack, termometerX, termometerY, 66, 0, termometerSizeX, termometerSizeY, true, 0.31F);
	}
	
	private static void renderOverlay(AbstractGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY)
	{
		gui.blit(matrixStack, termometerX, termometerY, 99, 0, termometerSizeX, termometerSizeY);
	}
	
	private static void renderArrow(AbstractGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY, float partialTicks, float temperature)
	{
		float maxArrowRotationNormal = 50F;
		float maxArrowRotationCritical = 125F;
		float arrowShift = -50F;
		float targetArrowRotation;
		
		if (temperature >= 0 && temperature <= 1)
		{
			targetArrowRotation = temperature * (maxArrowRotationNormal - arrowShift) + arrowShift;
		}
		else if (temperature < 0)
		{
			targetArrowRotation = arrowShift + temperature * (maxArrowRotationCritical - maxArrowRotationNormal);
		}
		else
		{
			targetArrowRotation = maxArrowRotationNormal + (temperature - 1) * (maxArrowRotationCritical - maxArrowRotationNormal);
		}
		
		targetArrowRotation = Math.max(-maxArrowRotationCritical, Math.min(maxArrowRotationCritical, targetArrowRotation));
		
		if (arrow_rotation < targetArrowRotation)
		{
			arrow_rotation++;
		}
		else if (arrow_rotation > targetArrowRotation)
		{
			arrow_rotation--;
		}
		
		float arrowRotationAnimation = MathHelper.lerp(partialTicks, prev_arrow_rotation, arrow_rotation);
		matrixStack.pushPose();
		matrixStack.translate(termometerX + termometerSizeX / 2, termometerY + termometerSizeY / 2, 0);
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(arrowRotationAnimation));
		matrixStack.translate(-termometerX - termometerSizeX / 2, -termometerY - termometerSizeY / 2, 0);
		gui.blit(matrixStack, termometerX, termometerY, 132, 0, termometerSizeX, termometerSizeY);
		matrixStack.popPose();
		prev_arrow_rotation = arrow_rotation;
	}
	
	private static void blitCircular(AbstractGui gui, MatrixStack matrixStack, float x, float y, float u, float v, float xSize, float ySize, boolean reverse, float fillPercentage)
	{
		if (fillPercentage <= 0)
		{
			return;
		}
		
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		Matrix4f pose = matrixStack.last().pose();
		float uSize = xSize / 256;
		float vSize = ySize / 256;
		u /= 256;
		v /= 256;
		bufferbuilder.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		
		if (reverse)
		{
			float firstThirdFill = fillPercentage > 1 / 3F ? 1F : fillPercentage * 3;
			bufferbuilder.vertex(pose, x + xSize, y + ySize, gui.getBlitOffset()).uv(u + uSize, v + vSize).endVertex();
			bufferbuilder.vertex(pose, x + xSize, y + ySize - ySize * firstThirdFill, gui.getBlitOffset()).uv(u + uSize, v + vSize - vSize * firstThirdFill).endVertex();
			bufferbuilder.vertex(pose, x + xSize / 2, y + ySize / 2, gui.getBlitOffset()).uv(u + uSize / 2, v + vSize / 2).endVertex();
			
			if (fillPercentage > 1 / 3F)
			{
				float secondThirdFill = fillPercentage > 2 / 3F ? 1F : (fillPercentage - 1 / 3F) * 3;
				bufferbuilder.vertex(pose, x + xSize, y, gui.getBlitOffset()).uv(u + uSize, v).endVertex();
				bufferbuilder.vertex(pose, x + xSize - xSize * secondThirdFill, y, gui.getBlitOffset()).uv(u + uSize - uSize * secondThirdFill, v).endVertex();
				bufferbuilder.vertex(pose, x + xSize / 2, y + ySize / 2, gui.getBlitOffset()).uv(u + uSize / 2, v + vSize / 2).endVertex();
			}
			
			if (fillPercentage > 2 / 3F)
			{
				float thirdThirdFill = fillPercentage >= 1F ? 1F : (fillPercentage - 2 / 3F) * 3;
				bufferbuilder.vertex(pose, x, y + ySize * thirdThirdFill, gui.getBlitOffset()).uv(u, v + vSize * thirdThirdFill).endVertex();
				bufferbuilder.vertex(pose, x, y + ySize, gui.getBlitOffset()).uv(u, v + vSize).endVertex();
				bufferbuilder.vertex(pose, x + xSize / 2, y + ySize / 2, gui.getBlitOffset()).uv(u + uSize / 2, v + vSize / 2).endVertex();
			}
		}
		else
		{
			float firstThirdFill = fillPercentage > 1 / 3F ? 1F : fillPercentage * 3;
			bufferbuilder.vertex(pose, x, y + ySize - ySize * firstThirdFill, gui.getBlitOffset()).uv(u, v + vSize - vSize * firstThirdFill).endVertex();
			bufferbuilder.vertex(pose, x, y + ySize, gui.getBlitOffset()).uv(u, v + vSize).endVertex();
			bufferbuilder.vertex(pose, x + xSize / 2, y + ySize / 2, gui.getBlitOffset()).uv(u + uSize / 2, v + vSize / 2).endVertex();
			
			if (fillPercentage > 1 / 3F)
			{
				float secondThirdFill = fillPercentage > 2 / 3F ? 1F : (fillPercentage - 1 / 3F) * 3;
				bufferbuilder.vertex(pose, x + xSize * secondThirdFill, y, gui.getBlitOffset()).uv(u + uSize * secondThirdFill, v).endVertex();
				bufferbuilder.vertex(pose, x, y, gui.getBlitOffset()).uv(u, v).endVertex();
				bufferbuilder.vertex(pose, x + xSize / 2, y + ySize / 2, gui.getBlitOffset()).uv(u + uSize / 2, v + vSize / 2).endVertex();
			}
			
			if (fillPercentage > 2 / 3F)
			{
				float thirdThirdFill = fillPercentage >= 1F ? 1F : (fillPercentage - 2 / 3F) * 3;
				bufferbuilder.vertex(pose, x + xSize, y + ySize * thirdThirdFill, gui.getBlitOffset()).uv(u + uSize, v + vSize * thirdThirdFill).endVertex();
				bufferbuilder.vertex(pose, x + xSize, y, gui.getBlitOffset()).uv(u + uSize, v).endVertex();
				bufferbuilder.vertex(pose, x + xSize / 2, y + ySize / 2, gui.getBlitOffset()).uv(u + uSize / 2, v + vSize / 2).endVertex();
			}
		}
		
		bufferbuilder.end();
		WorldVertexBufferUploader.end(bufferbuilder);
	}
}
