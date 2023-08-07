package com.superworldsun.superslegend.events;

import java.util.UUID;

import com.superworldsun.superslegend.Config;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.item.HeartContainer;
import com.superworldsun.superslegend.items.item.VoidContainer;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class PlayerHealthEvents {
	public static final UUID BASE_HEALTH_MODIFIER = UUID.fromString("6ed6de9f-a743-4bee-8e59-8a56d54bb054");
	public static final UUID HEARTS_MODIFIER = UUID.fromString("3dc4214d-14eb-455c-9700-a2ab1433dfcc");

	/**
	 * Adjusts player base health to the value of {@link Config#basePlayerHealth}
	 */
	@SubscribeEvent
	public static void adjustBaseHealth(EntityJoinLevelEvent event) {
		// Only change health of players
		if (!(event.getEntity() instanceof Player player)) return;
		AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
		AttributeModifier baseModifier = new AttributeModifier(BASE_HEALTH_MODIFIER, "Base", Config.basePlayerHealth - 20, Operation.ADDITION);
		// Add base modifier only if not added yet
		if (!maxHealth.hasModifier(baseModifier)) {
			maxHealth.addPermanentModifier(baseModifier);
		}
		// Or if config updated and value changed
		else if (maxHealth.getModifier(BASE_HEALTH_MODIFIER).getAmount() != baseModifier.getAmount()) {
			// Remove old instance and apply new one
			maxHealth.removeModifier(BASE_HEALTH_MODIFIER);
			maxHealth.addPermanentModifier(baseModifier);
		}
		// Fixing health being higher then max health
		if (player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
	}

	/**
	 * When player dies, his permanent health modifiers from {@link HeartContainer} and {@link VoidContainer} will persist through death
	 */
	@SubscribeEvent
	public static void reapplyHealthModifiers(PlayerEvent.Clone event) {
		if (!event.isWasDeath()) return;
		AttributeModifier modifier = event.getOriginal().getAttribute(Attributes.MAX_HEALTH).getModifier(HEARTS_MODIFIER);
		if (modifier != null) {
			event.getEntity().getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(modifier);
			// Also updates current health
			event.getEntity().setHealth(event.getEntity().getMaxHealth());
		}
	}

	/**
	 * @return Player's maximum health affected by modifiers from:<br>
	 *         {@link HeartContainer}, {@link VoidContainer} and {@link Config#basePlayerHealth}
	 */
	public static double getBaseHealth(Player player) {
		AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
		AttributeModifier heartsModifier = maxHealth.getModifier(PlayerHealthEvents.HEARTS_MODIFIER);
		AttributeModifier baseModifier = maxHealth.getModifier(PlayerHealthEvents.BASE_HEALTH_MODIFIER);
		return maxHealth.getBaseValue() + baseModifier.getAmount() + heartsModifier.getAmount();
	}

	/**
	 * Applies maximum health bonus, should only be used for {@link HeartContainer} and {@link VoidContainer}<br>
	 */
	public static void addBaseHealthModifier(Player player, float amount) {
		AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
		AttributeModifier modifier = maxHealth.getModifier(PlayerHealthEvents.HEARTS_MODIFIER);
		if (modifier == null) {
			modifier = new AttributeModifier(PlayerHealthEvents.HEARTS_MODIFIER, "Hearts", amount, Operation.ADDITION);
		} else {
			maxHealth.removeModifier(modifier);
			modifier = new AttributeModifier(PlayerHealthEvents.HEARTS_MODIFIER, "Hearts", modifier.getAmount() + amount, Operation.ADDITION);
		}
		maxHealth.addPermanentModifier(modifier);
		if (amount > 0) {
			player.heal(amount);
		} else if (player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
	}

	public static boolean canIncreaseBaseHealth(Player player) {
		return getBaseHealth(player) < 40;
	}

	public static boolean canDecreaseBaseHealth(Player player) {
		return getBaseHealth(player) > 1;
	}
}
