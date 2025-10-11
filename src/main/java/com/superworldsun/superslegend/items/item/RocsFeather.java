package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.interfaces.JumpingEntity;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.DoubleJumpMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class RocsFeather extends Item
{
	public RocsFeather(Properties properties)
	{
		super(properties);
	}

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event)
	{
		if (event.getEntity().getMainHandItem().getItem() instanceof RocsFeather)
		{
			event.setDistance(event.getDistance() - 2F);
		}
	}

	private static boolean canDoubleJump;
	private static boolean hasReleasedJumpKey;

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		Minecraft client = Minecraft.getInstance();
		LocalPlayer player = client.player;

		if (event.phase == TickEvent.Phase.END && player != null && player.input != null)
		{
			if ((player.onGround() || player.onClimbable()) && !player.isInWater())
			{
				hasReleasedJumpKey = false;
				canDoubleJump = true;
			}
			else if (!player.input.jumping)
			{
				hasReleasedJumpKey = true;
			}
			else if (!player.getAbilities().flying && canDoubleJump && hasReleasedJumpKey)
			{
				canDoubleJump = false;

				if (player.getMainHandItem().getItem() instanceof RocsFeather)
				{
					NetworkDispatcher.network_channel.sendToServer(new DoubleJumpMessage());
					((JumpingEntity) player).doubleJump();
				}
				else if (player.getOffhandItem().getItem() instanceof RocsFeather)
				{
					NetworkDispatcher.network_channel.sendToServer(new DoubleJumpMessage());
					((JumpingEntity) player).doubleJump();
				}
			}
		}
	}
}