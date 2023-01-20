package com.superworldsun.superslegend.client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
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

			if (minecraft.player == null) {
				return;
			}

			RenderSystem.enableBlend();
			int manaIconsX = event.getWindow().getGuiScaledWidth() / 2 + 91;
			int manaIconsY = event.getWindow().getGuiScaledHeight() - ForgeIngameGui.right_height;
			int mana = getManaForRender(minecraft.player);
			minecraft.getTextureManager().bind(MANA_TEXTURE);
			renderManaIcons(event.getMatrixStack(), minecraft, manaIconsX, manaIconsY, mana);
			ForgeIngameGui.right_height += 10;
			minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
			RenderSystem.disableBlend();
		}
	}

	protected static void renderManaIcons(MatrixStack matrixStack, Minecraft minecraft, int iconsX, int iconsY, int mana) {
		for (int i = 0; i < 10; ++i) {
			int iconIndex = i * 2 + 1;
			int iconX = iconsX - i * 8 - 9;
			int iconY = iconsY;
			renderManaIcon(matrixStack, minecraft.gui, mana, iconIndex, iconX, iconY);
		}
	}

	private static void renderManaIcon(MatrixStack matrixStack, IngameGui gui, int mana, int iconIndex, int iconX, int iconY) {
		gui.blit(matrixStack, iconX, iconY, 0, 0, 9, 9);

		if (iconIndex <= mana) {
			int crystalIcon = iconIndex == mana ? 18 : 9;
			gui.blit(matrixStack, iconX, iconY, crystalIcon, 0, 9, 9);
		}
	}

	private static int getManaForRender(PlayerEntity player) {
		int currentMana = 0;

		if (player.isAlive()) {
			currentMana = (int) ManaHelper.getMana(player);
			last_tick_mana = currentMana;
		} else {
			currentMana = last_tick_mana;
		}

		return currentMana;
	}
}
