package com.superworldsun.superslegend.events;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.api.IncomingDamageModifier;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class ItemEvents {
	/**
	 * Applies damage modifiers from all instances of {@link IncomingDamageModifier}
	 */
	@SubscribeEvent
	public static void applyDamageReductionBonus(LivingHurtEvent event) {
		CuriosApi.getCuriosHelper().findCurios(event.getEntity(), stack -> stack.getItem() instanceof IncomingDamageModifier).forEach(slot -> {
			IncomingDamageModifier item = (IncomingDamageModifier) slot.stack().getItem();
			if (item.canModifyDamage(event.getSource())) {
				event.setAmount(event.getAmount() * (1 + item.getDamageModifier()));
			}
		});
		event.getEntity().getArmorSlots().forEach(stack -> {
			if (stack.getItem() instanceof IncomingDamageModifier) {
				IncomingDamageModifier item = (IncomingDamageModifier) stack.getItem();
				if (item.canModifyDamage(event.getSource())) {
					event.setAmount(event.getAmount() * (1 + item.getDamageModifier()));
				}
			}
		});
		if (event.getAmount() <= 0) event.setCanceled(true);
	}
}
