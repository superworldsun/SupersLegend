package com.superworldsun.superslegend.client;

import com.superworldsun.superslegend.util.HylianTextUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

import java.util.function.IntSupplier;

public final class HylianFontMetrics {
    private static final ThreadLocal<Boolean> MEASURING_HYLIAN =
            ThreadLocal.withInitial(() -> false);

    private HylianFontMetrics() {
    }

    public static boolean isMeasuringHylian() {
        return MEASURING_HYLIAN.get();
    }

    public static int width(Font font, String text) {
        return measure(() -> font.width(HylianTextUtil.markAsHylian(text)));
    }

    public static int wordWrapHeight(Font font, String text, int width) {
        return measure(() -> font.wordWrapHeight(HylianTextUtil.markAsHylian(text), width));
    }

    private static int measure(IntSupplier measurement) {
        boolean previous = MEASURING_HYLIAN.get();
        MEASURING_HYLIAN.set(true);
        try {
            return measurement.getAsInt();
        } finally {
            MEASURING_HYLIAN.set(previous);
        }
    }
}
