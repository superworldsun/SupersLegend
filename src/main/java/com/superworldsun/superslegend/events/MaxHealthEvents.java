package com.superworldsun.superslegend.events;

import java.util.UUID;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.config.SupersLegendConfig;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaxHealthEvents
{
	public static final UUID BASE_HEALTH_MODIFIER_ID = UUID.fromString("6ed6de9f-a743-4bee-8e59-8a56d54bb054");
	
	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		// Only change health of players
		if (!(event.getEntity() instanceof PlayerEntity))
		{
			return;
		}
		
		PlayerEntity player = (PlayerEntity) event.getEntity();
		ModifiableAttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
		AttributeModifier baseHealthModifier = new AttributeModifier(BASE_HEALTH_MODIFIER_ID, "Base", SupersLegendConfig.getInstance().playerMaxHealth() - 20, Operation.ADDITION);
		
		// Add base modifier only if not added yet
		if (!maxHealth.hasModifier(baseHealthModifier))
		{
			maxHealth.addPermanentModifier(baseHealthModifier);
		}
		// Or if config updated and value changed
		else if (maxHealth.getModifier(BASE_HEALTH_MODIFIER_ID).getAmount() != baseHealthModifier.getAmount())
		{
			// Remove old instance and apply new one
			maxHealth.removeModifier(BASE_HEALTH_MODIFIER_ID);
			maxHealth.addPermanentModifier(baseHealthModifier);
		}
		
		// Fixing health being higher then max health
		if (player.getHealth() > player.getMaxHealth())
		{
			player.setHealth(player.getMaxHealth());
		}
	}
}
