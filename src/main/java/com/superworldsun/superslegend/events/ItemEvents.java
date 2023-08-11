package com.superworldsun.superslegend.events;

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.util.concurrent.AtomicDouble;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.api.IncomingDamageModifier;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class ItemEvents {
	/**
	 * Checks if damage modifiers from all instances of {@link IncomingDamageModifier}<br>
	 * reduce the damage to zero or below and cancels the damage
	 */
	@SubscribeEvent
	public static void cancelDamageTaken(LivingAttackEvent event) {
		float damage = getModifiedDamage(event.getEntity(), event.getSource(), event.getAmount());
		if (damage <= 0) event.setCanceled(true);
	}

	/**
	 * Applies damage modifiers from all instances of {@link IncomingDamageModifier}
	 */
	@SubscribeEvent
	public static void applyDamageReductionBonus(LivingHurtEvent event) {
		float damage = getModifiedDamage(event.getEntity(), event.getSource(), event.getAmount());
		event.setAmount(damage);
	}

	private static float getModifiedDamage(LivingEntity entity, DamageSource damage, float originalAmount) {
		AtomicDouble amount = new AtomicDouble(originalAmount);
		getDamageModifiers(entity).forEach(modifier -> {
			if (modifier.canModifyDamage(damage)) {
				amount.set(amount.get() * (1 + modifier.getDamageModifier()));
			}
		});
		return (float) amount.get();
	}

	private static Stream<IncomingDamageModifier> getDamageModifiers(LivingEntity entity) {
		Predicate<ItemStack> modifiesDamage = stack -> stack.getItem() instanceof IncomingDamageModifier;
		Stream<ItemStack> curios = CuriosApi.getCuriosHelper().findCurios(entity, modifiesDamage).stream().map(SlotResult::stack);
		Stream<ItemStack> armor = StreamSupport.stream(entity.getArmorSlots().spliterator(), false).filter(modifiesDamage);
		return Stream.concat(curios, armor).map(ItemStack::getItem).map(IncomingDamageModifier.class::cast);
	}
}
