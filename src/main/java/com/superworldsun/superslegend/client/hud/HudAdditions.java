package com.superworldsun.superslegend.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.mana.ManaProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class HudAdditions
{
	private static final ResourceLocation MAGIC_ICONS = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/magic.png");
	// Used for rendering mana when player is dead
	private static int lastTickMana;
	
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		// Render magic right after food
		if (event.getType() == ElementType.FOOD)
		{
			Minecraft minecraft = Minecraft.getInstance();
			minecraft.getTextureManager().bind(MAGIC_ICONS);
			IngameGui gui = minecraft.gui;
			PlayerEntity player = minecraft.player;
			
			if (player == null)
			{
				return;
			}
			
			RenderSystem.enableBlend();
			int width = event.getWindow().getGuiScaledWidth();
			int height = event.getWindow().getGuiScaledHeight();
			int left = width / 2 + 91;
			int top = height - ForgeIngameGui.right_height;
			ForgeIngameGui.right_height += 10;
			
			int level = 0;
			
			// This checks are for crash fix when rendering mana of dead player
			if (player.isAlive())
			{
				level = (int) ManaProvider.get(player).getMana();
				lastTickMana = level;
			}
			else
			{
				level = lastTickMana;
			}
			
			for (int i = 0; i < 10; ++i)
			{
				int idx = i * 2 + 1;
				int x = left - i * 8 - 9;
				int y = top;
				int icon = 9;
				
				gui.blit(event.getMatrixStack(), x, y, 0, 0, 9, 9);
				
				if (idx < level)
					gui.blit(event.getMatrixStack(), x, y, icon, 0, 9, 9);
				else if (idx == level)
					gui.blit(event.getMatrixStack(), x, y, icon + 9, 0, 9, 9);
			}
			
			// We need to switch texture back to vanilla one
			minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
			RenderSystem.disableBlend();
		}
	}
}
