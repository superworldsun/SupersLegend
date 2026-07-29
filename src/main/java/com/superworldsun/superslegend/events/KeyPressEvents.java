package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.capability.hookshot.HookModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class KeyPressEvents
{

    @SubscribeEvent
    public static void keyPress(InputEvent.Key event)
    {
        if(event.getKey() == GLFW.GLFW_KEY_UP && event.getAction() == GLFW.GLFW_PRESS){
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                HookModel.get(player).setkeyUpIsDown(true);
            }
        }
    }
}
