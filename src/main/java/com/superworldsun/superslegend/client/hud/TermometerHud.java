package com.superworldsun.superslegend.client.hud;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.events.TemperatureEvents;
import com.superworldsun.superslegend.registries.AttributeInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
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
	private static float prev_heat_level;
	private static float heat_level = 0.31F;
	private static float prev_cold_level;
	private static float cold_level = 0.31F;
	
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
			float temperature = TemperatureEvents.getTemperatureAroundPlayer(player);
			int termometerSizeX = 33;
			int termometerSizeY = 33;
			int termometerX = 2;
			int termometerY = event.getWindow().getGuiScaledHeight() - 2 - termometerSizeY;
			minecraft.getTextureManager().bind(TERMOMETER_TEXTURE);
			renderBackground(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY);
			renderDangerousColdLevel(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY, event.getPartialTicks());
			renderDangerousHeatLevel(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY, event.getPartialTicks());
			renderOverlay(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY);
			renderArrow(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY, event.getPartialTicks(), temperature);
			renderDebugInfo(gui, event.getMatrixStack(), termometerX, termometerY, termometerSizeX, termometerSizeY, event.getPartialTicks(), temperature);
			
			// we need to switch texture back to vanilla one
			minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
			RenderSystem.disableBlend();
		}
	}
	
	private static void renderBackground(AbstractGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY)
	{
		gui.blit(matrixStack, termometerX, termometerY, 0, 0, termometerSizeX, termometerSizeY);
	}
	
	private static void renderDangerousColdLevel(AbstractGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY, float partialTicks)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ClientPlayerEntity player = minecraft.player;
		double coldResistance = player.getAttributeValue(AttributeInit.COLD_RESISTANCE.get()) - 1;
		float targetColdLevel = 0.31F;
		
		if (coldResistance < 0.0D)
		{
			targetColdLevel += 0.19F * Math.abs(coldResistance);
		}
		else if (coldResistance > 0.0D)
		{
			targetColdLevel -= targetColdLevel * coldResistance;
		}
		
		if (cold_level < targetColdLevel)
		{
			cold_level += 0.01F;
		}
		else if (cold_level > targetColdLevel)
		{
			cold_level -= 0.01F;
		}
		
		if (Math.abs(cold_level - targetColdLevel) < 0.01F)
		{
			cold_level = targetColdLevel;
		}
		
		float coldLevelAnimation = MathHelper.lerp(partialTicks, prev_cold_level, cold_level);
		blitCircular(gui, matrixStack, termometerX, termometerY, 33, 0, termometerSizeX, termometerSizeY, false, coldLevelAnimation);
		prev_cold_level = cold_level;
	}
	
	private static void renderDangerousHeatLevel(AbstractGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY, float partialTicks)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ClientPlayerEntity player = minecraft.player;
		double heatResistance = player.getAttributeValue(AttributeInit.HEAT_RESISTANCE.get()) - 1;
		float targetHeatLevel = 0.31F;
		
		if (heatResistance < 0.0D)
		{
			targetHeatLevel += 0.19F * Math.abs(heatResistance);
		}
		else if (heatResistance > 0.0D)
		{
			targetHeatLevel -= targetHeatLevel * heatResistance;
		}
		
		if (heat_level < targetHeatLevel)
		{
			heat_level += 0.01F;
		}
		else if (heat_level > targetHeatLevel)
		{
			heat_level -= 0.01F;
		}
		
		if (Math.abs(heat_level - targetHeatLevel) < 0.01F)
		{
			heat_level = targetHeatLevel;
		}
		
		float heatLevelAnimation = MathHelper.lerp(partialTicks, prev_heat_level, heat_level);
		blitCircular(gui, matrixStack, termometerX, termometerY, 66, 0, termometerSizeX, termometerSizeY, true, heatLevelAnimation);
		prev_heat_level = heat_level;
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
		float arrowSpeed = 0.2F;
		
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
			arrow_rotation += arrowSpeed;
		}
		else if (arrow_rotation > targetArrowRotation)
		{
			arrow_rotation -= arrowSpeed;
		}
		
		if (Math.abs(arrow_rotation - targetArrowRotation) < arrowSpeed)
		{
			arrow_rotation = targetArrowRotation;
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
	
	private static void renderDebugInfo(IngameGui gui, MatrixStack matrixStack, int termometerX, int termometerY, int termometerSizeX, int termometerSizeY, float partialTicks, float temperature)
	{
		// String formattedTemperature = String.format("%.2f", temperature);
		// AbstractGui.drawCenteredString(matrixStack, gui.getFont(), formattedTemperature, termometerX + termometerSizeX / 2, termometerY - 10, 0xFFFFFF);
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
