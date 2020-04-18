package superworldsun.superslegend;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class Keybinds
{

    private static final String CATEGORY = "key.categories." + SupersLegend.modid;

    public static final KeyBinding bombMask = new KeyBinding(SupersLegend.modid+".key.bomb_mask", GLFW.GLFW_KEY_B, CATEGORY);

    static {
        ClientRegistry.registerKeyBinding(bombMask);

    }

}
