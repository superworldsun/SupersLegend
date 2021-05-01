package superworldsun.superslegend.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import org.lwjgl.glfw.GLFW;

public class KeyboardUtil {

    private static final long MINECRAFT_WINDOW = Minecraft.getInstance().getMainWindow().getHandle();


    public static boolean isPressingBButton()
    {
     return InputMappings.isKeyDown(MINECRAFT_WINDOW, GLFW.GLFW_KEY_B);
    }
}
