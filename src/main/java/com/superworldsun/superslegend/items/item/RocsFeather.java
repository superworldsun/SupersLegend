package com.superworldsun.superslegend.items.item;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.PegasusBootsInputEvents;
import com.superworldsun.superslegend.interfaces.JumpingEntity;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.DoubleJumpMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraft.world.phys.Vec3;
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
		if (isHeldBy(event.getEntity()))
		{
			event.setDistance(event.getDistance() - 2F);
		}
	}

	@SubscribeEvent
	public static void onLivingJump(LivingEvent.LivingJumpEvent event)
	{
		if (isHeldBy(event.getEntity()))
		{
			Vec3 movement = event.getEntity().getDeltaMovement();
			// Jump height is proportional to velocity squared, so sqrt(1.5)
			// produces approximately 1.5 times the normal jump height.
			event.getEntity().setDeltaMovement(movement.x,
					movement.y * Math.sqrt(1.5D), movement.z);
		}
	}

	public static boolean isHeldBy(LivingEntity entity)
	{
		return entity.getMainHandItem().getItem() instanceof RocsFeather
				|| entity.getOffhandItem().getItem() instanceof RocsFeather;
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
			// During a Pegasus Boots charge, the Feather permits the initial ground
			// jump but does not grant its usual second jump. Landing starts a new
			// jump instance; outside a charge the normal double jump is unchanged.
			if (PegasusBootsInputEvents.isCharging() && !player.onGround())
			{
				canDoubleJump = false;
				hasReleasedJumpKey = false;
				return;
			}
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

				if (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemInit.ROCS_CAPE.get())
						|| player.getMainHandItem().getItem() instanceof RocsFeather)
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
