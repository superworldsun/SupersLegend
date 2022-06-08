package com.superworldsun.superslegend.client.screen.widgets;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class RemoveButton extends Button
{
	public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/widgets.png");
	
	public RemoveButton(int x, int y, IPressable pressable, ITooltip tooltip)
	{
		super(x, y, 20, 20, new StringTextComponent(""), pressable, tooltip);
	}
	
	@Override
	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(WIDGETS_LOCATION);
		GL11.glColor4f(1F, 1F, 1F, alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		blit(matrixStack, x, y, 0, (isHovered() && Screen.hasShiftDown() ? 1 : 0) * 20, width, height);
		
		if (isHovered())
		{
			renderToolTip(matrixStack, mouseX, mouseY);
		}
	}
}
