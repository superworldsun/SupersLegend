package com.superworldsun.superslegend.items.curios.hands;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.google.common.base.Predicates;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.HandItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class StrengthHandItem extends HandItem {
	private static final Map<EntityType<?>, Integer> ENTITY_WEIGHTS = new HashMap<>();
	protected final int strength;

	public StrengthHandItem(Properties properties, int strength) {
		super(properties);
		this.strength = strength;
	}

	public boolean canPickUpEntity(EntityType<?> type) {
		return getMobWeight(type) <= strength;
	}

	public boolean canPickUpEntity(Entity entity) {
		return canPickUpEntity(entity.getType());
	}

	@SubscribeEvent
	public static void pickupMobIfPossible(PlayerInteractEvent.EntityInteract event) {
		PlayerEntity player = event.getPlayer();
		Entity target = event.getTarget();
		int mobWeight = getMobWeight(target);
		ItemStack strengthHandStack = getStrengthHandStack(player);
		boolean canPickUp = mobWeight == 0;
		if (!strengthHandStack.isEmpty()) {
			StrengthHandItem strengthHand = (StrengthHandItem) strengthHandStack.getItem();
			canPickUp = canPickUp || strengthHand.canPickUpEntity(target.getType());
		}
		if (player.getPassengers().isEmpty() && player.isCrouching() && canPickUp) {
			LivingEntity rider = (LivingEntity) target;
			if (!rider.isVehicle()) {
				rider.startRiding(player, true);
			}
		} else if (!player.getPassengers().isEmpty() && !player.isCrouching()) {
			LivingEntity rider = (LivingEntity) target;
			rider.stopRiding();
		}
	}

	@SubscribeEvent
	public static void dropMobIfCantHold(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (player.getPassengers().isEmpty()) {
			return;
		}
		ItemStack strengthHandStack = getStrengthHandStack(player);
		if (strengthHandStack.isEmpty()) {
			player.getPassengers().stream().filter(entity -> getMobWeight(entity) != 0).forEach(Entity::stopRiding);
		} else {
			StrengthHandItem strengthHand = (StrengthHandItem) strengthHandStack.getItem();
			player.getPassengers().stream().filter(Predicates.not(strengthHand::canPickUpEntity)).forEach(Entity::stopRiding);
		}
	}

	private static int getMobWeight(EntityType<?> type) {
		return ENTITY_WEIGHTS.getOrDefault(type, -1);
	}

	private static int getMobWeight(Entity entity) {
		return getMobWeight(entity.getType());
	}

	private static ItemStack getStrengthHandStack(PlayerEntity player) {
		return CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof StrengthHandItem, player).map(ImmutableTriple::getRight)
				.orElse(ItemStack.EMPTY);
	}

	static {
		ENTITY_WEIGHTS.put(EntityType.VEX, 0);
		ENTITY_WEIGHTS.put(EntityType.SILVERFISH, 0);
		ENTITY_WEIGHTS.put(EntityType.ENDERMITE, 0);
		ENTITY_WEIGHTS.put(EntityType.BAT, 0);
		ENTITY_WEIGHTS.put(EntityType.COD, 0);
		ENTITY_WEIGHTS.put(EntityType.TROPICAL_FISH, 0);
		ENTITY_WEIGHTS.put(EntityType.PUFFERFISH, 0);
		ENTITY_WEIGHTS.put(EntityType.SALMON, 0);
		ENTITY_WEIGHTS.put(EntityType.BEE, 0);
		ENTITY_WEIGHTS.put(EntityType.CHICKEN, 0);
		ENTITY_WEIGHTS.put(EntityType.PARROT, 0);
		ENTITY_WEIGHTS.put(EntityType.RABBIT, 0);
		ENTITY_WEIGHTS.put(EntityType.CAT, 0);
		ENTITY_WEIGHTS.put(EntityType.OCELOT, 0);
		ENTITY_WEIGHTS.put(EntityType.VEX, 0);
		ENTITY_WEIGHTS.put(EntityType.FOX, 0);
		ENTITY_WEIGHTS.put(EntityType.SNOW_GOLEM, 0);
		ENTITY_WEIGHTS.put(EntityType.PHANTOM, 0);
		ENTITY_WEIGHTS.put(EntityType.CAVE_SPIDER, 1);
		ENTITY_WEIGHTS.put(EntityType.SLIME, 1);
		ENTITY_WEIGHTS.put(EntityType.MAGMA_CUBE, 1);
		ENTITY_WEIGHTS.put(EntityType.ENDERMAN, 1);
		ENTITY_WEIGHTS.put(EntityType.SQUID, 1);
		ENTITY_WEIGHTS.put(EntityType.WOLF, 1);
		ENTITY_WEIGHTS.put(EntityType.SPIDER, 1);
		ENTITY_WEIGHTS.put(EntityType.BLAZE, 1);
		ENTITY_WEIGHTS.put(EntityType.DOLPHIN, 1);
		ENTITY_WEIGHTS.put(EntityType.STRAY, 1);
		ENTITY_WEIGHTS.put(EntityType.ZOMBIE_VILLAGER, 1);
		ENTITY_WEIGHTS.put(EntityType.WANDERING_TRADER, 1);
		ENTITY_WEIGHTS.put(EntityType.VILLAGER, 1);
		ENTITY_WEIGHTS.put(EntityType.CREEPER, 1);
		ENTITY_WEIGHTS.put(EntityType.WITCH, 1);
		ENTITY_WEIGHTS.put(EntityType.ZOMBIE, 1);
		ENTITY_WEIGHTS.put(EntityType.PLAYER, 2);
		ENTITY_WEIGHTS.put(EntityType.EVOKER, 2);
		ENTITY_WEIGHTS.put(EntityType.PILLAGER, 2);
		ENTITY_WEIGHTS.put(EntityType.VINDICATOR, 2);
		ENTITY_WEIGHTS.put(EntityType.HUSK, 2);
		ENTITY_WEIGHTS.put(EntityType.SKELETON_HORSE, 2);
		ENTITY_WEIGHTS.put(EntityType.ZOMBIFIED_PIGLIN, 2);
		ENTITY_WEIGHTS.put(EntityType.GUARDIAN, 2);
		ENTITY_WEIGHTS.put(EntityType.SHEEP, 2);
		ENTITY_WEIGHTS.put(EntityType.PANDA, 2);
		ENTITY_WEIGHTS.put(EntityType.PIGLIN, 2);
		ENTITY_WEIGHTS.put(EntityType.SHULKER, 2);
		ENTITY_WEIGHTS.put(EntityType.TURTLE, 2);
		ENTITY_WEIGHTS.put(EntityType.WITHER_SKELETON, 2);
		ENTITY_WEIGHTS.put(EntityType.LLAMA, 2);
		ENTITY_WEIGHTS.put(EntityType.TRADER_LLAMA, 2);
		ENTITY_WEIGHTS.put(EntityType.DONKEY, 2);
	}
}
