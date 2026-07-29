package com.superworldsun.superslegend.client.hud;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.items.ammobags.AmmoContainerItem;
import com.superworldsun.superslegend.items.ammobags.BombBagItem;
import com.superworldsun.superslegend.items.ammobags.BulletBagItem;
import com.superworldsun.superslegend.items.ammobags.QuiverItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public enum AmmoContainerHud implements IGuiOverlay {
    INSTANCE;

    private static final int HUD_X = 2;
    private static final int THERMOMETER_HEIGHT = 33;
    private static final int BOTTOM_MARGIN = 2;
    private static final int ROW_HEIGHT = 18;
    private static final int TEXT_X_OFFSET = 20;
    private static final int TEXT_Y_OFFSET = 5;
    private static final int HUD_Z_OFFSET = -200;

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft minecraft = gui.getMinecraft();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.options.hideGui || !gui.shouldDrawSurvivalElements()) {
            return;
        }

        List<HudEntry> equippedContainers = getEquippedContainers(player);

        int thermometerY = screenHeight - BOTTOM_MARGIN
                - (Config.isTemperatureEnabled() ? THERMOMETER_HEIGHT : 0);
        int firstRowY = thermometerY - equippedContainers.size() * ROW_HEIGHT;
        for (int row = 0; row < equippedContainers.size(); row++) {
            HudEntry entry = equippedContainers.get(row);
            int y = firstRowY + row * ROW_HEIGHT;
            renderContainer(graphics, minecraft, entry, HUD_X, y);
        }
    }

    private static List<HudEntry> getEquippedContainers(LocalPlayer player) {
        HudEntry bulletBags = new HudEntry();
        HudEntry quivers = new HudEntry();
        HudEntry bombBags = new HudEntry();

        CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(curios -> {
            for (int slot = 0; slot < curios.getSlots(); slot++) {
                ItemStack stack = curios.getStackInSlot(slot);
                if (stack.getItem() instanceof BulletBagItem) {
                    bulletBags.add(stack);
                } else if (stack.getItem() instanceof QuiverItem) {
                    quivers.add(stack);
                } else if (stack.getItem() instanceof BombBagItem) {
                    bombBags.add(stack);
                }
            }
        });

        List<HudEntry> entries = new ArrayList<>(3);
        bulletBags.addIfPresent(entries);
        quivers.addIfPresent(entries);
        bombBags.addIfPresent(entries);
        return entries;
    }

    private static void renderContainer(GuiGraphics graphics, Minecraft minecraft, HudEntry entry,
                                        int x, int y) {
        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, HUD_Z_OFFSET);
        graphics.renderItem(entry.icon, x, y);
        graphics.drawString(minecraft.font, Integer.toString(entry.totalCount), x + TEXT_X_OFFSET, y + TEXT_Y_OFFSET,
                0xFFFFFF, true);
        graphics.pose().popPose();
    }

    private static class HudEntry {
        private ItemStack icon = ItemStack.EMPTY;
        private int totalCount;

        private void add(ItemStack containerStack) {
            if (icon.isEmpty()) {
                icon = containerStack;
            }
            AmmoContainerItem containerItem = (AmmoContainerItem) containerStack.getItem();
            totalCount += containerItem.getTotalStoredItemCount(containerStack);
        }

        private void addIfPresent(List<HudEntry> entries) {
            if (!icon.isEmpty()) {
                entries.add(this);
            }
        }
    }

    @SubscribeEvent
    public static void register(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "ammo_containers", INSTANCE);
    }
}
