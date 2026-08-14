package com.superworldsun.superslegend.client.hud;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.wallet.RupeeWalletItem;
import com.superworldsun.superslegend.registries.ItemInit;
import com.superworldsun.superslegend.util.RupeeWalletUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public enum RupeeWalletHud implements IGuiOverlay {
    INSTANCE;

    private static final int THERMOMETER_RIGHT_X = 37;
    private static final int LEFT_MARGIN = 2;
    private static final int BOTTOM_MARGIN = 2;
    private static final int ICON_SIZE = 16;
    private static final int TEXT_X_OFFSET = 20;
    private static final int TEXT_Y_OFFSET = 5;
    private static final int HUD_Z_OFFSET = -200;
    private static final long COUNT_ANIMATION_DURATION_MS = 1_000L;

    private LocalPlayer trackedPlayer;
    private Item trackedWallet;
    private int animationStartValue;
    private int animationTargetValue;
    private long animationStartTime;
    private boolean animationInitialized;
    private Integer pendingInstantBalance;
    private long pendingInstantBalanceTime;

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft minecraft = gui.getMinecraft();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.options.hideGui || !gui.shouldDrawSurvivalElements()) {
            return;
        }

        var equippedWallet = RupeeWalletUtil.findEquippedWallet(player);
        if (equippedWallet.isEmpty()) {
            resetAnimation();
            return;
        }

        ItemStack wallet = equippedWallet.get().stack();
        RupeeWalletItem walletItem = (RupeeWalletItem) wallet.getItem();
        int displayedRupees = getDisplayedRupees(player, wallet, walletItem.getStoredRupees(wallet));
        int x = Config.isTemperatureEnabled() ? THERMOMETER_RIGHT_X : LEFT_MARGIN;
        int y = screenHeight - BOTTOM_MARGIN - ICON_SIZE;

        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, HUD_Z_OFFSET);
        graphics.renderItem(getWalletRupeeIcon(wallet), x, y);
        graphics.drawString(minecraft.font, Integer.toString(displayedRupees),
                x + TEXT_X_OFFSET, y + TEXT_Y_OFFSET, 0xFFFFFF, true);
        graphics.pose().popPose();
    }

    private int getDisplayedRupees(LocalPlayer player, ItemStack wallet, int storedRupees) {
        long now = System.currentTimeMillis();
        if (!animationInitialized || trackedPlayer != player || trackedWallet != wallet.getItem()) {
            trackedPlayer = player;
            trackedWallet = wallet.getItem();
            animationStartValue = storedRupees;
            animationTargetValue = storedRupees;
            animationStartTime = now;
            animationInitialized = true;
            return storedRupees;
        }

        if (pendingInstantBalance != null) {
            if (now - pendingInstantBalanceTime <= 1_500L) {
                int instantBalance = pendingInstantBalance;
                animationStartValue = instantBalance;
                animationTargetValue = instantBalance;
                animationStartTime = now;
                if (storedRupees == instantBalance) {
                    pendingInstantBalance = null;
                }
                return instantBalance;
            }
            pendingInstantBalance = null;
        }

        int displayedRupees = calculateDisplayedRupees(now);
        if (storedRupees != animationTargetValue) {
            animationStartValue = displayedRupees;
            animationTargetValue = storedRupees;
            animationStartTime = now;
        }

        return calculateDisplayedRupees(now);
    }

    private int calculateDisplayedRupees(long now) {
        if (animationStartValue == animationTargetValue) {
            return animationTargetValue;
        }

        float progress = Math.min(1.0F,
                (float) (now - animationStartTime) / (float) COUNT_ANIMATION_DURATION_MS);
        return animationStartValue
                + Math.round((animationTargetValue - animationStartValue) * progress);
    }

    private void resetAnimation() {
        animationInitialized = false;
        trackedPlayer = null;
        trackedWallet = null;
        pendingInstantBalance = null;
    }

    public static void requestInstantBalance(int balance) {
        INSTANCE.pendingInstantBalance = Math.max(0, balance);
        INSTANCE.pendingInstantBalanceTime = System.currentTimeMillis();
    }

    /** Keeps every wallet display using the same tier-to-rupee icon mapping. */
    public static ItemStack getWalletRupeeIcon(ItemStack wallet) {
        if (wallet.is(ItemInit.SMALL_WALLET.get())) {
            return new ItemStack(ItemInit.BLUE_RUPEE.get());
        }
        if (wallet.is(ItemInit.MEDIUM_WALLET.get())) {
            return new ItemStack(ItemInit.YELLOW_RUPEE.get());
        }
        if (wallet.is(ItemInit.BIG_WALLET.get())) {
            return new ItemStack(ItemInit.RED_RUPEE.get());
        }
        if (wallet.is(ItemInit.ADULT_WALLET.get())) {
            return new ItemStack(ItemInit.PURPLE_RUPEE.get());
        }
        if (wallet.is(ItemInit.GIANTS_WALLET.get())) {
            return new ItemStack(ItemInit.SILVER_RUPEE.get());
        }
        if (wallet.is(ItemInit.COLOSSAL_WALLET.get())) {
            return new ItemStack(ItemInit.GOLD_RUPEE.get());
        }
        return new ItemStack(ItemInit.RUPEE.get());
    }

    @SubscribeEvent
    public static void register(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "rupee_wallet", INSTANCE);
    }
}
