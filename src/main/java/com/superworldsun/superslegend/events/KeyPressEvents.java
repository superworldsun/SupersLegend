package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.entities.projectiles.arrows.AncientArrowEntity;
import com.superworldsun.superslegend.hookshotCap.capabilities.HookModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class KeyPressEvents
{

	@SubscribeEvent
	public void keyPress(InputEvent.KeyInputEvent event)
	{
		if(event.getKey() == GLFW.GLFW_KEY_UP && event.getAction() == GLFW.GLFW_PRESS){
			HookModel.get().setkeyUpIsDown(true);
		}
	}
}
