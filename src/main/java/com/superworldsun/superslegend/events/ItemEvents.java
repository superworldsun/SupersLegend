package com.superworldsun.superslegend.events;

import com.google.common.util.concurrent.AtomicDouble;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.api.IncomingDamageModifier;
import com.superworldsun.superslegend.api.SetIncomingDamageModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

	private static float getModifiedDamage(LivingEntity entity, DamageSource source, float amount) {
		AtomicDouble result = new AtomicDouble(amount);
		getDamageModifiers(entity).forEach(modifier ->
				result.set(modifier.modifyIncomingDamage(source, (float) result.get()))
		);
		getSetDamageModifiers(entity).forEach(modifier ->
				result.set(modifier.modifyIncomingDamage(source, (float) result.get(), getArmorPiecesCount(entity, modifier))));
		return (float) result.get();
	}

	private static int getArmorPiecesCount(LivingEntity entity, SetIncomingDamageModifier modifier) {
		return (int) StreamSupport.stream(entity.getArmorSlots().spliterator(), false)
				.map(ItemStack::getItem)
				.filter(ArmorItem.class::isInstance)
				.map(ArmorItem.class::cast)
				.map(ArmorItem::getMaterial)
				.filter(modifier::equals)
				.count();
	}

	private static Stream<IncomingDamageModifier> getDamageModifiers(LivingEntity entity) {
		Stream<IncomingDamageModifier> curios = CuriosApi.getCuriosHelper()
				.findCurios(entity, s -> s.getItem() instanceof IncomingDamageModifier)
				.stream()
				.map(SlotResult::stack)
				.map(ItemStack::getItem)
				.map(IncomingDamageModifier.class::cast);
		Stream<IncomingDamageModifier> armor = StreamSupport.stream(entity.getArmorSlots().spliterator(), false)
				.map(ItemStack::getItem)
				.filter(IncomingDamageModifier.class::isInstance)
				.map(IncomingDamageModifier.class::cast);
		return Stream.concat(curios, armor);
	}

	private static Stream<SetIncomingDamageModifier> getSetDamageModifiers(LivingEntity entity) {
		return StreamSupport.stream(entity.getArmorSlots().spliterator(), false)
				.map(ItemStack::getItem)
				.filter(ArmorItem.class::isInstance)
				.map(ArmorItem.class::cast)
				.map(ArmorItem::getMaterial)
				.distinct()
				.filter(SetIncomingDamageModifier.class::isInstance)
				.map(SetIncomingDamageModifier.class::cast);
	}
}
