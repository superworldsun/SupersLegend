package com.superworldsun.superslegend.events;

import com.google.common.base.Predicates;
import com.superworldsun.superslegend.SupersLegendMain;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class PlayerMobPickupEvents {
	// Makes it so when you have a mob held you cant attack it or anything else
	@SubscribeEvent
	public static void onPlayerAttack(AttackEntityEvent event) {
		PlayerEntity player = event.getPlayer();
		if (!player.getPassengers().isEmpty()) {
			event.setCanceled(true);
		}
	}

	// makes it so the right click function isnt used when you right click, dosent work the best
	@SubscribeEvent
	public static void onPlayerInteractItem(PlayerInteractEvent.RightClickItem event) {
		PlayerEntity player = event.getPlayer();
		if (!player.getPassengers().isEmpty()) {
			event.setCanceled(true);
		}
	}

	// Checks if the player is holding a chicken, if they do then fall slowly
	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntity();
			boolean hasChickenPassenger = player.getPassengers().stream().anyMatch(Predicates.instanceOf(ChickenEntity.class));
			if (hasChickenPassenger) {
				player.fallDistance = 0F;
				// slows fall speed
				if (player.getDeltaMovement().y < -0.1) {
					Vector3d movement = player.getDeltaMovement();
					player.setDeltaMovement(new Vector3d(movement.x, -0.08, movement.z));
				}
			}
		}
	}

	// removes players first person hand when holding mob
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		PlayerEntity player = minecraft.player;
		if (player != null && !player.getPassengers().isEmpty()) {
			event.setCanceled(true);
		}
	}
}
