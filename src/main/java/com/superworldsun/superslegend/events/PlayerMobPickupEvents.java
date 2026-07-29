package com.superworldsun.superslegend.events;

import com.google.common.base.Predicates;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.util.PlayerAnimationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class PlayerMobPickupEvents {
	/**
	 * Reconciles carried-player dismounts for every tracking client.
	 *
	 * <p>A player can leave another player by pressing sneak without going
	 * through any of this mod's explicit drop paths. Waiting until the server
	 * finishes that dismount before sending the passenger packet prevents the
	 * carrier from retaining a client-only passenger and raised-arms pose.</p>
	 */
	@SubscribeEvent
	public static void onCarriedEntityDismount(EntityMountEvent event) {
		if (event.isMounting() || event.getLevel().isClientSide()
				|| !(event.getEntityBeingMounted() instanceof ServerPlayer carrier)) {
			return;
		}

		Entity dismountingEntity = event.getEntityMounting();
		carrier.getServer().tell(new TickTask(carrier.getServer().getTickCount() + 1, () -> {
			// Another listener may cancel the dismount. Re-read the completed
			// relationship instead of assuming the mount event succeeded.
			boolean stillCarrying = dismountingEntity.getVehicle() == carrier
					|| !carrier.getPassengers().isEmpty();
			PlayerAnimationUtil.setArmsRaised(carrier,
					PlayerAnimationUtil.ArmsRaisedSource.CARRYING_MOB, stillCarrying);
			carrier.serverLevel().getChunkSource().broadcastAndSend(carrier,
					new ClientboundSetPassengersPacket(carrier));
		}));
	}

	// Prevents attacking when a mob is held
	@SubscribeEvent
	public static void onPlayerAttack(AttackEntityEvent event) {
		Player player = event.getEntity();
		if (!player.getPassengers().isEmpty()) {
			event.setCanceled(true);
		}
	}

	// Prevents item interaction when a mob is held
	@SubscribeEvent
	public static void onPlayerInteractItem(PlayerInteractEvent.RightClickItem event) {
		Player player = event.getEntity();
		if (!player.getPassengers().isEmpty()) {
			event.setCanceled(true);
		}
	}

	// Makes the player fall slowly if holding a chicken
	@SubscribeEvent
	public static void onLivingTick(LivingEvent.LivingTickEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			// Carried mobs ride the player as passengers. Keep the reusable pose tied
			// directly to that authoritative state so every pickup and release path
			// (right-click, damage, death, or losing the required gauntlet) is covered.
			if (!player.level().isClientSide) {
				PlayerAnimationUtil.setArmsRaised(player, PlayerAnimationUtil.ArmsRaisedSource.CARRYING_MOB,
						!player.getPassengers().isEmpty());
			}

			boolean hasChickenPassenger = player.getPassengers().stream().anyMatch(Predicates.instanceOf(Chicken.class));
			if (hasChickenPassenger) {
				player.fallDistance = 0F;
				// slows fall speed
				if (player.getDeltaMovement().y < -0.1) {
					Vec3 movement = player.getDeltaMovement();
					player.setDeltaMovement(new Vec3(movement.x, -0.08, movement.z));
				}
			}
		}
	}

	// Removes the player's first-person hand when holding a mob
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		if (player != null && !player.getPassengers().isEmpty()) {
			event.setCanceled(true);
		}
	}

	// Makes the entity dismount the player if it takes damage
	@SubscribeEvent
	public static void onEntityHurt(LivingHurtEvent event) {
		Entity entity = event.getEntity();
		if (entity.getVehicle() instanceof ServerPlayer player) {
			entity.stopRiding();
			player.serverLevel().getChunkSource().broadcastAndSend(player, new ClientboundSetPassengersPacket(player));
		}

		// Taking damage also makes a carrier release everything they picked up.
		// Copy the list because stopRiding mutates the player's passengers.
		if (entity instanceof ServerPlayer carrier && !carrier.getPassengers().isEmpty()) {
			for (Entity passenger : java.util.List.copyOf(carrier.getPassengers())) {
				passenger.stopRiding();
			}
			PlayerAnimationUtil.setArmsRaised(carrier,
					PlayerAnimationUtil.ArmsRaisedSource.CARRYING_MOB, false);
			carrier.serverLevel().getChunkSource().broadcastAndSend(carrier,
					new ClientboundSetPassengersPacket(carrier));
		}
	}
}
