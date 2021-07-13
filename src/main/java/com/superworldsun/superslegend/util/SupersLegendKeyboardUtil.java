package com.superworldsun.superslegend.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import org.lwjgl.glfw.GLFW;

public class SupersLegendKeyboardUtil {

    private static final long MINECRAFT_WINDOW = Minecraft.getInstance().getWindow().getWindow();

    public static boolean isHoldingLeftShift() {
        return InputMappings.isKeyDown(MINECRAFT_WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT);
    }
}