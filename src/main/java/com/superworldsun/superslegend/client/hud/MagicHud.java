package com.superworldsun.superslegend.client.hud;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.magic.MagicProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public enum MagicHud implements IGuiOverlay {
	INSTANCE;

	private static final ResourceLocation MAGIC_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/magic.png");
	private static int last_tick_magic;

	@Override
	public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
		if (gui.getMinecraft().options.hideGui || !gui.shouldDrawSurvivalElements()) return;
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.player == null) return;
		int magicIconsX = graphics.guiWidth() / 2 + 91;
		int magicIconsY = graphics.guiHeight() - gui.rightHeight;
		int magic = getMagicForRender(minecraft.player);
		gui.setupOverlayRenderState(true, false);
		renderMagic(graphics, minecraft, magicIconsX, magicIconsY, magic);
		gui.rightHeight += 10;
	}

	protected static void renderMagic(GuiGraphics graphics, Minecraft minecraft, int iconsX, int iconsY, int magic) {
		for (int i = 0; i < 10; ++i) {
			int iconIndex = i * 2 + 1;
			int iconX = iconsX - i * 8 - 9;
			int iconY = iconsY;
			renderMagicIcon(graphics, minecraft.gui, magic, iconIndex, iconX, iconY);
		}
	}

	private static void renderMagicIcon(GuiGraphics graphics, Gui gui, int magic, int iconIndex, int iconX, int iconY) {
		graphics.blit(MAGIC_TEXTURE, iconX, iconY, 0, 0, 9, 9);
		if (iconIndex <= magic) {
			int iconU = iconIndex == magic ? 18 : 9;
			graphics.blit(MAGIC_TEXTURE, iconX, iconY, iconU, 0, 9, 9);
		}
	}

	private static int getMagicForRender(Player player) {
		if (player.isAlive()) {
			last_tick_magic = (int) MagicProvider.getMagic(player);
		}
		return last_tick_magic;
	}

	@SubscribeEvent
	public static void register(RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "magic", INSTANCE);
	}
}
