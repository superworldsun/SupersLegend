package com.superworldsun.superslegend.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.mana.ManaHelper;

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
public class ManaHud {
	private static final ResourceLocation MANA_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/magic.png");
	private static int last_tick_mana;

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() == ElementType.FOOD) {
			Minecraft minecraft = Minecraft.getInstance();
			PlayerEntity player = minecraft.player;

			if (player == null) {
				return;
			}

			IngameGui gui = minecraft.gui;
			RenderSystem.enableBlend();
			int manaIconsX = event.getWindow().getGuiScaledWidth() / 2 + 91;
			int manaIconsY = event.getWindow().getGuiScaledHeight() - ForgeIngameGui.right_height;
			ForgeIngameGui.right_height += 10;
			int currentMana = 0;

			if (player.isAlive()) {
				currentMana = (int) ManaHelper.getMana(player);
				last_tick_mana = currentMana;
			} else {
				currentMana = last_tick_mana;
			}

			minecraft.getTextureManager().bind(MANA_TEXTURE);

			for (int i = 0; i < 10; ++i) {
				int iconNumber = i * 2 + 1;
				int iconX = manaIconsX - i * 8 - 9;
				int iconY = manaIconsY;

				gui.blit(event.getMatrixStack(), iconX, iconY, 0, 0, 9, 9);

				if (iconNumber <= currentMana) {
					int crystalIcon = iconNumber == currentMana ? 18 : 9;
					gui.blit(event.getMatrixStack(), iconX, iconY, crystalIcon, 0, 9, 9);
				}
			}

			// We need to switch texture back to vanilla one
			minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
			RenderSystem.disableBlend();
		}
	}
}
