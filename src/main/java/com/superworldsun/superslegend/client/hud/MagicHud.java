package com.superworldsun.superslegend.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.magic.MagicProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.annotation.ElementType;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class MagicHud {
    private static final ResourceLocation MANA_TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/magic.png");
    private static int last_tick_mana;

    //TODO, unifinished port of magic Hud
    /*@SubscribeEvent
    public static void onRenderGameOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getType() == ElementType.FOOD) {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.player == null) {
                return;
            }

            RenderSystem.enableBlend();
            int manaIconsX = event.getWindow().getGuiScaledWidth() / 2 + 91;
            int manaIconsY = event.getWindow().getGuiScaledHeight() - ForgeIngameGui.right_height;
            int mana = getManaForRender(minecraft.player);
            minecraft.getTextureManager().bindForSetup(MANA_TEXTURE);
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

    private static void renderManaIcon(MatrixStack matrixStack, Gui gui, int mana, int iconIndex, int iconX, int iconY) {
        gui.blit(matrixStack, iconX, iconY, 0, 0, 9, 9);

        if (iconIndex <= mana) {
            int crystalIcon = iconIndex == mana ? 18 : 9;
            gui.blit(matrixStack, iconX, iconY, crystalIcon, 0, 9, 9);
        }
    }*/

    private static int getManaForRender(Player player) {
        int currentMana = 0;

        if (player.isAlive()) {
            currentMana = (int) MagicProvider.getMagic(player);
            last_tick_mana = currentMana;
        } else {
            currentMana = last_tick_mana;
        }

        return currentMana;
    }
}

