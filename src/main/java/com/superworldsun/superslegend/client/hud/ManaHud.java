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
public class ManaHud
{
	private static final ResourceLocation MANA_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/magic.png");
	// Used for rendering mana when player is dead
	private static int lastTickMana;
	
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		// render magic right after food
		if (event.getType() == ElementType.FOOD)
		{
			Minecraft minecraft = Minecraft.getInstance();
			PlayerEntity player = minecraft.player;
			
			if (player == null)
			{
				return;
			}
			
			IngameGui gui = minecraft.gui;
			RenderSystem.enableBlend();
			int manaX = event.getWindow().getGuiScaledWidth() / 2 + 91;
			int manaY = event.getWindow().getGuiScaledHeight() - ForgeIngameGui.right_height;
			ForgeIngameGui.right_height += 10;
			
			int mana = 0;
			
			// these checks are for crash fix when rendering mana of dead player
			if (player.isAlive())
			{
				mana = (int) ManaProvider.get(player).getMana();
				lastTickMana = mana;
			}
			else
			{
				mana = lastTickMana;
			}
			
			minecraft.getTextureManager().bind(MANA_TEXTURE);
			
			for (int i = 0; i < 10; ++i)
			{
				int crystalNumber = i * 2 + 1;
				int crystalX = manaX - i * 8 - 9;
				int crystalY = manaY;
				
				gui.blit(event.getMatrixStack(), crystalX, crystalY, 0, 0, 9, 9);
				
				if (crystalNumber <= mana)
				{
					int crystalIcon = crystalNumber == mana ? 18 : 9;
					gui.blit(event.getMatrixStack(), crystalX, crystalY, crystalIcon, 0, 9, 9);
				}
			}
			
			// we need to switch texture back to vanilla one
			minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
			RenderSystem.disableBlend();
		}
	}
}
